package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.ast.value.VoidValue
import org.sssta.qdrawer.exception.IllegalOperateError

/**
 * Created by cauchywei on 15/12/15.
 */
class ConstDeclarationNode extends Node {
    Variable variable
    Node value

    @Override
    Value eval(Scope scope) {

        if (scope.exist(variable.name.value)) {
            Console.addError(new IllegalOperateError(this,variable.name.value + ' is const, it already defined'))
            return variable.eval(scope)
        }

        def valueType = value.checkType(scope)

        if (valueType == null) {
            return null
        }
        scope.putType(variable.name.value, valueType)

        def val = value.eval(scope)
        scope.putValue(variable.name.value, val)

        return new VoidValue()
    }

    @Override
    Type checkType(Scope scope) {

        if (scope.exist(variable.name.value)) {
            Console.addError(new IllegalOperateError(this,variable.name.value + ' is const, it already defined'))
            return null
        }
        return Type.VOID
    }
}
