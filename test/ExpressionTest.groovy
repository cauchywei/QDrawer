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

        def reader = new InputStreamReader(new StringInputStream(code))
        def laxer = new Laxer(reader)
        List<CodeError> errors = []
        def exp = Expression.parse(laxer, errors)

        assertEquals("1",exp.toString())

        reader.close()
    }

}
