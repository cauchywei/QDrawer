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

    void eval() {
        def clone = global.copy()

        clone.graphics2D?.translate(0,0)
        clone.graphics2D?.setColor(Color.RED)

        for (int i = 0; i < body.size(); i++) {
            body.get(i).eval(global)
            if (!Console.errors.isEmpty()) {
                break
            }
        }
    }
}
