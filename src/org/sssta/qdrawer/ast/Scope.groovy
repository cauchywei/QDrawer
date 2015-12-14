package org.sssta.qdrawer.ast
import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.Value
/**
 * Created by cauchywei on 15/9/14.
 */
class Scope{

    Scope parent
    HashMap<String,SymbolInfo> table

    Scope() {
    }

    Scope(Scope parent) {
        this.parent = parent
    }

    public void putAll(Scope other) {
        for (String name : other.table.keySet()) {
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

    public void putValue(String name, Value value) {
        def elm
        if (!exist(name)) {
            elm = new SymbolInfo()
        } else {
            elm = getSymbol(name)
        }

        elm.setValue(value)

        table.put(name,elm)
    }

    public void putType(String name, Type type) {
        def elm
        if (!exist(name)) {
            elm = new SymbolInfo()
        } else {
            elm = getSymbol(name)
        }

        elm.setType(type)

        table.put(name,elm)
    }

    public Value getValue(String name) {
        def localVal = getValueLocal(name)
        if (localVal == null) {
            return parent.getValue(name)
        }
        return localVal
    }

    public Value getValueLocal(String name) {
        def var = table.get(name)
        if (var == null) {
            return null
        }
        return var.getValue()
    }

    public Type getType(String name) {
        def localType = getTypeLocal(name)
        if (localType == null) {
            return parent.getType(name)
        }
        return localType
    }

    public Type getTypeLocal(String name) {
        def var = table.get(name)
        if (var == null) {
            return null
        }
        return var.getType()
    }

    public SymbolInfo getSymbol(String name) {
        def local = table.get(name)
        if (local == null) {
            return parent.getSymbol(name)
        }
        return local
    }

    public SymbolInfo getElementLocal(String name) {
        table.get(name)
    }
}
