package unitTest

import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.Parser
import org.sssta.qdrawer.ast.value.NumericValue
/**
 * Created by cauchywei on 15/12/21.
 */
class ModuleTest extends GroovyTestCase {

    @Test
    void testCorrectModule() {

        def code = '''module hello;
                    -- import stddrw;

                    using java.lang.Math;

                    const PI = 3.1415926;
                    a = 2;

                    for t form 1 to 10 step 1 {
                        draw(t,t);
                    }

                    if(a == 2)
                        draw(a,a);

                    if(a == 2){
                        draw(a*2,a*2);
                        b = 4*3;
                    }

                    if(a == 2){
                        draw(a,a);
                        b = 4*3;
                    }else{
                        b = -1;
                    }

                    func add(a,b){
                        return a+b;
                    }

                    func max(a,b){
                        if (a>b) a; else b;
                    }

                    c = sin(2*PI);
                    d = add(1,2);
                    e = max(99,100);

                    p = (20,30);
                    '''

        def parser = new Parser(new StringInputStream(code))

        def ast = parser.parse().build()

        def scope = ast.eval()

        def errors = Console.errors

        def var = scope.getValue("PI")
        assertTrue(var instanceof NumericValue)
        assertEquals(scope.getValue("PI").asType(NumericValue).value,3.1415926)

        var = scope.getValue("a")
        assertTrue(var instanceof NumericValue)
        assertEquals(var.asType(NumericValue).value,2)

        var = scope.getValue("b")
        assertTrue(var instanceof NumericValue)
        assertEquals(var.asType(NumericValue).value,12)

        var = scope.getValue("c")
        assertTrue(var instanceof NumericValue)
        assertEquals(var.asType(NumericValue).value,Math.sin(2*3.1415926))

        var = scope.getValue("d")
        assertTrue(var instanceof NumericValue)
        assertEquals(var.asType(NumericValue).value,3)

        var = scope.getValue("e")
        assertTrue(var instanceof NumericValue)
        assertEquals(var.asType(NumericValue).value,100)

    }
}
