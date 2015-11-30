package org.sssta.qdrawer.statement

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.statement.expression.Expression

/**
 * Created by cauchywei on 15/11/30.
 */
class FunctionStatement extends Statement {
    Token identifier
    List<Expression> arguments = []
    List<Statement> statements = []

    static FunctionStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new FunctionStatement()

        return statement
    }
}
