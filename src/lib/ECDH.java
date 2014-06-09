/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

/**
 *
 * @author Arden
 */
public class ECDH {
    public String name;
    public BigInteger a, b;
    public Kurva kurva;
    public Point Q, QA;
    MessageToDigest mDigest = new MessageToDigest("SHA-256");
    
    private BigInteger dA;
    private BigInteger dAB;
    
    public void setDomainParameter (Kurva kurva, BigInteger a, BigInteger b, Point Q) {
	this.kurva = kurva;
	this.a = a;
	this.b = b;
	this.Q = Q;
    }
    
    public void setName (String name) {
	this.name = name;
    }
    
    public void setPrivKey () {
	System.out.println("nama = " + name);
	dA = new BigInteger(mDigest.computeDigest(name.getBytes()), 16).mod(((ECFieldFp)kurva.getField()).getP());
	System.out.println(name + " memilih private key dA = " + dA);
    }
    
    public void setPubKey () {
	QA = Q.multiply(dA);
	System.out.println(name + " menghitung public key QA = dA*Q " + dA + "*" + Q + " = " + QA );
    }
    
    public void setKeyPair (Point QB) {
	Point QAB = QB.multiply(dA);
	System.out.println(name + " menghitung Point bersama QAB = dA*QB " + dA + "*" + QB + " = " + QAB );
	dAB = QAB.getAffineY();
	System.out.println("dan memilih kunci bersama dAB = QAB.y = " + QAB + ".y" + " = " + dAB );
    }
    
    public BigInteger getKeyPair () {
	return dAB;
    }
}
