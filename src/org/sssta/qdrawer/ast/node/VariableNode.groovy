package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.type.UndefinedType
import org.sssta.qdrawer.ast.value.UndefinedValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalTypeError
import org.sssta.qdrawer.lexer.Token
/**
 * Created by cauchywei on 15/12/14.
 */
class VariableNode extends ExpressionNode {

    Token name

    VariableNode(Token name) {
        this.name = name
    }

    @Override
    Value eval(Scope scope) {
        def value = scope.getValue(name.value)
        if (value == null || value instanceof UndefinedValue) {
            Console.addError(new IllegalTypeError(this,name.value +  ' is not defined.'))
            return new UndefinedValue()
        }
        return value
    }

    @Override
    Type checkType(Scope scope) {
        def type = scope.getType(name.value)
        if (type == null || type instanceof UndefinedType) {
            Console.addError(new IllegalTypeError(this,name.value +  ' is not defined.'))
            return Type.UNDEFINED
        }
        return type
    }
}
