/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.util.ArrayList;

/**
 *
 * @author Arden
 */
public class ECDSAScheme {
    public String name = "NONAME";
    public ArrayList<String> strProses = new ArrayList<>();
    java.util.Random rnd = new java.security.SecureRandom();
    public MessageToDigest mDigest = new MessageToDigest("SHA-256");
    
    public BigInteger a, b, p;
    public Kurva kurva;
    public Point G, Q, R, W;
    public BigInteger n;
    
    private BigInteger d;
    
    public BigInteger	r = BigInteger.ZERO,
			s;
    private Point TMP;
    private boolean same;
    
    public void domainParameters (BigInteger p, BigInteger a, BigInteger b, Point G, BigInteger n) {
	strProses.clear();
	System.out.println("Domain Parameters");
	strProses.add("Domain Parameters\n");
	ECField Fp = new ECFieldFp(p);
	this.a = a;
	this.b = b;
	kurva = new Kurva(Fp, a, b);
	this.G = G;
	this.n = n;
	
	strProses.add("kurva : " + kurva +"\n"
		+ "Point G = " + G + "\n");
	System.out.println("kurva : " + kurva +"\n"
		+ "G = " + G + "\n"
		+ "n = " + n);
    }
    
    public void setHashMethod (String algorithm) {
	mDigest	= new MessageToDigest(algorithm);
    }
    
    public void keyPairGeneration () {
	Q = G.multiply(d);
	strProses.add("hitung publik key Q :\nQ = dG = " + d + "*" + G + " = " + d + "*" + G + " = " + Q + "\n");
	System.out.println("Q = dG = " + d + "*" + G + " = " + d + "*" + G + " = " + Q);
    }
    
    public void setPrivateKey(BigInteger d) {
	this.d = d;
	strProses.add("private d = " + d + "\n");
	System.out.println("private d = " + d);
    }
    
    public void setPrivateKey() {
	//user = "haha";
	//d = (mDigest.computeDigest(user.getBytes()).modInverse(((ECFieldFp)(kurva.getField())).getP()));
	d = new BigInteger(mDigest.computeDigest(name.getBytes()), 16).mod(n);
	strProses.add("private d = " + d + "\n");
	System.out.println("private d = " + d);
    }
    
    public BigInteger getPrivateKey() {
	return d;
    }
    
