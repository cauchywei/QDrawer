package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class StringValue extends Value {

    String value

    StringValue(String value) {
        this.value = value
    }

    @Override
    Type getType() {
        Type.STRING
    }


    @Override
    public String toString() {
        value
    }
}
