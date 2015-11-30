package org.sssta.qdrawer.statement.expression
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
}