    public void SignatureGeneration(String message) {
	strProses.clear();
	strProses.add("\n\n\nSignature Generation\n");
	System.out.println("Signature Generation");
	BigInteger e = new BigInteger(mDigest.computeDigest(message.getBytes()), 16).mod(n);
	/*
	a)	Pilih nilai k antara [1,n-1]
	b)	Hitung nilai (x1,y1) = kG dan r = x1 mod n. Jika r = 0 ulangi langkah a
	c)	Hitung s = k-1 (e + d . r) mod n. Nilai e adalah Hash (m). Jika s = 0 ulangi langkah a
	d)	Signature dari pesan m adalah (r,s)
	*/
	BigInteger k = BigInteger.ONE;
	r = BigInteger.ZERO;
	while (r.compareTo(BigInteger.ZERO) != 1) {
	    k = new BigInteger(n.bitCount(), rnd);
	    strProses.add("Tentukan k secara acak \nk = " + k + "\n");
	    
	    while (k.compareTo(n.subtract(BigInteger.ONE)) == 1 || k.compareTo(BigInteger.ONE) != 1) {
		k = new BigInteger(n.bitCount(), rnd);
		strProses.add("Karena k < 1 atau k > p\nAcak kembali nilai k\nk = " + k);
	    }
	    strProses.add("karena k = " + k + " sudah sesuai(k <> 0), maka lanjut ke tahap berikutnya yaitu :\n");
	    System.out.println("k = " + k);
	    
	    //r = (G.multiply(k).getAffineX()).mod(n);
	    TMP = G.multiply(k);
	    r = (TMP.getAffineX());
	    strProses.add("Hitung e = HASH(Message) mod p = " + mDigest.getCurrentAlgorithm() + "(message) mod " + n + " = " + e + "\n");
	    strProses.add("Hitung r = (k*G).x = (" + k + "*" + G + ").x = (" + TMP + ").x = " + r + "\n");
	    if (r.compareTo(BigInteger.ZERO) == 1) {
		s = k.add(e.add(d.multiply(r)));
		strProses.add("karena didapatkan r = " + r + " > 0, maka hitung s = k + e + d*r\n"
			+ "s = " + k + " + " + e + " + " + " + " + d + "*" + r + " = " + s + "\n");
		if (s.compareTo(BigInteger.ZERO) != 1) {
		    r = BigInteger.ZERO;
		    strProses.add("karena didapatkan s = 0 maka ulangi dari tahap penentuan bilangan acak k\n");
		}
	    }
	    else
		strProses.add("karena didapatkan r = 0 maka ulangi dari tahap penentuan bilangan acak k\n");
	}
	strProses.add ("Maka didapatkan Tanda Tangan Digital : \nr = " + r + ", dan s = " + s + "\nProses Signature Generation Berhasil\n");
	//strProses.add("r = (kG).x = ("+ k + "*" + G + ").x = " + TMP + ".x = " + r + "\n"
	//		    + "s = k+(e+d*r) = " + k + "+(" + e + "+" + d + "*" + r + ") = " + s + "\n"
	//		    + "Proses Signature Generation selesai\n\n");
	System.out.println("r = (kG).x = ("+ k + "*" + G + ").x = " + TMP + ".x = " + r + "\n"
			    + "s = k+(e+d*r) = " + k + "+(" + e + "+" + d + "*" + r + ") = " + s);
    }
    
    public boolean SignatureVerification (String message, String SignR, String SignS) {
	r = new BigInteger(SignR);
	s = new BigInteger(SignS);
	strProses.add("\n\nSignature Verification\n");
	System.out.println("Signature Verification");
	BigInteger  e = new BigInteger(mDigest.computeDigest(message.getBytes()), 16).mod(n),
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
	strProses.add("R = -(e*G + r*Q) = -(" + e + "*" + G + " + " + r + "*" + Q + ") = -(" + G.multiply(e) + " + " + Q.multiply(r) + ") = " + R + "\n");
	System.out.println("R = -(e*G + r*Q) = -(" + e + "*" + G + " + " + r + "*" + Q + ") = -(" + G.multiply(e) + " + " + Q.multiply(r) + ") = " + R);
	
	W = R.add(G.multiply(s));
	strProses.add("W = R + s*G = " + R + " + " + s + "*" + G + " = " +  R + " + " + G.multiply(s) + " = " + W + "\n");
	System.out.println("W = R + s*G = " + R + " + " + s + "*" + G + " = " +  R + " + " + G.multiply(s) + " = " + W);
	
	//V = V.negate();
	
	//System.out.println("V = V^-1 = " + V.negate() + "^-1 = " + V);
	
	v = W.getAffineX();
	strProses.add("v = W.x = " + W + ".x = " + v + "\n");
	System.out.println("v = W.x = " + W + ".x = " + v);
	
	strProses.add("v = " + v + ", r = " + r + "\n");
	System.out.println("v = " + v + ", r = " + r);
	
	strProses.add("v " + (v.equals(r) ? "=" : "!=") + " r\n");
	System.out.println("v " + (v.equals(r) ? "=" : "!=") + " r");
	
	same = v.equals(r);
	
	if (v.equals(r)) {
	    strProses.add("Verifikasi Berhasil\n");
	    System.out.println("Verifikasi Berhasil");
	}
	else {
	    strProses.add("Verifikasi Gagal\n");
	    System.out.println("Verifikasi Gagal");
	}
	return same;
    }
}
