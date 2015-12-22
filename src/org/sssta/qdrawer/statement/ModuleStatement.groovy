package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/10.
 */
class ModuleStatement extends Statement {

    Token name;

    static ModuleStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new ModuleStatement()
        if (laxer.peekToken()?.type != TokenType.MODULE) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted module declaration.')
            return null
        } else {
            statement.addToken(laxer.takeToken())
            if (laxer.peekToken()?.type == TokenType.IDENTIFIER){
                statement.name = laxer.takeToken()
                statement.addToken(statement.name)
            }else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted module name.')
            }
        }

        return statement
    }

    @Override
    Node createAstNode() {
        return null
    }
}
