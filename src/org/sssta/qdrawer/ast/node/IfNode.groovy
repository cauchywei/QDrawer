package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.BooleanType
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.type.UnionType
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError
/**
 * Created by cauchywei on 15/12/12.
 */
class IfNode extends Node {

    Node condition
    Node thenNode
    Node elseNode

    @Override
    Value eval(Scope scope) {
        def condition = condition.eval(scope)

        if (condition instanceof BooleanValue) {
            if (condition.value) {
                return thenNode.eval(scope)
            } else {
                return elseNode.eval(scope)
            }
        } else {
            Console.addError(new IllegalTypeError(this,'illegal type: require boolean but found ' + condition.type))
        }
        return null
    }

    @Override
    Type checkType(Scope scope) {
        def conditionType = condition.checkType(scope)
        if (conditionType instanceof BooleanType) {
               return UnionType.union(thenNode.checkType(scope),elseNode.checkType(scope))
        } else {
            Console.addError(new IllegalTypeError(this,'illegal type. require boolean but found ' + condition))
        }
        return null
    }
}
