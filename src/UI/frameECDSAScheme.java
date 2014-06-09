/*
 * Created by JFormDesigner on Tue Feb 25 20:54:48 ICT 2014
 */

package UI;

import java.awt.*;
import java.awt.event.*;
import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import javax.swing.*;
import javax.swing.GroupLayout;
import javax.swing.border.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import lib.ECDSAScheme;
import lib.Kurva;
import lib.MessageToDigest;
import lib.Point;

/**
 * @author arden allstars
 */
public class frameECDSAScheme extends JFrame {
    java.util.Random rnd = new java.security.SecureRandom();
    ECDSAScheme ecdsa = new ECDSAScheme();
    int bits;
    BigInteger p, a, b;
    ECField Fp;
    Kurva kurva;
    Point G, Q;
    BigInteger d;
    String hashtype = "";
    MessageToDigest md;
    
    public frameECDSAScheme() {
        initComponents();
	jtpECDSAScheme.setEnabledAt(1, false);
	jtpECDSAScheme.setEnabledAt(2, false);
	jcbHash.addItem("SHA-1");
	jcbHash.addItem("SHA-256");
	jcbHash.addItem("SHA-512");
	jcbHash.addItem("MD5");
	hashtype = jcbHash.getSelectedItem() + "";
	ecdsa.mDigest.setAlgorithm(hashtype);
	//jtaInfo.setText("Pertama-tama input data Field Fp dimana kurva y^2 = x^3 + a*x^2 + b\n");
    }

    private void thisComponentHidden(ComponentEvent e) {
        // TODO add your code here
	JFrame frame = new frameMainMenu();
	frame.setVisible(true);
    }

    private void jtfFieldFpFocusLost() {
        // TODO add your code here
	if (jtfFieldFp.getText().isEmpty()) {
	    jtfA.setEnabled(false);
	    jtfB.setEnabled(false);
	    jbtAcakA.setEnabled(false);
	    jbtAcakB.setEnabled(false);
	}
	else {
	    try {
		BigInteger test = new BigInteger(jtfFieldFp.getText());
		if (!test.isProbablePrime(5))
		    JOptionPane.showMessageDialog(rootPane, "Bilangan Fp harus merupakan angka prima");
	    }
	    catch (NumberFormatException ex) {
		JOptionPane.showMessageDialog(rootPane, "Bilangan Fp tidak boleh mengandung karakter selain angka");
	    }
	}
    }

    private void jbtAcakFieldFpActionPerformed() {
        // TODO add your code here
	Object x[] = new Object[251];
	for (int i=5; i< 256; i++)
	    x[i-5] = i;
	bits = Integer.parseInt(JOptionPane.showInputDialog(new JFrame(), "Tentukan Jumlah Bits", "Acak Bilangan Prima p", JOptionPane.PLAIN_MESSAGE, new ImageIcon(), (Object[]) x, 5) + "");
	p = new BigInteger(bits, 5, rnd);
	jtfFieldFp.setText(p+"");
	jtfA.setEnabled(true);
	jtfB.setEnabled(true);
	jbtAcakA.setEnabled(true);
	jbtAcakB.setEnabled(true);
    }

    private void jtfFieldFpMouseEntered() {
        // TODO add your code here
	if("".equals(jtfFieldFp.getText()))
	    jtfFieldFp.setToolTipText("Tentukan Field Fp : untuk kurva pada Field p Bilangan Prima Besar");
	else
	    jtfFieldFp.setToolTipText(jtfFieldFp.getText());
    }

    private void jbtAcakAActionPerformed() {
        // TODO add your code here
	if (jtfFieldFp.getText().equals(""))
	    JOptionPane.showMessageDialog(new JFrame(), "Input Field Fp first!", "Field Fp isEmpty", JOptionPane.WARNING_MESSAGE);
	else {
	    bits = new BigInteger(jtfFieldFp.getText()).bitCount();
	    int tmp;
	    tmp = rnd.nextInt(bits);
	    bits = bits - tmp;
	    a = new BigInteger(bits, rnd);
	    while (a.equals(BigInteger.ZERO))
		a = new BigInteger(bits, rnd);
	    jtfA.setText(a + "");
	    if (!jtfB.getText().isEmpty())
		jbtOK.setEnabled(true);
	}
    }

    private void jtfAMouseEntered() {
        // TODO add your code here
	if("".equals(jtfA.getText()))
	    jtfA.setToolTipText("Tentukan Konstanta A Bilangan antara 1 - (p-1)");
	else
	    jtfA.setToolTipText(jtfA.getText());
    }

    private void jbtAcakBActionPerformed() {
        // TODO add your code here
	if (jtfFieldFp.getText().equals(""))
	    JOptionPane.showMessageDialog(new JFrame(), "Input Field Fp first!", "Field Fp isEmpty", JOptionPane.WARNING_MESSAGE);
	else {
	    bits = new BigInteger(jtfFieldFp.getText()).bitCount();
	    int tmp;
	    tmp = rnd.nextInt(bits);
	    bits = bits - tmp;
	    b = new BigInteger(bits, rnd);
	    while (b.equals(BigInteger.ZERO))
		b = new BigInteger(bits, rnd);
	    jtfB.setText(b + "");
	    if (!jtfA.getText().isEmpty())
		jbtOK.setEnabled(true);
	}
    }

