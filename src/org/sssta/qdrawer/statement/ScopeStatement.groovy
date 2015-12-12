package org.sssta.qdrawer.statement

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType

/**
 * Created by cauchywei on 15/12/12.
 */
class ScopeStatement extends Statement {

    List<Statement> statements = [];
    boolean hasScopeMark;

    static ScopeStatement parse(Laxer laxer, List<CodeError> errors) {
        def scopeStatement = new ScopeStatement()
        def multiLineWithScope = false


        if (laxer.peekToken()?.type == TokenType.OPEN_SCOPE) {
            multiLineWithScope = true
            laxer.takeToken()
        }

        def subStatement

        while (subStatement = Statement.parse(laxer, [])) {
            scopeStatement.statements << subStatement
        }

        if (multiLineWithScope) {

            if (laxer.peekToken()?.type == TokenType.CLOSE_SCOPE) {
                laxer.takeToken()
            } else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted }.')
                return null
            }

        }

        if (!multiLineWithScope && scopeStatement.statements.isEmpty()) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted module declaration.')
            return null
        }

        scopeStatement.hasScopeMark = multiLineWithScope

        return scopeStatement
    }
}
