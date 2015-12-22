package org.sssta.qdrawer.ast

import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.UndefinedValue
import org.sssta.qdrawer.ast.value.Value
import org.sssta.qdrawer.ast.value.VoidValue

import java.awt.*
import java.util.List
/**
 * Created by cauchywei on 15/9/14.
 */
class Scope {

    public static final int DEFAULT_POINT_RADIUS = 2

    static Graphics2D graphics2D
    static int pointRadius = DEFAULT_POINT_RADIUS

    Scope parent
    HashMap<String, SymbolInfo> table = new HashMap<>()
    List<Class> usings = [];
    List<Ast> imports = [];



    boolean isFunctionScope = false
    boolean returnFlag = false
    Value returnValue = new VoidValue()

    Scope() {
    }

    Scope(Scope parent) {
        this.parent = parent
        this.isFunctionScope = parent.isFunctionScope
    }

    public void putAll(Scope other) {
        for (String name : other.table.keySet()) {
            table.put(name, other.table.get(name));
        }
    }

    public boolean existLocal(String name) {
        return table.containsKey(name)
    }

    public boolean exist(String name) {
        if (table.containsKey(name)) {
            return true
        } else if (parent != null) {
            return parent.exist(name)
        } else {
            return false
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

        table.put(name, elm)
    }

    public void putType(String name, Type type) {
        def elm
        if (!exist(name)) {
            elm = new SymbolInfo()
        } else {
            elm = getSymbol(name)
        }

        elm.setType(type)

        table.put(name, elm)
    }

    public Value getValue(String name) {
        def localVal = getValueLocal(name)
        if (localVal == null ) {
            if (parent != null) {
                return parent.getValue(name)
            }
            else {
                return new UndefinedValue()
            }
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
            if (parent != null) {
                return parent.getType(name)
            } else {
                return Type.UNDEFINED
            }
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

    public void putSymbol(String name, SymbolInfo symbol) {
        table.put(name, symbol)
    }

    public SymbolInfo getSymbol(String name) {
        def local = table.get(name)
        if (local == null) {
            if (parent != null) {
                return parent.getSymbol(name)
            }else{
                return null
            }
        }
        return local
    }

    public SymbolInfo getElementLocal(String name) {
        table.get(name)
    }

    public List<Class> getUsings() {
        if (parent != null) {
            return parent.getUsings()
        }
        return usings
    }

    public Scope copy() {
        def copy = new Scope()
        copy.parent = parent
        copy.table = new HashMap<>()
        copy.putAll(this)
        copy.usings.addAll(usings)
        copy.imports.addAll(imports)

        copy.isFunctionScope = isFunctionScope
        copy.returnFlag = returnFlag
        copy.returnValue = returnValue
        return copy
    }
}
