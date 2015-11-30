package org.sssta.qdrawer.statement
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/11/30.
 */
class ConstantDeclarationStatement extends Statement{

    AssignmentStatement assignmentStatement

    static ConstantDeclarationStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new ConstantDeclarationStatement()

        if (laxer.peekToken().type != TokenType.CONST) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted const symbol.')
            return null
        }

        laxer.takeToken()
        statement.assignmentStatement = AssignmentStatement.parse(laxer,errors)

        return statement
    }

}
