package org.sssta.qdrawer
import org.sssta.qdrawer.ast.Ast
import org.sssta.qdrawer.ast.node.Node
import org.sssta.qdrawer.lexer.CodeError
import org.sssta.qdrawer.lexer.Token
import org.sssta.qdrawer.statement.ImportStatement
import org.sssta.qdrawer.statement.Statement
import org.sssta.qdrawer.statement.UsingStatement
/**
 * Created by cauchywei on 15/9/9.
 */
class Module  {

    Token name
    List<ImportStatement> importStatements = []
    List<UsingStatement> usingStatements = []
    List<Statement> statements = []

    Ast build() {
        def ast = new Ast()

        //TODO process import
        importStatements.each {

        }

        usingStatements.each {
            def usingLib = it.library.value
            try {
                ast.global.usings << Class.forName(usingLib)
            } catch (e) {
                Console.errors << new CodeError(it.library,"Can't found the Java Class " + impLib)
            }
        }


        List<Node> moduleBody = []
        for (int i = 0; i < statements.size(); i++) {
            moduleBody << statements.get(i).createAstNode()
        }

        ast.body = moduleBody

        return ast
    }


}
