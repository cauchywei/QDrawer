package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.SymbolInfo
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.node.Variable
import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class FunctionValue extends Value {

    List<Variable> params
    List<Node> body
    Variable name
    Scope parentScope

    @Override
    Type getType() {
        return Type.FUNCTION
    }

    @Override
    Object getJavaValue() {
        return null
    }

    Value eval(List<Node> args) {
        //put our params to our local scope
        def scope = new Scope(parentScope)
        args.eachWithIndex { Node arg, int i -> scope.putSymbol(params[i].name.value,new SymbolInfo(type: arg.checkType(scope),value: arg.eval(scope))) }

        def lastValue = new VoidValue()
        for (int i = 0; i < body.size(); i++) {
            def node = body[i]
            lastValue = node.eval(scope)
            if (scope.returnFlag) {
                break
            }
        }

        return lastValue
    }

    Type checkType(List<Node> args) {
        return eval(args).type
    }
}
