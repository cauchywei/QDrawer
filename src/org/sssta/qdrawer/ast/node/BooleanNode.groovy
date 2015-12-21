package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.Value

/**
 * Created by cauchywei on 15/12/13.
 */
class BooleanNode extends ExpressionNode{

    boolean bool

    BooleanNode(boolean bool) {
        this.bool = bool
    }

    @Override
    Value eval(Scope scope) {
        return new BooleanValue(bool)
    }

    @Override
    Type checkType(Scope scope) {
        return Type.BOOLEAN
    }
}
