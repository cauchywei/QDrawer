package org.sssta.qdrawer.statement.expression

/**
 * Created by cauchywei on 15/11/30.
 */
class PowerExpression extends Expression {
    Expression exponent
    Expression base


    @Override
    public String toString() {
        '(pow ' + base + ' ' + exponent + ')'
    }
}