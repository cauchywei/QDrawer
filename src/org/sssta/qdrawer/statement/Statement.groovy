package org.sssta.qdrawer.statement
import org.sssta.qdrawer.CodeElement
import org.sssta.qdrawer.ast.AstDeclaration
import org.sssta.qdrawer.complier.SymbolModule
import org.sssta.qdrawer.grammer.GrammarSymbol
/**
 * Created by cauchywei on 15/9/10.
 */
abstract class Statement extends CodeElement {

    abstract GrammarSymbol createSymbol(boolean secondary)
    abstract AstDeclaration generateAst(SymbolModule symbolModule)

}