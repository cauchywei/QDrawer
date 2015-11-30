package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.lexer.Token

/**
 * Created by cauchywei on 15/11/30.
 */
class VariableExpression extends Expression {
    Token identifier

    @Override
    public String toString() {
       identifier.value;
    }
}
