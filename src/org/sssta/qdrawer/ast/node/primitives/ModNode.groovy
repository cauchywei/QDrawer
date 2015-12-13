package org.sssta.qdrawer.ast.node.primitives

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError

/**
 * Created by cauchywei on 15/12/13.
 */
class ModNode extends PrimitiveNode {

    Node left
    Node right

    ModNode() {
        super('%', 2)
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

        if (rightV.asType(NumericValue).value.intValue() == 0) {
            Console.addError(new IllegalTypeError(right,'illegal type:require Numeric but found ' + rightV.type))
            return null
        }

        return new NumericValue(leftV.asType(NumericValue).value.intValue() % (int)rightV.asType(NumericValue).value.intValue())
    }

    @Override
    Type checkType(Scope scope) {

        def leftType = left.checkType(scope)

        if (!(leftType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(left,'illegal type:require Numeric but found ' + leftType))
        }

        def rightType = right.checkType(scope)

        if (!(rightType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(right,'illegal type:require Numeric but found ' + rightType))
        }
        return Type.NUMERIC
    }
}
