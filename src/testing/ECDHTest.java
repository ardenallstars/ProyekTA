/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import lib.ECDH;
import lib.Kurva;
import lib.Point;

/**
 *
 * @author Arden
 */
public class ECDHTest {
    public static BigInteger a, b, p;
    public static Kurva kurva;
    public static Point Q;
    public static BigInteger dB;
    
    private static BigInteger dA;
    private static BigInteger keyPair;
    private static ECField Fp;
    public static void main(String args[]) {
	int bit = 8;
	ECDH ecdhA = new ECDH(), 
	     ecdhB = new ECDH();
	java.util.Random rnd = new java.security.SecureRandom();
	p = new BigInteger(bit, 5, rnd);
	Fp = new ECFieldFp(p);
	
	a = b = new BigInteger("0");
	while (a.compareTo(new BigInteger("3")) != 1 || a.compareTo(p) == 1)
	    a = new BigInteger (bit, rnd);
	while (b.compareTo(new BigInteger("3")) != 1 || b.compareTo(p) == 1)
	    b = new BigInteger (bit, rnd);
	kurva = new Kurva(Fp, a, b);
	Q = kurva.getRandomPoint(bit);
	
	ecdhA.setDomainParameter(kurva, a, b, Q);
	ecdhB.setDomainParameter(kurva, a, b, Q);
	
	ecdhA.setName("Alice");
	ecdhB.setName("Bob");
	
	ecdhA.setPrivKey();
	ecdhB.setPrivKey();
	
	ecdhA.setPubKey();
	ecdhB.setPubKey();
	
	ecdhA.setKeyPair(ecdhB.QA);
	ecdhB.setKeyPair(ecdhA.QA);
	
	System.out.println("kunci bersama Alice = " + ecdhA.getKeyPair() + "\n"
		+ "kunci bersama Bob = " + ecdhB.getKeyPair());
    }
}
