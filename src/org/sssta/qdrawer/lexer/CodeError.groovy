package org.sssta.qdrawer.lexer
/**
 * Created by cauchywei on 15/9/9.
 */
class CodeError {

    int col,row
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

    @Override
    public String toString() {
        return "Error{" +
                "row=" + row +
                ", col=" + col +
                ", message=" + message + '}';
    }
}
