package org.sssta.qdrawer.statement.expression
/**
 * Created by cauchywei on 15/11/30.
 */
class InvokeExpression extends Expression {
    Expression function
    List<Expression> arguments

    @Override
    public String toString() {
        def sb = new StringBuilder('(' + function + ' ')
        arguments.each { sb.append(it.toString()).append(' ')}
        sb.deleteCharAt(sb.size()-1)
        sb.append(')')
        return sb.toString()
    }
}
