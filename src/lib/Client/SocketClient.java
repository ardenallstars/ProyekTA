/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Client;

import java.awt.HeadlessException;
import java.io.File;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Date;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import lib.Message;

/**
 *
 * @author Arden
 */
public class SocketClient implements Runnable {

    public int port;
    public String serverAddr;
    public Socket socket;
    public ChatFrame chatFrame;
    public ObjectInputStream In;
    public ObjectOutputStream Out;
    public History history;
    
    public SocketClient(ChatFrame frame) throws IOException {
	chatFrame = frame;
	this.serverAddr = chatFrame.serverAddr;
	this.port = chatFrame.port;
	socket = new Socket(InetAddress.getByName(serverAddr), port);
	
	Out = new ObjectOutputStream(socket.getOutputStream());
	Out.flush();
	In = new ObjectInputStream(socket.getInputStream());
	history = chatFrame.history;
    }

    @Override
    @SuppressWarnings("CallToThreadStopSuspendOrResumeManager")
    public void run() {
	boolean keepRunning = true;
	while (keepRunning) {
	    try {
		Message msg = (Message)In.readObject();
		System.out.println("Incoming : " + msg.toString());
		if (msg.type.equals("message")) {
		    if (msg.recipient.equals(chatFrame.username)) {
			chatFrame.jtaMessage.append("[" + msg.sender + " > Me] : " + msg.content + "\n");
		    }
		    else {
			chatFrame.jtaMessage.append("[" + msg.sender + " > " + msg.recipient + "] : " + msg.content + "\n");
		    }
		    
		    if (!msg.content.equals(".bye") && !msg.sender.equals(chatFrame.username)) {
			String msgTime = new Date().toString();
			try {
			    history.addMessage(msg, msgTime);
			    DefaultTableModel table = (DefaultTableModel)chatFrame.historyFrame.jtblHistory.getModel();
			    table.addRow(new Object[] {
				msg.sender, msg.content, "Me", msgTime
			    });
			}
			catch (Exception e) {
			}
		    }
		}
		else if(msg.type.equals("login")) {
		    if (msg.content.equals("TRUE")) {
			chatFrame.jbtLogin.setEnabled(false);
			chatFrame.jbtSignUp.setEnabled(false);
			chatFrame.jbtSendMessage.setEnabled(true);
			chatFrame.jtaMessage.append("[SERVER > Me] : Login Berhasil\n");
			chatFrame.jtfUserName.setEnabled(false);
			chatFrame.jpfPassword.setEnabled(false);
		    }
		    else {
			chatFrame.jtaMessage.append("[SERVER > Me] : Login Gagal\n");
		    }
		}
		else if (msg.type.equals("test")) {
		    chatFrame.jbtConnect.setEnabled(false);
		    chatFrame.jbtLogin.setEnabled(true);
		    chatFrame.jbtSignUp.setEnabled(true);
		    chatFrame.jtfUserName.setEnabled(true);
		    chatFrame.jpfPassword.setEnabled(false);
		    chatFrame.jtfHostAddr.setEditable(false);
		    chatFrame.jtfHostPort.setEditable(false);
		}
		else if (msg.type.equals("newuser")) {
		    if (!msg.content.equals(chatFrame.username)) {
			boolean exists = false;
			for (int i = 0; i < chatFrame.model.getSize(); i++) {
			    if (chatFrame.model.getElementAt(i).equals(msg.content)) {
				exists = true;
				break;
			    }
			}
			if (!exists) {
			    chatFrame.model.addElement(msg.content);
			}
		    }
		}
		else if (msg.type.equals("signup")) {
		    if (msg.content.equals("TRUE")) {
			chatFrame.jbtLogin.setEnabled(false);
			chatFrame.jbtSignUp.setEnabled(false);
			chatFrame.jbtSendMessage.setEnabled(true);
			chatFrame.jtaMessage.append("[SERVER > Me] : Signup Berhasil\n");
		    }
		    else {
			chatFrame.jtaMessage.append("[SERVER > Me] : Signup Gagal\n");
		    }
		}
		else if (msg.type.equals("signout")) {
		    if (msg.content.equals(chatFrame.username)) {
			chatFrame.jtaMessage.append("[" + msg.sender + " > Me] : Bye\n");
			chatFrame.jbtConnect.setEnabled(true);
			chatFrame.jbtSendMessage.setEnabled(false);
			chatFrame.jtfHostAddr.setEditable(true);
			chatFrame.jtfHostPort.setEditable(true);
			
			for (int i = 1; i < chatFrame.model.size(); i++) {
			    chatFrame.model.removeElementAt(i);
			}
			chatFrame.clientThread.stop();
		    }
		    else {
			chatFrame.model.removeElement(msg.content);
			chatFrame.model.removeElement(msg.content);
			chatFrame.jtaMessage.append("[" + msg.sender + " > All] : " + msg.content + "telah signed out\n");
		    }
		}
		else if (msg.type.equals("upload_req")) {
		    if (JOptionPane.showConfirmDialog(chatFrame, ("Accept '" + msg.content + "' from " + msg.sender + " ?")) == 0) {
			JFileChooser jf = new JFileChooser();
			jf.setSelectedFile(new File(msg.content));
			int returnVal = jf.showSaveDialog(chatFrame);
			String saveTo = jf.getSelectedFile().getPath();
			if (saveTo != null & returnVal == JFileChooser.APPROVE_OPTION) {
			    Download dwn = new Download(saveTo, chatFrame);
			    Thread t = new Thread (dwn);
			    t.start();
			    
			    send(new Message("upload_res", chatFrame.username, ("" + dwn.port), msg.sender));
			}
			else {
			    send(new Message("upload_res", chatFrame.username, "NO", msg.sender));
			}
		    }
		    else {
			send(new Message("upload_res", chatFrame.username, "NO", msg.sender));
		    }
		}
		else if (msg.type.equals("upload_res")) {
		    if (!msg.content.equals("NO")) {
			int port = Integer.parseInt(msg.content);
			String addr = msg.sender;
			chatFrame.jbtBrowseFile.setEnabled(false);
			chatFrame.jbtSendFile.setEnabled(false);
			Upload upl = new Upload(addr, port, chatFrame.file, chatFrame);
			Thread t = new Thread(upl);
			t.start();
		    }
		    else {
			chatFrame.jtaMessage.append("[SERVER > Me] : " + msg.sender +  " file request ditolak\n");
		    }
		}
		else {
		    chatFrame.jtaMessage.append("[SERVER > Me] : Tipe Pesan tidak dikenal\n");
		}
	    }
	    catch (IOException | ClassNotFoundException | HeadlessException | NumberFormatException e) {
		keepRunning = false;
		chatFrame.jtaMessage.append("[Aplikasi > Me] : Kesalahan Koneksi\n");
		chatFrame.jbtConnect.setEnabled(true);
		chatFrame.jtfHostAddr.setEditable(true);
		chatFrame.jtfHostPort.setEditable(true);
		chatFrame.jbtSendMessage.setEnabled(false);
		chatFrame.jbtBrowseFile.setEnabled(false);
		for (int i = 1; i < chatFrame.model.size(); i++) {
		    chatFrame.model.removeElementAt(i);
		}
		
		chatFrame.clientThread.stop();
		System.out.println ("Exception SocketClient run()");
	    }
	}
    }
    
    void send(Message msg) {
	try {
	    Out.writeObject(msg);
	    Out.flush();
	    System.out.println("Outgoing : " + msg.toString());
	    
	    if (msg.type.equals("message") && !msg.content.equals(".bye")) {
		String msgTime = (new Date()).toString();
		//try {
		history.addMessage(msg, msgTime);

		DefaultTableModel tabel = (DefaultTableModel)chatFrame.historyFrame.jtblHistory.getModel();
		tabel.addRow(new Object[] {
		    "Me", msg.content, msg.recipient, msgTime
		});
		//} catch (Exception e) {}
	    }
	} catch (IOException e) {
	    System.out.println("Exception SocketClient send()");
	}
    }
    
    public void closeThread(Thread t) {
	t = null;
    }
}
