/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.Socket;
import java.security.spec.ECFieldFp;
import lib.Kurva;
import lib.Message;
import lib.MessageToDigest;
import lib.Point;
import lib.ECDH;

/**
 *
 * @author Arden
 */
public class SocketProtocol extends Thread {
    public int port;
    public String serverAddr;
    public Socket socket;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    
    public static String sender;
    public static String me;
    public static String messageText;
    
    public static java.util.Random rnd = new java.security.SecureRandom();
    public MessageToDigest mDigest = new MessageToDigest("SHA-256");
    public static final int bit = 160;
    public static BigInteger p, a, b;
    public static ECFieldFp Fp;
    
    //public key
    public static Kurva kurva;
    public Point G, Q, R, W;
    
    private BigInteger d;
    
    public BigInteger	r,
			s;
    private Point TMP;
    private boolean same;
    
    public SocketProtocol (String serverAddr, int port) throws IOException {
	this.serverAddr = serverAddr;
	this.port = port;
	socket = new Socket(InetAddress.getByName(serverAddr), port);
	
	Out = new ObjectOutputStream(socket.getOutputStream());
	Out.flush();
	In = new ObjectInputStream(socket.getInputStream());
    }
    
    public void run() {
	boolean keepRunning = true;
	    while (keepRunning) {
		try {
		    Message message = (Message)In.readObject();
		    if (message.recipient.equals(me)) {
			System.out.println("Incoming : " + message.toString());
			switch (message.type) {
			    case "keyPair":
				{
				    String str = message.content;
				    int i = 0;
				    while (str.charAt(i) != ' ')
					i++;p = new BigInteger(str.substring(0, i-1));
				    str = str.substring(i+1, str.length());
				    i = 0;
				    while (str.charAt(i) != ' ')
					i++;a = new BigInteger(str.substring(0, i-1));
				    str = str.substring(i+1, str.length());
				    b = new BigInteger(str);
				    Fp = new ECFieldFp(p);
				    kurva = new Kurva(Fp, a, b);
				    G = kurva.getRandomPoint(bit);
				    Message msg = new Message ("PointG", me, G.getAffineX() + "," + G.getAffineY(), message.sender);
				    Out.writeObject(msg);
				    Out.flush();
				    System.out.println("Outgoing : " + message.toString());
				    break;
				}
			    case "PointG":
				{
				    String str = message.content;
				    int i = 0;
				    while (str.charAt(i) != ',')
					i++;
				    G = new Point (kurva, new BigInteger(str.substring(0, i-1)), new BigInteger(str.substring(i+1, str.length())));
				    break;
				}
			    case "Sign":
				{
				    String str = message.content;
				    int i = 0;
				    while (str.charAt(i) != ' ')
					i++;r = new BigInteger(str.substring(0, i-1));
				    str = str.substring(i+1, str.length());
				    i = 0;
				    while (str.charAt(i) != ',')
					i++;s = new BigInteger(str.substring(0, i-1));
				    str = str.substring(i+1, str.length());
				    messageText = str;
				    break;
				}
			}
		    }
		}
	    catch (IOException | ClassNotFoundException e) {
		System.out.println("Kesalahan jaringan! \nCobalah beberapa saat lagi...");
	    }
	}
    }
    
    @SuppressWarnings("static-access")
    void keyPair(String sender, String recipient) {
	p = new BigInteger(bit, 5, rnd);
	a = new BigInteger(bit, rnd); b = new BigInteger(bit, rnd);
	Fp = new ECFieldFp(p);
	kurva = new Kurva(Fp,b,p);
	me = sender;
	this.sender = recipient;
	Message message = new Message("keyPair", sender, p + " " + a + " " + b, recipient);
	try {
	    Out.writeObject(message);
	    Out.flush();
	    System.out.println("Outgoing : " + message.toString());
	} catch (IOException e) {
	    System.out.println("Exception SocketProtocol send()");
	}
    }
    
