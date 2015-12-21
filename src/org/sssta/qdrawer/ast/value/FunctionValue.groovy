package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.node.Variable
import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/13.
 */
class FunctionValue extends Value {

    List<Variable> args
    List<Node> body
    Variable name

    @Override
    Type getType() {
        return Type.FUNCTION
    }

    @Override
    Object getJavaValue() {
        return null
    }
}
