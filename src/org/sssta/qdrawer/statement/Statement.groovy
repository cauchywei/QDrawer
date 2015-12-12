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
        def statement = null

            def token = laxer.peekToken()
            switch (token.getType()) {
                case TokenType.OPEN_SCOPE:
                    return  ScopeStatement.parse(laxer,errors)
                    break;
                case TokenType.CONST:
                case {token.isIdentifier()}:

                    def expr = Expression.parse(laxer, errors)
                    if (expr == null) {
                        return null
                    }

                    statement =  new ExpressionStatement(expr)

                    if (laxer.peekToken()?.type != TokenType.COMMA) {
                        errors << new CodeError(col:laxer.col,row: laxer.row,message: 'Excepted ;')
                        return null
                    }

                    laxer.takeToken()
                    return statement
                case TokenType.FOR:
                    return ForStatement.parse(laxer,errors)
                    break
                case TokenType.FUNC:
                    break
                case TokenType.SEMICO:
                case TokenType.COMMENT:
                    laxer.takeToken()
                    return new EmptyStatement()
                    break
                default:
                    return null
            }
        return null

    }
}