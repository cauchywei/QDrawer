package org.sssta.qdrawer.ast.node
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.SymbolInfo
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.NumericValue
import org.sssta.qdrawer.ast.value.PointValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.exception.IllegalOperateError
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/12/13.
 */
class AssignmentNode extends Node {

    Variable variable
    Node value

    @Override
    Value eval(Scope scope) {

        def symbol = scope.getSymbol(variable.name.value)
        if (symbol == null  ) {
            scope.putSymbol(variable.name.value,new SymbolInfo())
        }else if (symbol.isConst) {
            Console.addError(new IllegalOperateError(this,variable.name.value + ' is const, it can\'t be modified'))
            return variable.eval(scope)
        }

        def valueType = value.checkType(scope)

        if (valueType == null) {
            return null
        }

        def val = value.eval(scope)


        if (variable.name.type == TokenType.ORIGIN) {
            if (!(val instanceof PointValue)) {
                def trans = val.asType(PointValue)
                scope.graphics2D?.translate(trans.x.value, trans.y.value)
            } else {
                Console.addError(new IllegalOperateError(this,'origin must be a Point.but now is ' + valueType.name))
                return null
            }
        }else if (variable.name.type == TokenType.ROT) {
            if (!(val instanceof NumericValue)) {
                scope.graphics2D?.rotate(val.asType(NumericValue).value)
            } else {
                Console.addError(new IllegalOperateError(this,'rot must be a Number.but now is ' + valueType.name))
                return null
            }
        }else if (variable.name.type == TokenType.SCALE) {
            if (!(val instanceof NumericValue)) {
                def rot = val.asType(PointValue)
                scope.graphics2D?.translate(rot.x.value, rot.y.value)
            } else {
                Console.addError(new IllegalOperateError(this,'scale must be a Point.but now is ' + valueType.name))
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
