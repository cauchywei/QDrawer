package org.sssta.qdrawer.ast.type

import org.sssta.qdrawer.ast.BaseProperty

/**
 * Created by cauchywei on 15/12/13.
 */
class Type extends BaseProperty {


    static final Type BOOLEAN = new BooleanType()
    static final Type STRING = new StringType()
    static final Type NUMERIC = new NumericType()
    static final Type POINT = new PointType()
    static final Type VOID = new VoidType()
    static final Type FUNCTION = new FunctionType()



    String name

    Type(String name) {
        this.name = name
    }

    boolean equals(o) {
        if (this.is(o)) return true
        if (getClass() != o.class) return false

        Type type = (Type) o

        if (name != type.name) return false

        return true
    }

    int hashCode() {
        return (name != null ? name.hashCode() : 0)
    }
}
