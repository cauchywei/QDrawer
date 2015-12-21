package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.ExpressionNode
import org.sssta.qdrawer.ast.node.primitives.PowerNode

/**
 * Created by cauchywei on 15/11/30.
 */
class PowerExpression extends Expression {
    Expression exponent
    Expression base


    @Override
    public String toString() {
        '(pow ' + base + ' ' + exponent + ')'
    }

    @Override
    ExpressionNode createAstNode() {
        return new PowerNode(base: base.createAstNode(), exponent: exponent.createAstNode())
    }
}
