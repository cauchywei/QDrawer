package org.sssta.qdrawer.statement

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.expression.Expression

/**
 * Created by cauchywei on 15/10/19.
 */
class ForStatement extends Statement {

    Token forVariable
    Expression start,end,step
    ScopeStatement scopeStatement

    static ForStatement parse(Laxer laxer, List<CodeError> errors) {

        def statement = new ForStatement()

        if (laxer.peekToken().type != TokenType.FOR) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted for symbol.')
            return null
        }

        laxer.takeToken()

        if (!laxer.peekToken().isIdentifier()) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an identifier after for.')
            return null
        }

        statement.forVariable = laxer.takeToken()

        if (!laxer.peekToken().type == TokenType.FROM) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted from symbol.')
            return null
        }

        laxer.takeToken()

        def start = Expression.parse(laxer,errors)
        if (start == null) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an expression.')
            return null
        }

        statement.start = start

        if (!laxer.peekToken().type == TokenType.TO) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted to symbol.')
            return null
        }

        laxer.takeToken()

        def end = Expression.parse(laxer,errors)
        if (start == null) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an expression.')
            return null
        }

        statement.end = end


        if (!laxer.peekToken().type == TokenType.STEP) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted step symbol.')
            return null
        }

        laxer.takeToken()

        def step = Expression.parse(laxer,errors)
        if (start == null) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an expression.')
            return null
        }

        statement.step = step

        def scopestat = ScopeStatement.parse(laxer, errors)

        if (scopestat == null) {
            return null
        }

        statement.scopeStatement = scopestat

        return statement
    }

}
