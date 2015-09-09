package lexer

/**
 * Created by cauchywei on 15/9/9.
 */
enum TokenType {

    UNKNOWN,
    IMPORT,

    NUMBERIC,
    STRING,

    IDENTIFIER,

    IS,
    ORIGIN,
    SCALE,
    ROT,

    T,

    FOR,
    FORM,
    TO,
    DRAW,
    STEP,

    OPEN_BRACKET('('),
    CLOSE_BRACKET(')'),
    SEMICO(';'),
    COMMA(','),

    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    MOD('%'),
    POWER('**'),

    CONST,

    COMMENT,
    ASSIGMENT('=')


    String identifier

    TokenType(){
        this(name())
    }

    TokenType(String id) {
        this.identifier = id
    }

}
