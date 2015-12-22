package org.sssta.qdrawer.lexer
import org.sssta.qdrawer.exception.ReachTheEndOfCodeException
/**
 * Created by cauchywei on 15/9/9.
 */
class Laxer {


    static enum  ParseState
    {
        BEGIN,
        NUMERIC,
        IDENTIFIER,
        COMMENT,
        STRING,
        STRING_ESCAPING
    }


    static final int DEFAULT_CHAR_BUFFER_SIZE = 30
    static final int DEFAULT_TOKEN_BUFFER_SIZE = 20

    public static final def KEY_IDENTIFIERS =[TokenType.MODULE    .name(),
                                              TokenType.IMPORT    .name(),
                                              TokenType.USING     .name(),
                                              TokenType.FUNC      .name(),
                                              TokenType.CONST     .name(),
                                              TokenType.IS        .name(),
                                              TokenType.ORIGIN    .name(),
                                              TokenType.SCALE     .name(),
                                              TokenType.ROT       .name(),
                                              TokenType.T         .name(),
                                              TokenType.FOR       .name(),
                                              TokenType.FROM      .name(),
                                              TokenType.TO        .name(),
                                              TokenType.AND       .name(),
                                              TokenType.OR        .name(),
                                              TokenType.TRUE      .name(),
                                              TokenType.FALSE     .name(),
                                              TokenType.STEP      .name(),
                                              TokenType.IF        .name(),
                                              TokenType.RETURN    .name(),
                                              TokenType.ELSE      .name(),]


    List<Character> charBuffer = []
    List<Token> tokenBuffer = []
    List<CodeError> errors = []
    Stack<Token> usedTokens = []
    InputStream  mStreamReader
    int col = 1
    int row = 1
    int currentIndex;

    int startIndex

    Laxer(InputStream mStreamReader) {
        this.mStreamReader = mStreamReader
    }


