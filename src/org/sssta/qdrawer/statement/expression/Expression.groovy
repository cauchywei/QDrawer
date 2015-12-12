package org.sssta.qdrawer.statement.expression
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/14.
 */
abstract class Expression {


    static Expression parse(Laxer laxer, List<CodeError> errors) {

        int saveIndex = laxer.save()


        if (laxer.peekToken()?.type == TokenType.CONST || laxer.peekToken()?.isIdentifier()) {

            def token = laxer.takeToken()
            def assignmentExpression = new AssignmentExpression()

            if (token.type == TokenType.CONST) {
                assignmentExpression.isConst = true
                if (!laxer.peekToken()?.isIdentifier()) {
                    errors << new CodeError(col: laxer.col,row: laxer.row,message: 'Excepted a variable after const')
                    return null
                }

                token = laxer.takeToken()
            }

            assignmentExpression.variable = new VariableExpression(identifier: token)


            if (laxer.peekToken()?.type == TokenType.IS || laxer.peekToken()?.type == TokenType.ASSIGMENT) {
                laxer.takeToken()

                def expr = parse(laxer,[])
                if (expr != null) {
                    assignmentExpression.value = expr
                    return assignmentExpression
                }
            }
        }
        laxer.go(saveIndex)

        return  parseExpression(laxer,errors);
    }

    static parseExpression ( Laxer laxer, List<CodeError> errors) {
        parseBinaryExpression(laxer,[BinaryOperator.OR],Expression.&parseAndExpression,errors);
    }

    static parseAndExpression (Laxer laxer, List<CodeError> errors) {
        parseBinaryExpression(laxer,[BinaryOperator.AND],Expression.&parseCompareExpression,errors);
    }

    static parseCompareExpression (Laxer laxer, List<CodeError> errors) {
        parseBinaryExpression(laxer,[BinaryOperator.LT,BinaryOperator.LTE,BinaryOperator.GT,BinaryOperator.GTE,BinaryOperator.EQ,BinaryOperator.NE],Expression.&parseBaseTerm,errors);
    }

    static parseBaseTerm (Laxer laxer, List<CodeError> errors) {
        parseBinaryExpression(laxer,[BinaryOperator.PLUS,BinaryOperator.MINUS],Expression.&parseTerm,errors);
    }

    static parseTerm (Laxer laxer, List<CodeError> errors) {
        parseBinaryExpression(laxer,[BinaryOperator.MUL,BinaryOperator.DIV,BinaryOperator.MOD],Expression.&parseFactor,errors);
    }

    static parseFactor (Laxer laxer, List<CodeError> errors) {
        def token = laxer.peekToken()

        if (token?.type == TokenType.PLUS || token?.type == TokenType.MINUS || token?.type == TokenType.NOT) {
            token = laxer.takeToken()
            UnaryExpression unaryExpression = new UnaryExpression()
            unaryExpression.opt = UnaryOperator.valueOf(token.type.toString())
            def expr = parseFactor(laxer, errors)
            if (expr == null) {
//                errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal parseExpression after "+unaryExpression.opt.toString()+".")
                return null
            }
            unaryExpression.expression = expr
            return unaryExpression
        } else {
            return parseComponent(laxer,errors)
        }
    }

    static parseComponent (Laxer laxer, List<CodeError> errors ){

        def atomExpr = parseAtom(laxer ,errors)
        if (atomExpr == null) {
            return null
        }

        if (laxer.peekToken()?.type == TokenType.POWER) {
            laxer.takeToken()
            def expr = new PowerExpression()
            expr.base = atomExpr
            def exp = parseComponent(laxer,errors)
            if (exp == null) {
                return null
            }
            expr.exponent = exp
            return expr
        } else {
            return atomExpr
        }

    }

    static parseAtom (Laxer laxer, List<CodeError> errors) {
        def next = laxer.takeToken()

        if (next?.isIdentifier()) {

            def var = new VariableExpression(identifier: next)
            if (laxer.peekToken()?.type != TokenType.OPEN_BRACKET) {
                return var
            } else {

                def func = new InvokeExpression(function: var)
                def args = []
                func.arguments = args
                laxer.takeToken()
                def arg = parseExpression(laxer, errors)
                if (arg != null) {
                    args << arg
                    while (laxer.peekToken()?.type == TokenType.COMMA) {
                        laxer.takeToken()
                        arg = parseExpression(laxer,errors)
                        if (arg == null) {
                            errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an argument.")
                            return null
                        }
                        args << arg
                    }
                }

                if (laxer.peekToken()?.type != TokenType.CLOSE_BRACKET) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted ).")
                    return null
                }

                laxer.takeToken()
                return func

            }


        }else if (next?.isLiteral()){
            return new LiteralExpression(token: next)
        } else if (next?.type == TokenType.OPEN_BRACKET) {

            def expr = parseExpression(laxer,errors)

            if (laxer.peekToken()?.type != TokenType.CLOSE_BRACKET) {
                errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted ).")
                return null
            }

            laxer.takeToken()

            return expr
        } else {
            return null
        }

    }


    static Expression parseBinaryExpression(Laxer laxer, List<BinaryOperator> binaryOperators,def subExprFunc, List<CodeError> errors) {

        Expression left = subExprFunc(laxer,errors)

        if (left != null) {

            def optToken = laxer.peekToken()

            while( (binaryOperators.any({ optToken?.value == it.opt }))) {
                def opt = laxer.takeToken()
                BinaryExpression binaryExpression = new BinaryExpression()
                binaryExpression.opt = BinaryOperator.find({it.opt == opt.value})

                binaryExpression.left = left

                def right = subExprFunc(laxer, errors)

                if (right == null) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal right expression.")
                }

                binaryExpression.right = right
                left = binaryExpression

                optToken = laxer.peekToken()
            }
            return left
        } else {
            errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal expression.")
            return null
        }
    }
}