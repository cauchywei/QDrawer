package org.sssta.qdrawer.statement.expression

/**
 * Created by cauchywei on 15/11/30.
 */
enum UnaryOperator {

    POSTIVE('+'),
    NEGTIVE('-'),
    NOT('!')

    String opt

    UnaryOperator(String opt) {
        this.opt = opt
    }
}