    private void jtfBMouseEntered() {
        // TODO add your code here
	if("".equals(jtfB.getText()))
	    jtfB.setToolTipText("Tentukan Konstanta B Bilangan antara 1 - (p-1)");
	else
	    jtfB.setToolTipText(jtfB.getText());
    }

    private void jbtOKActionPerformed() {
        // TODO add your code here
	if (jtfFieldFp.getText().equals("") || jtfA.getText().equals("") || jtfB.getText().equals(""))
	    JOptionPane.showMessageDialog(new JFrame(), "Field Fp atau konstanta A atau B kosong");
	else
	{
	    try {
		a = new BigInteger(jtfA.getText());
		b = new BigInteger(jtfB.getText());
		if (a.equals(BigInteger.ZERO) || b.equals(BigInteger.ZERO))
		    throw new IllegalArgumentException();
		Fp = new ECFieldFp(new BigInteger(jtfFieldFp.getText()));
		kurva = new Kurva(Fp, a, b);
		jtpECDSAScheme.setEnabledAt(1, false);
		jtpECDSAScheme.setEnabledAt(2, false);
		jtfGx.setEnabled(true);
		jtfGy.setEnabled(true);
		jbtAcakPointG.setEnabled(true);
		jtfPrivKeyD.setEnabled(true);
		jbtAcakD.setEnabled(true);
		jbtOK2.setEnabled(true);
	    }
	    catch (IllegalArgumentException e) {
		JOptionPane.showMessageDialog(rootPane, "A atau B tidak boleh > Fp ataupun < 1");
	    }
	}
    }

    private void jbtAcakPointGActionPerformed() {
        // TODO add your code here
	bits = new BigInteger(jtfFieldFp.getText()).bitCount();
	int tmp;
	tmp = rnd.nextInt(bits);
	bits = bits - tmp;
	G = kurva.getRandomPoint(bits);
	jtfGx.setText(G.getAffineX() + "");
	jtfGy.setText(G.getAffineY() + "");
    }

    private void jbtAcakDActionPerformed() {
        // TODO add your code here
	if (jtfFieldFp.getText().isEmpty())
	    jtfPrivKeyD.setText(rnd.nextInt(100) + "");
	else {
	    bits = new BigInteger(jtfFieldFp.getText()).bitCount();
	    jtfPrivKeyD.setText(new BigInteger(bits, rnd) + "");
	}
    }

    private void jcbHashItemStateChanged() {
        // TODO add your code here
	hashtype = jcbHash.getSelectedItem() + "";
	ecdsa.mDigest.setAlgorithm(hashtype);
	if (!jtaMessage.getText().isEmpty()) {
	    String message = jtaMessage.getText();
	    md = new MessageToDigest(hashtype);
	    jtaHashedMessage.setText(md.computeDigest(message.getBytes()));
	}
    }

    private void jtfAFocusLost() {
        // TODO add your code here
	try {
	    a = new BigInteger(jtfFieldFp.getText());
	    if (!jtfB.getText().isEmpty())
		jbtOK.setEnabled(true);
	}
	catch (NumberFormatException ex) {
	    JOptionPane.showMessageDialog(rootPane, "Bilangan a tidak boleh mengandung karakter selain angka");
	}
    }

    private void jtfBFocusLost() {
        // TODO add your code here
	try {
	    b = new BigInteger(jtfFieldFp.getText());
	    if (!jtfA.getText().isEmpty())
		jbtOK.setEnabled(true);
	}
	catch (NumberFormatException ex) {
	    JOptionPane.showMessageDialog(rootPane, "Bilangan b tidak boleh mengandung karakter selain angka");
	}
    }

    private void jbtOK2ActionPerformed() {
        // TODO add your code here
	jtaInfo.setText("");
	kurva = new Kurva(Fp, a, b);
	G = new Point(kurva, new BigInteger(jtfGx.getText()), new BigInteger(jtfGy.getText()));
	d = new BigInteger(jtfPrivKeyD.getText());
	jtpECDSAScheme.setEnabledAt(1, true);
	if (!G.isPointOnCurve(kurva))
	{
	    JOptionPane.showMessageDialog(rootPane, "Point G tidak berada pada " + kurva);
	    throw new IllegalArgumentException("Point G tidak berada pada " + kurva);
	}
	ecdsa.domainParameters(p, a, b, G, p);
	ecdsa.setPrivateKey(d);
	ecdsa.keyPairGeneration();
	
	for (int i=0; i<ecdsa.strProses.size(); i++)
	    jtaInfo.append(ecdsa.strProses.get(i));
    }

