package org.sssta.qdrawer.ast.node.primitives
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.type.BooleanType
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError
/**
 * Created by cauchywei on 15/12/13.
 */
class NotNode extends PrimitiveNode {

    Node node

    NotNode(Node node) {
        this()
        this.node = node
    }

    NotNode() {
        super('!', 1)
    }

    @Override
    Value eval(Scope scope) {

        def value = node.eval(scope)

        if (!(value instanceof BooleanValue)) {
            Console.addError(new IllegalTypeError(node,'illegal type:require Boolean but found ' + value.type))
            return null
        }

        return new BooleanValue(!value.asType(BooleanValue).value)
    }

    @Override
    Type checkType(Scope scope) {

        def type = node.checkType(scope)

        if (!(type instanceof BooleanType)) {
            Console.addError(new IllegalTypeError(node,'illegal type:require Boolean but found ' + type))
        }

        return Type.BOOLEAN
    }
}
