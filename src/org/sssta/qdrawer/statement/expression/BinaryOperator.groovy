package org.sssta.qdrawer.statement.expression

/**
 * Created by cauchywei on 15/11/30.
 */
enum BinaryOperator {

    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    MOD('%'),
    AND('and'),
    OR('or'),
    POWER('**'),

    LT('<'),
    GT('>'),
    EQ('=='),
    LTE('<='),
    GTE('>=')

    String opt

    BinaryOperator(String opt) {
        this.opt = opt
    }
}
