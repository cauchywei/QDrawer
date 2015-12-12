import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.Parser
import org.sssta.qdrawer.statement.ExpressionStatement
import org.sssta.qdrawer.statement.ForStatement
import org.sssta.qdrawer.statement.FunctionDeclarationStatement
import org.sssta.qdrawer.statement.IfStatement
import org.sssta.qdrawer.statement.ReturnStatement
import org.sssta.qdrawer.statement.Statement
import org.sssta.qdrawer.statement.expression.AssignmentExpression
/**
 * Created by cauchywei on 15/9/10.
 */
class StatementTest extends GroovyTestCase{

    @Test
    void testCorrectStatement() {
        def code = '''module hello;
                    import stddrw;
                    import stdio;
                    using java.lang.Math;

                    const PI = 3.1415926;
                    a = 2;

                    for t form 1 to 10 step 1 {
                        draw(t,t);
                    }

                    if(a == 2)
                        draw(a,a);

                    if(a == 2){
                        draw(a,a);
                        b = 4*3;
                    }

                    if(a == 2){
                        draw(a,a);
                        b = 4*3;
                    }else{
                        b = -1;
                    }

                    func sin(v){
                        return java.lang.Math.sin(v);
                    }

                    sin(2*PI);

                    '''

        def parser = new Parser(new StringInputStream(code))

        def module = parser.parse()

        assertEquals([],parser.errors)

        assertEquals('hello',module.name.value)

        //test import
        assertEquals(2,module.importStatements.size())
        assertEquals('stddrw',module.importStatements[0].name)
        assertEquals('stdio',module.importStatements[1].name)

        //test using
        assertEquals(1,module.usingStatements.size())
        assertEquals('java.lang.Math',module.usingStatements[0].library.value)


        assertEquals(8,module.statements.size())

        Statement statement

        //const PI = 3.1415926
        statement = module.statements[0]
        assertTrue(statement instanceof ExpressionStatement)
        def expr = ((ExpressionStatement) statement).expression
        assertTrue(expr instanceof AssignmentExpression)
        def assigExpr = (AssignmentExpression) expr
        assertTrue(assigExpr.isConst)
        assertEquals('PI',assigExpr.variable.identifier.value)
        assertEquals('3.1415926',assigExpr.value.toString())


        //a = 2.2
        statement = module.statements[1]
        assertTrue(statement instanceof ExpressionStatement)
        expr = ((ExpressionStatement) statement).expression
        assertTrue(expr instanceof AssignmentExpression)
        assigExpr = (AssignmentExpression) expr
        assertFalse(assigExpr.isConst)
        assertEquals('a',assigExpr.variable.identifier.value)
        assertEquals('2',assigExpr.value.toString())


//        for t form 1 to 10 step 1 {
//            draw(t,t);
//        }        statement = module.statements[0]
        statement = module.statements[2]
        assertTrue(statement instanceof ForStatement)
        expr = ((ForStatement) statement)
        assertEquals('1',expr.start.toString())
        assertEquals('10',expr.end.toString())
        assertEquals('1',expr.step.toString())
        assertEquals(1,expr.scopeStatement.statements.size())

        statement = module.statements[3]
        assertTrue(statement instanceof IfStatement)
        expr = ((IfStatement) statement)
        assertEquals('(== a 2)',expr.condition.toString())
        assertNotNull(expr.ifScopeStatement)
        assertEquals(1,expr.ifScopeStatement.statements.size())
        assertFalse(expr.ifScopeStatement.hasScopeMark)
        assertNull(expr.elseScopeStatement)

        statement = module.statements[4]
        assertTrue(statement instanceof IfStatement)
        expr = ((IfStatement) statement)
        assertEquals('(== a 2)',expr.condition.toString())
        assertNotNull(expr.ifScopeStatement)
        assertEquals(2,expr.ifScopeStatement.statements.size())
        assertTrue(expr.ifScopeStatement.hasScopeMark)
        assertNull(expr.elseScopeStatement)

        statement = module.statements[5]
        assertTrue(statement instanceof IfStatement)
        expr = ((IfStatement) statement)
        assertEquals('(== a 2)',expr.condition.toString())
        assertNotNull(expr.ifScopeStatement)
        assertEquals(2,expr.ifScopeStatement.statements.size())
        assertTrue(expr.ifScopeStatement.hasScopeMark)

        assertNotNull(expr.elseScopeStatement)
        assertEquals(1,expr.elseScopeStatement.statements.size())
        assertTrue(expr.elseScopeStatement.hasScopeMark)


//        func sin(v){
//            return java.lang.Math.sin(v);
//        }
        statement = module.statements[6]
        assertTrue(statement instanceof FunctionDeclarationStatement)
        expr = ((FunctionDeclarationStatement) statement)
        assertEquals('sin',expr.functionName.toString())
        assertEquals(1,expr.arguments.size())
        assertEquals(1,expr.scopeStatement.statements.size())
        assertTrue(expr.scopeStatement.statements[0] instanceof ReturnStatement)


//        sin(2*PI);
        statement = module.statements[7]
        assertTrue(statement instanceof ExpressionStatement)
        expr = ((ExpressionStatement) statement)
        assertEquals('(sin (* 2 PI))',expr.expression.toString())

    }

}
