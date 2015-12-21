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
class PowerNode extends PrimitiveNode {

    Node base
    Node exponent

    PowerNode() {
        super('**', 2)
    }

    @Override
    Value eval(Scope scope) {

        def leftV = base.eval(scope)

        if (!(leftV instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(base,'illegal type:require Numeric but found ' + leftV.type))
            return null
        }

        def rightV = exponent.eval(scope)

        if (!(rightV instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(exponent,'illegal type:require Numeric but found ' + rightV.type))
            return null
        }

        return new NumericValue(Math.pow(leftV.asType(NumericValue).value,rightV.asType(NumericValue).value))
    }

    @Override
    Type checkType(Scope scope) {

        def leftType = base.checkType(scope)

        if (!(leftType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(base,'illegal type:require Numeric but found ' + leftType))
        }

        def rightType = exponent.checkType(scope)

        if (!(rightType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(exponent,'illegal type:require Numeric but found ' + rightType))
        }
        return Type.NUMERIC
    }
}
