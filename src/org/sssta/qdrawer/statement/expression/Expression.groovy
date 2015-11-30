package org.sssta.qdrawer.statement.expression
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/14.
 */
abstract class Expression {

    boolean isConst = false;

    static Expression parse(Laxer laxer, List<CodeError> errors) {
        return  parseExpression(laxer,errors);
    }

    static def parseExpression = { Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.OR],parseAndExpression,errors);
    };

    static def parseAndExpression = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.AND],parseCompareExpression,errors);
    }

    static def parseCompareExpression = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.LT,BinaryOperator.LTE,BinaryOperator.GT,BinaryOperator.GTE,BinaryOperator.EQ,BinaryOperator.NE],parseBaseTerm,errors);
    }

    static def parseBaseTerm = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.PLUS,BinaryOperator.MINUS],parseTerm,errors);
    }

    static def parseTerm = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.MUL,BinaryOperator.DIV,BinaryOperator.MOD],parseFactor,errors);
    }

    static def parseFactor = {Laxer laxer, List<CodeError> errors ->
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
            return Expression.parseComponent(laxer,errors)
        }
    }

    static def parseComponent = {Laxer laxer, List<CodeError> errors ->

        def atomExpr = Expression.parseAtom(laxer ,errors)
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

    static def parseAtom = {Laxer laxer, List<CodeError> errors ->
        def next = laxer.takeToken()

        if (next?.isIdentifier()) {

            def id = new VariableExpression(identifier: id)
            if (laxer.peekToken()?.type != TokenType.OPEN_BRACKET) {
                return id
            } else {

                def func = new InvokeExpression(function: id)
                def args = []
                func.arguments = args
                laxer.takeToken()
                def arg = Expression.parseExpression(laxer, errors)
                if (arg != null) {
                    args << arg
                    while (laxer.peekToken()?.type == TokenType.COMMA) {
                        laxer.takeToken()
                        arg = Expression.parseExpression(laxer,errors)
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

            def expr = Expression.parseExpression(laxer,errors)

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

            if (binaryOperators.any({ optToken?.value == it.toString() })) {
                def opt = laxer.takeToken()
                BinaryExpression binaryExpression = new BinaryExpression()
                binaryExpression.opt = BinaryOperator.valueOf(opt.value)

                binaryExpression.left = left

                def right = subExprFunc(laxer, errors)

                if (right == null) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal right expression.")
                }

                binaryExpression.right = right

                return binaryExpression
            } else {
                return left
            }

        } else {
            errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal expression.")
            return null
        }
    }
}