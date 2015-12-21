package org.sssta.qdrawer.gui

import org.sssta.qdrawer.lexer.CodeError

/**
 * Created by cauchywei on 15/12/21.
 */
interface ErrorListener{
    void onError(CodeError error)
}