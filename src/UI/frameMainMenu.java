/*
 * Created by JFormDesigner on Tue Feb 25 19:14:10 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author arden allstars
 */
public class frameMainMenu extends JFrame {
    public frameMainMenu() {
        initComponents();
    }
    JFrame frame;
    String str[] = {
	"ECDSA Scheme merupakan Skema dari proses-proses yang akan dibahas pada Algoritma ECDSA. Proses-proses tersebut berupa Key Generation (Pembentukan Kunci), Signature Generation (Pembentukan Tanda Tangan), Signature Verification(Verifikasi Tanda Tangan).", 
	"Chatting Implementation (Implementasi Aplikasi Obrolan) merupakan fitur Aplikasi yang akan mengimplementasikan algoritma ECDSA. Aplikasi ini akan menghubungkan ke pengguna lain untuk melakukan obrolan.", 
	"Teori-teori yang berhubungan dengan ECDSA akan ditampilkan disini", 
	"Informasi mengenai pembuat perangkat lunak dan sekaligus penyusun tugas akhir skripsi Strata-1 Jurusan Teknik Informasi STMIK-Mikroskil, Medan.", 
	"Keluar Dari Program"};
    String str1[] = {"Pembentukan Kunci (Key Generation) merupakan proses pembentukan kunci privat dan kunci publik yang akan digunakan pada skema Signature Generation dan Signature Verification. Kunci privat diketahui oleh pihak pertama (yang akan diverifikasi atau diperiksa keabsahannya pada kedua skema tersebut), sedangkan kunci publik disebarkan atau diketahui oleh pihak-pihak lainnya yang akan memeriksa keaslian atau keabsahan data dari pihak pertama.", "Signature Generation adalah bagian dari pembentukan Tanda Tangan Digital yang akan dilakukan oleh pihak pertama. Pada bagian ini pihak pertama melakukan hashing pada pesan yang akan dikirimkan. Kemudian melakukan tanda tangan terhadap pesan yang telah dihash."};

    private void jbtChattingActionPerformed(ActionEvent e) {
        // TODO add your code here
	//frame = new frameChattingImplementation();
	frame.setVisible(true);
	setVisible(false);
    }

    private void jbtAboutUsActionPerformed(ActionEvent e) {
        // TODO add your code here
	frame = new frameAboutUs();
	frame.setVisible(true);
    }

    private void jbtECDSAActionPerformed(ActionEvent e) {
        // TODO add your code here
	frame = new frameECDSAScheme();
	frame.setVisible(true);
	setVisible(false);
    }

    private void jbtTheoryActionPerformed(ActionEvent e) {
        // TODO add your code here
	frame = new frameTheory();
	frame.setVisible(true);
	setVisible(false);
    }

    private void jbtECDSAMouseEntered() {
        // TODO add your code here
	jtaInfo.setText(str[0]);
    }

    private void jbtChattingMouseEntered() {
        // TODO add your code here
	jtaInfo.setText(str[1]);
    }

    private void jbtTheoryMouseEntered() {
        // TODO add your code here
	jtaInfo.setText(str[2]);
    }

    private void jbtAboutUsMouseEntered() {
        // TODO add your code here
	jtaInfo.setText(str[3]);
    }

