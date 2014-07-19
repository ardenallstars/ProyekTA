/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import java.math.BigInteger;

/**
 *
 * @author Arden
 */
public class SubstringTest {
    static String str = "1234 35546 12312";
    
    public static  void main (String args[]) {
	int i = 0;
	while (str.charAt(i) != ' ')
	    i++;
	BigInteger a = new BigInteger(str.substring(0, i-1));
	str = str.substring(i+1, str.length());
	
	i = 0;
	while (str.charAt(i) != ' ')
	    i++;
	BigInteger b = new BigInteger(str.substring(0, i-1));
	str = str.substring(i+1, str.length());
	
	BigInteger c = new BigInteger(str);
	
	System.out.println("a = " + a);
	System.out.println("b = " + b);
	System.out.println("c = " + c);
    }
}
