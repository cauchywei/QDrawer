package org.sssta.qdrawer.gui
import org.apache.tools.ant.filters.StringInputStream
import org.sssta.qdrawer.lexer.Laxer
import org.sssta.qdrawer.lexer.TokenType

import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener
import javax.swing.text.Style
import javax.swing.text.StyleConstants
import javax.swing.text.StyledDocument
import java.awt.*
/**
 * Created by cauchywei on 15/12/21.
 */
class HighLightRender implements DocumentListener {

    JTextPane codePane
    Style keywordStyle,normalStyle,numberStyle,stringStyle,commentStyle
    StyledDocument document

    HighLightRender(JTextPane codePane) {
        this.codePane = codePane
        document = (StyledDocument)codePane.getDocument()

        normalStyle = document.addStyle("NormalStyle", null);

        keywordStyle = document.addStyle("KeywordStyle", null);
        numberStyle = document.addStyle("NumberStyle", null);
        stringStyle = document.addStyle("StringStyle", null);
        commentStyle = document.addStyle("CommentStyle", null);

//        StyleConstants.setBackground(keywordStyle,new Color(0,0,0));


        StyleConstants.setForeground(normalStyle,codePane.getForeground())
        StyleConstants.setForeground(keywordStyle,new Color(213,139,72));
        StyleConstants.setForeground(numberStyle,new Color(124,168,198));
        StyleConstants.setForeground(stringStyle,new Color(125,151,110));
        StyleConstants.setForeground(commentStyle,new Color(146,146,146));



    }

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

    void onChange(DocumentEvent e) {
        new Thread(){
            @Override
            void run() {
                def text = codePane.getText();
                def laxer = new Laxer(new StringInputStream(text))
//                SwingUtilities.invokeLater(new Runnable() {
//
//                    @Override
//                    void run() {
//                        document.setCharacterAttributes(0,text.length(),normalStyle,true)
//                    }
//                })
                while (laxer.hasNext()) {
                    final def token = laxer.takeToken()

                        print token.value + " "
                        print token.start +" "
                        println token.value.length() +" "
                        int last = 0

                    if (token.value.length() <= 1) {
                        continue
                    }
                    SwingUtilities.invokeLater(new Runnable() {
                            @Override
                            void run() {
                                def style

                                if (token.value.toUpperCase() in Laxer.KEY_IDENTIFIERS) {
                                    style = keywordStyle
                                }else if (token.type == TokenType.NUMBERIC) {
                                    style = numberStyle
                                }else if (token.type == TokenType.STRING) {
                                    style = stringStyle
                                }else if (token.type == TokenType.COMMENT) {
                                    style = commentStyle
                                }else {
                                    style = normalStyle
                                }


                                document.setCharacterAttributes(token.start,token.value.length(),style,true)
//                                document.setCharacterAttributes(last,token.start - last,normalStyle,true)

                                last = token.start + token.value.length()
                            }
                        });

                }
            }
        }.start();

    }


}
