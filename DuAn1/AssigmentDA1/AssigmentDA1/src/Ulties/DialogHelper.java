/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ulties;

import java.awt.Component;
import javax.swing.JOptionPane;

/**
 *
 * @author fuoc
 */
public class DialogHelper {

    public static void alert(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message);
    }

    /**
     * * * @param parent
     * * @param message yes/no * @return true/false
     */
    public static boolean confirm(Component parent, String message) {
        int result = JOptionPane.showConfirmDialog(parent, message, "", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * * * @param parent
     *  * @param message * @return
     *
     */
    public static String prompt(Component parent, String message) {
        return JOptionPane.showInputDialog(parent, message, "", JOptionPane.INFORMATION_MESSAGE);
    }
}
