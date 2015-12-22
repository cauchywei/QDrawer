package org.sssta.qdrawer.gui

import org.apache.tools.ant.filters.StringInputStream
import org.sssta.qdrawer.Console
import org.sssta.qdrawer.Parser
import org.sssta.qdrawer.lexer.CodeError

import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.PlainDocument
import java.awt.*

/**
 * Created by cauchywei on 15/12/21.
 */
class DrawerWindow extends JFrame {

    interface ErrorListener {
        void onError(CodeError error);
    }

    JTextPane codePane
    CanvasPanel canvas
    JTextPane consolePane = new JTextPane()
    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane codeSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);


    HighLightRender highLightRender


    DrawerWindow() {
        setTitle("QDrawer")
        setBackground(new Color(30, 30, 20))

        def screenSize = Toolkit.getDefaultToolkit().getScreenSize()


        def container = new JPanel()
        container.setLayout(new BorderLayout());


        codePane = new JTextPane()
        codePane.setFont(new Font("Consolas", Font.BOLD, 16))
        codePane.setBackground(new Color(57, 57, 57))
        codePane.setForeground(new Color(200, 200, 200))
        codePane.setSelectedTextColor(new Color(200, 200, 200))
        codePane.setSelectionColor(new Color(45, 88, 147))
        codePane.setCaretColor(new Color(220, 220, 220))
        codePane.setMargin(new Insets(20, 20, 20, 20))
        codePane.setMinimumSize(new Dimension((screenSize.getWidth() / 2).intValue(), 200))
        def doc = codePane.getDocument()
        doc.putProperty(PlainDocument.tabSizeAttribute, 4);

        consolePane.setFont(new Font("Consolas", Font.BOLD, 16))
        consolePane.setBackground(new Color(57, 57, 57))
        consolePane.setForeground(new Color(255, 131, 126))
        consolePane.setSelectedTextColor(new Color(200, 200, 200))
        consolePane.setSelectionColor(new Color(45, 88, 147))
        consolePane.setCaretColor(new Color(220, 220, 220))
        consolePane.setMargin(new Insets(15, 15, 15, 15))
        consolePane.setEditable(false)
        consolePane.setText("Code Error: hahahahah")

        codeSplitPane.setTopComponent(new JScrollPane(codePane))
        codeSplitPane.setBottomComponent(new JScrollPane(consolePane))


        codeSplitPane.setDividerLocation((screenSize.getHeight() * 0.7).intValue())

        splitPane.setLeftComponent(codeSplitPane)

        canvas = new CanvasPanel(this)
        canvas.setMinimumSize(new Dimension((screenSize.getWidth() / 2).intValue(), screenSize.getHeight().intValue()))

        splitPane.setRightComponent(canvas)
        splitPane.setDividerLocation((screenSize.getWidth() / 2.3).intValue())

        getContentPane().add(splitPane)

        setMinimumSize(screenSize)
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack()
        setVisible(true)

        highLightRender = new HighLightRender(codePane)

        codePane.getDocument().addDocumentListener(highLightRender)
        codePane.getDocument().addDocumentListener(new DocumentListener() {

            long lastTime
            Thread lastThread

            @Override
            void insertUpdate(DocumentEvent e) {
                onChange(e)
            }

            @Override
            void removeUpdate(DocumentEvent e) {
                onChange(e)
            }

            @Override
            void changedUpdate(DocumentEvent e) {
            }

            void onChange(DocumentEvent event) {
//                if (lastTime + 200 < System.currentTimeMillis()) {
                    lastTime = System.currentTimeMillis()
                    try {
                        try {
                            lastThread?.stop()
                        } catch (e1) {

                        }
                        lastThread = new Thread() {
                            @Override
                            void run() {

                                try {
                                    Console.errors.clear()
                                    def code = codePane.getText()
                                    def parser = new Parser(new StringInputStream(code))
                                    def module = parser.parse()
                                    def ast = module.build()
                                    canvas.ast = ast
                                } catch (codeError) {

                                } finally {

                                    onError(Console.errors)
                                }


                            }
                        }
                        lastThread.start()
                    } catch (err) {

                    }

                }
//            }
        })
    }

    void  onError(final java.util.List<CodeError> errors) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override

                void run() {
                    def sb = new StringBuilder()

                    for (CodeError it :errors){
                        sb.append(String.format("[line: %d (%d,%d)] %s\n", it.row, it.row, it.col, it.message))
                    }
                    println sb.toString()
                    consolePane.setText(sb.toString())
                }
            })

    }

}
