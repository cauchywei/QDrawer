package test
import org.sssta.qdrawer.exception.ReachTheEndOfCodeException
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType
import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
/**
 * Created by cauchywei on 15/9/9.
 */
class LaxerTest extends GroovyTestCase{

    @Test
    void testCorrectToken() {

        def code = 'import imPOrt const is origin scale rot for from to t step draw hello 233 CPP14 Ja_VA "hello world!" "hello\\tworld\\n" "he1llo\t wor34ld" \n  22.3 22e3 22E3 +-*/%** ()  > < >= <= = != ! or and   -- QAQ \n// 23333333333\n'
        def reader = new InputStreamReader(new StringInputStream(code))
        def laxer = new Laxer(reader)

        matchToken(laxer, 'import', TokenType.IMPORT)
        matchToken(laxer, 'imPOrt', TokenType.IMPORT)
        matchToken(laxer, 'const', TokenType.CONST)
        matchToken(laxer, 'is', TokenType.IS)
        matchToken(laxer, 'origin', TokenType.ORIGIN)
        matchToken(laxer, 'scale', TokenType.SCALE)
        matchToken(laxer, 'rot', TokenType.ROT)
        matchToken(laxer, 'for', TokenType.FOR)
        matchToken(laxer, 'from', TokenType.FROM)
        matchToken(laxer, 'to', TokenType.TO)
        matchToken(laxer, 't', TokenType.T)
        matchToken(laxer, 'step', TokenType.STEP)
        matchToken(laxer, 'draw', TokenType.DRAW)

        matchToken(laxer, 'hello', TokenType.IDENTIFIER)
        matchToken(laxer, '233', TokenType.NUMBERIC)
        matchToken(laxer, 'CPP14', TokenType.IDENTIFIER)
        matchToken(laxer, 'Ja_VA', TokenType.IDENTIFIER)
        matchToken(laxer, 'hello world!', TokenType.STRING)
        matchToken(laxer, 'hello\\tworld\\n', TokenType.STRING)
        matchToken(laxer, 'he1llo\t wor34ld', TokenType.STRING)
        matchToken(laxer, '22.3', TokenType.NUMBERIC)
        matchToken(laxer, '22e3', TokenType.NUMBERIC)
        matchToken(laxer, '22E3', TokenType.NUMBERIC)
        matchToken(laxer, '+', TokenType.PLUS)
        matchToken(laxer, '-', TokenType.MINUS)
        matchToken(laxer, '*', TokenType.MUL)
        matchToken(laxer, '/', TokenType.DIV)
        matchToken(laxer, '%', TokenType.MOD)
        matchToken(laxer, '**', TokenType.POWER)
        matchToken(laxer, '(', TokenType.OPEN_BRACKET)
        matchToken(laxer, ')', TokenType.CLOSE_BRACKET)
        matchToken(laxer, '>', TokenType.GT)
        matchToken(laxer, '<', TokenType.LT)
        matchToken(laxer, '>=', TokenType.GTE)
        matchToken(laxer, '<=', TokenType.LTE)
        matchToken(laxer, '=', TokenType.EQ)
        matchToken(laxer, '!=', TokenType.UE)
        matchToken(laxer, '!', TokenType.NOT)
        matchToken(laxer, 'or', TokenType.OR)
        matchToken(laxer, 'and', TokenType.AND)

        matchToken(laxer, '-- QAQ ', TokenType.COMMENT)
        matchToken(laxer, '// 23333333333', TokenType.COMMENT)

        try {
            laxer.takeToken()
        } catch (e) {
            assertTrue(e instanceof ReachTheEndOfCodeException)
        }

        reader.close()

        code = 'FOR T FROM 0 TO 2*PI STEP PI/50 DRAW(SIN(T),COS(T));'
        reader = new InputStreamReader(new StringInputStream(code))
        laxer = new Laxer(reader)
        def token

        while (laxer.hasNext()) {
            token = laxer.takeToken()
            println token.value
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

        def token = laxer.takeToken()
        assertEquals([],laxer.errors)
        assertEquals(value,token.getValue())
        assertEquals(type,token.getType())
    }

    static void catchError(String code,int col,TokenType type) {

        def reader = new InputStreamReader(new StringInputStream(code))
        def laxer = new Laxer(reader)
        def errors = []
        def token = laxer.takeToken()
        assertNull(token)
        assertEquals(1,laxer.errors.size())
        assertEquals(col,errors.get(0).getCol())
        assertEquals(type,errors.get(0).getType())
        reader.close()

    }

}
