/*
 * Created by JFormDesigner on Wed Feb 26 09:54:37 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;

/**
 * @author arden allstars
 */
public class frameTheory extends JFrame {
    public frameTheory() {
        initComponents();
    }
    JFrame frame;

    private void jbtNextActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void jbtPreviousActionPerformed(ActionEvent e) {
        // TODO add your code here
    }

    private void jbtExitActionPerformed(ActionEvent e) {
        // TODO add your code here
	setVisible(false);
    }

    private void thisComponentHidden(ComponentEvent e) {
        // TODO add your code here
	frame = new frameMainMenu();
	frame.setVisible(true);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        jlbTitle = new JLabel();
        label1 = new JLabel();
        jbtExit = new JButton();
        jbtNext = new JButton();
        jbtPrevious = new JButton();

        //======== this ========
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                thisComponentHidden(e);
            }
        });
        Container contentPane = getContentPane();

        //---- jlbTitle ----
        jlbTitle.setText("Theory");
        jlbTitle.setFont(new Font("Magneto", Font.BOLD, 42));

        //---- label1 ----
        label1.setBorder(new BevelBorder(BevelBorder.LOWERED));

        //---- jbtExit ----
        jbtExit.setText("EXIT");
        jbtExit.setFont(new Font("Garamond", Font.BOLD, 20));
        jbtExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtExitActionPerformed(e);
            }
        });

        //---- jbtNext ----
        jbtNext.setText("NEXT");
        jbtNext.setFont(new Font("Garamond", Font.BOLD, 20));
        jbtNext.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtNextActionPerformed(e);
            }
        });

        //---- jbtPrevious ----
        jbtPrevious.setText("PREVIOUS");
        jbtPrevious.setFont(new Font("Garamond", Font.BOLD, 20));
        jbtPrevious.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                jbtPreviousActionPerformed(e);
            }
        });

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(label1, GroupLayout.DEFAULT_SIZE, 764, Short.MAX_VALUE)
                            .addContainerGap())
                        .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                            .addGap(0, 305, Short.MAX_VALUE)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addComponent(jlbTitle)
                                    .addGap(309, 309, 309))
                                .addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
                                    .addComponent(jbtPrevious)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jbtNext)
                                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jbtExit)
                                    .addContainerGap())))))
        );
        contentPaneLayout.linkSize(SwingConstants.HORIZONTAL, new Component[] {jbtExit, jbtNext, jbtPrevious});
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(26, 26, 26)
                    .addComponent(jlbTitle)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(label1, GroupLayout.PREFERRED_SIZE, 426, GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(jbtExit)
                        .addComponent(jbtNext)
                        .addComponent(jbtPrevious))
                    .addContainerGap())
        );
        contentPaneLayout.linkSize(SwingConstants.VERTICAL, new Component[] {jbtExit, jbtNext, jbtPrevious});
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JLabel jlbTitle;
    private JLabel label1;
    private JButton jbtExit;
    private JButton jbtNext;
    private JButton jbtPrevious;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
