/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Client;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;

/**
 *
 * @author Arden
 */
public class Upload implements Runnable {
    public String addr;
    public int port;
    public Socket socket;
    public FileInputStream In;
    public OutputStream Out;
    public File file;
    public ChatFrame chatFrame;
    
    public Upload(String addr, int port, File filePath, ChatFrame frame) {
	super();
	try {
	    file = filePath;
	    chatFrame = frame;
	    socket = new Socket(InetAddress.getByName(addr), port);
	    Out = socket.getOutputStream();
	    In = new FileInputStream(filePath);
	}
	catch (IOException e) {
	    System.out.println("Exception [Upload : Upload(...)]");
	}
    }

    @Override
    public void run() {
	try {
	    byte[] buffer = new byte[1024];
	    int count;
	    
	    while((count = In.read(buffer)) >= 0) {
		Out.write(buffer, 0, count);
	    }
	    Out.flush();
	    
	    chatFrame.jtaMessage.append("[Aplikasi > Me] : Upload file selesai\n");
	    chatFrame.jbtBrowseFile.setEnabled(true);
	    chatFrame.jbtSendFile.setEnabled(true);
	    chatFrame.jtfFile.setVisible(true);
	    
	    if (In != null)
		In.close();
	    if (Out != null)
		Out.close();
	    if (socket != null)
		socket.close();
	}
	catch (IOException e) {
	    System.out.println("Exception [Upload : run()]");
	}
    }
}
