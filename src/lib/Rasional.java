package lib;

import java.math.BigDecimal;
import java.math.BigInteger;

public class Rasional extends Number implements Comparable{
    // Bidang-bidang data untuk pembilang dan penyebut
    private BigInteger pembilang = BigInteger.ZERO;
    private BigInteger penyebut = BigInteger.ONE;

    public Rasional(){
	    this(BigInteger.ZERO, BigInteger.ONE);
    }

    public Rasional(BigInteger pembilang){
	this.pembilang = pembilang;
	penyebut = BigInteger.ONE;
    }

    public Rasional(BigInteger pembilang, BigInteger penyebut){
	BigInteger gcd = gcd(pembilang, penyebut);
	//this.pembilang = ((penyebut > 0) ? 1 : -1) * pembilang / gcd;
	//this.penyebut = Math.abs(penyebut) / gcd;
	this.pembilang = (penyebut.compareTo(BigInteger.ZERO) == 1 ? BigInteger.ONE : new BigInteger("-1"))
			    .multiply(pembilang).divide(gcd);
	this.penyebut = penyebut.abs().divide(gcd);
    }

    public static BigInteger gcd(BigInteger P, BigInteger Q) {
	//System.out.println("gcd ("+ P + ", " + Q + ")");
	BigInteger p = P.abs();
	BigInteger q = Q.abs();
	BigInteger r = BigInteger.ONE;

	System.out.println();

	while (r.compareTo(BigInteger.ZERO)!=0)
	{
	    r = p.remainder(q);
	    p = q;
	    q = r;
	}
	return p;
    }

    /* Mengembalikan pembilang */
    public BigInteger dapatPembilang() {
	    return pembilang;
    }

    /* Mengembalikan penyebut */
    public BigInteger dapatPenyebut() {
	    return penyebut;
    }

    /* Menambahkan suatu angka rasional kepada rasional ini */
    public Rasional add(Rasional rasionalKedua){
	//BigInteger n = pembilang * rasionalKedua.dapatPenyebut() + penyebut * rasionalKedua.dapatPembilang();
	//BigInteger d = penyebut * rasionalKedua.dapatPenyebut();
	BigInteger n = pembilang.multiply(rasionalKedua.dapatPenyebut())
			.add(penyebut.multiply(rasionalKedua.dapatPembilang()));
	BigInteger d = penyebut.multiply(rasionalKedua.dapatPenyebut());
	return new Rasional(n, d);
    }

    /* Mengurangi suatu angka rasional dari rasional ini */
    public Rasional subtract(Rasional rasionalKedua){
	//BigInteger n = pembilang * rasionalKedua.dapatPenyebut() - penyebut * rasionalKedua.dapatPembilang();
	//BigInteger d = penyebut * rasionalKedua.dapatPenyebut();
	BigInteger n = pembilang.multiply(rasionalKedua.dapatPenyebut())
			.subtract(penyebut.multiply(rasionalKedua.dapatPembilang()));
	BigInteger d = penyebut.multiply(rasionalKedua.dapatPenyebut());
	return new Rasional(n, d);
    }

    /* Mengalikan suatu angka rasional dengan rasional ini */
    public Rasional multiply(Rasional rasionalKedua){
	//BigInteger n = pembilang * rasionalKedua.dapatPembilang();
	//BigInteger d = penyebut * rasionalKedua.dapatPenyebut();
	BigInteger n = pembilang.multiply(rasionalKedua.dapatPembilang());
	BigInteger d = penyebut.multiply(rasionalKedua.dapatPenyebut());
	return new Rasional(n, d);
    }

    /* Membagi suatu angka rasional dari rasional ini */
    public Rasional divide(Rasional rasionalKedua){
	//BigInteger n = pembilang * rasionalKedua.dapatPenyebut();
	//BigInteger d = penyebut * rasionalKedua.pembilang;
	BigInteger n = pembilang.multiply(rasionalKedua.dapatPenyebut());
	BigInteger d = penyebut.multiply(rasionalKedua.dapatPembilang());
	return new Rasional(n, d);
    }

    /* Mengoverride metode toString() */
    public String toString() {
	if (penyebut.compareTo(BigInteger.ONE) == 0)
	    return pembilang + "";
	else
	    return pembilang + "/" + penyebut;
    }

    /* Mengoverride metode equals dalam kelas Object */
    public boolean equals(Object parm1) {
	return (this.subtract((Rasional)(parm1))).dapatPembilang().equals(BigInteger.ZERO);
    }

    /* Mengimplementasikan metode abstrak BigIntegerValue dalam java.lang.Number */
    public BigDecimal BigIntegerValue() {
	    return new BigDecimal(pembilang+"").divide(new BigDecimal(penyebut+""));
    }

    /* Mengimplementasikan metode compareTo dalam java.lang.Comparable */
    public int compareTo(Object o) {
	return (this.subtract((Rasional)o)).dapatPembilang().compareTo(BigInteger.ZERO);
    }

    @Override
    public int intValue() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public long longValue() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public float floatValue() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public double doubleValue() {
	throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}