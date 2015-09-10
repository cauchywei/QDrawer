package org.sssta.qdrawer
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.statement.ModuleStatement

/**
 * Created by cauchywei on 15/9/10.
 */
class Parser {

    static final int DEFAULT_TOKEN_BUFFER_SIZE = 20

    Laxer laxer
    InputStreamReader inputStreamReader
    List<CodeError> errors = []

    Parser(InputStreamReader inputStreamReader) {
        this.inputStreamReader = inputStreamReader
        laxer = new Laxer(inputStreamReader)
    }

    Module parse(){

        def module = new Module()

        module.moduleStatement = ModuleStatement.parse(laxer,errors)




        return module
    }


}
