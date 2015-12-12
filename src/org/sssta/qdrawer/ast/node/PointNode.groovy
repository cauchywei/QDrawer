package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.NumericType
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.PointValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.lexer.CodeError

/**
 * Created by cauchywei on 15/12/13.
 */
class PointNode extends Node{

    Node x,y

    @Override
    Value eval(Scope scope) {

        def vx = x.eval(scope)

        if (!(vx instanceof NumericValue)) {
            Console.errors << new CodeError(x,'illegal type:require Numeric but found ' + vx.type)
        }

        def vy = y.eval(scope)

        if (!(vy instanceof NumericValue)) {
            Console.errors << new CodeError(x,'illegal type:require Numeric but found ' + vy.type)
        }

        return new PointValue(vx.asType(NumericValue),vy.asType(NumericValue))
    }

    @Override
    Type checkType(Scope scope) {

        def xType = x.checkType(scope)
        if (!(xType instanceof NumericType)) {
            Console.errors << new CodeError(x,'illegal type:require Numeric but found ' + xType)
            return null
        }

        def yType = y.checkType(scope)
        if (!(yType instanceof NumericType)) {
            Console.errors << new CodeError(x,'illegal type:require Numeric but found ' + yType)
            return null
        }

        return Type.POINT
    }
}
