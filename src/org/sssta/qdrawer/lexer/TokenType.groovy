package org.sssta.qdrawer.lexer

/**
 * Created by cauchywei on 15/9/9.
 */
enum TokenType {

    UNKNOWN('unknown'),

    IMPORT('import'),
    USING('using'),
    MODULE('module'),
    CONST('const'),

    IS('is'),
    ORIGIN('origin'),
    SCALE('scale'),
    ROT('rot'),

    T('T'),

    FOR('for'),
    FROM('from'),
    TO('to'),
    STEP('step'),

    FUNC("func"),
    RETURN("return"),


    NUMBERIC('numeric'),
    STRING('string'),
    TRUE('true'),
    FALSE('false'),

    IF('if'),
    ELSE('else'),

    IDENTIFIER('identifier'),




    OPEN_BRACKET('('),
    CLOSE_BRACKET(')'),
    OPEN_SCOPE('{'),
    CLOSE_SCOPE('}'),
    SEMICO(';'),
    COMMA(','),

    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    MOD('%'),
    AND('&&'),
    OR('||'),
    POWER('**'),

    NOT('!'),
    NE('!='),

    LT('<'),
    GT('>'),
    EQ('=='),
    LTE('<='),
    GTE('>='),

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
