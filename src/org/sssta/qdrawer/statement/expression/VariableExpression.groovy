package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.VariableNode
import org.sssta.qdrawer.lexer.Token
/**
 * Created by cauchywei on 15/11/30.
 */
class VariableExpression extends Expression {
    Token identifier

    VariableExpression(Token identifier) {
        this.identifier = identifier
    }

    @Override
    public String toString() {
       identifier.value;
    }

    @Override
    VariableNode createAstNode() {
        return new VariableNode(identifier)
    }
}
