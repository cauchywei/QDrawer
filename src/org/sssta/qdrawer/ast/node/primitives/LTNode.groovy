package org.sssta.qdrawer.ast.node.primitives

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError

/**
 * Created by cauchywei on 15/12/13.
 */
class LTNode extends PrimitiveNode {

    Node left
    Node right

    LTNode() {
        super('<', 2)
    }

    @Override
    Value eval(Scope scope) {

        def leftV = left.eval(scope)

        //TODO add more type plus opt

        if (!(leftV instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(left,'illegal type:require Numeric but found ' + leftV.type))
            return null
        }

        def rightV = right.eval(scope)

        if (!(rightV instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(right,'illegal type:require Numeric but found ' + rightV.type))
            return null
        }

        return new BooleanValue(leftV.asType(NumericValue).value < rightV.asType(NumericValue).value)
    }

    @Override
    Type checkType(Scope scope) {

        def leftType = left.checkType(scope)

        //TODO add more type plus opt

        if (!(leftType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(left,'illegal type:require Numeric but found ' + leftType))
        }

        def rightType = right.checkType(scope)

        if (!(rightType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(right,'illegal type:require Numeric but found ' + rightType))
        }
        return Type.BOOLEAN
    }
}
