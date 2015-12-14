package org.sssta.qdrawer.ast

import org.sssta.qdrawer.ast.type.Type
import org.sssta.qdrawer.ast.value.UndefinedValue
import org.sssta.qdrawer.ast.value.Value
/**
 * Created by cauchywei on 15/12/12.
 */
class SymbolInfo extends HashMap<String,BaseProperty> implements Cloneable {

    static final String KEY_VALUE = "value"
    static final String KEY_TYPE = "type"
    static final String KEY_LINE_NUM = "line_num"

    Type type = Type.UNDEFINED
    Value value = new UndefinedValue()
    boolean isConst

    public void setType(Type type) {
        //TODO interrupted for const var?
        put(KEY_TYPE, type)
    }

    public void setValue(Value value) {
        put(KEY_VALUE,value)
    }

    public Value getProperty(String key) {
        get(key)
    }
}
