package org.sssta.qdrawer.lexer

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

}
