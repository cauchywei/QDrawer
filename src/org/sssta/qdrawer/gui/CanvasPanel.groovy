package org.sssta.qdrawer.gui

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
                    synchronized (ast){
                        if (ast != null) {
                            println "draw..."

                            Dimension size = getSize();
                            if (doubleBuffer == null ||
                                    doubleBuffer.getWidth(CanvasPanel.this) != size.width ||
                                    doubleBuffer.getHeight(CanvasPanel.this) != size.height)
                            {
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

                }

            }
        }
    }

    CanvasPanel() {
        repaintThread.start()
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g)


    }

    @Override
    public void update(Graphics g) {
        Dimension size = getSize();
        if (doubleBuffer == null ||
                doubleBuffer.getWidth(this) != size.width ||
                doubleBuffer.getHeight(this) != size.height)
        {
            doubleBuffer = createImage(size.width.intValue(), size.height.intValue());
        }


        if (doubleBuffer != null) {
            Graphics g2 = doubleBuffer.getGraphics();


//
//            g2.setColor(Color.WHITE)
//            g2.drawRect(0,0,size.width.intValue(), size.height.intValue())
//            g2.setColor(Color.RED)


            paint(g2);
            g2.dispose();
            g.drawImage(doubleBuffer, 0, 0, null);
        }
        else {
            paint(g);
        }
    }

    synchronized void setAst(Ast ast) {
        this.ast = ast

    }
}
