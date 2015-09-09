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

        def code = 'hello 233 CPP14 Ja_VA "hello world!" "hello\\tworld\\n" "he1llo\t wor34ld" \n  22.3 22e3 22E3 +-*/%** ()  -- // '
        def reader = new InputStreamReader(new StringInputStream(code))
        def laxer = new Laxer(reader)

        matchToken(laxer,'hello',TokenType.IDENTIFIER)
        matchToken(laxer,'233',TokenType.NUMBERIC)
        matchToken(laxer,'CPP14',TokenType.IDENTIFIER)
        matchToken(laxer,'Ja_VA',TokenType.IDENTIFIER)
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

        reader.close()
    }

    @Test
    void testBadToken(){

        catchError('3Q',2,TokenType.NUMBERIC)
        catchError('😳',1,TokenType.UNKNOWN)
        catchError('23.2.',5,TokenType.NUMBERIC)
        catchError('23.2e',5,TokenType.NUMBERIC)
        catchError('23e2e',5,TokenType.NUMBERIC)
        catchError('23.2.2',5,TokenType.NUMBERIC)
        catchError('3.14Q',5,TokenType.NUMBERIC)
        catchError('"hel\nlo"',5,TokenType.STRING)
        catchError('"hello\\"',9,TokenType.STRING)

    }


    static void matchToken(Laxer laxer,String value,TokenType type){
        def errors = []
        def token = laxer.getToken(errors)
        assertEquals([],errors)
        assertEquals(value,token.getValue())
        assertEquals(type,token.getType())
    }

    static void catchError(String code,int col,TokenType type) {

        def reader = new InputStreamReader(new StringInputStream(code))
        def laxer = new Laxer(reader)
        def errors = []
        def token = laxer.getToken(errors)
        assertNull(token)
        assertEquals(1,errors.size())
        assertEquals(col,errors.get(0).getCol())
        assertEquals(type,errors.get(0).getType())
        reader.close()

    }

}
