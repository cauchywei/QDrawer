package org.sssta.qdrawer.gui

import javax.swing.SwingUtilities

/**
 * Created by cauchywei on 15/12/21.
 */
class Launcher {
    static void main(def args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new DrawerWindow()
            }
        });
    }
}
