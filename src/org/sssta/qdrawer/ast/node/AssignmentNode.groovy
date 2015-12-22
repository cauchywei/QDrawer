package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.SymbolInfo
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.BooleanValue
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.PointValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalOperateError
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/12/13.
 */
class AssignmentNode extends ExpressionNode {

    VariableNode variable
    Node value

    AssignmentNode(VariableNode variable, Node value) {
        this.variable = variable
        this.value = value
    }

    @Override
    Value eval(Scope scope) {

        def symbol = scope.getSymbol(variable.name.value)
        if (symbol == null  ) {
            scope.putSymbol(variable.name.value,new SymbolInfo())
        }else if (symbol.getProperty("const").asType(BooleanValue)?.value) {
            Console.addError(new IllegalOperateError(variable.name,variable.name.value + ' is const, it can\'t be modified'))
            return variable.eval(scope)
        }

        def valueType = value.checkType(scope)

        if (valueType == null) {
            return null
        }

        def val = value.eval(scope)


        if (variable.name.type == TokenType.ORIGIN) {
            if (val instanceof PointValue) {
                def trans = val
                scope.graphics2D?.translate(trans.x.value, trans.y.value)
            } else {
                Console.addError(new IllegalOperateError(variable.name,'origin must be a Point.but now is ' + valueType.name))
                return null
            }
        }else if (variable.name.type == TokenType.ROT) {
            if (val instanceof NumericValue) {
                scope.graphics2D?.rotate(val.value)
            } else {
                Console.addError(new IllegalOperateError(variable.name,'rot must be a Number.but now is ' + valueType.name))
                return null
            }
        }else if (variable.name.type == TokenType.SCALE) {
            if (val instanceof NumericValue) {
                def rot = val
                scope.graphics2D?.scale(rot.value, rot.value)
            } else if(val instanceof PointValue){
                def rot = val
                scope.graphics2D?.scale(rot.x.value, rot.y.value)

            } else {
                Console.addError(new IllegalOperateError(variable.name,'scale must be a Point.but now is ' + valueType.name))
                return null
            }
        }

        scope.putType(variable.name.value, valueType)
        scope.putValue(variable.name.value, val)

        return val
    }

    @Override
    Type checkType(Scope scope) {
        return value.checkType(scope)
    }
}
