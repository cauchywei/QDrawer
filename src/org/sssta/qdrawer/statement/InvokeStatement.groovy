package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.node.FunctionCallNode
import org.sssta.qdrawer.statement.expression.InvokeExpression
/**
 * Created by cauchywei on 15/11/30.
 */
class InvokeStatement extends Statement {
    InvokeExpression invokeExpression

    @Override
    FunctionCallNode createAstNode() {
        return invokeExpression.createAstNode()
    }
}
