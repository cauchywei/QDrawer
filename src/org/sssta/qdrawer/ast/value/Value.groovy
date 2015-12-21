package org.sssta.qdrawer.ast.value

import org.sssta.qdrawer.ast.BaseProperty
import org.sssta.qdrawer.ast.type.Type

/**
 * Created by cauchywei on 15/12/12.
 */
abstract class Value<T> extends BaseProperty {
    abstract Type getType()
    abstract T getJavaValue()
}
