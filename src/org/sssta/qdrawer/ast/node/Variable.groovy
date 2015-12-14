package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.UndefinedValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.lexer.Token
/**
 * Created by cauchywei on 15/12/14.
 */
class Variable extends Node {

    Token name

    @Override
    Value eval(Scope scope) {
        def value = scope.getValue(name.value)
        if (value == null) {
            return new UndefinedValue()
        }
        return value
    }

    @Override
    Type checkType(Scope scope) {
        def type = scope.getType(name.value)
        if (type == null) {
            return Type.UNDEFINED
        }
        return type
    }
}
