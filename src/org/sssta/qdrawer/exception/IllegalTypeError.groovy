package org.sssta.qdrawer.exception

import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Token

/**
 * Created by cauchywei on 15/12/13.
 */
class IllegalTypeError extends CodeError {
    IllegalTypeError() {
    }

    IllegalTypeError(Token token, String message) {
        super(token, message)
    }

    IllegalTypeError(Node node, String message) {
        super(node, message)
    }
}
