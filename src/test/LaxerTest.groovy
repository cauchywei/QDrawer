package test

import exception.ReachTheEndOfCodeException
import lexer.Laxer
import lexer.TokenType
import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
/**
 * Created by cauchywei on 15/9/9.
 */
class LaxerTest extends GroovyTestCase{

    @Test
    void testCorrectToken(){
        String code = 'hello 233 C "hello world!" "hello\\tworld\\n" "he1llo\t wor34ld" \n  22.3 22e3 22E3 +-*/%** ()  -- // '


        def reader = new InputStreamReader(new StringInputStream(code))
        def errors = []

        Laxer laxer = new Laxer(reader)
        matchToken(laxer,'hello',TokenType.IDENTIFIER)


        matchToken(laxer,'233',TokenType.NUMBERIC)

        matchToken(laxer,'C',TokenType.IDENTIFIER)

        matchToken(laxer,'hello world!',TokenType.STRING)

        matchToken(laxer,'hello\\tworld\\n',TokenType.STRING)

        matchToken(laxer,'he1llo\t wor34ld',TokenType.STRING)

        matchToken(laxer,'22.3',TokenType.NUMBERIC)

        matchToken(laxer,'22e3',TokenType.NUMBERIC)

        matchToken(laxer,'22E3',TokenType.NUMBERIC)

        matchToken(laxer,'+',TokenType.PLUS)

        matchToken(laxer,'-',TokenType.MINUS)

        matchToken(laxer,'*',TokenType.MUL)

        matchToken(laxer,'/',TokenType.DIV)

        matchToken(laxer,'%',TokenType.MOD)

        matchToken(laxer,'**',TokenType.POWER)

        matchToken(laxer,'(',TokenType.OPEN_BRACKET)

        matchToken(laxer,')',TokenType.CLOSE_BRACKET)

        matchToken(laxer,'--',TokenType.COMMENT)

        matchToken(laxer,'//',TokenType.COMMENT)

        try {
           laxer.getToken([])
        }catch (e){
            assertTrue(e instanceof ReachTheEndOfCodeException)
        }
    }

    @Test
    void testBadToken(){

    }


    static void matchToken(Laxer laxer,String value,TokenType type){
        def errors = []
        def token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals(value,token.getValue())
        assertEquals(type,token.getType())
    }

}
