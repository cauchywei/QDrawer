package org.sssta.qdrawer.ast

import org.sssta.qdrawer.ast.value.Value

/**
 * Created by cauchywei on 15/12/12.
 */
class ElementInfo extends HashMap<String,Value> implements Cloneable {
    static final String KEY_VALUE = "value"
    static final String KEY_TYPE = "type"
    static final String KEY_LINE_NUM = "line_num"

    public Value getType() {
        get(KEY_TYPE)
    }

    public Value getValue() {
        get(KEY_VALUE)
    }

    public Value getProperty(String key) {
        get(key)
    }
}
