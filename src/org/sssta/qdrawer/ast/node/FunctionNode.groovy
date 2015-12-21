package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.SymbolInfo
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.FunctionValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalOperateError
/**
 * Created by cauchywei on 15/12/15.
 */
class FunctionNode extends Node {

    Variable funcName
    List<Variable> args
    List<Node> body

    @Override
    Value eval(Scope scope) {

        if (scope.exist(funcName.name.value)) {
            Console.addError(new IllegalOperateError(this,funcName.name.value + 'is a func, it already defined'))
        }

        def value = new FunctionValue(name: funcName, params: args, body: body, parentScope: scope)
        def funcSymbol = new SymbolInfo(value: value,type: Type.FUNCTION)
        scope.putSymbol(funcName.name.value,funcSymbol)

        return value
    }

    @Override
    Type checkType(Scope scope) {

        if (scope.exist(funcName.name.value)) {
            Console.addError(new IllegalOperateError(this,funcName.name.value + 'is a func, it already defined'))
        }
        return Type.FUNCTION
    }
}
