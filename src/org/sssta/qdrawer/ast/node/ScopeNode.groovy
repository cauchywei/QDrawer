package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.ast.value.VoidValue
import org.sssta.qdrawer.exception.IllegalTypeError

/**
 * Created by cauchywei on 15/12/21.
 */
class ScopeNode extends Node {

    List<Node> body

    ScopeNode(List<Node> body) {
        this.body = body
    }

    void preprocess(Scope scope) {
        //empty for override
    }

    @Override
    Value eval(Scope scope) {

        preprocess(scope)

        def lastVal = new VoidValue()
        for (int j = 0; j < body.size(); j++) {
            lastVal = body[j].eval(scope)
            if (scope.returnFlag) {
                if (!scope.isFunctionScope) {
                    Console.addError(new IllegalTypeError(body[j], 'Unexcepted found a return statement in a non-function scope'))
                }
                break
            }
        }

        return lastVal
    }

    @Override
    Type checkType(Scope scope) {
        return Type.VOID
    }
}
