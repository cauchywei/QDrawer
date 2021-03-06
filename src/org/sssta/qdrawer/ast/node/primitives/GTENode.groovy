package org.sssta.qdrawer.ast.node.primitives

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.type.NumericType
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError

/**
 * Created by cauchywei on 15/12/13.
 */
class GTENode extends PrimitiveNode {

    Node left
    Node right

    GTENode() {
        super('>=', 2)
    }

    @Override
    Value eval(Scope scope) {

        def leftV = left.eval(scope)

        if (!(leftV instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(left,'illegal type:require Numeric but found ' + leftV.type))
            return null
        }


        def rightV = right.eval(scope)

        if (!(rightV instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(right,'illegal type:require Numeric but found ' + rightV.type))
            return null
        }

        return new BooleanValue(leftV.value >= rightV.value)
    }

    @Override
    Type checkType(Scope scope) {

        def leftType = left.checkType(scope)

        if (!(leftType instanceof NumericType)) {
            Console.addError(new IllegalTypeError(left,'illegal type:require Number but found ' + leftType))
        }

        def rightType = right.checkType(scope)

        if (!(rightType instanceof NumericType)) {
            Console.addError(new IllegalTypeError(right,'illegal type:require Number but found ' + rightType))
        }
        return Type.BOOLEAN
    }
}
