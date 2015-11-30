package org.sssta.qdrawer
import org.sssta.qdrawer.ast.AstDeclaration
import org.sssta.qdrawer.complier.SymbolModule
import org.sssta.qdrawer.grammer.GrammarSymbol
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

    @Override
    GrammarSymbol createSymbol(boolean secondary) {
        return null
    }

    @Override
    AstDeclaration generateAst(SymbolModule symbolModule) {
        return null
    }


}