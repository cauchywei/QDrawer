package org.sssta.qdrawer.statement.expression
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
}
