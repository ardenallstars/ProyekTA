/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import lib.ECDSAScheme3;
import lib.Kurva;
import lib.Point;

/**
 *
 * @author Arden
 */
public class ECDSASchemeTest {
    public static String user  = "haha";
    static java.util.Random rnd = new java.security.SecureRandom();
    public static int bit;
    
    public static BigInteger p, a, b;
    public static Kurva kurva;
    public static Point G, Q;
    public static BigInteger n;
    
    private static BigInteger d;
    
    public static void main (String args[]) {
	ECDSAScheme3 ecdsa = new ECDSAScheme3();
	boolean random;
	
	bit = 10;
	//p = new BigInteger(bit, 5, rnd);
	//while (a.compareTo(new BigInteger("3")) != 1 && a.compareTo(p) == 1)
	//    a = new BigInteger(bit, rnd);
	//while (b.compareTo(new BigInteger("0")) != 1 && a.compareTo(p) == 1)
	//    b = new BigInteger(bit, rnd);
	random = true;
	if (random) {
	    p = new BigInteger(bit, 8, rnd);
	    ECField Fp = new ECFieldFp(p);
	    a = b = new BigInteger("0");
	    while (a.compareTo(new BigInteger("3")) != 1 || a.compareTo(p) == 1)
		a = new BigInteger (bit, rnd);
	    while (b.compareTo(new BigInteger("3")) != 1 || b.compareTo(p) == 1)
		b = new BigInteger (bit, rnd);
	    kurva = new Kurva(Fp, a, b);
	    G = kurva.getRandomPoint(p.bitCount());
	    //n = p.subtract(new BigInteger(bit-1, 5, rnd)).nextProbablePrime();
	    //n = new BigInteger(bit-1, 5, rnd);
	    n = p;
	}
	else {
	    p = new BigInteger("127");
	    ECField Fp = new ECFieldFp(p);
	    a = new BigInteger("71");
	    b = new BigInteger("26");
	    kurva = new Kurva(Fp, a, b);
	    G = new Point(kurva, new BigInteger("96"), new BigInteger("61"));
	    n = new BigInteger("127");
	}
	ecdsa.domainParameters(p, a, b, G, n);
	ecdsa.keyPairGeneration();
	
	String message = "Pengujian ECDSA";
	ecdsa.SignatureGeneration(message);
	
	if (ecdsa.SignatureVerification (message, ecdsa.r, ecdsa.s))
	    System.out.println("Signature Verified");
	else
	    System.out.println("Signature Invalid");
    }
}
