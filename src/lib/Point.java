/*
    public Point (BigInteger x, BigInteger y)
    public Point (Kurva kurva, BigInteger x, BigInteger y)
    public void setKurva (Kurva kurva)
    public Point add (Point obj)
    public BigInteger invers (BigInteger angka, BigInteger p)
    public Point multiply (Point p, BigInteger n)
*/

package lib;

import java.math.BigInteger;
import java.security.spec.ECFieldFp;

/**
 *
 * @author Arden
 */
public class Point extends ECPoint{
    Kurva kurva;
    public static final Point POINT_INFINITY = new Point();
    
    private Point(){
	super();
    }
    
    public Point (BigInteger x, BigInteger y) {
	super(x, y);
    }
    
    public Point (Kurva kurva, BigInteger x, BigInteger y) {
	super(x, y);
	this.kurva = kurva;
    }
    
    public void setKurva (Kurva kurva) {
	this.kurva = kurva;
    }
    
    public Point add (Point obj){
	BigInteger xHasil, yHasil;
	Rasional lambdaRasional;
	BigInteger lambda;
	BigInteger p = ((ECFieldFp)(kurva.getField())).getP();
	if (obj.equals(POINT_INFINITY))
	    return this;
	if (this.equals(POINT_INFINITY))
	    return obj;
	if ((this.getAffineX().compareTo(obj.getAffineX()) == 0)
	    && (this.getAffineY().compareTo(p.subtract(obj.getAffineY())) == 0))
	    return POINT_INFINITY;
	if (this.equals(obj)){
	    if (this.getAffineY().equals(BigInteger.ZERO)) {
		throw new IllegalArgumentException("Singular Point");
	    }
	    lambdaRasional = new Rasional(new BigInteger("3")
		    .multiply(this.getAffineX())
		    .multiply(this.getAffineX()).add(kurva.getA()),
		    new BigInteger("2").multiply(this.getAffineY())
	    );
	}
	else{
	    lambdaRasional = new Rasional(
		     obj.getAffineY()
			.subtract(this.getAffineY()),
		     obj.getAffineX()
		        .subtract(this.getAffineX())
	    );
	}
	
	if (lambdaRasional.dapatPenyebut().equals(BigInteger.ONE))
	    lambda = lambdaRasional.dapatPembilang();
	else {
	    lambda = (lambdaRasional.dapatPembilang().multiply(lambdaRasional.dapatPenyebut().modInverse(p))).mod(p);
	}
	
	if ((lambda.compareTo(BigInteger.ZERO) == -1))
	    lambda = lambda.add(((ECFieldFp)(kurva.getField())).getP());
	
	xHasil = (lambda.multiply(lambda)
		.subtract(this.getAffineX())
		.subtract(obj.getAffineX()))
		.remainder(((ECFieldFp)(kurva.getField())).getP());
	if ((xHasil.compareTo(BigInteger.ZERO) == -1))
	    xHasil = xHasil.add(((ECFieldFp)(kurva.getField())).getP());
	
	yHasil = (lambda.multiply(this.getAffineX().subtract(xHasil))
		.subtract(this.getAffineY()))
		.remainder(((ECFieldFp)(kurva.getField())).getP());
	if ((yHasil.compareTo(BigInteger.ZERO) == -1))
	    yHasil = yHasil.add(((ECFieldFp)(kurva.getField())).getP());
	
	
	//System.out.println(this + " add " + obj + " = " + new Point((Kurva) kurva, xHasil, yHasil));
	return new Point((Kurva) kurva, xHasil, yHasil);
    }
    
    public Point multiply (BigInteger n) {
	if (n.compareTo(BigInteger.ONE) == 0)
	    return this;
	if (n.mod(new BigInteger("2")).equals(BigInteger.ONE))
	    return this.add(this.multiply(n.subtract(BigInteger.ONE)));
	else
	    return (this.add(this)).multiply(n.divide(new BigInteger("2")));
    }
    
    public Point negate	() {
	if (this.getAffineX() == null || this.getAffineY() == null) {
	    throw new NullPointerException();
	}
	return new Point(kurva, this.getAffineX(), ((ECFieldFp)kurva.getField()).getP().subtract(this.getAffineY()));
    }
    
    public boolean isPointOnCurve (Kurva kurva) {
	
	BigInteger hsl =this.getAffineX().pow(3)
			.add(kurva.getA().multiply(this.getAffineX()))
			.add(kurva.getB());
	
	return hsl.mod(((ECFieldFp)kurva.getField()).getP()).equals(this.getAffineY().pow(2).mod(((ECFieldFp)kurva.getField()).getP()));
    }
    
    public String toString () {
	return "(" + this.getAffineX() + ", " + this.getAffineY() + ")";
    }
}
