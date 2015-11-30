import org.apache.tools.ant.filters.StringInputStream
import org.junit.Test
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.statement.expression.Expression
/**
 * Created by cauchywei on 15/9/10.
 */
class ExpressionTest extends GroovyTestCase{

    @Test
    void testCorrectExpression() {

        test("1","1")
        test("2+3","(+ 2 3)")
        test("1+2+3","(+ (+ 1 2) 3)")
        test("1+2+3+4","(+ (+ (+ 1 2) 3) 4)")

        test("1*2+3","(+ (* 1 2) 3)")
        test("1+2*3","(+ 1 (* 2 3))")
        test("1+2*3+4","(+ (+ 1 (* 2 3)) 4)")

        test("2/3","(/ 2 3)")
        test("1/2/3","(/ (/ 1 2) 3)")
        test("1/2*3/4","(/ (* (/ 1 2) 3) 4)")
        test("(((((1)))))","1")

        test("x >= 2 && x < 10","(&& (>= x 2) (< x 10))")
        test("(year%4==0 && year%100!=0) || (year%400==0)","(|| (&& (== (% year 4) 0) (!= (% year 100) 0)) (== (% year 400) 0))")

        test("hasGirlFriend(doge) || !coding(doge,10)","(|| (hasGirlFriend doge) (! (coding doge 10)))")

    }

    static void test(String code,excepted) {
        def errors = []
        def laxer = new Laxer(new StringInputStream(code))
        assertEquals(excepted,Expression.parse(laxer, errors).toString())
        assertEquals(0,errors.size())
    }

}
