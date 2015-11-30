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

    Token forVarible;
    Expression start,end,step;
    PointExpression pointExpression;

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

        laxer.takeToken()

        if (!laxer.peekToken().type == TokenType.FROM) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted from symbol.')
            return null
        }

        laxer.takeToken()





        return statement
    }

        @Override
    String getName() {
        'For'
    }
}
