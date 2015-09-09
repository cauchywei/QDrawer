package lexer
import exception.ReachTheEndOfCodeException
/**
 * Created by cauchywei on 15/9/9.
 */
class Laxer {


    enum  ParseState
    {
        BEGIN,
        NUMERIC,
        IDENTIFIER,
        STRING,
        STRING_ESCAPING
    }

    static final int DEFAULT_CHAR_BUFFER_SIZE = 10

    List<Token> mTokenBuffer = []
    List<Character> mCharBuffer = []
    InputStreamReader  mStreamReader
    int col = 1,row = 1


    Laxer(InputStreamReader mStreamReader) {
        "]\
p-=uy678"
        this.mStreamReader = mStreamReader
    }


    Token getToken(List<CodeError> errors){

        if (!mTokenBuffer.isEmpty()){

            def remove = mTokenBuffer.remove(0)
            return remove
        }

        def state = ParseState.BEGIN
        StringBuilder buffer = new StringBuilder()
        def c

        while (c = takeChar()) {


            switch (state) {

                case ParseState.BEGIN:
                    switch (c){
                        case '(':
                            return new Token(TokenType.OPEN_BRACKET)
                        case ')':
                            return new Token(TokenType.CLOSE_BRACKET)
                        case ',':
                            return new Token(TokenType.COMMA)
                        case '+':
                            return new Token(TokenType.PLUS)
                        case '-':
                            def next = peekChar()
                            if (next == '-'){
                                takeChar()
                                return new Token('--',  TokenType.COMMENT)
                            }else {
                                return new Token(TokenType.MINUS)
                            }
                        case '*':
                            def next = peekChar()
                            if (next == '*'){
                                takeChar()
                                return new Token(TokenType.POWER)
                            }else {
                                return new Token(TokenType.MUL)
                            }
                        case '/':
                            def next = peekChar()
                            if (next == '/'){
                                takeChar()
                                return new Token('//',  TokenType.COMMENT)
                            }else {
                                return new Token(TokenType.DIV)
                            }
                        case '%':
                            return new Token(TokenType.MOD)
                        case ';':
                            return new Token(TokenType.SEMICO)
                        case '"':
                            state = ParseState.STRING
                            break

                        case Character.isDigit(c):
                            buffer << c
                            state = ParseState.NUMERIC
                            break;
                        case '\n':
                            row++
                            col = 0
                        case '\t':
                        case '\r':
                        case ' ':
                            break
                        default:

                        if(c ==~ /[a-zA-Z_]/) {
                            buffer << c
                            state = ParseState.IDENTIFIER
                            break;
                        }else if (c ==~ /[0-9]/){
                            buffer << c
                            state = ParseState.NUMERIC
                            break;
                        }else {
                            errors << generateError(TokenType.UNKNOWN, "Unexcepted character: '" + c + "'")
                            return null
                        }
                    }

                    break
                case ParseState.NUMERIC:
                    if ( Character.isDigit(c)) {
                        buffer << c
                    }else if (c == '.'){

                        if (buffer.contains('.') || buffer.contains('e') || buffer.contains('E')){
                            errors << generateError(TokenType.NUMBERIC,'Unexcepted character \'' + c + '\' after numeric ')
                            return null
                        } else if (!(peekChar() ==~ /[0-9]/)){
                            errors << generateError(TokenType.NUMBERIC,'Excepted a number after the dot.')
                            return null
                        } else {
                            buffer << c
                        }

                    }else if (Character.toLowerCase(c) == 'e') {

                        if (buffer.contains('e') || buffer.contains('E')){
                            errors << generateError(TokenType.NUMBERIC,'Unexcepted character \'' + c + '\' after numeric ')
                            return null
                        }else if (!(peekChar() ==~ /[0-9]/)){
                            errors << generateError(TokenType.NUMBERIC,'Excepted a number after the exp identifier.')
                            return null
                        } else {
                            buffer << c
                        }
                    }else if ( c ==~ /[a-zA-Z_]/){
                        errors << generateError(TokenType.NUMBERIC,'Unexcepted character \'' + c + '\' after numeric ')
                        return null
                    }else {
                        backforwardChar(c)
                        return new Token(buffer.toString(), TokenType.NUMBERIC)
                    }

                    break
                case ParseState.IDENTIFIER:
                    if (!(c ==~ /[a-zA-Z_0-9]/)) {
                        backforwardChar(c)
                        return new Token(buffer.toString(), TokenType.IDENTIFIER)
                    }else {
                        buffer << c
                    }

                    break
                case ParseState.STRING:

                    if (c == '"') {
                        return new Token(buffer.toString(), TokenType.STRING)
                    }else if (c == '\\') {
                        buffer << c
                        state = ParseState.STRING_ESCAPING
                    }else if (c == '\n'){
                        errors << generateError(TokenType.STRING, 'String must in a line.');
                        return null;
                    }else {
                        buffer << c
                    }

                    break
                case ParseState.STRING_ESCAPING:
                    if (c == '\n') {
                        errors << generateError(TokenType.STRING, 'String must in a line.');
                        return null;
                    } else {
                        buffer << c
                        state = ParseState.STRING;
                    }
                    break
            }

            col++
        }


        switch (state){

            case ParseState.BEGIN:
                throw new ReachTheEndOfCodeException()
                return null;
            case ParseState.NUMERIC:
                return new Token(buffer.toString(), TokenType.NUMBERIC)
            case ParseState.IDENTIFIER:
                return new Token(buffer.toString(), TokenType.NUMBERIC)
            case ParseState.STRING:
            case ParseState.STRING_ESCAPING:
                errors << generateError(TokenType.STRING,'String end excepted.')
                return null;
        }

    }

    private Character peekChar() throws ReachTheEndOfCodeException{
        if (mCharBuffer.isEmpty()) {
            inflateCharBuffer(DEFAULT_CHAR_BUFFER_SIZE)
        }

        if (mCharBuffer.isEmpty()) {
            return null
        }

        mCharBuffer.first()
    }

    private Character takeChar() throws ReachTheEndOfCodeException {

        if (mCharBuffer.isEmpty()) {
            inflateCharBuffer(DEFAULT_CHAR_BUFFER_SIZE)
        }

        if (mCharBuffer.isEmpty()) {
            return null
        }

        mCharBuffer.remove(0)
    }

    void backforwardToken(Token token){
        mTokenBuffer.add(0,token)
    }

    boolean hasNext(){
        return peekChar() != null
    }

    private void backforwardChar(char chr){
        if (col - 1 > 0)
            col--
        mCharBuffer.add(0,chr)
    }

    private void inflateCharBuffer(int size){
        int count = 0;
        char chr;
        while (count++ < size && (chr = mStreamReader.read()) != -1){
            mCharBuffer << chr
        }
    }

    CodeError generateError(TokenType type,String msg){
        new CodeError(col: col,row: row,message: msg,type: type)
    }

}
