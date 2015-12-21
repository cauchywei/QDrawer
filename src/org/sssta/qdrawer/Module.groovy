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

        importStatements.each {
            def impLib = it.getName().value
            try {
                ast.global.usings << Class.forName(impLib)
            } catch (e) {
                Console.errors << new CodeError(it.name,"Can't found the Java Class " + impLib)
            }
        }

        //TODO process using

        List<Node> moduleBody = []

        for (int i = 0; i < statements.size(); i++) {
            moduleBody << statements.get(i).createAstNode()
        }

        ast.body = moduleBody

        return ast
    }


}
