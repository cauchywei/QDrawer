package org.sssta.qdrawer.lexer

import org.sssta.qdrawer.ast.node.CodeRange

/**
 * Created by cauchywei on 15/9/9.
 */
class Token {

    String value
    TokenType type
    int col
    int row

    Token(TokenType type) {
        this.type = type
        this.value = type.getIdentifier()
    }

    Token(String value, TokenType type) {
        this.value = value
        this.type = type
    }

    boolean isIdentifier() {
        switch (type) {

            case TokenType.ORIGIN:
            case TokenType.SCALE:
            case TokenType.ROT:
            case TokenType.T:
            case TokenType.IDENTIFIER:
                return true
            default:
                return false
        }
    }

    boolean isLiteral() {
        switch (type) {
            case TokenType.STRING:
            case TokenType.NUMBERIC:
            case TokenType.TRUE:
            case TokenType.FALSE:
                return true
            default:
                return false
        }
    }

    CodeRange getRange() {
        return new CodeRange(startRow: row,endRow: row,startCol: col,endCol: col + value.length() + (type == TokenType.STRING?2:0))
    }

}
