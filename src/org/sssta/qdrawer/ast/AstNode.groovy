package org.sssta.qdrawer.ast

/**
 * Created by cauchywei on 15/9/10.
 */
abstract class AstNode {
    AstNode parent;
    abstract void accept(AstVisitor visitor);
}
