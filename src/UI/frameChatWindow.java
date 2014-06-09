/*
 * Created by JFormDesigner on Tue Feb 25 21:43:04 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author arden allstars
 */
public class frameChatWindow extends JFrame {
    public frameChatWindow() {
        initComponents();
    }

    private void jbtSendActionPerformed(ActionEvent e) {
        // TODO add your code here
	String messageString = jtaChatMessage.getText();
	//bla bla . . . .
    }


    private void thisWindowOpened(WindowEvent e) {
        // TODO add your code here
	jlbChatWith.setText(getTitle());
	setTitle("Chatting Window");
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        jlbChatWith = new JLabel();
        jlbTyping = new JLabel();
        jspInputMessage = new JScrollPane();
        jtaInputMessage = new JTextArea();
        jbtSend = new JButton();
        jspChatMessage = new JScrollPane();
        jtaChatMessage = new JTextArea();

        //======== this ========
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                thisWindowOpened(e);
            }
        });
        Container contentPane = getContentPane();

        //---- jlbChatWith ----
        jlbChatWith.setText("chat with");

        //---- jlbTyping ----
        jlbTyping.setText("type your message here :");

        //======== jspInputMessage ========
        {
            jspInputMessage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jspInputMessage.setViewportView(jtaInputMessage);
        }

        //---- jbtSend ----
        jbtSend.setText("SEND");
        jbtSend.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtSendActionPerformed(e);
            }
        });

        //======== jspChatMessage ========
        {
            jspChatMessage.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            jspChatMessage.setViewportView(jtaChatMessage);
        }

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(jspInputMessage, GroupLayout.Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addComponent(jspChatMessage, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(jbtSend)
                                .addComponent(jlbTyping)
                                .addComponent(jlbChatWith))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jlbChatWith)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jspChatMessage, GroupLayout.PREFERRED_SIZE, 268, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jlbTyping)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jspInputMessage, GroupLayout.DEFAULT_SIZE, 79, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jbtSend)
                    .addGap(6, 6, 6))
        );
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JLabel jlbChatWith;
    private JLabel jlbTyping;
    private JScrollPane jspInputMessage;
    private JTextArea jtaInputMessage;
    private JButton jbtSend;
    private JScrollPane jspChatMessage;
    private JTextArea jtaChatMessage;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
