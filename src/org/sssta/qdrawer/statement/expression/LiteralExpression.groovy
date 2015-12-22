package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.BooleanNode
import org.sssta.qdrawer.ast.node.ExpressionNode
import org.sssta.qdrawer.ast.node.NumericNode
import org.sssta.qdrawer.ast.node.StringNode
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType

/**
 * Created by cauchywei on 15/11/30.
 */
class LiteralExpression extends Expression {
    Token token

    @Override
    public String toString() {
        token.value;
    }

    @Override
    ExpressionNode createAstNode() {
        switch (token.type) {
            case TokenType.STRING:
                return new StringNode(token.value.substring(1,token.value.length()-1))
            case TokenType.NUMBERIC:
                return new NumericNode(Double.valueOf(token.value))
            case TokenType.TRUE:
            case TokenType.FALSE:
                return new BooleanNode(Boolean.valueOf(token.value))
            default:
                throw new IllegalArgumentException(token.type.identifier + "is not a LiteralExpress")
        }

    }
}
