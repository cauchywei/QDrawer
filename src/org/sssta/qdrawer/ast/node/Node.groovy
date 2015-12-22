package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.Value
/**
 * Created by cauchywei on 15/9/10.
 */
abstract class Node {

    CodeRange range
    Node parent
    abstract Value eval(Scope scope)
    abstract Type checkType(Scope scope)
}
