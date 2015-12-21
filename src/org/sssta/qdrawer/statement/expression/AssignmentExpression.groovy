package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.AssignmentNode
import org.sssta.qdrawer.ast.node.ConstDeclarationNode
import org.sssta.qdrawer.ast.node.ExpressionNode

/**
 * Created by cauchywei on 15/12/2.
 */
class AssignmentExpression extends Expression {


    VariableExpression variable;
    Expression value;

    boolean isConst = false;

    @Override
    public String toString() {
        return "(" +
                "= " + variable +
                " " + value +
                ')';
    }

    @Override
    ExpressionNode createAstNode() {
        if (isConst) {
            return new ConstDeclarationNode(variable.createAstNode(), value.createAstNode())
        } else {
            return new AssignmentNode(variable.createAstNode(), value.createAstNode())
        }
    }
}
