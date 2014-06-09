/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import lib.ECDSAScheme;
import lib.Kurva;
import lib.Point;

/**
 *
 * @author Arden
 */
public class InversPointTest {
    
    public static String user  = "haha";
    static java.util.Random rnd = new java.security.SecureRandom();
    public static int bit;
    
    public static BigInteger p, a, b, n, k, s;
    public static Kurva kurva;
    public static Point G, S, K;
    
    
    public static void main (String args[]) {
	boolean random;
	
	bit = 5;
	//p = new BigInteger(bit, 5, rnd);
	//while (a.compareTo(new BigInteger("3")) != 1 && a.compareTo(p) == 1)
	//    a = new BigInteger(bit, rnd);
	//while (b.compareTo(new BigInteger("0")) != 1 && a.compareTo(p) == 1)
	//    b = new BigInteger(bit, rnd);
	random = true;
	if (random) {
	    p = new BigInteger(bit, 5, rnd);
	    ECField Fp = new ECFieldFp(p);
	    a = b = new BigInteger("0");
	    while (a.compareTo(new BigInteger("3")) != 1 || a.compareTo(p) == 1)
		a = new BigInteger (bit, rnd);
	    while (b.compareTo(new BigInteger("3")) != 1 || b.compareTo(p) == 1)
		b = new BigInteger (bit, rnd);
	    kurva = new Kurva(Fp, a, b);
	    G = kurva.getRandomPoint(p.bitCount());
	    n = p.subtract(new BigInteger(bit-1, 5, rnd));
	}
	else {
	    p = new BigInteger("23");
	    ECField Fp = new ECFieldFp(p);
	    a = new BigInteger("1");
	    b = new BigInteger("1");
	    kurva = new Kurva(Fp, a, b);
	    G = new Point(kurva, new BigInteger("13"), new BigInteger("7"));
	    n = new BigInteger("23");
	}
	k = new BigInteger(n.bitCount(), rnd);
	while (k.compareTo(n) == 1 || k.compareTo(BigInteger.ONE) != 1) {
	    k = new BigInteger(n.bitCount(), rnd);
	}
	K = G;
	System.out.println("K = " + K + "\n"
		+ "k = " + k);
	
	s = k.modInverse(n);
	S = G.multiply(s);
	System.out.println("s = k^-1 mod p = " + k + "^-1 mod " + p + " = " + s);
	System.out.println("S = sG = " + s + "*" + G + " = " + S);
	
	k = s.modInverse(n);
	K = G.multiply(k);
	System.out.println("k = s^-1 mod p = " + s + "^-1 mod " + p + " = " + k);
	//System.out.println("S = G = " + k + "^-1 mod " + p + " = " + s);
    }
}
