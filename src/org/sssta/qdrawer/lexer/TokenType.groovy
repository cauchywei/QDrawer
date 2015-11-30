package org.sssta.qdrawer.lexer

/**
 * Created by cauchywei on 15/9/9.
 */
enum TokenType {

    UNKNOWN('Unknown'),

    IMPORT('import'),
    USE('use'),
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
    DRAW('draw'),
    STEP('step'),

    FUNC("func"),

    NUMBERIC('numeric'),
    STRING('string'),
    TRUE('true'),
    FALSE('false'),

    IDENTIFIER('identifier'),




    OPEN_BRACKET('('),
    CLOSE_BRACKET(')'),
    SEMICO(';'),
    COMMA(','),

    PLUS('+'),
    MINUS('-'),
    MUL('*'),
    DIV('/'),
    MOD('%'),
    AND('and'),
    OR('or'),
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
