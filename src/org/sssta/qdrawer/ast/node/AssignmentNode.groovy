package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalOperateError
/**
 * Created by cauchywei on 15/12/13.
 */
class AssignmentNode extends Node {

    Variable variable
    Node value

    @Override
    Value eval(Scope scope) {

        def symbol = scope.getSymbol(variable.name.value)
        if (symbol != null && symbol.isConst) {
            Console.addError(new IllegalOperateError(this,variable.name.value + ' is const, it can\'t be modified'))
            return variable.eval(scope)
        }

        def valueType = value.checkType(scope)

        if (valueType == null) {
            return null
        }

        if (variable.checkType(scope) == Type.UNDEFINED) {
            scope.putType(variable.name.value, valueType)
        }

        def val = value.eval(scope)
        scope.putValue(variable.name.value, val)

        return val
    }

    @Override
    Type checkType(Scope scope) {
        return value.checkType(scope)
    }
}
