package org.sssta.qdrawer.ast.node

import org.sssta.qdrawer.ast.Scope
import org.sssta.qdrawer.ast.value.Value
/**
 * Created by cauchywei on 15/9/10.
 */
abstract class Node {
    Node parent
    abstract Value eval(Scope scope)
    abstract Value checkType(Scope scope)
}
