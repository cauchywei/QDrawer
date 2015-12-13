package org.sssta.qdrawer.ast.node.primitives

import org.sssta.qdrawer.ast.node.ExpressionNode

/**
 * Created by cauchywei on 15/12/13.
 */
abstract class PrimitiveNode extends ExpressionNode {
    String opt
    int arity

    PrimitiveNode(String opt, int arity) {
        this.opt = opt
        this.arity = arity
    }
}
