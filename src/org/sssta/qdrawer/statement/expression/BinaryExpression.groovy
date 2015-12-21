package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.ExpressionNode
import org.sssta.qdrawer.ast.node.primitives.*

/**
 * Created by cauchywei on 15/10/19.
 */
class BinaryExpression extends Expression {
    BinaryOperator opt;
    Expression left, right;

    @Override
    String toString() {
       '(' + opt.opt + ' ' + left + ' ' + right + ')'
    }

    @Override
    ExpressionNode createAstNode() {
        def leftNode = left.createAstNode(),rightNode = right.createAstNode()
        switch (opt) {
            case BinaryOperator.PLUS:
                return new PlusNode(left:leftNode,right:rightNode)
            case BinaryOperator.MINUS:
                return new MinusNode(left:leftNode,right:rightNode)
            case BinaryOperator.MUL:
                return new MultNode(left:leftNode,right:rightNode)
            case BinaryOperator.DIV:
                return new DivNode(left:leftNode,right:rightNode)
            case BinaryOperator.MOD:
                return new ModNode(left:leftNode,right:rightNode)
            case BinaryOperator.AND:
                return new AndNode(left:leftNode,right:rightNode)
            case BinaryOperator.OR:
                return new OrNode(left:leftNode,right:rightNode)
            case BinaryOperator.LT:
                return new LTNode(left:leftNode,right:rightNode)
            case BinaryOperator.GT:
                return new GTNode(left:leftNode,right:rightNode)
            case BinaryOperator.EQ:
                return new EqNode(left:leftNode,right:rightNode)
            case BinaryOperator.NE:
                return new NENode(left:leftNode,right:rightNode)
            case BinaryOperator.LTE:
                return new LTENode(left:leftNode,right:rightNode)
            case BinaryOperator.GTE:
                return new GTENode(left:leftNode,right:rightNode)
        }
        return null
    }
}