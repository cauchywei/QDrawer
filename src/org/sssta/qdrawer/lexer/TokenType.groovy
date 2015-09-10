package org.sssta.qdrawer.lexer

/**
 * Created by cauchywei on 15/9/9.
 */
enum TokenType {

    UNKNOWN('Unknown'),

    IMPORT('import'),
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

    NUMBERIC('numeric'),
    STRING('string'),

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
    POWER('**'),

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
