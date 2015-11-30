package org.sssta.qdrawer.statement.expression
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/14.
 */
abstract class Expression {

    boolean isConst = false;

    static Expression parse(Laxer laxer, List<CodeError> errors) {



        return  expression;
    }



    static def orExpr = { Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.OR],andExpr,errors);
    };

    static def andExpr = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.AND],compareExpr,errors);
    }

    static def compareExpr = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.LT,BinaryOperator.LTE,BinaryOperator.GT,BinaryOperator.GTE,BinaryOperator.EQ,BinaryOperator.NE],baseTerm,errors);
    }

    static def baseTerm = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.PLUS,BinaryOperator.MINUS],factor,errors);
    }

    static def term = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.MUL,BinaryOperator.DIV,BinaryOperator.MOD],factor,errors);
    }

    static def factor = {Laxer laxer, List<CodeError> errors ->
        def token = laxer.peekToken()

        if (token?.type == TokenType.PLUS || token?.type == TokenType.MINUS || token?.type == TokenType.NOT) {
            token = laxer.takeToken()
            UnaryExpression unaryExpression = new UnaryExpression()
            unaryExpression.opt = UnaryOperator.valueOf(token.type.toString())
            def expr = factor(laxer, errors)
            if (expr == null) {
//                errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal expression after "+unaryExpression.opt.toString()+".")
                return null
            }
            unaryExpression.expression = expr
            return unaryExpression
        } else {
            return component(laxer,errors)
        }
    }

    static def component = {Laxer laxer, List<CodeError> errors ->

        def atomExpr = atom(laxer ,errors)
        if (atomExpr == null) {
            return null
        }

        if (laxer.peekToken()?.type == TokenType.POWER) {
            laxer.takeToken()
            def expr = new PowerExpression()
            expr.base = atomExpr
            def exp = component(laxer,errors)
            if (exp == null) {
                return null
            }
            expr.exponent = exp
            return expr
        } else {
            return atomExpr
        }

    }

    static def atom = {Laxer laxer, List<CodeError> errors ->
        def next = laxer.takeToken()

        if (next?.isIdentifier()) {

        }else if (next.type == TokenType.OPEN_BRACKET) {

        } else {
            return null
        }

    }


    static Expression parseBinaryExpression(Laxer laxer, List<BinaryOperator> binaryOperators,def subExprFunc, List<CodeError> errors) {
        Expression expression = null
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

    static Expression parseUnaryExpression(Laxer laxer, List<UnaryOperator> unaryOperatorList,def subExprFunc, List<CodeError> errors) {
        Expression expression = null

            def optToken = laxer.peekToken()

            if (unaryOperatorList.any({ optToken?.value == it.toString() })) {
                def opt = laxer.takeToken()
                BinaryExpression binaryExpression = new BinaryExpression()
                binaryExpression.opt = BinaryOperator.valueOf(opt.value)

                binaryExpression.left = left

                def right = subExprFunc(laxer, errors)

                if (right == null) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal right expression.")
                }

                binaryExpression.right = right
            } else {
                return left
            }
    }

}