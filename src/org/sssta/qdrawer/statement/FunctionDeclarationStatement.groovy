package org.sssta.qdrawer.statement
import org.sssta.qdrawer.ast.node.FunctionNode
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.ast.node.VariableNode
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.expression.VariableExpression
/**
 * Created by cauchywei on 15/11/30.
 */
class FunctionDeclarationStatement extends Statement {

    VariableExpression functionName
    List<VariableExpression> arguments = []
    ScopeStatement scopeStatement;

    static FunctionDeclarationStatement parse(Laxer laxer, List<CodeError> errors) {
        def funcStatement = new FunctionDeclarationStatement()

        if (laxer.peekToken()?.type != TokenType.FUNC) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted func declaration.')
            return null
        } else {
            laxer.takeToken()

            if (laxer.peekToken()?.type == TokenType.IDENTIFIER){
                funcStatement.functionName = new VariableExpression(laxer.takeToken())

                if (laxer.peekToken()?.type != TokenType.OPEN_BRACKET) {
                    errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted (.")
                    return null
                } else {

                    laxer.takeToken()
                    def arg = laxer.peekToken()
                    if (arg != null) {
                        laxer.takeToken()
                        funcStatement.arguments << new VariableExpression(arg)
                        while (laxer.peekToken()?.type == TokenType.COMMA) {
                            laxer.takeToken()
                            arg = laxer.takeToken()
                            if (arg == null) {
                                errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted an argument.")
                                return null
                            }
                            funcStatement.arguments << new VariableExpression(arg)
                        }
                    }

                    if (laxer.peekToken()?.type != TokenType.CLOSE_BRACKET) {
                        errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted ).")
                        return null
                    }

                    laxer.takeToken()

                    def scopeStatement = ScopeStatement.parse(laxer, errors)
                    if (scopeStatement == null || !scopeStatement.hasScopeMark) {
                        errors << new CodeError(row :laxer.row,col :laxer.col,message: "Excepted scope after function declaration.")
                        return null
                    }

                    funcStatement.scopeStatement = scopeStatement
                }

            }else {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted function name.')
                return null
            }
        }

        return funcStatement
    }

    @Override
    Node createAstNode() {
        List<VariableNode> args = []
        arguments.each { args << new VariableNode(it.identifier)}
        return new FunctionNode(funcName:functionName.createAstNode(),args:args,scopeNode: scopeStatement.createAstNode() )
    }
}