    private Token getToken(){

        def state = ParseState.BEGIN
        StringBuilder buffer = new StringBuilder()
        def c

        while (c = takeChar()) {


            switch (state) {

                case ParseState.BEGIN:
                    startIndex = currentIndex
                    switch (c){
                        case '(':
                            return generateToken(TokenType.OPEN_BRACKET)
                        case ')':
                            return generateToken(TokenType.CLOSE_BRACKET)
                        case '{':
                            return generateToken(TokenType.OPEN_SCOPE)
                        case '}':
                            return generateToken(TokenType.CLOSE_SCOPE)
                        case ',':
                            return generateToken(TokenType.COMMA)
                        case '+':
                            return generateToken(TokenType.PLUS)
                        case '-':
                            def next = peekChar()
                            if (next == '-'){
                                takeChar()
                                buffer << '--'
//                                return generateToken('--',  TokenType.COMMENT)
                                state = ParseState.COMMENT
                                break
                            }else {
                                return generateToken(TokenType.MINUS)
                            }
                        case '*':
                            def next = peekChar()
                            if (next == '*'){
                                takeChar()
                                return generateToken(TokenType.POWER)
                            }else {
                                return generateToken(TokenType.MUL)
                            }
                        case '/':
                            def next = peekChar()
                            if (next == '/'){
                                takeChar()
                                buffer << '//'
                                state = ParseState.COMMENT
                                break
                            }else {
                                return generateToken(TokenType.DIV)
                            }
                        case '%':
                            return generateToken(TokenType.MOD)
                        case '=':
                            def next = peekChar()
                            if (next == '='){
                                takeChar()
                                return generateToken(TokenType.EQ)
                            }else {
                                return generateToken(TokenType.ASSIGMENT)
                            }
                        case '&':
                            def next = peekChar()
                            if (next == '&'){
                                takeChar()
                                return generateToken(TokenType.AND)
                            }else {
//                                return generateToken(TokenType.ASSIGMENT)
                                return null
                            }
                        case '|':
                            def next = peekChar()
                            if (next == '|'){
                                takeChar()
                                return generateToken(TokenType.OR)
                            }else {
//                                return generateToken(TokenType.ASSIGMENT)
                                return null
                            }
                        case '>':
                            def next = peekChar()
                            if (next == '='){
                                takeChar()
                                return generateToken(TokenType.GTE)
                            }else {
                                return generateToken(TokenType.GT)
                            }
                        case '<':
                            def next = peekChar()
                            if (next == '='){
                                takeChar()
                                return generateToken(TokenType.LTE)
                            }else {
                                return generateToken(TokenType.LT)
                            }
                        case '!':
                            def next = peekChar()
                            if (next == '='){
                                takeChar()
                                return generateToken(TokenType.NE)
                            }else {
                                return generateToken(TokenType.NOT)
                            }
                        case ';':
                            return generateToken(TokenType.SEMICO)
                        case '"':
                            buffer << c
                            state = ParseState.STRING
                            break
                        case {Character.isDigit(c)}:
                            buffer << c
                            state = ParseState.NUMERIC
                            break;
                        case '\n':
                            if (state == ParseState.COMMENT) {
                                generateToken(buffer.toString(),TokenType.COMMENT);
                            }
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
                        return generateToken(buffer.toString(), TokenType.NUMBERIC)
                    }

                    break
                case ParseState.IDENTIFIER:
                    if (!(c ==~ /[a-zA-Z_0-9\.]/)) {
                        backforwardChar(c)
                        return generateTokenForIdentifier(buffer.toString())
                    }else {
                        buffer << c
                    }

                    break
                case ParseState.STRING:

                    if (c == '"') {
                        buffer << '"'
                        return generateToken(buffer.toString(), TokenType.STRING)
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

                case ParseState.COMMENT:
                    if (c == '\n'){
                        return generateToken(buffer.toString(),TokenType.COMMENT)
                    }else {
                        buffer << c
                    }
                    break
            }

        }


        switch (state){

            case ParseState.BEGIN:
                throw new ReachTheEndOfCodeException()
                return null;
            case ParseState.NUMERIC:
                return generateToken(buffer.toString(), TokenType.NUMBERIC)
            case ParseState.IDENTIFIER:
                return generateTokenForIdentifier(buffer.toString())
            case ParseState.STRING:
            case ParseState.STRING_ESCAPING:
                errors << generateError(TokenType.STRING,'String end excepted.')
                return null;
            case ParseState.COMMENT:
                return generateToken(buffer.toString(),TokenType.COMMENT)
        }

    }


    private Token generateTokenForIdentifier(String identifier){
        def upperCase = identifier.toUpperCase()
        if (upperCase in KEY_IDENTIFIERS){
            def type = TokenType.valueOf(upperCase)
            return generateToken(identifier,type)
        }else {
            return generateToken(identifier,TokenType.IDENTIFIER)
        }
    }

    private Character peekChar(){
        if (charBuffer.isEmpty()) {
            inflateCharBuffer(DEFAULT_CHAR_BUFFER_SIZE)
        }

        if (charBuffer.isEmpty()) {
            throw new ReachTheEndOfCodeException()
        }

        charBuffer.first()
    }

    private Character takeChar(){

        if (charBuffer.isEmpty()) {
            inflateCharBuffer(DEFAULT_CHAR_BUFFER_SIZE)
        }

        if (charBuffer.isEmpty()) {
            return null
        }


        def remove = charBuffer.remove(0)

        if (remove == '\n') {
            row++
            col = 0
        } else {
            col++
        }
        currentIndex++

        return remove
    }

    private boolean hasNextChar(){
        return peekChar() != null
    }

    private void backforwardChar(char chr){
        if (col - 1 > 0)
            col--
        else {
            row --
            col = 0
        }
        currentIndex--
        charBuffer.add(0,chr)
    }

    private void inflateCharBuffer(int size){
        int count = 0;
        char chr;
        while (count++ < size && (chr = mStreamReader.read()) != -1){
            charBuffer << chr
        }
    }

    public boolean hasNext(){
        peekToken() != null
    }

    public Token takeToken() {
        if (tokenBuffer.isEmpty()){
            inflateBuffer(DEFAULT_TOKEN_BUFFER_SIZE)
        }

        if (tokenBuffer.isEmpty()) {
            return null
        }


        def remove = tokenBuffer.remove(0)
        usedTokens << remove

        return remove
    }

    public Token peekToken() {
        if (tokenBuffer.isEmpty()){
            inflateBuffer(DEFAULT_TOKEN_BUFFER_SIZE)
        }

        if (tokenBuffer.isEmpty()) {
            return null
        }

        return tokenBuffer.get(0)
    }



    private void inflateBuffer(int size) {
        def count = 0
        try {
            while (count++ < size && hasNextChar()) {
                tokenBuffer << getToken()
            }
        } catch (ReachTheEndOfCodeException e) {
            //ignore
        }

    }

    Token generateToken(TokenType type){
        def token = new Token(type)
        token.col = col
        token.row = row
        token.start = currentIndex - type.name().length() - 1
        token.end = currentIndex
        token
    }
    Token generateToken(String value, TokenType type) {
        def token = generateToken(type)
        token.value = value
        token.start = currentIndex - value.length()
        return token
    }

    CodeError generateError(TokenType type,String msg){
        new CodeError(col: col,row: row,message: msg,type: type)
    }


    int save() {
        return usedTokens.size()
    }

    boolean go(int index) {
        if (index < 0) {
            false
        }

        int origin = save();

        try {
            while (usedTokens.size() > index) {
                tokenBuffer.add(0,usedTokens.pop())
            }

            while (usedTokens.size() < index) {
                usedTokens.push tokenBuffer.remove(0)
            }
        } catch (Exception e) {
            go(origin);
            return false
        }
    }

}
