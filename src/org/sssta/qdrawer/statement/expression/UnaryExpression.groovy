package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.ast.node.ExpressionNode
import org.sssta.qdrawer.ast.node.NumericNode
import org.sssta.qdrawer.ast.node.primitives.MinusNode
import org.sssta.qdrawer.ast.node.primitives.NotNode
import org.sssta.qdrawer.ast.node.primitives.PlusNode

/**
 * Created by cauchywei on 15/11/30.
 */
 class UnaryExpression extends Expression {
    UnaryOperator opt;
    Expression expression;

    @Override
    public String toString() {
        "(" + opt.opt +" " + expression +')';
    }

     @Override
     ExpressionNode createAstNode() {
         switch (opt) {

             case UnaryOperator.POSTIVE:
                 return new PlusNode(new NumericNode(0),expression.createAstNode())
                 break
             case UnaryOperator.NEGTIVE:
                 return new MinusNode(new NumericNode(0),expression.createAstNode())
                 break
             case UnaryOperator.NOT:
                 return new NotNode(expression.createAstNode())
                 break
         }
         return null
     }
 }
