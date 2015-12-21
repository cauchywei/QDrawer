package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.StringValue
import org.sssta.qdrawer.ast.value.Value

/**
 * Created by cauchywei on 15/12/13.
 */
class StringNode extends ExpressionNode{

    String stringValue

    StringNode(String stringValue) {
        this.stringValue = stringValue
    }

    @Override
    Value eval(Scope scope) {
        return new StringValue(stringValue)
    }

    @Override
    Type checkType(Scope scope) {
        return Type.STRING
    }
}
