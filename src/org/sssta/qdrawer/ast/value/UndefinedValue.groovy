package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/14.
 */
class UndefinedValue extends Value<Object> {
    @Override
    Type getType() {
        return Type.UNDEFINED
    }

    @Override
    Object getJavaValue() {
        return null
    }

    @Override
    boolean equals(Object obj) {
        if (obj instanceof UndefinedValue) {
            return true
        }
        return super.equals(obj)
    }
}
