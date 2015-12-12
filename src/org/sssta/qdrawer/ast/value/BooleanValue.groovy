package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class BooleanValue extends Value {

    static final Boolean TURE = new BooleanValue(true)
    static final Boolean FALSE = new BooleanValue(false)


    boolean value

    BooleanValue(boolean aBoolean) {
        this.value = aBoolean
    }

    @Override
    Type getType() {
        Type.BOOLEAN
    }


    @Override
    public String toString() {
        value?'true':'false'
    }
}
