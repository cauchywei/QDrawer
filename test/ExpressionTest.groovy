import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.Parser
/**
 * Created by cauchywei on 15/9/10.
 */
class ExpressionTest extends GroovyTestCase{

    @Test
    void testCorrectExpression() {
        def code = '''module hello;
                    import stddrw;
                    import stdio;'''

        def reader = new InputStreamReader(new StringInputStream(code))
        def parser = new Parser(reader)



        reader.close()
    }

}