    private void jbtConfirmMessageActionPerformed() {
        // TODO add your code here
	hashtype = jcbHash.getSelectedItem() + "";
	ecdsa.setHashMethod(hashtype);
	ecdsa.SignatureGeneration(jtaMessage.getText());
	for (int i=0; i<ecdsa.strProses.size(); i++)
	    jtaInfo.append(ecdsa.strProses.get(i));
	jtpECDSAScheme.setEnabledAt(2, true);
	jtfSignR.setText(ecdsa.r + "");
	jtfSignS.setText(ecdsa.s + "");
	jtaMessage2.setText(jtaMessage.getText());
    }

    private void jbtOK3ActionPerformed() {
        // TODO add your code here
	ecdsa.SignatureVerification(jtaMessage2.getText(), jtfSignR.getText(), jtfSignS.getText());
	for (int i=0; i<ecdsa.strProses.size(); i++)
	    jtaInfo.append(ecdsa.strProses.get(i));
    }

    private void jtfGxFocusLost() {
        // TODO add your code here
	BigInteger Gx;
	try {
	    Gx = new BigInteger(jtfGx.getText());
	}
	catch (NumberFormatException ex) {
	    JOptionPane.showMessageDialog(rootPane, "Bilangan Gx tidak boleh mengandung karakter selain angka");
	}
    }

    private void jtfGyFocusLost() {
        // TODO add your code here
	BigInteger Gy;
	try {
	    Gy = new BigInteger(jtfGy.getText());
	}
	catch (NumberFormatException ex) {
	    JOptionPane.showMessageDialog(rootPane, "Bilangan Gy tidak boleh mengandung karakter selain angka");
	}
    }

