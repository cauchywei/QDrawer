package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.PointValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError
/**
 * Created by cauchywei on 15/12/13.
 */
class PointNode extends ExpressionNode{

    ExpressionNode x,y

    @Override
    Value eval(Scope scope) {

        def vx = x.eval(scope)

        if (!(vx instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(x,'illegal type:require Numeric but found ' + vx.type))
            return null
        }

        def vy = y.eval(scope)

        if (!(vy instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(y,'illegal type:require Numeric but found ' + vy.type))
            return null
        }

        return new PointValue(vx.asType(NumericValue),vy.asType(NumericValue))
    }

    @Override
    Type checkType(Scope scope) {

        def xType = x.checkType(scope)
        if (!(xType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(x,'illegal type:require Numeric but found ' + xType))
            return null
        }

        def yType = y.checkType(scope)
        if (!(yType instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(y,'illegal type:require Numeric but found ' + yType))
            return null
        }

        return Type.POINT
    }
}
