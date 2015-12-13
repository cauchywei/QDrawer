package org.sssta.qdrawer.exception

import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Token

/**
 * Created by cauchywei on 15/12/13.
 */
class IllegalOperateError extends CodeError {
    IllegalOperateError() {
    }

    IllegalOperateError(Token token, String message) {
        super(token, message)
    }

    IllegalOperateError(Node node, String message) {
        super(node, message)
    }
}
