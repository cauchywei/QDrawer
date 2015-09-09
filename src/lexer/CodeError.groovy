package lexer
/**
 * Created by cauchywei on 15/9/9.
 */
class CodeError {

    int col,row
    String message


    @Override
    public String toString() {
        return "Error{" +
                "col=" + col +
                ", row=" + row +
                ", message='" + message + '\'' +
                '}';
    }
}
