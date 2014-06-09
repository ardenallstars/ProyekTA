/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import lib.*;
/**
 *
 * @author Arden
 */
public class CobaECC {
    public static void main (String argsp[]) {
	int bit = 10;
	java.util.Random rnd = new java.util.Random();
	
	ECField Fp;
	Kurva kurva;
	Point P, Q, G ; 
	BigInteger a, b, p;
	
	boolean random = false;
	if (random) {
	    p = new BigInteger(bit, 5, rnd);
	    Fp = new ECFieldFp(p);
	    a = b = new BigInteger("0");
	    while (a.compareTo(new BigInteger("3")) != 1 || a.compareTo(p) == 1)
		a = new BigInteger (bit, rnd);
	    while (b.compareTo(new BigInteger("3")) != 1 || b.compareTo(p) == 1)
		b = new BigInteger (bit, rnd);
	    kurva = new Kurva(Fp, a, b);
	    G = kurva.getRandomPoint(p.bitCount());
	}
	else {
	    p = new BigInteger("127");
	    Fp = new ECFieldFp(p);
	    a = new BigInteger("71");
	    b = new BigInteger("26");
	    kurva = new Kurva(Fp, a, b);
	    G = new Point(kurva, new BigInteger("96"), new BigInteger("61"));
	    //G = new Point(kurva, new BigInteger("112"), new BigInteger("44"));
	    //G = kurva.getRandomPoint(p.bitCount());
	    //G = kurva.getFirstPoint();
	}
	System.out.println("kurva = " + kurva);
	
	int i;
	System.out.println("G = " + G + "\n\n");
	Q = G;
	
	Q = Q.multiply(new BigInteger("5217"));
	System.out.println("Q = " + Q + "\n");
	
	//Q = Q.add(new Point(new BigInteger("50"), new BigInteger("59")));
	//System.out.println("Q = " + Q + "\n");
	/*
	for (i = 2; i < 68; i++) {
	    P = G.multiply(new BigInteger(""+i));
	    System.out.println(i + "G = " + P + "\n\n");
	    
	    Q = Q.add(G);
	    System.out.println(i + "G = Q = " + Q + "\n");
	    //if (!P.getAffineX().equals(BigInteger.ZERO) && P.getAffineY().equals(BigInteger.ZERO))
	//	throw new IllegalArgumentException("Singular");
	}
	*/
    }
}
