package test

import lexer.Laxer
import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test

/**
 * Created by cauchywei on 15/9/9.
 */
class LaxerTest extends GroovyTestCase{

    @Test
    void testCorrectToken(){
        String code = 'hello 233 cauchy "23333"  "hello world" 22.3 22e5 3E2 '


        def reader = new InputStreamReader(new StringInputStream(code))
        def errors = []

        Laxer laxer = new Laxer(reader)
        laxer.getToken(errors)

        assertEquals([],errors)

    }

}
