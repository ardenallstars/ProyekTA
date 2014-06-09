/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package testing;

import lib.MessageToDigest;

/**
 *
 * @author Arden
 */
public class DigestTest {
    public static void main (String args[]) {
        String m = "haha";
	MessageToDigest mtd = new MessageToDigest("SHA-512");
	System.out.println("" + mtd.computeDigest(m.getBytes()));
    }
}
