package org.sssta.qdrawer.statement
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.expression.Expression
/**
 * Created by cauchywei on 15/12/12.
 */
class IfStatement extends Statement {

    Expression condition;
    ScopeStatement ifScopeStatement;
    ScopeStatement elseScopeStatement;

    static IfStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new IfStatement()
        if (laxer.peekToken()?.type != TokenType.IF) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted if declaration.')
            return null
        } else {
            laxer.takeToken()

            if (laxer.peekToken()?.type != TokenType.OPEN_BRACKET) {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted (.')
                return null
            } else {

                def condition = Expression.parse(laxer, errors)

                if (condition == null) {
                    errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted a condition expression.')
                    return null
                }

                statement.condition = condition

                if (laxer.peekToken()?.type != TokenType.CLOSE_BRACKET) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted ).")
                    return null
                }

                laxer.takeToken()

                def ifScopeStatement = ScopeStatement.parse(laxer, errors)
                if (ifScopeStatement == null) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted statement(s) after if.")
                    return null
                }

                statement.ifScopeStatement = ifScopeStatement

                if (laxer.peekToken()?.type == TokenType.ELSE) {
                    laxer.takeToken()

                    def elseScopeStatement = ScopeStatement.parse(laxer, errors)
                    if (elseScopeStatement == null) {
                        errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted statement(s) after else.")
                        return null
                    }

                    statement.elseScopeStatement = elseScopeStatement

                }
            }
        }

        return statement
    }
}
