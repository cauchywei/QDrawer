package lexer

/**
 * Created by cauchywei on 15/9/9.
 */
class Token {

    String value;
    TokenType type;

    Token(String value, TokenType type) {
        this.value = value
        this.type = type
    }

    String getValue() {
        return value?value:type.getIdentifier()
    }

}
