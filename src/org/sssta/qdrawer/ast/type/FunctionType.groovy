package org.sssta.qdrawer.ast.type

import org.sssta.qdrawer.ast.node.VariableNode

/**
 * Created by cauchywei on 15/12/13.
 */
class FunctionType extends Type {

    List<Type> types
    VariableNode funcName

    FunctionType() {
        super('Function')
    }
}
