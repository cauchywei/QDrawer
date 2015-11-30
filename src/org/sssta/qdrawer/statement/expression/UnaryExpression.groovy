package org.sssta.qdrawer.statement.expression

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
}
