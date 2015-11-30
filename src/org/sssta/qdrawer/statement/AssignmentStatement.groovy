package org.sssta.qdrawer.statement
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.expression.Expression
/**
 * Created by cauchywei on 15/9/14.
 */
class AssignmentStatement extends Statement{

    Expression value
    Token identifier

    static AssignmentStatement parse(Laxer laxer, List<CodeError> errors){
        def statement = new AssignmentStatement()

        if (!laxer.peekToken().isIdentifier()) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an identifier after const.')
            return null
        }

        statement.identifier = laxer.takeToken()

        if (laxer.peekToken().type != TokenType.IS || laxer.peekToken().type != TokenType.ASSIGMENT) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an identifier after const.')
            return null
        }

        laxer.takeToken()


        def expression = Expression.parse(laxer, errors)
        if (expression == null) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted an expression.')
            return null
        }
        statement.value = expression

        return statement
    }
}
