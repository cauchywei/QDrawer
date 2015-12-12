package org.sssta.qdrawer.statement

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType

/**
 * Created by cauchywei on 15/12/12.
 */
class ScopeStatement extends Statement {

    List<Statement> statements = [];

    static ScopeStatement parse(Laxer laxer, List<CodeError> errors) {
        def scopeStatement = new ScopeStatement()
        def multiLine = false


        if (laxer.peekToken()?.type == TokenType.OPEN_SCOPE) {
            multiLine = true
            laxer.takeToken()
        }

        def subStatement

        while (subStatement = Statement.parse(laxer, [])) {
            scopeStatement.statements << subStatement
        }

        if (multiLine) {

            if (laxer.peekToken()?.type == TokenType.CLOSE_SCOPE) {
                laxer.takeToken()
            } else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted }.')
                return null
            }

        }

        if (!multiLine && scopeStatement.statements.isEmpty()) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted module declaration.')
            return null
        }

        return scopeStatement
    }
}
