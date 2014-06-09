/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package main;

import UI.frameMainMenu;
import javax.swing.JFrame;
import javax.swing.UIManager;

/**
 *
 * @author Arden
 */
public class KurvaEliptik {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
	// TODO code application logic heretry {
	String style[] = {"Metal", "Nimbus", "CDE/Motif", "Windows", "Windows Classic"};
	int selectedStyle = 4;
	try {
            javax.swing.UIManager.LookAndFeelInfo[] installedLookAndFeels=javax.swing.UIManager.getInstalledLookAndFeels();
	    for (UIManager.LookAndFeelInfo installedLookAndFeel : installedLookAndFeels) {
		if (style[selectedStyle-1].equals(installedLookAndFeel.getName())) {
		    javax.swing.UIManager.setLookAndFeel(installedLookAndFeel.getClassName());
		    break;
		}
	    }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frameMainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
	JFrame frame = new frameMainMenu();
	frame.setVisible(true);
    }
    
}
