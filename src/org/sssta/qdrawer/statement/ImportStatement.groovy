package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/10.
 */
class ImportStatement extends Statement{


    Token name;

    static ImportStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new ImportStatement()
        if (laxer.peekToken()?.type != TokenType.IMPORT) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted import declaration.')
            return null
        } else {
            laxer.takeToken()
            if (laxer.peekToken()?.type == TokenType.IDENTIFIER){
                statement.name = laxer.takeToken()
            }else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted import lib name.')
                return null
            }
        }

        return statement
    }

    @Override
    Node createAstNode() {
        return null
    }
}
