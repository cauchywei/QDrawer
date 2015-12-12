package org.sssta.qdrawer

import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.statement.ImportStatement
import org.sssta.qdrawer.statement.Statement
import org.sssta.qdrawer.statement.UsingStatement
/**
 * Created by cauchywei on 15/9/9.
 */
class Module extends Statement {

    Token name
    List<ImportStatement> importStatements = []
    List<UsingStatement> usingStatements = []

    List<Statement> statements = []


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
