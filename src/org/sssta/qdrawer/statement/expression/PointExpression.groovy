package org.sssta.qdrawer.statement.expression

/**
 * Created by cauchywei on 15/11/30.
 */
class PointExpression extends Expression {
    Expression x, y, z

    @Override
    public String toString() {
        '(' + x + ', ' + y +', ' + z +')';
    }
}
