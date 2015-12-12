package org.sssta.qdrawer.ast.type

/**
 * Created by cauchywei on 15/12/13.
 */
class UnionType extends Type {

    public Set<Type> types = [];

    UnionType() {
        super('Union')
    }

    public void add(Type type) {
        if (type instanceof UnionType) {
            types.addAll(((UnionType) type).types);
        } else {
            types.add(type);
        }
    }

    public Type first() {
        return types.iterator().next();
    }
    public int size() {
        types.size()
    }

    public static Type union(Collection<Type> types) {
        UnionType u = new UnionType();
        for (Type v : types) {
            u.add(v);
        }
        if (u.size() == 1) {
            return u.first();
        } else {
            return u;
        }
    }


    public static Type union(Type... types) {
        UnionType u = new UnionType();
        for (Type v : types) {
            u.add(v);
        }
        if (u.size() == 1) {
            return u.first();
        } else {
            return u;
        }
    }


}
