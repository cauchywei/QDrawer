package org.sssta.qdrawer.statement

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/10.
 */
class ImportStatement extends Statement{


    String name;


    static ImportStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new ImportStatement()
        if (laxer.peekToken()?.type != TokenType.IMPORT) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted import declaration.')
        } else {
            statement.keyToken = laxer.takeToken()
            if (laxer.peekToken()?.type == TokenType.IDENTIFIER){
                statement.name = laxer.takeToken().value
            }else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted import lib name.')
            }
        }

        return statement
    }

//    @Override
//    GrammarSymbol createSymbol(boolean secondary) {
//        return null
//    }
//
//    @Override
//    AstDeclaration generateAst(SymbolModule symbolModule) {
//        return null
//    }


}
