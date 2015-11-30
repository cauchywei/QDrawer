package org.sssta.qdrawer.statement
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.statement.expression.Expression
/**
 * Created by cauchywei on 15/9/14.
 */
class AssignmentStatement extends Statement{

//    ReferenceExpression target
    Expression valueExpresstion


    @Override
    String getName() {
        return var.value
    }

    static AssignmentStatement parse(Laxer laxer, List<CodeError> errors){
        def statement = new AssignmentStatement()

        laxer.peekToken()

        return statement
    }
}
