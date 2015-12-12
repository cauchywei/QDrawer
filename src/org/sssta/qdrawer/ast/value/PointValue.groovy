package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class PointValue extends Value {
    NumericValue x,y

    PointValue(NumericValue x, NumericValue y) {
        this.x = x
        this.y = y
    }

    @Override
    Type getType() {
        Type.POINT
    }
}
