package org.sssta.qdrawer
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.ImportStatement
import org.sssta.qdrawer.statement.ModuleStatement
import org.sssta.qdrawer.statement.Statement
import org.sssta.qdrawer.statement.UsingStatement
/**
 * Created by cauchywei on 15/9/10.
 */
class Parser {

    Laxer laxer
    InputStream inputStream
    List<CodeError> errors = []

    Parser(InputStream inputStream) {
        this.inputStream = inputStream
        laxer = new Laxer(inputStream)
    }

    Module parse(){

        def module = new Module()

        module.name = ModuleStatement.parse(laxer,errors).name
        matchSemico()
        while (laxer.peekToken()?.type == TokenType.IMPORT) {
            module.importStatements << ImportStatement.parse(laxer, errors)
            matchSemico()
        }

        while (laxer.peekToken()?.type == TokenType.USING) {
            module.usingStatements << UsingStatement.parse(laxer, errors)
            matchSemico()
        }

        def statement
        while (laxer.hasNext() && (statement = Statement.parse(laxer,errors))) {
            module.statements << statement

        }

        return module
    }

    void matchSemico() {
        if (laxer.peekToken()?.type == TokenType.SEMICO) {
            laxer.takeToken()
        } else {
            errors << new CodeError(col: laxer.col, row: laxer.row,message: 'Excepted ; at line ' + laxer.row)
        }
    }


}