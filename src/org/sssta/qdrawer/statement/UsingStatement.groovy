package org.sssta.qdrawer.statement

import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/10.
 */
class UsingStatement extends Statement{


    Token library;

    static UsingStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new UsingStatement()
        if (laxer.peekToken()?.type != TokenType.USING) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted import declaration.')
            return null
        } else {
            laxer.takeToken()
            if (laxer.peekToken()?.type == TokenType.IDENTIFIER){
                statement.library = laxer.takeToken()
            }else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted import lib name.')
                return null
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
