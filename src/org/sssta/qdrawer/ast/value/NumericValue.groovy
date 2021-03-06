package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class NumericValue extends Value<Double> {
    double value

    NumericValue(double value) {
        this.value = value
    }

    @Override
    Type getType() {
        Type.NUMERIC
    }

    @Override
    Double getJavaValue() {
        return value
    }
}
