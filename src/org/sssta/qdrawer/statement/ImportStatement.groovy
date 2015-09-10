package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.AstDeclaration
import org.sssta.qdrawer.complier.SymbolModule
import org.sssta.qdrawer.grammer.GrammarSymbol

/**
 * Created by cauchywei on 15/9/10.
 */
class ImportStatement extends Statement{


    @Override
    String getName() {
        return null
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
