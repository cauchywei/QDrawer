package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.NumericType
import org.sssta.qdrawer.ast.type.PointType
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.*
import org.sssta.qdrawer.exception.IllegalOperateError

/**
 * Created by cauchywei on 15/12/15.
 */
class FunctionCallNode extends Node {

    Variable funcName
    List<Node> args

    @Override
    Value eval(Scope scope) {

        //may it is a draw func :)
        if (funcName.name.value.equalsIgnoreCase("draw")) {
            if (args.size() == 1 && args[0].checkType(scope) instanceof PointType) {
                def point = args[0].eval(scope).asType(PointValue)
                scope.graphics2D?.drawOval(point.x.value.intValue(), point.y.value.intValue(), Scope.pointRadius, Scope.pointRadius)
            } else if (args.size() == 2 && args[0].checkType(scope) instanceof NumericType && args[1].checkType(scope) instanceof NumericType) {
                scope.graphics2D?.drawOval(args[0].eval(scope).asType(NumericValue).value.intValue(),
                        args[1].eval(scope).asType(NumericValue).value.intValue(), Scope.pointRadius, Scope.pointRadius)
            } else {
                Console.addError(new IllegalOperateError(this, 'draw()\'s argument(s) must be a Point or two Numeric.'))
                return null
            }
        }


        boolean methodNotFound = true

        //if the function is not declared in qdrawer code then search the java lib
        if (!scope.exist(funcName.name.value)) {

            def name = funcName.name.value

            def clazz
            def methName
            //may be is has a full name such as java.lang.Math.sin()
            if (name.contains(".")) {
                def index = name.lastIndexOf('.')
                clazz = Class.forName(name.substring(0, index))
                methName = name.substring(index + 1, name.size())
            } else {
                //otherwise search the import list

                def imports = scope.getImports()
                clazz = imports.find { it.methods.any { it.name.equalsIgnoreCase(name) } }
                methName = name
            }


            if (clazz != null && methName != null) {

                methodNotFound = false

                def meth = clazz.getMethods().find({ it.name == methName })

                def count = meth.getParameterCount()
                if (count != args.size()) {
                    Console.addError(new IllegalOperateError(this, funcName.name.value + ' arguments size is not matched.'))
                    return null
                }

                def ret
                if (count == 0) {
                    ret = meth.invoke(null)
                } else if (count == 1) {
                    ret = meth.invoke(null, args[0].eval(scope).getJavaValue())
                } else if (count == 2) {
                    ret = meth.invoke(null, args[0].eval(scope).getJavaValue(), args[1].eval(scope).getJavaValue())
                } else {
                    Console.addError(new IllegalOperateError(this, ' arguments size is not matched.'))
                    return null
                }

                if (ret instanceof Void) {
                    return new VoidValue()
                } else if (ret instanceof Number) {
                    return new NumericValue(ret.asType(Number).doubleValue())
                } else if (ret instanceof Boolean) {
                    return new BooleanValue(ret.asType(Boolean).booleanValue())
                } else if (ret instanceof String) {
                    return new StringValue(ret.asType(String))
                } else {
                    Console.addError(new IllegalOperateError(this, funcName.name.value + '()\'s return type is not supported now!'))
                    return null
                }

            }


        }

        Console.addError(new IllegalOperateError(this, funcName.name.value + 'is not a function, it not defined'))
        return null

    }

    @Override
    Type checkType(Scope scope) {

        //may it is a draw func :)
        if (funcName.name.value.equalsIgnoreCase("draw")) {
            if (args.size() == 1 && args[0].checkType(scope) instanceof PointType) {
                return Type.VOID
            } else if (args.size() == 2 && args[0].checkType(scope) instanceof NumericType && args[1].checkType(scope) instanceof NumericType) {
                return Type.VOID
            } else {
                Console.addError(new IllegalOperateError(this, 'draw()\'s argument(s) must be a Point or two Numeric.'))
                return null
            }
        }

        boolean methodNotFound = true

        //if the function is not declared in qdrawer code then search the java lib
        if (!scope.exist(funcName.name.value)) {

            def name = funcName.name.value

            def clazz
            def methName
            //may be is has a full name such as java.lang.Math.sin()
            if (name.contains(".")) {
                def index = name.lastIndexOf('.')
                clazz = Class.forName(name.substring(0, index))
                methName = name.substring(index + 1, name.size())
            } else {
                //otherwise search the import list

                def imports = scope.getImports()
                clazz = imports.find { it.methods.any { it.name.equalsIgnoreCase(name) } }
                methName = name
            }


            if (clazz != null && methName != null) {

                methodNotFound = false

                def meth = clazz.getMethods().find({ it.name == methName })

                def count = meth.getParameterCount()
                if (count != args.size()) {
                    Console.addError(new IllegalOperateError(this, funcName.name.value + ' arguments size is not matched.'))
                    return null
                }

                if (count > 3) {
                    Console.addError(new IllegalOperateError(this, ' arguments size is not matched,it must be less than 3.'))
                }

                def retType = meth.returnType
                if (retType == Void.TYPE) {
                    return Type.VOID
                } else if (retType == Number.class) {
                    return Type.NUMERIC
                } else if (retType == Boolean.class) {
                    return Type.BOOLEAN
                } else if (retType == String.class) {
                    return Type.STRING
                } else {
                    Console.addError(new IllegalOperateError(this, funcName.name.value + '()\'s return type is not supported now!'))
                    return null
                }

            }


        }

        Console.addError(new IllegalOperateError(this, funcName.name.value + 'is not a function, it not defined'))
        return null
    }
}
