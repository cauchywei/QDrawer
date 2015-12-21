package org.sssta.qdrawer.ast
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.node.Node

import java.awt.*
import java.util.List
/**
 * Created by cauchywei on 15/12/21.
 */
class Ast {

    Scope global
    List<Node> body

    Ast(Scope global) {
        this.global = global
    }

    Ast() {
       global = new Scope()
    }

    Scope eval() {
        def cloneScope = global.copy()

        cloneScope.graphics2D?.translate(0,0)
        cloneScope.graphics2D?.setColor(Color.RED)

        for (int i = 0; i < body.size(); i++) {
            body.get(i).eval(cloneScope)
            if (!Console.errors.isEmpty()) {
                break
            }
        }

        return cloneScope
    }
}
