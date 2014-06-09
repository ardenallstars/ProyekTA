/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;

/**
 *
 * @author Arden
 */
public class ECDSAScheme3 {
    public String user = "NONAME";
    java.util.Random rnd = new java.security.SecureRandom();
    MessageToDigest mDigest = new MessageToDigest("SHA-256");
    
    public BigInteger a, b, p;
    public Kurva kurva;
    public Point G, Q, R, V;
    public BigInteger n;
    
    private BigInteger d;
    
    public BigInteger	r = BigInteger.ZERO,
			s;
    
    public void domainParameters (BigInteger p, BigInteger a, BigInteger b, Point G, BigInteger n) {
	System.out.println("Domain Parameters");
	ECField Fp = new ECFieldFp(p);
	this.a = a;
	this.b = b;
	kurva = new Kurva(Fp, a, b);
	this.G = G;
	this.n = n;
	
	System.out.println("kurva : " + kurva +"\n"
		+ "G = " + G + "\n"
		+ "n = " + n);
    }
    
    public void keyPairGeneration () {
	setPrivateKey();
	
	Q = G.multiply(d);
	System.out.println("Q = dG = " + d + "*" + G + " = " + d + "*" + G + " = " + Q);
    }
    
    public void setPrivateKey(BigInteger d) {
	this.d = d;
	System.out.println("private d = " + d);
    }
    
    public void setPrivateKey() {
	user = "haha";
	//d = (mDigest.computeDigest(user.getBytes()).modInverse(((ECFieldFp)(kurva.getField())).getP()));
	d = new BigInteger(mDigest.computeDigest(user.getBytes()), 16).mod(n);
	System.out.println("private d = " + d);
    }
    
    public BigInteger getPrivateKey() {
	return d;
    }
    
    public void SignatureGeneration(String message) {
	System.out.println("Signature Generation");
	BigInteger e = new BigInteger(mDigest.computeDigest(message.getBytes()), 16).mod(n);
	/*
	a)	Pilih nilai k antara [1,n-1]
	b)	Hitung nilai (x1,y1) = kG dan r = x1 mod n. Jika r = 0 ulangi langkah a
	c)	Hitung s = k-1 (e + d . r) mod n. Nilai e adalah Hash (m). Jika s = 0 ulangi langkah a
	d)	Signature dari pesan m adalah (r,s)
	*/
	BigInteger k = BigInteger.ONE;
	while (r.compareTo(BigInteger.ZERO) != 1) {
	    k = new BigInteger(n.bitCount(), rnd);
	    while (k.compareTo(n) == 1 || k.compareTo(BigInteger.ONE) != 1) {
		k = new BigInteger(n.bitCount(), rnd);
	    }
	    System.out.println("k = " + k);
	    
	    r = (G.multiply(k).getAffineX()).mod(n);
	    if (r.compareTo(BigInteger.ZERO) == 1) {
		s = k.add(e.add(d.multiply(r)));
		if (s.compareTo(BigInteger.ZERO) != 1)
		    r = BigInteger.ZERO;
	    }
	}
	System.out.println("r = (kG).x = ("+ k + "*" + G + ").x = " + G.multiply(k) + ".x = " + r + "\n"
			    + "s = k+(e+d*r) = " + k + "+(" + e + "+" + d + "*" + r + ") = " + s);
    }
    
    public boolean SignatureVerification (String message, BigInteger r, BigInteger s) {
	System.out.println("Signature Verification");
	BigInteger  w,
		    e = new BigInteger(mDigest.computeDigest(message.getBytes()), 16).mod(n),
		    v;

	if (r.compareTo(BigInteger.ZERO) != 1)
	    if (r.compareTo(n) != -1)
		return false;
	if (s.compareTo(BigInteger.ZERO) != 1)
	    if (s.compareTo(n) != -1)
		return false;
	//w = s;
	//System.out.println("w = s^-1 mod n = " + s + "^-1 mod " + n + " = " + w);
	//System.out.println("w = s = " + s);
	
	R = (G.multiply(e).add(Q.multiply(r))).negate();
	System.out.println("R = -(e*G + r*Q) = -(" + e + "*" + G + " + " + r + "*" + Q + ") = -(" + G.multiply(e) + " + " + Q.multiply(r) + ") = " + R);
	
	V = R.add(G.multiply(s));
	System.out.println("V = R + s*G = " + R + " + " + s + "*" + G + " = " +  R + " + " + G.multiply(s) + " = " + V);
	
	//V = V.negate();
	
	//System.out.println("V = V^-1 = " + V.negate() + "^-1 = " + V);
	
	v = V.getAffineX();
	System.out.println("v = V.x = " + V + ".x = " + v);
	
	System.out.println("v = " + v + ", r = " + r);
	System.out.println("v " + (v.equals(r) ? "=" : "!=") + " r");
	return v.equals(r);
    }
}
