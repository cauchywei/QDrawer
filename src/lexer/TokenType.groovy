package lexer

/**
 * Created by cauchywei on 15/9/9.
 */
enum TokenType {

    UNKNOWN('Unknown'),
    IMPORT('import'),

    NUMBERIC('numeric'),
    STRING('string'),

    IDENTIFIER('identifier'),

    IS('is'),
    ORIGIN('origin'),
    SCALE('scale'),
    ROT('rot'),

    T('T'),

    FOR('for'),
    FROM('from'),
    TO('to'),
    DRAW('draw'),
    STEP('step'),

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

    CONST('const'),

    COMMENT('comment'),
    ASSIGMENT('=')


    String identifier

    TokenType(){
        this(values()[ordinal()].name())
    }

    TokenType(String id) {
        this.identifier = id
    }

}
