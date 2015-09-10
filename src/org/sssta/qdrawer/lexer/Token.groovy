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

}
