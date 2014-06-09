/*
 * Created by JFormDesigner on Wed Feb 26 09:57:17 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.GroupLayout;

/**
 * @author arden allstars
 */
public class frameAboutUs extends JFrame {
    public frameAboutUs() {
        initComponents();
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        jpAtas = new JPanel();
        lblLogo = new JLabel();
        jtaJudulSkripsi = new JTextArea();
        jpBawah = new JPanel();
        jlbGaris = new JLabel();
        jbtOK = new JButton();
        jlbMikroskil = new JLabel();
        jpTengah = new JPanel();
        jlbTugasAkhir = new JLabel();
        jlbMahasiswa1 = new JLabel();
        jlbMahasiswa2 = new JLabel();
        action1 = new actionPerform();

        //======== this ========
        setIconImage(null);
        setTitle("About Us");
        setResizable(false);
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== jpAtas ========
        {
            jpAtas.setBackground(Color.white);
            jpAtas.setPreferredSize(new Dimension(364, 100));

            //---- lblLogo ----
            lblLogo.setIcon(new ImageIcon("D:\\PROPOSAL TA\\program designs\\img\\dsIcon2.jpg"));

            //---- jtaJudulSkripsi ----
            jtaJudulSkripsi.setBorder(null);
            jtaJudulSkripsi.setBackground(Color.white);
            jtaJudulSkripsi.setText("IMPLEMENTASI TANDA TANGAN DIGITAL DENGAN MENGGUNAKAN METODE ELLIPTIC CURVE DSA PADA APLIKASI CHATTING");
            jtaJudulSkripsi.setEditable(false);
            jtaJudulSkripsi.setLineWrap(true);
            jtaJudulSkripsi.setWrapStyleWord(true);
            jtaJudulSkripsi.setFont(new Font("Times New Roman", Font.BOLD, 13));

            GroupLayout jpAtasLayout = new GroupLayout(jpAtas);
            jpAtas.setLayout(jpAtasLayout);
            jpAtasLayout.setHorizontalGroup(
                jpAtasLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                        .addComponent(lblLogo)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 10, Short.MAX_VALUE)
                        .addComponent(jtaJudulSkripsi, GroupLayout.PREFERRED_SIZE, 234, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
            jpAtasLayout.setVerticalGroup(
                jpAtasLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtaJudulSkripsi, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(GroupLayout.Alignment.TRAILING, jpAtasLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblLogo))
            );
        }
        contentPane.add(jpAtas, BorderLayout.NORTH);

        //======== jpBawah ========
        {
            jpBawah.setBackground(Color.white);
            jpBawah.setPreferredSize(new Dimension(364, 52));

            //---- jlbGaris ----
            jlbGaris.setText("________________________________________________");
            jlbGaris.setFont(new Font("Courier New", Font.PLAIN, 11));

            //---- jbtOK ----
            jbtOK.setAction(action1);

            //---- jlbMikroskil ----
            jlbMikroskil.setText("STMIK - MIKROSKIL, MEDAN (C) 2014");
            jlbMikroskil.setFont(new Font("Times New Roman", Font.BOLD, 12));

            GroupLayout jpBawahLayout = new GroupLayout(jpBawah);
            jpBawah.setLayout(jpBawahLayout);
            jpBawahLayout.setHorizontalGroup(
                jpBawahLayout.createParallelGroup()
                    .addGroup(jpBawahLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpBawahLayout.createParallelGroup()
                            .addGroup(jpBawahLayout.createSequentialGroup()
                                .addComponent(jlbGaris)
                                .addContainerGap(8, Short.MAX_VALUE))
                            .addGroup(GroupLayout.Alignment.TRAILING, jpBawahLayout.createSequentialGroup()
                                .addGap(0, 30, Short.MAX_VALUE)
                                .addComponent(jlbMikroskil)
                                .addGap(18, 18, 18)
                                .addComponent(jbtOK)
                                .addGap(34, 34, 34))))
            );
            jpBawahLayout.setVerticalGroup(
                jpBawahLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jpBawahLayout.createSequentialGroup()
                        .addComponent(jlbGaris)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jpBawahLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbMikroskil)
                            .addComponent(jbtOK))
                        .addContainerGap(9, Short.MAX_VALUE))
            );
        }
        contentPane.add(jpBawah, BorderLayout.SOUTH);

        //======== jpTengah ========
        {
            jpTengah.setBackground(Color.white);
            jpTengah.setPreferredSize(new Dimension(364, 110));
            jpTengah.setLayout(new GridLayout(3, 0));

            //---- jlbTugasAkhir ----
            jlbTugasAkhir.setText("Tugas Akhir Jurusan Teknik Informatika (S1)");
            jlbTugasAkhir.setFont(new Font("Times New Roman", Font.BOLD, 15));
            jlbTugasAkhir.setHorizontalAlignment(SwingConstants.CENTER);
            jpTengah.add(jlbTugasAkhir);

            //---- jlbMahasiswa1 ----
            jlbMahasiswa1.setText("Arden Liw (10.111.0460)");
            jlbMahasiswa1.setFont(new Font("Times New Roman", Font.BOLD, 15));
            jlbMahasiswa1.setHorizontalAlignment(SwingConstants.CENTER);
            jpTengah.add(jlbMahasiswa1);

            //---- jlbMahasiswa2 ----
            jlbMahasiswa2.setText("Singgih Chongronegoro (10.111.1481)");
            jlbMahasiswa2.setFont(new Font("Times New Roman", Font.BOLD, 15));
            jlbMahasiswa2.setHorizontalAlignment(SwingConstants.CENTER);
            jpTengah.add(jlbMahasiswa2);
        }
        contentPane.add(jpTengah, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JPanel jpAtas;
    private JLabel lblLogo;
    private JTextArea jtaJudulSkripsi;
    private JPanel jpBawah;
    private JLabel jlbGaris;
    private JButton jbtOK;
    private JLabel jlbMikroskil;
    private JPanel jpTengah;
    private JLabel jlbTugasAkhir;
    private JLabel jlbMahasiswa1;
    private JLabel jlbMahasiswa2;
    private actionPerform action1;
    // JFormDesigner - End of variables declaration  //GEN-END:variables

    private class actionPerform extends AbstractAction {
        private actionPerform() {
            // JFormDesigner - Action initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
            // Generated using JFormDesigner Evaluation license - arden allstars
            putValue(NAME, "OK");
            // JFormDesigner - End of action initialization  //GEN-END:initComponents
        }

        public void actionPerformed(ActionEvent e) {
            // TODO add your code here
	    setVisible(false);
        }
    }
}
