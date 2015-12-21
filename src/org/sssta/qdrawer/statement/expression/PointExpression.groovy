package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.ExpressionNode
import org.sssta.qdrawer.ast.node.PointNode

/**
 * Created by cauchywei on 15/11/30.
 */
class PointExpression extends Expression {
    Expression x, y

    @Override
    public String toString() {
        '(' + x + ', ' + y +')';
    }

    @Override
    ExpressionNode createAstNode() {
        return new PointNode(x: x.createAstNode(),y: y.createAstNode())
    }
}
