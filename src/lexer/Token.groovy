package lexer

/**
 * Created by cauchywei on 15/9/9.
 */
class Token {

    TokenType type;
    String value;

    String getValue() {
        return value?value:type.getIdentifier()
    }

}
