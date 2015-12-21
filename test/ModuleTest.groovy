import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.Parser

/**
 * Created by cauchywei on 15/12/21.
 */
class ModuleTest extends GroovyTestCase {

    @Test
    void testCorrectModule() {

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
                        return sin(v);
                    }

                    sin(2*PI);

                    '''

        def parser = new Parser(new StringInputStream(code))

        def ast = parser.parse().build()



    }
}
