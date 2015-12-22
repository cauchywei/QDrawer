package org.sssta.qdrawer
import org.sssta.qdrawer.lexer.CodeError
/**
 * Created by cauchywei on 15/12/12.
 */
class Console {
    static List<CodeError> errors = []
    static boolean abort = false

    public static void addError(CodeError codeError){
        errors << codeError
    }
}
