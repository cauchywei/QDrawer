package org.sssta.qdrawer.statement
import org.sssta.qdrawer.CodeElement
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.sssta.qdrawer.statement.expression.Expression
/**
 * Created by cauchywei on 15/9/10.
 */
abstract class Statement extends CodeElement {
    static Statement parse(Laxer laxer, List<CodeError> errors) {

        while (1){

            def token = laxer.peekToken()
            if (token == null) {
                return null
            }
            switch (token.type) {
                case TokenType.OPEN_SCOPE:
                    return  ScopeStatement.parse(laxer,errors)
                    break;
                case TokenType.CONST:
                case {token.isIdentifier()}:

                    def expr = Expression.parse(laxer, errors)
                    if (expr == null) {
                        return null
                    }

                    def statement =  new ExpressionStatement(expr)

                    if (laxer.peekToken()?.type != TokenType.SEMICO) {
                        errors << new CodeError(col:laxer.col,row: laxer.row,message: 'Excepted ;')
                        return null
                    }

                    laxer.takeToken()
                    return statement
                case TokenType.IF:
                    return IfStatement.parse(laxer,errors)
                case TokenType.FOR:
                    return ForStatement.parse(laxer,errors)
                    break
                case TokenType.FUNC:
                    return FunctionDeclarationStatement.parse(laxer,errors)
                    break
                case TokenType.SEMICO:
                    laxer.takeToken()
                    return new EmptyStatement()
                case TokenType.COMMENT:
                    laxer.takeToken()
                    continue
                    break
                case TokenType.RETURN:
                    return ReturnStatement.parse(laxer,errors)
                default:
                    errors << new CodeError(col:laxer.col,row: laxer.row,message: 'Excepted a statement rather than import, using or other strange things ;')
                    return null
            }
            break
        }
        return null
    }

    public abstract Node createAstNode();
}