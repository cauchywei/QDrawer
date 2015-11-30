package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer

/**
 * Created by cauchywei on 15/9/14.
 */
abstract class Expression {

    boolean isConst = false;

    static Expression parse(Laxer laxer, List<CodeError> errors) {
//        def expression = new Expression()
//
//
//        return expression
    }


}