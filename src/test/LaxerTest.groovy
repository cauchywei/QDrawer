package test
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
        String code = 'hello 233 C "hello world!" "hello\\tworld\\n" "he1llo\t wor34ld"  22.3 22e3 22E3 +-*/%** ()  -- // '


        def reader = new InputStreamReader(new StringInputStream(code))
        def errors = []

        Laxer laxer = new Laxer(reader)

        def token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('hello',token.getValue())
        assertEquals(TokenType.IDENTIFIER,token.getType())


        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('233',token.getValue())
        assertEquals(TokenType.NUMBERIC,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('C',token.getValue())
        assertEquals(TokenType.IDENTIFIER,token.getType())


        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('hello world!',token.getValue())
        assertEquals(TokenType.STRING,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('hello\\t world\\n',token.getValue())
        assertEquals(TokenType.STRING,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('he1llo\t wor34ld',token.getValue())
        assertEquals(TokenType.STRING,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('22.3',token.getValue())
        assertEquals(TokenType.NUMBERIC,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('22e3',token.getValue())
        assertEquals(TokenType.NUMBERIC,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('22e4',token.getValue())
        assertEquals(TokenType.NUMBERIC,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('+',token.getValue())
        assertEquals(TokenType.PLUS,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('-',token.getValue())
        assertEquals(TokenType.MINUS,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('*',token.getValue())
        assertEquals(TokenType.MUL,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('/',token.getValue())
        assertEquals(TokenType.DIV,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('%',token.getValue())
        assertEquals(TokenType.MOD,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('**',token.getValue())
        assertEquals(TokenType.POWER,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('(',token.getValue())
        assertEquals(TokenType.OPEN_BRACKET,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals(')',token.getValue())
        assertEquals(TokenType.CLOSE_BRACKET,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('--',token.getValue())
        assertEquals(TokenType.COMMENT,token.getType())

        token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals('//',token.getValue())
        assertEquals(TokenType.COMMENT,token.getType())
    }

}
