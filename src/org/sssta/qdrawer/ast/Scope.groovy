package org.sssta.qdrawer.ast

import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.lexer.Token
/**
 * Created by cauchywei on 15/9/14.
 */
class Scope{

    Scope parent
    HashMap<Token,ElementInfo> table

    Scope() {
    }

    Scope(Scope parent) {
        this.parent = parent
    }

    public void putAll(Scope other) {
        for (Token name : other.table.keySet()) {
            table.put(name,other.table.get(name));
        }
    }

    public boolean existLocal(String name) {
        return table.containsKey(name)
    }

    public boolean exist(String name) {
        if (table.containsKey(name)) {
            return true
        } else {
            return parent.exist(name)
        }
    }

    public Value getValue(Token name) {
        def localVal = getValueLocal(name)
        if (localVal == null) {
            return parent.getValue(name)
        }
        return localVal
    }

    public Value getValueLocal(Token name) {
        def var = table.get(name)
        if (var == null) {
            return null
        }
        return var.getValue()
    }

    public Value getType(Token name) {
        def localType = getType()Local(name)
        if (localType == null) {
            return parent.getType(name)
        }
        return localType
    }

    public Value getTypeLocal(Token name) {
        def var = table.get(name)
        if (var == null) {
            return null
        }
        return var.getType()
    }

    public ElementInfo getElement(Token name) {
        def local = table.get(name)
        if (local == null) {
            return parent.getElement(name)
        }
        return local
    }

    public ElementInfo getElementLocal(Token name) {
        table.get(name)
    }
}
