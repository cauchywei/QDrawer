package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class FunctionValue extends Value {
    @Override
    Type getType() {
        return Type.FUNCTION
    }
}
