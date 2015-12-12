import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.Parser
/**
 * Created by cauchywei on 15/9/10.
 */
class StatementTest extends GroovyTestCase{

    @Test
    void testCorrectStatement() {
        def code = '''
                    module hello;
                    import stddrw;
                    import stdio
                    using java.util.Math;

                    const PI = 3.1415926

                    for t form 1 to 10 step 1 {
                        draw(t,t)
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
                        return java.utils.Math.sin(v);
                    }


                    '''

        def reader = new InputStreamReader(new StringInputStream(code))
        def parser = new Parser(reader)

        def module = parser.parse()
        assertEquals([],parser.errors)
        assertEquals('hello',module.name)
        assertEquals(2,module.importStatements.size())
        assertEquals('stddrw',module.importStatements[0].name)
        assertEquals('stdio',module.importStatements[1].name)

        reader.close()
    }

}