    public void closeThread(Thread t) {
	t = null;
    }
    public void setPrivateKey(String username) {
	d = new BigInteger(mDigest.computeDigest(username.getBytes()), 16).mod(p);
    }
    
    public BigInteger getPrivateKey() {
	return d;
    }
    
    public void SignatureGeneration(String message) {
	System.out.println("Signature Generation");
	BigInteger e = new BigInteger(mDigest.computeDigest(message.getBytes()), 16).mod(p);
	/*
	a)	Pilih nilai k antara [1,n-1]
	b)	Hitung nilai (x1,y1) = kG dan r = x1 mod n. Jika r = 0 ulangi langkah a
	c)	Hitung s = k-1 (e + d . r) mod n. Nilai e adalah Hash (m). Jika s = 0 ulangi langkah a
	d)	Signature dari pesan m adalah (r,s)
	*/
	BigInteger k = BigInteger.ONE;
	r = BigInteger.ZERO;
	while (r.compareTo(BigInteger.ZERO) != 1) {
	    k = new BigInteger(bit, rnd);
	    
	    while (k.compareTo(p.subtract(BigInteger.ONE)) == 1 || k.compareTo(BigInteger.ONE) != 1) {
		k = new BigInteger(p.bitCount(), rnd);
	    }
	    System.out.println("k = " + k);
	    
	    //r = (G.multiply(k).getAffineX()).mod(n);
	    TMP = G.multiply(k);
	    r = (TMP.getAffineX());
	    if (r.compareTo(BigInteger.ZERO) == 1) {
		s = k.add(e.add(d.multiply(r)));
		if (s.compareTo(BigInteger.ZERO) != 1) {
		    r = BigInteger.ZERO;
		}
	    }
	}
	System.out.println("r = (kG).x = ("+ k + "*" + G + ").x = " + TMP + ".x = " + r + "\n"
			    + "s = k+(e+d*r) = " + k + "+(" + e + "+" + d + "*" + r + ") = " + s);
	
	Message msg = new Message("Sign", me, r + " " + s + "," + message, sender);
	try {
	    Out.writeObject(msg);
	    Out.flush();
	    System.out.println("Outgoing : " + msg.toString());
	}
	catch (IOException exception) {
	    System.out.println("Exception SocketProtocol send()");
	}
    }
    
    public boolean SignatureVerification (String message, String SignR, String SignS) {
	r = new BigInteger(SignR);
	s = new BigInteger(SignS);
	System.out.println("Signature Verification");
	BigInteger  e = new BigInteger(mDigest.computeDigest(message.getBytes()), 16).mod(p),
		    v;

	if (r.compareTo(BigInteger.ZERO) != 1)
	    if (r.compareTo(p) != -1)
		return false;
	if (s.compareTo(BigInteger.ZERO) != 1)
	    if (s.compareTo(p) != -1)
		return false;
	//w = s;
	//System.out.println("w = s^-1 mod n = " + s + "^-1 mod " + n + " = " + w);
	//System.out.println("w = s = " + s);
	
	R = (G.multiply(e).add(Q.multiply(r))).negate();
	System.out.println("R = -(e*G + r*Q) = -(" + e + "*" + G + " + " + r + "*" + Q + ") = -(" + G.multiply(e) + " + " + Q.multiply(r) + ") = " + R);
	
	W = R.add(G.multiply(s));
	System.out.println("W = R + s*G = " + R + " + " + s + "*" + G + " = " +  R + " + " + G.multiply(s) + " = " + W);
	
	//V = V.negate();
	
	//System.out.println("V = V^-1 = " + V.negate() + "^-1 = " + V);
	
	v = W.getAffineX();
	System.out.println("v = W.x = " + W + ".x = " + v);
	
	System.out.println("v = " + v + ", r = " + r);
	
	System.out.println("v " + (v.equals(r) ? "=" : "!=") + " r");
	
	same = v.equals(r);
	
	if (v.equals(r)) {
	    System.out.println("Verifikasi Berhasil");
	}
	else {
	    System.out.println("Verifikasi Gagal");
	}
	return same;
    }
}