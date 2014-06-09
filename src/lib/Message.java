/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib;

import java.io.Serializable;

/**
 *
 * @author Arden
 */
public class Message implements Serializable {
    
    private static final long serialVersionUID = 1L;
    public String sender, type, recipient, content;
    
    public Message (String type, String sender, String content, String recipient) {
	this.type = type;
	this.sender = sender;
	this.content = content;
	this.recipient = recipient;
    }
    
    @Override
    public String toString() {
	return "{sender='" + sender + "', content='" + content + "', type='" + type + "', recipient='" + recipient + "'}";
    }
}
