package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.Value

/**
 * Created by cauchywei on 15/12/21.
 */
class ReturnNode extends Node {

    Node node

    ReturnNode(Node node) {
        this.node = node
    }

    @Override
    Value eval(Scope scope) {
        def ret = node.eval(scope)
        scope.returnFlag = true
        scope.returnValue = ret
        return ret
    }

    @Override
    Type checkType(Scope scope) {
        return node.checkType(scope)
    }
}
