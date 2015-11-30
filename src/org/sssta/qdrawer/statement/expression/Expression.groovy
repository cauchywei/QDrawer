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
        parseBinaryExpression(laxer,[BinaryOperator.LT,BinaryOperator.LTE,BinaryOperator.GT,BinaryOperator.GTE,BinaryOperator.EQ,],term,errors);
    }

    static def term = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.AND],term,errors);
    }

    static def Factor = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.AND],term,errors);
    }

    static def component = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.AND],term,errors);
    }

    static def atom = {Laxer laxer, List<CodeError> errors ->
        parseBinaryExpression(laxer,[BinaryOperator.AND],term,errors);
    }


    static Expression parseBinaryExpression(Laxer laxer, List<BinaryOperator> binaryOperators,def subExprFunc, List<CodeError> errors) {
        Expression expression = null
        Expression left = subExprFunc(laxer,errors)

        if (left != null) {

            def optToken = laxer.peekToken()

            if (binaryOperators.any({ optToken.value == it.toString() })) {
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

        } else {
            errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an illegal expression.")
            return null
        }
    }

}