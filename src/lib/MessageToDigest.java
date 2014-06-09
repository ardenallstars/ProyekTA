/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 *
 * @author Arden
 */
 
public final class MessageToDigest {
    private MessageDigest currentAlgorithm;
    
    public MessageToDigest (String alg) {
	setAlgorithm(alg);
    }
    
    public void setAlgorithm(String alg) {
	try {
	    currentAlgorithm = MessageDigest.getInstance(alg);
	} catch (NoSuchAlgorithmException e) {
	    System.out.println("" + e);
	}
    }
    
    public String getCurrentAlgorithm() {
	return currentAlgorithm.getAlgorithm();
    }
/*
    public void loadFile() {
      JFileChooser chooser = new JFileChooser();
      chooser.setCurrentDirectory(new File("."));

      int r = chooser.showOpenDialog(this);
      if (r == JFileChooser.APPROVE_OPTION) {
	String name = chooser.getSelectedFile().getAbsolutePath();
	computeDigest(loadBytes(name));
      }
    }
*/
    public byte[] loadBytes(String name) {
	FileInputStream in = null;

	try {
	    in = new FileInputStream(name);
	    ByteArrayOutputStream buffer = new ByteArrayOutputStream();
	    int ch;
	    while ((ch = in.read()) != -1)
		buffer.write(ch);
	    return buffer.toByteArray();
	} catch (IOException e) {
	    if (in != null) {
		try {
		    in.close();
		} catch (IOException e2) {
		}
	    }
	    return null;
	}
    }

    public String computeDigest(byte[] b) {
	//currentAlgorithm.reset();
	currentAlgorithm.update(b);
	byte[] hash = currentAlgorithm.digest();
	String d = "";
	for (int i = 0; i < hash.length; i++) {
	    int v = hash[i] & 0xFF;
	    if (v < 16)
		d += "0";
	    d += Integer.toString(v, 16).toUpperCase() + "";
	}
	
	return d;
    }
}
