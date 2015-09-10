package org.sssta.qdrawer.exception

/**
 * Created by cauchywei on 15/9/9.
 */
class BadTokenException extends Exception{
    BadTokenException(String token) {
        super('Parsed bad token \"' + token + '\"')
    }
}
