package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.SymbolInfo
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.ast.value.VoidValue
import org.sssta.qdrawer.exception.IllegalTypeError
/**
 * Created by cauchywei on 15/12/21.
 */
class ForNode extends Node {

    VariableNode var
    ExpressionNode from
    ExpressionNode to
    ExpressionNode step
    ScopeNode body

    @Override
    Value eval(Scope envr) {

        def fromVal = from.eval(envr)
        if (!(fromVal instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(from,'illegal type:require Numeric but found ' + fromVal?.type))
            return null
        }

        def toVal = to.eval(envr)
        if (!(toVal instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(to,'illegal type:require Numeric but found ' + toVal?.type))
            return null
        }

        def stepVal = step.eval(envr)
        if (!(stepVal instanceof NumericValue)) {
            Console.addError(new IllegalTypeError(step,'illegal type:require Numeric but found ' + stepVal?.type))
            return null
        }

        def loopVar = var.name.value
        def scope = new Scope(envr)
        scope.putSymbol(loopVar,new SymbolInfo(Type.NUMERIC,new NumericValue(fromVal.value)))

        def lastVal = new VoidValue()
        OUTER:for (double i = fromVal.value; fromVal.value<toVal.value?i < toVal.value:i>toVal.value; i+= stepVal.value) {
            scope.putValue(loopVar,new NumericValue(i))
            lastVal = body.eval(scope)
            if (scope.returnFlag) {
                return scope.returnValue
            }
        }

        return lastVal
    }

    @Override
    Type checkType(Scope scope) {
        return null
    }
}
