package org.sssta.qdrawer.statement.expression

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType

/**
 * Created by cauchywei on 15/9/14.
 */
abstract class Expression {

    boolean isConst = false;

    static Expression parse(Laxer laxer, List<CodeError> errors) {

        Expression left = term(laxer,errors);
        if (laxer.peekToken().type == TokenType.PLUS || laxer.peekToken().type == TokenType.MINUS) {

        }

        return  left;
    }

    static Expression term(Laxer laxer, List<CodeError> errors {

    }

    static Expression Factor(Laxer laxer, List<CodeError> errors) {

    }

    static Expression component(Laxer laxer, List<CodeError> errors) {

    }

    static Expression atom(Laxer laxer, List<CodeError> errors) {
        
    }

}