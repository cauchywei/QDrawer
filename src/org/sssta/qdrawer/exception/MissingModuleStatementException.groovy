package org.sssta.qdrawer.exception

/**
 * Created by cauchywei on 15/9/10.
 */
class MissingModuleStatementException extends Exception {
    MissingModuleStatementException() {
        super('Excepted a module declaration in line one.')
    }
}
