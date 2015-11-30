import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.statement.expression.Expression
/**
 * Created by cauchywei on 15/9/10.
 */
class ExpressionTest extends GroovyTestCase{

    @Test
    void testCorrectExpression() {
        def code = "1"
        def laxer = new Laxer(new StringInputStream(code))
        List<CodeError> errors = []

        def exp = Expression.parse(laxer, errors)
        assertEquals(0,errors.size())
        assertEquals("1",exp.toString())


        code = "2+3"
        laxer = new Laxer(new StringInputStream(code))
        errors = []

        exp = Expression.parse(laxer, errors)
        assertEquals("(+ 2 3)",exp.toString())

        code = "1+2+3"
        laxer = new Laxer(new StringInputStream(code))
        errors = []

        exp = Expression.parse(laxer, errors)
        assertEquals("(+ (+ 1 2) 3)",exp.toString())


    }

}
