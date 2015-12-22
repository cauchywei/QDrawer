package org.sssta.qdrawer.gui

import org.sssta.qdrawer.Console
import org.sssta.qdrawer.ast.Ast

import javax.swing.*
import java.awt.*
/**
 * Created by cauchywei on 15/12/21.
 */
class CanvasPanel extends JPanel {

    Ast ast
    private Image doubleBuffer


    Thread repaintThread = new Thread(){
        @Override
        void run() {
            while (true) {
                try {
                    if (ast != null) {

                        synchronized (ast) {

                            Dimension size = getSize();
                            if (doubleBuffer == null ||
                                    doubleBuffer.getWidth(CanvasPanel.this) != size.width ||
                                    doubleBuffer.getHeight(CanvasPanel.this) != size.height) {
                                doubleBuffer = createImage(size.width.intValue(), size.height.intValue());
                            }


                            def g2 = (Graphics2D) doubleBuffer.getGraphics()
                            ast.global.graphics2D = g2;

                            paint(g2)
                            ast.eval()
                            g2.dispose();
                            getGraphics().drawImage(doubleBuffer, 0, 0, null);
                        }

                    }
                    sleep(200)
                } catch (e) {
                    e.printStackTrace()
                }finally {
                    try {
                        window.onError(Console.errors)
                    } catch (e1) {

                    }
                }

            }
        }
    }

    DrawerWindow window

    CanvasPanel(DrawerWindow window) {
        this.window = window
        repaintThread.start()
    }


    void setAst(Ast ast) {
        this.ast = ast

    }
}