    private void jtfPrivKeyDFocusLost() {
        // TODO add your code here
	try {
	    d = new BigInteger(jtfPrivKeyD.getText());
	}
	catch (NumberFormatException ex) {
	    JOptionPane.showMessageDialog(rootPane, "Private Key D tidak boleh mengandung karakter selain angka");
	}
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        // Generated using JFormDesigner Evaluation license - arden allstars
        jpInfo = new JPanel();
        jlbInfo = new JLabel();
        jspInfo = new JScrollPane();
        jtaInfo = new JTextArea();
        jbtSkipKeyGen = new JButton();
        jbtNextKeyGen = new JButton();
        jbtPrevKeyGen = new JButton();
        jpIsi = new JPanel();
        jtpECDSAScheme = new JTabbedPane();
        jpKeyGen = new JPanel();
        jlbField = new JLabel();
        jlbA = new JLabel();
        jlbB = new JLabel();
        label1 = new JLabel();
        label2 = new JLabel();
        label3 = new JLabel();
        jtfFieldFp = new JTextField();
        jtfA = new JTextField();
        jtfB = new JTextField();
        jbtAcakFieldFp = new JButton();
        jbtAcakA = new JButton();
        jbtAcakB = new JButton();
        jbtOK = new JButton();
        jlbGx = new JLabel();
        jtfGx = new JTextField();
        jlbGy = new JLabel();
        jtfGy = new JTextField();
        jbtAcakPointG = new JButton();
        label4 = new JLabel();
        label5 = new JLabel();
        jbtOK2 = new JButton();
        jlbPrivKeyD = new JLabel();
        label6 = new JLabel();
        jtfPrivKeyD = new JTextField();
        jbtAcakD = new JButton();
        jpSignGen = new JPanel();
        jlbHashType = new JLabel();
        jcbHash = new JComboBox();
        jlbHashType2 = new JLabel();
        jlbHashType3 = new JLabel();
        jlbHashType4 = new JLabel();
        scrollPane1 = new JScrollPane();
        jtaMessage = new JTextArea();
        jlbHashType5 = new JLabel();
        jlbHashType6 = new JLabel();
        jtaHashedMessage = new JTextArea();
        jbtConfirmMessage = new JButton();
        jpSignVerification = new JPanel();
        jlbSignR = new JLabel();
        jtfSignR = new JTextField();
        label7 = new JLabel();
        label8 = new JLabel();
        jlbSignS = new JLabel();
        jtfSignS = new JTextField();
        jbtOK3 = new JButton();
        jlbSignS2 = new JLabel();
        label9 = new JLabel();
        scrollPane2 = new JScrollPane();
        jtaMessage2 = new JTextArea();
        jlbTitle = new JLabel();

        //======== this ========
        setTitle("ECDSA Scheme");
        setResizable(false);
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentHidden(ComponentEvent e) {
                thisComponentHidden(e);
            }
        });
        Container contentPane = getContentPane();
        contentPane.setLayout(new BorderLayout());

        //======== jpInfo ========
        {

            // JFormDesigner evaluation mark
            jpInfo.setBorder(new javax.swing.border.CompoundBorder(
                new javax.swing.border.TitledBorder(new javax.swing.border.EmptyBorder(0, 0, 0, 0),
                    "JFormDesigner Evaluation", javax.swing.border.TitledBorder.CENTER,
                    javax.swing.border.TitledBorder.BOTTOM, new java.awt.Font("Dialog", java.awt.Font.BOLD, 12),
                    java.awt.Color.red), jpInfo.getBorder())); jpInfo.addPropertyChangeListener(new java.beans.PropertyChangeListener(){public void propertyChange(java.beans.PropertyChangeEvent e){if("border".equals(e.getPropertyName()))throw new RuntimeException();}});


            //---- jlbInfo ----
            jlbInfo.setText("Keterangan Proses :");
            jlbInfo.setFont(jlbInfo.getFont().deriveFont(jlbInfo.getFont().getStyle() | Font.BOLD, jlbInfo.getFont().getSize() + 2f));

            //======== jspInfo ========
            {

                //---- jtaInfo ----
                jtaInfo.setLineWrap(true);
                jtaInfo.setEditable(false);
                jspInfo.setViewportView(jtaInfo);
            }

            //---- jbtSkipKeyGen ----
            jbtSkipKeyGen.setText("skip");
            jbtSkipKeyGen.setEnabled(false);
            jbtSkipKeyGen.setVisible(false);

            //---- jbtNextKeyGen ----
            jbtNextKeyGen.setText("next");
            jbtNextKeyGen.setVisible(false);

            //---- jbtPrevKeyGen ----
            jbtPrevKeyGen.setText("prev");
            jbtPrevKeyGen.setEnabled(false);
            jbtPrevKeyGen.setVisible(false);

            GroupLayout jpInfoLayout = new GroupLayout(jpInfo);
            jpInfo.setLayout(jpInfoLayout);
            jpInfoLayout.setHorizontalGroup(
                jpInfoLayout.createParallelGroup()
                    .addGroup(jpInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpInfoLayout.createParallelGroup()
                            .addGroup(jpInfoLayout.createSequentialGroup()
                                .addComponent(jlbInfo)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jbtPrevKeyGen)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jbtNextKeyGen)
                                .addGap(10, 10, 10)
                                .addComponent(jbtSkipKeyGen))
                            .addComponent(jspInfo))
                        .addContainerGap())
            );
            jpInfoLayout.setVerticalGroup(
                jpInfoLayout.createParallelGroup()
                    .addGroup(jpInfoLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jpInfoLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                            .addComponent(jlbInfo)
                            .addComponent(jbtSkipKeyGen)
                            .addComponent(jbtNextKeyGen)
                            .addComponent(jbtPrevKeyGen))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jspInfo, GroupLayout.DEFAULT_SIZE, 130, Short.MAX_VALUE)
                        .addContainerGap())
            );
        }
        contentPane.add(jpInfo, BorderLayout.SOUTH);

        //======== jpIsi ========
        {
            jpIsi.setMinimumSize(new Dimension(0, 0));
            jpIsi.setMaximumSize(new Dimension(800, 600));

            //======== jtpECDSAScheme ========
            {
                jtpECDSAScheme.setFont(new Font("Century Gothic", Font.PLAIN, 13));

                //======== jpKeyGen ========
                {

                    //---- jlbField ----
                    jlbField.setText("Field Fp");
                    jlbField.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jlbA ----
                    jlbA.setText("a");
                    jlbA.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jlbB ----
                    jlbB.setText("b");
                    jlbB.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- label1 ----
                    label1.setText(":");

                    //---- label2 ----
                    label2.setText(":");

                    //---- label3 ----
                    label3.setText(":");

                    //---- jtfFieldFp ----
                    jtfFieldFp.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfFieldFp.setToolTipText("Tentukan Field Fp : untuk kurva pada Field p Bilangan Prima Besar");
                    jtfFieldFp.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jtfFieldFpMouseEntered();
                        }
                    });
                    jtfFieldFp.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            jtfFieldFpFocusLost();
                        }
                    });

                    //---- jtfA ----
                    jtfA.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfA.setToolTipText("Tentukan Konstanta A Bilangan antara 1 - (p-1)");
                    jtfA.setEnabled(false);
                    jtfA.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jtfAMouseEntered();
                        }
                    });
                    jtfA.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            jtfAFocusLost();
                        }
                    });

                    //---- jtfB ----
                    jtfB.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfB.setToolTipText("Tentukan Konstanta B Bilangan antara 1 - (p-1)");
                    jtfB.setEnabled(false);
                    jtfB.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jtfBMouseEntered();
                        }
                    });
                    jtfB.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            jtfBFocusLost();
                        }
                    });

                    //---- jbtAcakFieldFp ----
                    jbtAcakFieldFp.setText("Acak");
                    jbtAcakFieldFp.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtAcakFieldFpActionPerformed();
                        }
                    });

                    //---- jbtAcakA ----
                    jbtAcakA.setText("Acak");
                    jbtAcakA.setEnabled(false);
                    jbtAcakA.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtAcakAActionPerformed();
                        }
                    });

                    //---- jbtAcakB ----
                    jbtAcakB.setText("Acak");
                    jbtAcakB.setEnabled(false);
                    jbtAcakB.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtAcakBActionPerformed();
                        }
                    });

                    //---- jbtOK ----
                    jbtOK.setText("konfirmasi kurva");
                    jbtOK.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    jbtOK.setEnabled(false);
                    jbtOK.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtOKActionPerformed();
                        }
                    });

                    //---- jlbGx ----
                    jlbGx.setText("Gx");
                    jlbGx.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jtfGx ----
                    jtfGx.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfGx.setToolTipText("Tentukan Konstanta B Bilangan antara 1 - (p-1)");
                    jtfGx.setEnabled(false);
                    jtfGx.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jtfBMouseEntered();
                        }
                    });
                    jtfGx.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            jtfGxFocusLost();
                        }
                    });

                    //---- jlbGy ----
                    jlbGy.setText("Gy");
                    jlbGy.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jtfGy ----
                    jtfGy.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfGy.setToolTipText("Tentukan Konstanta B Bilangan antara 1 - (p-1)");
                    jtfGy.setEnabled(false);
                    jtfGy.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jtfBMouseEntered();
                        }
                    });
                    jtfGy.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            jtfGyFocusLost();
                        }
                    });

                    //---- jbtAcakPointG ----
                    jbtAcakPointG.setText("Acak");
                    jbtAcakPointG.setEnabled(false);
                    jbtAcakPointG.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtAcakPointGActionPerformed();
                        }
                    });

                    //---- label4 ----
                    label4.setText(":");

                    //---- label5 ----
                    label5.setText(":");

                    //---- jbtOK2 ----
                    jbtOK2.setText("Parameter siap");
                    jbtOK2.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    jbtOK2.setEnabled(false);
                    jbtOK2.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtOK2ActionPerformed();
                        }
                    });

                    //---- jlbPrivKeyD ----
                    jlbPrivKeyD.setText("privat d");
                    jlbPrivKeyD.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- label6 ----
                    label6.setText(":");

                    //---- jtfPrivKeyD ----
                    jtfPrivKeyD.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfPrivKeyD.setToolTipText("Tentukan Konstanta B Bilangan antara 1 - (p-1)");
                    jtfPrivKeyD.setEnabled(false);
                    jtfPrivKeyD.addMouseListener(new MouseAdapter() {
                        @Override
                        public void mouseEntered(MouseEvent e) {
                            jtfBMouseEntered();
                        }
                    });
                    jtfPrivKeyD.addFocusListener(new FocusAdapter() {
                        @Override
                        public void focusLost(FocusEvent e) {
                            jtfPrivKeyDFocusLost();
                        }
                    });

                    //---- jbtAcakD ----
                    jbtAcakD.setText("Acak");
                    jbtAcakD.setEnabled(false);
                    jbtAcakD.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtAcakDActionPerformed();
                        }
                    });

                    GroupLayout jpKeyGenLayout = new GroupLayout(jpKeyGen);
                    jpKeyGen.setLayout(jpKeyGenLayout);
                    jpKeyGenLayout.setHorizontalGroup(
                        jpKeyGenLayout.createParallelGroup()
                            .addGroup(jpKeyGenLayout.createSequentialGroup()
                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                        .addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jbtOK, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                        .addGap(23, 23, 23)
                                        .addGroup(jpKeyGenLayout.createParallelGroup()
                                            .addGroup(jpKeyGenLayout.createSequentialGroup()
                                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                                    .addComponent(jlbA)
                                                    .addComponent(jlbB)
                                                    .addComponent(jlbField))
                                                .addGap(42, 42, 42)
                                                .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                                                    .addGroup(GroupLayout.Alignment.LEADING, jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(label1, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jtfFieldFp, GroupLayout.PREFERRED_SIZE, 555, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(GroupLayout.Alignment.LEADING, jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(label3, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jtfB, GroupLayout.PREFERRED_SIZE, 555, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(GroupLayout.Alignment.LEADING, jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(label2, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jtfA, GroupLayout.DEFAULT_SIZE, 1, Short.MAX_VALUE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                                    .addComponent(jbtAcakFieldFp)
                                                    .addComponent(jbtAcakA)
                                                    .addComponent(jbtAcakB))
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(GroupLayout.Alignment.TRAILING, jpKeyGenLayout.createSequentialGroup()
                                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(jlbPrivKeyD)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                                        .addComponent(label6, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(jlbGx)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 65, Short.MAX_VALUE)
                                                        .addComponent(label4, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(GroupLayout.Alignment.TRAILING, jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(jlbGy)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                        .addComponent(label5, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)))
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                                        .addComponent(jtfPrivKeyD, GroupLayout.PREFERRED_SIZE, 354, GroupLayout.PREFERRED_SIZE)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jbtAcakD)
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(jbtOK2, GroupLayout.PREFERRED_SIZE, 202, GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                                        .addGroup(jpKeyGenLayout.createParallelGroup()
                                                            .addComponent(jtfGy, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE)
                                                            .addComponent(jtfGx, GroupLayout.PREFERRED_SIZE, 560, GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                                        .addComponent(jbtAcakPointG)))))))
                                .addContainerGap())
                    );
                    jpKeyGenLayout.setVerticalGroup(
                        jpKeyGenLayout.createParallelGroup()
                            .addGroup(jpKeyGenLayout.createSequentialGroup()
                                .addGap(19, 19, 19)
                                .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(label1)
                                    .addComponent(jbtAcakFieldFp)
                                    .addComponent(jtfFieldFp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbField))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbA, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label2)
                                    .addComponent(jtfA, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtAcakA))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbB, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(label3)
                                    .addComponent(jtfB, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jbtAcakB))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtOK)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                    .addGroup(jpKeyGenLayout.createSequentialGroup()
                                        .addGroup(jpKeyGenLayout.createParallelGroup()
                                            .addGroup(jpKeyGenLayout.createSequentialGroup()
                                                .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jtfGx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(label4))
                                                .addGap(8, 8, 8))
                                            .addGroup(GroupLayout.Alignment.TRAILING, jpKeyGenLayout.createSequentialGroup()
                                                .addComponent(jlbGx)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(jpKeyGenLayout.createParallelGroup()
                                            .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                                .addComponent(jtfGy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addComponent(label5))
                                            .addComponent(jlbGy)))
                                    .addComponent(jbtAcakPointG, GroupLayout.PREFERRED_SIZE, 54, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpKeyGenLayout.createParallelGroup()
                                    .addComponent(jlbPrivKeyD)
                                    .addComponent(jbtOK2)
                                    .addGroup(jpKeyGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(label6)
                                        .addComponent(jbtAcakD)
                                        .addComponent(jtfPrivKeyD, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addGap(29, 29, 29))
                    );
                }
                jtpECDSAScheme.addTab("KEY GENERATION", jpKeyGen);

                //======== jpSignGen ========
                {

                    //---- jlbHashType ----
                    jlbHashType.setText("Hash Message Type");
                    jlbHashType.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jcbHash ----
                    jcbHash.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    jcbHash.addItemListener(new ItemListener() {
                        @Override
                        public void itemStateChanged(ItemEvent e) {
                            jcbHashItemStateChanged();
                        }
                    });

                    //---- jlbHashType2 ----
                    jlbHashType2.setText("Message");
                    jlbHashType2.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jlbHashType3 ----
                    jlbHashType3.setText(":");
                    jlbHashType3.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jlbHashType4 ----
                    jlbHashType4.setText(":");
                    jlbHashType4.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //======== scrollPane1 ========
                    {

                        //---- jtaMessage ----
                        jtaMessage.setLineWrap(true);
                        jtaMessage.setWrapStyleWord(true);
                        jtaMessage.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                        jtaMessage.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
                        jtaMessage.setBackground(new Color(240, 240, 240));
                        scrollPane1.setViewportView(jtaMessage);
                    }

                    //---- jlbHashType5 ----
                    jlbHashType5.setText("Hashed Message");
                    jlbHashType5.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jlbHashType6 ----
                    jlbHashType6.setText(":");
                    jlbHashType6.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jtaHashedMessage ----
                    jtaHashedMessage.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
                    jtaHashedMessage.setWrapStyleWord(true);
                    jtaHashedMessage.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtaHashedMessage.setLineWrap(true);
                    jtaHashedMessage.setEditable(false);
                    jtaHashedMessage.setBackground(new Color(240, 240, 240));

                    //---- jbtConfirmMessage ----
                    jbtConfirmMessage.setText("Confirm Message");
                    jbtConfirmMessage.setFont(new Font("Tahoma", Font.PLAIN, 15));
                    jbtConfirmMessage.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtConfirmMessageActionPerformed();
                        }
                    });

                    GroupLayout jpSignGenLayout = new GroupLayout(jpSignGen);
                    jpSignGen.setLayout(jpSignGenLayout);
                    jpSignGenLayout.setHorizontalGroup(
                        jpSignGenLayout.createParallelGroup()
                            .addGroup(jpSignGenLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jpSignGenLayout.createParallelGroup()
                                    .addGroup(jpSignGenLayout.createSequentialGroup()
                                        .addGroup(jpSignGenLayout.createParallelGroup()
                                            .addGroup(jpSignGenLayout.createSequentialGroup()
                                                .addComponent(jlbHashType5)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jlbHashType6, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jpSignGenLayout.createSequentialGroup()
                                                .addComponent(jlbHashType2)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jlbHashType4, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE))
                                            .addGroup(jpSignGenLayout.createSequentialGroup()
                                                .addComponent(jlbHashType)
                                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jlbHashType3, GroupLayout.PREFERRED_SIZE, 15, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 3, Short.MAX_VALUE)))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpSignGenLayout.createParallelGroup()
                                            .addGroup(jpSignGenLayout.createSequentialGroup()
                                                .addComponent(jcbHash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(scrollPane1, GroupLayout.Alignment.TRAILING)
                                            .addComponent(jtaHashedMessage, GroupLayout.Alignment.TRAILING)))
                                    .addGroup(GroupLayout.Alignment.TRAILING, jpSignGenLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jbtConfirmMessage)))
                                .addContainerGap())
                    );
                    jpSignGenLayout.setVerticalGroup(
                        jpSignGenLayout.createParallelGroup()
                            .addGroup(jpSignGenLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jpSignGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbHashType)
                                    .addComponent(jlbHashType3)
                                    .addComponent(jcbHash, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpSignGenLayout.createParallelGroup()
                                    .addComponent(scrollPane1, GroupLayout.PREFERRED_SIZE, 130, GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jlbHashType2)
                                    .addComponent(jlbHashType4))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpSignGenLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                    .addComponent(jlbHashType5)
                                    .addComponent(jlbHashType6)
                                    .addComponent(jtaHashedMessage, GroupLayout.PREFERRED_SIZE, 50, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jbtConfirmMessage)
                                .addContainerGap(18, Short.MAX_VALUE))
                    );
                }
                jtpECDSAScheme.addTab("SIGNATURE GENERATION", jpSignGen);

                //======== jpSignVerification ========
                {

                    //---- jlbSignR ----
                    jlbSignR.setText("Sign r");
                    jlbSignR.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jtfSignR ----
                    jtfSignR.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfSignR.setToolTipText("Tentukan Field Fp : untuk kurva pada Field p Bilangan Prima Besar");
                    jtfSignR.setEditable(false);

                    //---- label7 ----
                    label7.setText(":");
                    label7.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- label8 ----
                    label8.setText(":");
                    label8.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jlbSignS ----
                    jlbSignS.setText("Sign s");
                    jlbSignS.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- jtfSignS ----
                    jtfSignS.setFont(new Font("Times New Roman", Font.PLAIN, 14));
                    jtfSignS.setToolTipText("Tentukan Konstanta A Bilangan antara 1 - (p-1)");
                    jtfSignS.setEditable(false);

                    //---- jbtOK3 ----
                    jbtOK3.setText("Verifikasi Tanda Tangan");
                    jbtOK3.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            jbtOK3ActionPerformed();
                        }
                    });

                    //---- jlbSignS2 ----
                    jlbSignS2.setText("Message");
                    jlbSignS2.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //---- label9 ----
                    label9.setText(":");
                    label9.setFont(new Font("Tahoma", Font.PLAIN, 15));

                    //======== scrollPane2 ========
                    {

                        //---- jtaMessage2 ----
                        jtaMessage2.setEditable(false);
                        jtaMessage2.setBackground(new Color(240, 240, 240));
                        jtaMessage2.setBorder(new SoftBevelBorder(SoftBevelBorder.LOWERED));
                        jtaMessage2.setLineWrap(true);
                        jtaMessage2.setWrapStyleWord(true);
                        scrollPane2.setViewportView(jtaMessage2);
                    }

                    GroupLayout jpSignVerificationLayout = new GroupLayout(jpSignVerification);
                    jpSignVerification.setLayout(jpSignVerificationLayout);
                    jpSignVerificationLayout.setHorizontalGroup(
                        jpSignVerificationLayout.createParallelGroup()
                            .addGroup(jpSignVerificationLayout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jpSignVerificationLayout.createParallelGroup()
                                    .addGroup(jpSignVerificationLayout.createSequentialGroup()
                                        .addGroup(jpSignVerificationLayout.createParallelGroup()
                                            .addComponent(jlbSignS2)
                                            .addComponent(jlbSignS)
                                            .addComponent(jlbSignR))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(jpSignVerificationLayout.createParallelGroup()
                                            .addComponent(label7, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label8, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE)
                                            .addComponent(label9, GroupLayout.PREFERRED_SIZE, 14, GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jpSignVerificationLayout.createParallelGroup()
                                            .addComponent(jtfSignS)
                                            .addComponent(jtfSignR)
                                            .addComponent(scrollPane2, GroupLayout.Alignment.TRAILING)))
                                    .addGroup(GroupLayout.Alignment.TRAILING, jpSignVerificationLayout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(jbtOK3, GroupLayout.PREFERRED_SIZE, 205, GroupLayout.PREFERRED_SIZE)))
                                .addContainerGap())
                    );
                    jpSignVerificationLayout.setVerticalGroup(
                        jpSignVerificationLayout.createParallelGroup()
                            .addGroup(jpSignVerificationLayout.createSequentialGroup()
                                .addGroup(jpSignVerificationLayout.createParallelGroup()
                                    .addGroup(jpSignVerificationLayout.createSequentialGroup()
                                        .addGap(15, 15, 15)
                                        .addGroup(jpSignVerificationLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                            .addComponent(jlbSignR)
                                            .addComponent(label7)))
                                    .addGroup(jpSignVerificationLayout.createSequentialGroup()
                                        .addContainerGap()
                                        .addComponent(jtfSignR, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jpSignVerificationLayout.createParallelGroup()
                                    .addGroup(jpSignVerificationLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jlbSignS, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(label8))
                                    .addComponent(jtfSignS, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jpSignVerificationLayout.createParallelGroup()
                                    .addGroup(jpSignVerificationLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                        .addComponent(jlbSignS2)
                                        .addComponent(label9))
                                    .addComponent(scrollPane2, GroupLayout.PREFERRED_SIZE, 162, GroupLayout.PREFERRED_SIZE))
                                .addGap(9, 9, 9)
                                .addComponent(jbtOK3)
                                .addGap(16, 16, 16))
                    );
                }
                jtpECDSAScheme.addTab("SIGNATURE VERIFICATION", jpSignVerification);
            }

            //---- jlbTitle ----
            jlbTitle.setText("ECDSA Scheme");
            jlbTitle.setFont(new Font("Magneto", Font.BOLD, 42));

            GroupLayout jpIsiLayout = new GroupLayout(jpIsi);
            jpIsi.setLayout(jpIsiLayout);
            jpIsiLayout.setHorizontalGroup(
                jpIsiLayout.createParallelGroup()
                    .addGroup(jpIsiLayout.createSequentialGroup()
                        .addGroup(jpIsiLayout.createParallelGroup()
                            .addGroup(jpIsiLayout.createSequentialGroup()
                                .addGap(197, 197, 197)
                                .addComponent(jlbTitle)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(jpIsiLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jtpECDSAScheme, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE)))
                        .addContainerGap())
            );
            jpIsiLayout.setVerticalGroup(
                jpIsiLayout.createParallelGroup()
                    .addGroup(GroupLayout.Alignment.TRAILING, jpIsiLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jlbTitle)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jtpECDSAScheme, GroupLayout.PREFERRED_SIZE, 315, GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
            );
        }
        contentPane.add(jpIsi, BorderLayout.CENTER);
        pack();
        setLocationRelativeTo(getOwner());
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
	/*
	jtfFieldFp.getDocument().addDocumentListener(new DocumentListener() {
	    public void changedUpdate(DocumentEvent e) {
		warn();
	    }
	    public void removeUpdate(DocumentEvent e) {
		warn();
	    }
	    public void insertUpdate(DocumentEvent e) {
		warn();
	    }

	    public void warn() {
		if (jtfFieldFp.getText().isEmpty())
		    JOptionPane.showMessageDialog(new JFrame(), "Field Fp tidak boleh kosong!");
		try
		{
		    p = new BigInteger(jtfFieldFp.getText());
		    if (p.isProbablePrime(5))
			JOptionPane.showMessageDialog(new JFrame(), "Field Fp harus bilangan prima!");
		}  
		catch(NumberFormatException ex)  
		{
		    JOptionPane.showMessageDialog(new JFrame(), "illegal digit!");
		}
	    }
	});
	*/
	
	jtaMessage.getDocument().addDocumentListener(new DocumentListener() {
	    public void changedUpdate(DocumentEvent e) {
		hash();
	    }
	    public void removeUpdate(DocumentEvent e) {
		hash();
	    }
	    public void insertUpdate(DocumentEvent e) {
		hash();
	    }

	    public void hash() {
		String message = jtaMessage.getText();
		hashtype = jcbHash.getSelectedItem() + "";
		md = new MessageToDigest(hashtype);
		jtaHashedMessage.setText(md.computeDigest(message.getBytes()));
	    }
	});
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - arden allstars
    private JPanel jpInfo;
    private JLabel jlbInfo;
    private JScrollPane jspInfo;
    private JTextArea jtaInfo;
    private JButton jbtSkipKeyGen;
    private JButton jbtNextKeyGen;
    private JButton jbtPrevKeyGen;
    private JPanel jpIsi;
    private JTabbedPane jtpECDSAScheme;
    private JPanel jpKeyGen;
    private JLabel jlbField;
    private JLabel jlbA;
    private JLabel jlbB;
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField jtfFieldFp;
    private JTextField jtfA;
    private JTextField jtfB;
    private JButton jbtAcakFieldFp;
    private JButton jbtAcakA;
    private JButton jbtAcakB;
    private JButton jbtOK;
    private JLabel jlbGx;
    private JTextField jtfGx;
    private JLabel jlbGy;
    private JTextField jtfGy;
    private JButton jbtAcakPointG;
    private JLabel label4;
    private JLabel label5;
    private JButton jbtOK2;
    private JLabel jlbPrivKeyD;
    private JLabel label6;
    private JTextField jtfPrivKeyD;
    private JButton jbtAcakD;
    private JPanel jpSignGen;
    private JLabel jlbHashType;
    private JComboBox jcbHash;
    private JLabel jlbHashType2;
    private JLabel jlbHashType3;
    private JLabel jlbHashType4;
    private JScrollPane scrollPane1;
    private JTextArea jtaMessage;
    private JLabel jlbHashType5;
    private JLabel jlbHashType6;
    private JTextArea jtaHashedMessage;
    private JButton jbtConfirmMessage;
    private JPanel jpSignVerification;
    private JLabel jlbSignR;
    private JTextField jtfSignR;
    private JLabel label7;
    private JLabel label8;
    private JLabel jlbSignS;
    private JTextField jtfSignS;
    private JButton jbtOK3;
    private JLabel jlbSignS2;
    private JLabel label9;
    private JScrollPane scrollPane2;
    private JTextArea jtaMessage2;
    private JLabel jlbTitle;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}
