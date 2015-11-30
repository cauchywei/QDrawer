package org.sssta.qdrawer

import org.sssta.qdrawer.statement.ImportStatement
import org.sssta.qdrawer.statement.ModuleStatement
import org.sssta.qdrawer.statement.Statement
/**
 * Created by cauchywei on 15/9/9.
 */
class Module extends Statement {

    String name
    ModuleStatement moduleStatement
    List<ImportStatement> importStatements = []
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
