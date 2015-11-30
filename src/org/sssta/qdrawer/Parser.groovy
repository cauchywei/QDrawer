package org.sssta.qdrawer

import org.sssta.qdrawer.exception.IllegalDeclarationException
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.AssignmentStatement
import org.sssta.qdrawer.statement.ConstantDeclarationStatement
import org.sssta.qdrawer.statement.ForStatement
import org.sssta.qdrawer.statement.ImportStatement
import org.sssta.qdrawer.statement.InvokeStatement
import org.sssta.qdrawer.statement.ModuleStatement
import org.sssta.qdrawer.statement.expression.Expression
import org.sssta.qdrawer.statement.expression.InvokeExpression

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
                    module.statements << ConstantDeclarationStatement.parse(laxer,errors)
                    break;
                case {token.isIdentifier()}:

                    def save = laxer.save()
                    laxer.takeToken()
                    if (laxer.peekToken()?.type == TokenType.IS || laxer.peekToken()?.type == TokenType.ASSIGMENT) {
                        laxer.go2(save)
                        module.statements << AssignmentStatement.parse(laxer,errors)
                        break
                    }

                    laxer.go2(save)

                    def expr = Expression.parse(laxer, errors)

                    if (expr instanceof InvokeExpression) {
                        module.statements << new InvokeStatement(invokeExpression: expr)
                    } else {
                        //ingore
                    }
                    break
                case TokenType.FOR:
                    module.statements << ForStatement.parse(laxer,errors)
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