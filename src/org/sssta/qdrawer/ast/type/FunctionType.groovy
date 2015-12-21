package org.sssta.qdrawer.ast.type

import org.sssta.qdrawer.ast.node.Variable

/**
 * Created by cauchywei on 15/12/13.
 */
class FunctionType extends Type {

    List<Type> types
    Variable funcName

    FunctionType() {
        super('Function')
    }
}
