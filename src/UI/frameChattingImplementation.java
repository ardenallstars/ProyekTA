/*
 * Created by JFormDesigner on Tue Feb 25 21:01:25 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author arden allstars
 */
public class frameChattingImplementation extends JFrame {
    public frameChattingImplementation() {
        initComponents();
	
	frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
		jbtLogin.setText("LOGIN");
		jtfName.setEnabled(true);
            }
        });
    }
    JFrame frame = new frameLoggedIn();

    private void jtfNameActionPerformed(ActionEvent e) {
        // TODO add your code here
	jbtLogin.requestFocusInWindow();
    }

    private void jbtLoginActionPerformed(ActionEvent e) {
        // TODO add your code here
	if ("LOGIN".equals(jbtLogin.getText())) {
	    frame.setVisible(true);
	    jbtLogin.setText("LOG OUT");
	    jtfName.setEnabled(false);
	}
	else
	{
	    jbtLogin.setText("LOGIN");
	    frame.setVisible(false);
	    jtfName.setEnabled(true);
	}
    }

    private void thisComponentHidden(ComponentEvent e) {
        // TODO add your code here
	frame.setVisible(false);
	frame = new frameMainMenu();
	frame.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        jlbName = new JLabel();
        jtfName = new JTextField();
        jbtLogin = new JButton();
        jtbpUser = new JTabbedPane();
        jpActivityLog = new JPanel();
        jpPlain = new JPanel();
        jpCipher = new JPanel();
        jpSignature = new JPanel();

        //======== this ========
        setTitle("Chatting Implementation");
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                thisComponentHidden(e);
            }
        });
        Container contentPane = getContentPane();

        //---- jlbName ----
        jlbName.setText("Name :");
        jlbName.setFont(new Font("Arial Narrow", Font.PLAIN, 16));
        jlbName.setLabelFor(jtfName);

        //---- jtfName ----
        jtfName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jtfNameActionPerformed(e);
            }
        });

        //---- jbtLogin ----
        jbtLogin.setText("LOGIN");
        jbtLogin.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtLoginActionPerformed(e);
            }
        });

        //======== jtbpUser ========
        {

            //======== jpActivityLog ========
            {

                // JFormDesigner evaluation mark
                jpActivityLog.setBorder(new javax.swing.border.CompoundBorder(
                    new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                        "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                        javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                        java.awt.Color.red), jpActivityLog.getBorder())); jpActivityLog.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


                GroupLayout jpActivityLogLayout = new GroupLayout(jpActivityLog);
                jpActivityLog.setLayout(jpActivityLogLayout);
                jpActivityLogLayout.setHorizontalGroup(
                    jpActivityLogLayout.createParallelGroup()
                        .addGap(0, 379, Short.MAX_VALUE)
                );
                jpActivityLogLayout.setVerticalGroup(
                    jpActivityLogLayout.createParallelGroup()
                        .addGap(0, 375, Short.MAX_VALUE)
                );
            }
            jtbpUser.addTab("activity log", jpActivityLog);

            //======== jpPlain ========
            {

                GroupLayout jpPlainLayout = new GroupLayout(jpPlain);
                jpPlain.setLayout(jpPlainLayout);
                jpPlainLayout.setHorizontalGroup(
                    jpPlainLayout.createParallelGroup()
                        .addGap(0, 379, Short.MAX_VALUE)
                );
                jpPlainLayout.setVerticalGroup(
                    jpPlainLayout.createParallelGroup()
                        .addGap(0, 375, Short.MAX_VALUE)
                );
            }
            jtbpUser.addTab("plain message", jpPlain);

            //======== jpCipher ========
            {

                GroupLayout jpCipherLayout = new GroupLayout(jpCipher);
                jpCipher.setLayout(jpCipherLayout);
                jpCipherLayout.setHorizontalGroup(
                    jpCipherLayout.createParallelGroup()
                        .addGap(0, 379, Short.MAX_VALUE)
                );
                jpCipherLayout.setVerticalGroup(
                    jpCipherLayout.createParallelGroup()
                        .addGap(0, 375, Short.MAX_VALUE)
                );
            }
            jtbpUser.addTab("cipher message", jpCipher);

            //======== jpSignature ========
            {

                GroupLayout jpSignatureLayout = new GroupLayout(jpSignature);
                jpSignature.setLayout(jpSignatureLayout);
                jpSignatureLayout.setHorizontalGroup(
                    jpSignatureLayout.createParallelGroup()
                        .addGap(0, 379, Short.MAX_VALUE)
                );
                jpSignatureLayout.setVerticalGroup(
                    jpSignatureLayout.createParallelGroup()
                        .addGap(0, 375, Short.MAX_VALUE)
                );
            }
            jtbpUser.addTab("signature", jpSignature);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(jlbName)
                            .addGap(28, 28, 28)
                            .addComponent(jtfName, GroupLayout.PREFERRED_SIZE, 166, GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jbtLogin)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jtbpUser))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jlbName)
                        .addComponent(jtfName, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbtLogin))
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jtbpUser)
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JLabel jlbName;
    private JTextField jtfName;
    private JButton jbtLogin;
    private JTabbedPane jtbpUser;
    private JPanel jpActivityLog;
    private JPanel jpPlain;
    private JPanel jpCipher;
    private JPanel jpSignature;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
