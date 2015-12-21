package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.node.Node

/**
 * Created by cauchywei on 15/11/30.
 */
class EmptyStatement extends Statement {

    @Override
    Node createAstNode() {
        return null
    }
}
