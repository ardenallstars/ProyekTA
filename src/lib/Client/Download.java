/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Client;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * @author Arden
 */
public class Download implements Runnable {
    public ServerSocket server;
    public Socket socket;
    public int port;
    public String saveTo = "";
    public InputStream In;
    public FileOutputStream Out;
    public ChatFrame chatFrame;
    
    public Download (String saveTo, ChatFrame frame) {
	try {
	    server = new ServerSocket(0);
	    port = server.getLocalPort();
	    this.saveTo = saveTo;
	    chatFrame = frame;
	}
	catch (IOException ex) {
	    System.out.println("Exception [Download : Download(...)]");
	}
    }

    @Override
    public void run() {
	try {
	    socket = server.accept();
	    System.out.println("Download : " + socket.getRemoteSocketAddress());
	    
	    In = socket.getInputStream();
	    Out = new FileOutputStream(saveTo);
	    
	    byte[] buffer = new byte[1024];
	    int count;
	    
	    while ((count = In.read(buffer)) >= 0) {
		Out.write(buffer, 0, count);
		
		if (Out != null)
		    Out.close();
		if (In != null)
		    In.close();
		if (socket != null)
		    socket.close();
	    }
	} catch (IOException e) {
	    System.out.println("Exception [Download : run (...)]");
	}
    }
    
}
