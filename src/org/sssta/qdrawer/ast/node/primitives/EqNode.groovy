package org.sssta.qdrawer.ast.node.primitives

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.type.BooleanType
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError
/**
 * Created by cauchywei on 15/12/13.
 */
class EqNode extends PrimitiveNode {

    Node left
    Node right

    EqNode() {
        super('==', 2)
    }

    @Override
    Value eval(Scope scope) {

        def leftV = left.eval(scope)
        def rightV = right.eval(scope)

        if (leftV instanceof NumericValue && rightV instanceof NumericValue) {
            return new BooleanValue(Double.compare(leftV.asType(NumericValue).value,rightV.asType(NumericValue).value) == 0)
        }else if (leftV instanceof BooleanValue && rightV instanceof BooleanValue) {
            return new BooleanValue(leftV.asType(BooleanValue).value == rightV.asType(BooleanValue).value)
        } else {
            Console.addError(new IllegalTypeError(this, 'unsupported comparing between ' + leftV.type + ' and '+ rightV.type + 'two types'))
            return null
        }
    }

    @Override
    Type checkType(Scope scope) {

        def leftType = left.checkType(scope)
        def rightType = right.checkType(scope)

        if (!(leftType instanceof NumericValue && rightType instanceof NumericValue
        || leftType instanceof BooleanType && rightType instanceof BooleanType)) {
            Console.addError(new IllegalTypeError(this, 'unsupported comparing between ' + leftType + ' and '+ leftType + 'two types'))
        }

        return Type.BOOLEAN
    }
}