    private void jbtExitMouseEntered() {
        // TODO add your code here
	jtaInfo.setText(str[4]);
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        jpTengah = new JPanel();
        jbtECDSA = new JButton();
        jbtChatting = new JButton();
        jbtTheory = new JButton();
        jbtAboutUs = new JButton();
        jbtExit = new JButton();
        jpAtas = new JPanel();
        jlbTitle = new JLabel();
        jpBawah = new JPanel();
        jspInfo = new JScrollPane();
        jtaInfo = new JTextArea();
        jlbInfo = new JLabel();
        action1 = new Action1();

        //======== this ========
        setTitle("Chatting Application Implements Elliptical Curve Digital Signature Algorithm");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== jpTengah ========
        {

            // JFormDesigner evaluation mark
            jpTengah.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), jpTengah.getBorder())); jpTengah.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


            //---- jbtECDSA ----
            jbtECDSA.setText("ECDSA SCHEME");
            jbtECDSA.setFont(new Font("Times New Roman", Font.BOLD, 16));
            jbtECDSA.setPreferredSize(new Dimension(275, 50));
            jbtECDSA.setToolTipText("Skema Algoritma Elliptic Curve DSA");
            jbtECDSA.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jbtECDSAActionPerformed(e);
                }
            });
            jbtECDSA.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    jbtECDSAMouseEntered();
                }
            });

            //---- jbtChatting ----
            jbtChatting.setText("CHATTING IMPLEMENTATION");
            jbtChatting.setFont(new Font("Times New Roman", Font.BOLD, 16));
            jbtChatting.setPreferredSize(new Dimension(275, 50));
            jbtChatting.setToolTipText("Uji Chatting yang sudah mengimplementasikan Metode ECDSA");
            jbtChatting.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jbtChattingActionPerformed(e);
                }
            });
            jbtChatting.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    jbtChattingMouseEntered();
                }
            });

            //---- jbtTheory ----
            jbtTheory.setText("THEORY");
            jbtTheory.setFont(new Font("Times New Roman", Font.BOLD, 16));
            jbtTheory.setPreferredSize(new Dimension(275, 50));
            jbtTheory.setToolTipText("Teori-teori yang berhubungan dengan ECDSA");
            jbtTheory.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jbtTheoryActionPerformed(e);
                }
            });
            jbtTheory.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    jbtTheoryMouseEntered();
                }
            });

            //---- jbtAboutUs ----
            jbtAboutUs.setText("ABOUT US");
            jbtAboutUs.setFont(new Font("Times New Roman", Font.BOLD, 16));
            jbtAboutUs.setPreferredSize(new Dimension(275, 50));
            jbtAboutUs.setToolTipText("Tentang Pengembang Program");
            jbtAboutUs.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    jbtAboutUsActionPerformed(e);
                }
            });
            jbtAboutUs.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    jbtAboutUsMouseEntered();
                }
            });

            //---- jbtExit ----
            jbtExit.setFont(new Font("Times New Roman", Font.BOLD, 16));
            jbtExit.setPreferredSize(new Dimension(275, 50));
            jbtExit.setAction(action1);
            jbtExit.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseEntered(MouseEvent e) {
                    jbtExitMouseEntered();
                }
            });

            GroupLayout jpTengahLayout = new GroupLayout(jpTengah);
            jpTengah.setLayout(jpTengahLayout);
            jpTengahLayout.setHorizontalGroup(
                jpTengahLayout.createParallelGroup()
                    .addGroup(jpTengahLayout.createSequentialGroup()
                        .addGap(255, 255, 255)
                        .addGroup(jpTengahLayout.createParallelGroup()
                            .addComponent(jbtECDSA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtChatting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtTheory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtAboutUs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                            .addComponent(jbtExit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(254, Short.MAX_VALUE))
            );
            jpTengahLayout.setVerticalGroup(
                jpTengahLayout.createParallelGroup()
                    .addGroup(jpTengahLayout.createSequentialGroup()
                        .addComponent(jbtECDSA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jbtChatting, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jbtTheory, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jbtAboutUs, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(jbtExit, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 15, Short.MAX_VALUE))
            );
        }
        contentPane.add(jpTengah, BorderLayout.CENTER);

        //======== jpAtas ========
        {

            //---- jlbTitle ----
            jlbTitle.setText("Elliptical Curve DSA");
            jlbTitle.setFont(new Font("Magneto", Font.BOLD, 42));

            GroupLayout jpAtasLayout = new GroupLayout(jpAtas);
            jpAtas.setLayout(jpAtasLayout);
            jpAtasLayout.setHorizontalGroup(
                jpAtasLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                        .addContainerGap(147, Short.MAX_VALUE)
                        .addComponent(jlbTitle)
                        .addGap(143, 143, 143))
            );
            jpAtasLayout.setVerticalGroup(
                jpAtasLayout.createParallelGroup()
                    .addGroup(jpAtasLayout.createSequentialGroup()
                        .addGap(25, 25, 25)
                        .addComponent(jlbTitle)
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }
        contentPane.add(jpAtas, BorderLayout.NORTH);

        //======== jpBawah ========
        {

            //======== jspInfo ========
            {

                //---- jtaInfo ----
                jtaInfo.setLineWrap(true);
                jtaInfo.setFont(jtaInfo.getFont().deriveFont(jtaInfo.getFont().getStyle() | Font.BOLD, jtaInfo.getFont().getSize() + 6f));
                jtaInfo.setWrapStyleWord(true);
                jspInfo.setViewportView(jtaInfo);
            }

            //---- jlbInfo ----
            jlbInfo.setText("Info :");
            jlbInfo.setFont(jlbInfo.getFont().deriveFont(jlbInfo.getFont().getStyle() | Font.BOLD, jlbInfo.getFont().getSize() + 2f));

            GroupLayout jpBawahLayout = new GroupLayout(jpBawah);
            jpBawah.setLayout(jpBawahLayout);
            jpBawahLayout.setHorizontalGroup(
                jpBawahLayout.createParallelGroup()
                    .addGroup(jpBawahLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpBawahLayout.createParallelGroup()
                            .addGroup(jpBawahLayout.createSequentialGroup()
                                .addComponent(jlbInfo)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jspInfo))
                        .addContainerGap())
            );
            jpBawahLayout.setVerticalGroup(
                jpBawahLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jpBawahLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlbInfo)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jspInfo, GroupLayout.PREFERRED_SIZE, 144, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
        }
        contentPane.add(jpBawah, BorderLayout.SOUTH);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
	jtaInfo.setText(str[0]);
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JPanel jpTengah;
    private JButton jbtECDSA;
    private JButton jbtChatting;
    private JButton jbtTheory;
    private JButton jbtAboutUs;
    private JButton jbtExit;
    private JPanel jpAtas;
    private JLabel jlbTitle;
    private JPanel jpBawah;
    private JScrollPane jspInfo;
    private JTextArea jtaInfo;
    private JLabel jlbInfo;
    private Action1 action1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private class Action1 extends AbstractAction {
        private Action1() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            // Generated using JFormDesigner Evaluation license - arden allstars
            putValue(NAME, "EXIT");
            putValue(SHORT_DESCRIPTION, "Keluar Dari Program");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            // TODO add your code here
	    System.exit(0);
        }
    }
}
