package org.sssta.qdrawer.gui

import javax.swing.*
import java.awt.*

/**
 * Created by cauchywei on 15/12/21.
 */
class DrawerWindow extends JFrame {

    JTextPane codePane
    JPanel canvas
    JTextPane consolePane = new JTextPane()

    JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
    JSplitPane codeSplitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT);

    DrawerWindow() {
        setTitle("QDrawer")
        def screenSize = Toolkit.getDefaultToolkit().getScreenSize()


        def container = new JPanel()
        container.setLayout(new BorderLayout());

        codePane = new JTextPane()
        codePane.setFont(new Font("Consolas",Font.BOLD,16))
        codePane.setBackground(new Color(57,57,57))
        codePane.setForeground(new Color(200,200,200))
        codePane.setSelectedTextColor(new Color(200,200,200))
        codePane.setSelectionColor(new Color(45,88,147))
        codePane.setCaretColor(new Color(220,220,220))
        codePane.setMargin(new Insets(20,20,20,20))
        codePane.setMinimumSize(new Dimension((screenSize.getWidth()/2).intValue(),200))


        consolePane.setFont(new Font("Consolas",Font.BOLD,16))
        consolePane.setBackground(new Color(57,57,57))
        consolePane.setForeground(new Color(255,131,126))
        consolePane.setSelectedTextColor(new Color(200,200,200))
        consolePane.setSelectionColor(new Color(45,88,147))
        consolePane.setCaretColor(new Color(220,220,220))
        consolePane.setMargin(new Insets(15,15,15,15))
        consolePane.setEditable(false)
        consolePane.setText("Code Error: hahahahah")

        codeSplitPane.setTopComponent(new JScrollPane(codePane))
        codeSplitPane.setBottomComponent(consolePane)


        codeSplitPane.setDividerLocation((screenSize.getHeight()*0.7).intValue())

        splitPane.setLeftComponent(codeSplitPane)

        canvas = new CanvasPanel()

        splitPane.setRightComponent(canvas)
        splitPane.setDividerLocation((screenSize.getWidth()/2.3).intValue())

        getContentPane().add(splitPane)

//        setBounds(0,0,screenSize.getWidth().intValue(),screenSize.getHeight().intValue())
        setMinimumSize(screenSize)
        setExtendedState(getExtendedState() | MAXIMIZED_BOTH);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack()
        setVisible(true)
    }
}
