package org.sssta.qdrawer.statement.expression
import org.sssta.qdrawer.ast.node.FunctionCallNode
import org.sssta.qdrawer.ast.node.Node

/**
 * Created by cauchywei on 15/11/30.
 */
class InvokeExpression extends Expression {
    VariableExpression function
    List<Expression> arguments

    @Override
    public String toString() {
        def sb = new StringBuilder('(' + function + ' ')
        arguments.each { sb.append(it.toString()).append(' ')}
        sb.deleteCharAt(sb.size()-1)
        sb.append(')')
        return sb.toString()
    }

    @Override
    FunctionCallNode createAstNode() {
        List<Node> args = []
        arguments.each { args << it.createAstNode()}
        return new FunctionCallNode(funcName: function.createAstNode(),args: args)
    }
}
