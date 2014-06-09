/*
 * Created by JFormDesigner on Tue Feb 25 21:35:54 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.event.*;
import org.jdesktop.beansbinding.*;
import org.jdesktop.beansbinding.AutoBinding.UpdateStrategy;

/**
 * @author arden allstars
 */
public class frameLoggedIn extends JFrame {
    public frameLoggedIn() {
        initComponents();
	for (int i = 1; i < 5; i++) {
	    listModel.addElement("Nama " + i);
	}
    }
    JFrame frame = new frameChatWindow();

    private void jbtChatWithActionPerformed(ActionEvent e) {
        // TODO add your code here
	frame.setTitle((String) jlstOnlineUsers.getSelectedValue());
	frame.setVisible(true);
    }

    private void jbtLogOutActionPerformed(ActionEvent e) {
        // TODO add your code here
	setVisible(false);
    }

    private void thisComponentHidden(ComponentEvent e) {
        // TODO add your code here
	if (frame.isVisible())
	    frame.setVisible(false);
    }

    private void jlstOnlineUsersValueChanged(ListSelectionEvent e) {
        // TODO add your code here
	if (jlstOnlineUsers.isSelectionEmpty()) {
	    jbtChatWith.setEnabled(false);
	}
	else
	    jbtChatWith.setEnabled(true);
	jlstOnlineUsers.setSelectedIndex(jlstOnlineUsers.getSelectedIndex());
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        scrollPane1 = new JScrollPane();
        jlstOnlineUsers = new JList();
        jlbOnlineUsers = new JLabel();
        jbtLogOut = new JButton();
        jbtChatWith = new JButton();
        listModel = new DefaultListModel();

        //======== this ========
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                thisComponentHidden(e);
            }
        });
        Container contentPane = getContentPane();

        //======== scrollPane1 ========
        {

            //---- jlstOnlineUsers ----
            jlstOnlineUsers.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            jlstOnlineUsers.addListSelectionListener(new ListSelectionListener() {
                @Override
                public void valueChanged(ListSelectionEvent e) {
                    jlstOnlineUsersValueChanged(e);
                }
            });
            scrollPane1.setViewportView(jlstOnlineUsers);
        }

        //---- jlbOnlineUsers ----
        jlbOnlineUsers.setText("Online Users");
        jlbOnlineUsers.setLabelFor(jlstOnlineUsers);
        jlbOnlineUsers.setFont(new Font("Arial Rounded MT Bold", Font.BOLD, 16));

        //---- jbtLogOut ----
        jbtLogOut.setText("LOG OUT");
        jbtLogOut.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        jbtLogOut.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtLogOutActionPerformed(e);
            }
        });

        //---- jbtChatWith ----
        jbtChatWith.setText("CHAT WITH");
        jbtChatWith.setFont(new Font("Arial Rounded MT Bold", Font.PLAIN, 14));
        jbtChatWith.setEnabled(false);
        jbtChatWith.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtChatWithActionPerformed(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addComponent(scrollPane1)
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(jlbOnlineUsers)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(jbtChatWith)
                            .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 9, Short.MAX_VALUE)
                            .addComponent(jbtLogOut)))
                    .addContainerGap())
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jlbOnlineUsers)
                    .addGap(4, 4, 4)
                    .addComponent(scrollPane1, GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtChatWith)
                        .addComponent(jbtLogOut))
                    .addContainerGap())
        );
        pack();
        setLocationRelativeTo(getOwner());

        //---- bindings ----
        bindingGroup = new BindingGroup();
        bindingGroup.addBinding(Bindings.createAutoBinding(UpdateStrategy.READ_WRITE,
            listModel, jlstOnlineUsers, BeanProperty.create("model")));
        bindingGroup.bind();
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    @Override
    public Component add(Component comp) {
	return super.add(comp); //To change body of generated methods, choose Tools | Templates.
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JScrollPane scrollPane1;
    private JList jlstOnlineUsers;
    private JLabel jlbOnlineUsers;
    private JButton jbtLogOut;
    private JButton jbtChatWith;
    private DefaultListModel listModel;
    private BindingGroup bindingGroup;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
