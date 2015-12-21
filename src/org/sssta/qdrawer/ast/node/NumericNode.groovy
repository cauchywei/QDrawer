package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.Value

/**
 * Created by cauchywei on 15/12/13.
 */
class NumericNode extends ExpressionNode{

    double number

    @Override
    Value eval(Scope scope) {
        return new NumericValue(number)
    }

    @Override
    Type checkType(Scope scope) {
        return Type.NUMERIC
    }
}
