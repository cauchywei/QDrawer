package org.sssta.qdrawer

import org.sssta.qdrawer.ast.node.CodeRange
import org.sssta.qdrawer.lexer.Token

/**
 * Created by cauchywei on 15/9/10.
 */
abstract class CodeElement {
    List<Token> tokens = []
    CodeRange range = new CodeRange()

    void addToken(Token token) {
        if (tokens.isEmpty()) {
            range = token.getRange()
        } else {
            range.union(token.getRange())
        }
    }
}
