package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer

/**
 * Created by cauchywei on 15/10/19.
 */
class BinaryExpression extends Expression {



    static BinaryExpression parse(Laxer laxer, List<CodeError> errors) {
        BinaryExpression expression = new BinaryExpression();


        return expression;
    }
}