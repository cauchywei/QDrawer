package org.sssta.qdrawer.statement

import org.sssta.qdrawer.ast.node.ReturnNode
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
/**
 * Created by cauchywei on 15/12/12.
 */
class ReturnStatement extends Statement{
    Statement returnExpression //expression or empty statement

    static ReturnStatement parse(Laxer laxer, List<CodeError> errors) {
        def statement = new ReturnStatement()
        if (laxer.peekToken()?.type != TokenType.RETURN) {
            errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted return symbol.')
        } else {
            statement.addToken(laxer.takeToken())
            def ret = Statement.parse(laxer, errors)
            statement.getRange().union(ret.getRange())
            if (ret == null || !(ret instanceof ExpressionStatement) && !(ret instanceof EmptyStatement)) {
                errors << new CodeError(col: laxer.col, row: laxer.row, message: 'Excepted return expression .')
                return null
            }

            statement.returnExpression = ret
        }

        return statement
    }

    @Override
    ReturnNode createAstNode() {
        return new ReturnNode(returnExpression.createAstNode())
    }
}
