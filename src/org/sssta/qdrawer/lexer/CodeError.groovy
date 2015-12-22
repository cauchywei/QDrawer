package org.sssta.qdrawer.lexer

import org.sssta.qdrawer.ast.node.Node

/**
 * Created by cauchywei on 15/9/9.
 */
class CodeError {

    int col = -1,row = -1
    String message
    TokenType type

    CodeError() {
    }

    CodeError(Token token,String message) {
        col = token.col
        row = token.row
        type = token.type

        this.message = message
    }

    CodeError(Node node,String message) {
//        col = node.range?.startCol
//        row = node.range?.startRow
        this.message = message
    }

    @Override
    public String toString() {
        return "Error{" +
                "row=" + row +
                ", col=" + col +
                ", message=" + message + '}';
    }
}
