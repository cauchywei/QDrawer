package org.sssta.qdrawer.ast.value

import groovy.json.internal.NumberValue
import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class PointValue extends Value {
    NumberValue x,y

    PointValue(NumberValue x, NumberValue y) {
        this.x = x
        this.y = y
    }

    @Override
    Type getType() {
        Type.POINT
    }
}
