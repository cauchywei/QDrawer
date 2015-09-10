package org.sssta.qdrawer.statement
import org.sssta.qdrawer.ast.AstDeclaration
import org.sssta.qdrawer.complier.SymbolModule
import org.sssta.qdrawer.grammer.GrammarSymbol
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/9/10.
 */
class ModuleStatement extends Statement {

    String name;

    static ModuleStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new ModuleStatement()
        if (laxer.peekToken().type != TokenType.MODULE) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted module declaration.')
        } else {
            statement.keyToken = laxer.takeToken()
            if (laxer.hasNext() && laxer.peekToken().type == TokenType.STRING){
                statement.name = laxer.takeToken().value
            }else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted module declaration.')
            }
        }

        return statement
    }

    @Override
    GrammarSymbol createSymbol(boolean secondary) {
        return null
    }

    @Override
    AstDeclaration generateAst(SymbolModule symbolModule) {
        return null
    }
}
