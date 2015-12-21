package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.node.ExpressionNode
import org.sssta.qdrawer.statement.expression.Expression

/**
 * Created by cauchywei on 15/12/12.
 */
class ExpressionStatement extends Statement{
    Expression expression

    ExpressionStatement(Expression expression) {
        this.expression = expression
    }

    @Override
    ExpressionNode createAstNode() {
        return expression.createAstNode()
    }
}
