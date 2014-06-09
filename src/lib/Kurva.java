package lib;

import java.math.BigInteger;
import java.security.spec.ECField;
import java.security.spec.ECFieldFp;
import java.security.spec.EllipticCurve;

/**
 *
 * @author Arden
 */

public class Kurva extends EllipticCurve{

    public Kurva (ECField field, BigInteger a, BigInteger b) {
	super(field, a, b);
	if ((new BigInteger("4").multiply(a.pow(3))
		.add(new BigInteger("27").multiply(b.pow(2))))
		.mod(((ECFieldFp)field).getP()).equals(BigInteger.ZERO))
	    throw new IllegalArgumentException("4a^3 + 27b^2 mod p tidak boleh = 0\n");
	else if (a.compareTo(BigInteger.ZERO) != 1) {
	    throw new IllegalArgumentException("first coefficient must greater than 0");
	}
    }

    public String toString() {
	return "y^2 = x^3 + " + this.getA() + "x + " + this.getB() + " mod " + ((ECFieldFp)(this.getField())).getP();
    }
    
    public Point getRandomPoint(int bit) {
	java.util.Random rnd = new java.security.SecureRandom();
	BigInteger  p = ((ECFieldFp)this.getField()).getP(),
		    x = BigInteger.ONE,
		    y,
		    n = BigInteger.ONE,
		    k = p.subtract(BigInteger.ONE);
	while (k.equals(p.subtract(BigInteger.ONE))) {
	    x = new BigInteger(bit, rnd).mod(p);
	    n = (x.modPow(new BigInteger("3"), p).add(this.getA().multiply(x)).add(this.getB())).mod(p);
	    k = kriteriaEuler(n , p);
	    while (k.equals(p.subtract(BigInteger.ONE)) && x.compareTo(p.subtract(BigInteger.ONE)) == -1) {
		x = x.add(BigInteger.ONE);
		n = (x.modPow(new BigInteger("3"), p).add(this.getA().multiply(x)).add(this.getB())).mod(p);
		k = kriteriaEuler(n , p);
	    }
	}
	y = sqrtMod(n, p);
	return new Point (this, x, y);
    }
    
    public Point getFirstPoint () {
	BigInteger  x = new BigInteger("0"), 
		    y,
		    p = ((ECFieldFp)this.getField()).getP(),
		    n = (x.modPow(new BigInteger("3"), p).add(this.getA().multiply(x)).add(this.getB())).mod(p),
		    k = kriteriaEuler(n , p);
	if (this.getA().equals(BigInteger.ZERO)) {
	    x = BigInteger.ONE;
	}
	while (x.compareTo(p) == -1 && k.equals(p.subtract(BigInteger.ONE))) {
	    x = x.add(BigInteger.ONE);
	    n = (x.modPow(new BigInteger("3"), p).add(this.getA().multiply(x)).add(this.getB())).mod(p);
	    k = kriteriaEuler(n , p);
	}
	if (x.compareTo(p) == 1)
	    return Point.POINT_INFINITY;
	y = sqrtMod(n, p);
	return new Point (this, x, y);
    }
    
    public BigInteger kriteriaEuler (BigInteger n, BigInteger p) {
	//a^((p-1)/2) =  0 mod p jika        habis dibagi a = x^2 mod p
	return n.modPow((p.subtract(BigInteger.ONE))
		.divide(new BigInteger("2"))
		, p);
    }
    
    public BigInteger sqrtMod (BigInteger n, BigInteger p){
	BigInteger  Q = p.subtract(BigInteger.ONE), 
		    //S = BigInteger.ZERO,
		    z = new BigInteger("2"),
		    R,
		    c,
		    t,
		    b;
		    //M;
	int S = 0, M;
	if (n.equals(BigInteger.ZERO))
	    return BigInteger.ZERO;
	
	while (Q.mod(new BigInteger("2")).equals(BigInteger.ZERO)) {
	    Q = Q.divide(new BigInteger("2"));
	    //S = S.add(BigInteger.ONE);
	    S++;
	}
	//if (S.equals(BigInteger.ONE)) {
	if (S == 1) {
	    //R = n^((Q+1)/2) mod p , 
	    R = n.modPow((Q.add(BigInteger.ONE)).divide(new BigInteger("2")), p);
	    return R;
	}
	//z bukan sisa quadrat modulo p dengan a^((p-1)/2) = -1 mod p
	//dan tentukan c = z^Q mod p
	while (!(z.modPow((p.subtract(BigInteger.ONE)).divide(new BigInteger("2")),p).equals(p.subtract(BigInteger.ONE)))) {
	    z = z.add(BigInteger.ONE);
	}
	c = z.modPow(Q, p);
	
	//R = n^((Q+1)/2) mod p , 
	//t = n^Q mod p,
	//M = S
	R = n.modPow((Q.add(BigInteger.ONE)).divide(new BigInteger("2")), p);
	t = n.modPow(Q, p);
	M = S;
	
	/*
	    loop
	1. jika t = 1 mod p, return R
	2. jika tidak, cari plg rendah i, 0 < i < M, dimana t^(2^i) = 1 mod p
	3. hitung 
		b = c^(2^(M-i-1)) dan tentukan :
			R = Rb mod p
			t = t * b^2 mod p
			c = b^2 mod p
			M = i
	*/
	while (true) {
	    if (t.equals(BigInteger.ONE))
		return R;
	    int i = 1;
	    while (!(t.modPow((new BigInteger("2").pow(i)), p).equals(BigInteger.ONE)) && i < M-1) {
		i++;
	    }
	    if (i >= M) {
		throw new IllegalArgumentException("tidak ada akar modulo dari sqrt(" + n + ") mod " + p + "\nSolusi ganti a, b atau p");
	    }
	    b = c.modPow(new BigInteger("2").pow(M-i-1), p);
	    
	    R = (R.multiply(b)).mod(p);
	    t = (t.multiply(b.pow(2))).mod(p);
	    c = (b.multiply(b)).mod(p);
	    M = i;
	}
    }
}