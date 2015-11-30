package org.sssta.qdrawer

import org.sssta.qdrawer.exception.IllegalDeclarationException
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.ImportStatement
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
        matchSemico()
        while (laxer.peekToken() == TokenType.IMPORT) {
            module.importStatements << ImportStatement.parse(laxer, errors)
            matchSemico()
        }

        while (laxer.hasNext()) {
            def token = laxer.peekToken()
            switch (token.getType()) {

                case TokenType.CONST:
                case TokenType.ORIGIN:
                case TokenType.SCALE:
                case TokenType.ROT:
                case TokenType.T:
                case TokenType.IDENTIFIER:

                    break
                case TokenType.FOR:
                    break
                case TokenType.FUNC:
                    break
                case TokenType.SEMICO:
                    break
                case TokenType.COMMENT:
                    laxer.takeToken()
                    continue
                    break
                default:
                    throw new IllegalDeclarationException(token.value + ' declaration or statement not allowed here. at line ' + token.row)
            }
            matchSemico()
        }

        return module
    }

    void matchSemico() {
        if (laxer.peekToken() == TokenType.SEMICO) {
            laxer.takeToken()
        } else {
            errors << new CodeError(col: laxer.col, row: laxer.row,message: 'Excepted ; at line ' + laxer.row)
        }
    }


}