/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package lib.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import lib.Message;
//import com.socket.Message;

/**
 *
 * @author Arden
 */
class ServerThread extends Thread {
    public SocketServer server = null;
    public Socket socket = null;
    public int ID = -1;
    public String username = "";
    public ObjectInputStream streamIn = null;
    public ObjectOutputStream streamOut = null;
    public ServerFrame frameServer;
    
    public ServerThread (SocketServer server, Socket socket) {
	super();
	this.server = server;
	this.socket = socket;
	ID = this.socket.getPort();
	frameServer = this.server.frameServer;
    }
    
    public void send (Message msg) {
	try {
	    streamOut.writeObject(msg);
	    streamOut.flush();
	} catch (IOException ex) {
	    System.out.println("Exception [SocketClient : send(...)]");
	}
    }
    
    public int getID() {
	return ID;
    }
    
    //@SuppressWarnings("deprecation")
    public void run() {
	frameServer.jtaProses.append("\nServer Thread " + ID + " sedang berjalan.");
	while (true) {
	    try {
		Message msg = (Message)streamIn.readObject();
		server.handle(ID, msg);
	    }
	    catch (IOException | ClassNotFoundException ioe) {
		System.out.println(ID + " ERROR dalam pembacaan Message : " + ioe.getMessage());
		server.remove(ID);
		stop();
	    }
	}
    }
    
    public void open() throws IOException {
	streamOut = new ObjectOutputStream(socket.getOutputStream());
	streamOut.flush();
	streamIn = new ObjectInputStream(socket.getInputStream());
    }
    
    public void close() throws IOException {
	if (socket != null)
	    socket.close();
	if (streamIn != null)
	    streamIn.close();
	if (streamOut != null)
	    streamOut.close();
    }
}



public class SocketServer implements Runnable {
    public ServerThread clients[];
    public ServerSocket server = null;
    public Thread	thread = null;
    public int clientCount = 0, port = 13000;
    public ServerFrame frameServer;
    public Database db;
    
    public SocketServer (ServerFrame frame) {
	clients = new ServerThread[50];
	frameServer = frame;
	db = new Database(frameServer.filePath);
	
	try {
	    server = new ServerSocket(port);
	    port = server.getLocalPort();
	    frameServer.jtaProses.append("Server dimulai. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start();
	}
	catch (IOException ioe) {
	    frameServer.jtaProses.append("\nTidak bisa menyambungkan ke port " + port + " : " + ioe.getMessage());
	}
    }
    
    public SocketServer (ServerFrame frame, int port) {
	clients = new ServerThread[50];
	frameServer = frame;
	this.port = port;
	db = new Database(frameServer.filePath);
	
	try {
	    server = new ServerSocket(this.port);
	    this.port = server.getLocalPort();
	    frameServer.jtaProses.append("Server dimulai. IP : " + InetAddress.getLocalHost() + ", Port : " + server.getLocalPort());
	    start();
	}
	catch (IOException ex) {
	    frameServer.jtaProses.append("\nTidak dapat menyambungkan ke port " + this.port + " : " + ex.getMessage());
	}
	
    }

    @Override
    public void run() {
	while (thread != null) {
	    try {
		frameServer.jtaProses.append("\nMenunggu sambungan dari klien . . .");
		addThread(server.accept());
	    }
	    catch (IOException ex) {
		frameServer.jtaProses.append("\nServer error : tidak dapat menerima klien\n");
		frameServer.RetryStart(0);
	    }
	}
    }
    
    public void start() {
	if (thread == null) {
	    thread = new Thread(this);
	    thread.start();
	}
    }
    
    public void stop() {
	if (thread != null) {
	    thread = null;
	    thread.stop();
	}
    }
    
    private int findClient (int ID) {
	for (int i = 0; i < clientCount; i++)
	    if (clients[i].getID() == ID)
		return  i;
	return -1;
    }
    
    public synchronized void handle (int ID, Message msg) {
	if (msg.content.equals(".bye")) {
	    announce("signout", "SERVER", msg.sender);
	    remove(ID);
	}
	else {
	    if (msg.type.equals("login")) {
		if (findUserThread(msg.sender) == null) {
		    if (db.checkLogin(msg.sender, msg.content)) {
			clients[findClient(ID)].username = msg.sender;
			clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender));
			announce("newuser", "SERVER", msg.sender);
			sendUserList(msg.sender);
		    }
		    else
			clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender));
		}
		else
		    clients[findClient(ID)].send(new Message("login", "SERVER", "FALSE", msg.sender));
	    }
	    else if (msg.type.equals("message")) {
		if (msg.recipient.equals("All"))
		    announce("message", msg.sender, msg.content);
		else {
		    findUserThread(msg.recipient).send(new Message(msg.type, msg.sender, msg.content, msg.recipient));
		}
	    }
	    else if (msg.type.equals("test")) {
		clients[findClient(ID)].send(new Message("test", "SERVER", "OK", msg.sender));
	    }
	    else if (msg.type.equals("signup")) {
		if (findUserThread(msg.sender) == null) {
		    if (!db.userExists(msg.sender)) {
			db.addUser(msg.sender, msg.content);
			clients[findClient(ID)].username = msg.sender;
			clients[findClient(ID)].send(new Message("signup", "SERVER", "TRUE", msg.sender));
			clients[findClient(ID)].send(new Message("login", "SERVER", "TRUE", msg.sender));
			announce("newuser", "SERVER", msg.sender);
			sendUserList(msg.sender);
		    }
		    else
			clients[findClient(ID)].send(new Message("signup", "SERVER", "FALSE", msg.sender));
		}
		else
		    clients[findClient(ID)].send(new Message("signup", "SERVER", "FALSE", msg.sender));
	    }
	    else if (msg.type.equals("upload_req")) {
		if (msg.recipient.equals("All")) {
		    clients[findClient(ID)].send(new Message("message", "SERVER", "Menggunggah ke semua user tidak diperbolehkan", msg.sender));
		}
		else
		    findUserThread(msg.recipient).send(new Message("upload_req", msg.sender, msg.content, msg.recipient));
	    }
	    else if (msg.type.equals("upload_res")) {
		if (!msg.content.equals("NO")) {
		    String IP = findUserThread(msg.sender).socket.getInetAddress().getHostAddress();
		    findUserThread(msg.recipient).send(new Message("upload_res", IP, msg.content, msg.recipient));
		}
		else
		    findUserThread(msg.recipient).send(new Message("upload_res", msg.sender, msg.content, msg.recipient));
	    }
	}
    }
    
    public void announce (String type, String sender, String content) {
	Message msg = new Message (type, sender, content, "All");
	for (int i = 0; i < clientCount; i++) {
	    clients[i].send(msg);
	}
    }
    
    public void sendUserList (String toWhom) {
	for (int i = 0; i < clientCount; i++) {
	    findUserThread(toWhom).send(new Message("newuser", "SERVER", clients[i].username, toWhom));
	}
    }
    
    private ServerThread findUserThread (String user) {
	for (int i =0; i < clientCount; i++)
	    if (clients[i].username.equals(user))
		return clients[i];
	return null;
    }
    
    public synchronized void remove (int ID) {
	int pos = findClient(ID);
	if (pos >= 0) {
	    ServerThread toTerminate = clients[pos];
	    frameServer.jtaProses.append("\nMelepaskan thread klien " + ID + " pada posisi " + pos);
	    if (pos < clientCount-1) {
		for (int i = pos+1; i < clientCount; i++) {
		    clients[i-1] = clients[i];
		}
	    }
	    clientCount--;
	    try {
		toTerminate.close();
	    }
	    catch (IOException ioe) {
		frameServer.jtaProses.append("\nError menutup thread : " + ioe.getMessage());
	    }
	    toTerminate.stop();
	}
    }
    
    private void addThread (Socket socket) {
	if (clientCount < clients.length) {
	    frameServer.jtaProses.append("\nKlien diterima : " + socket);
	    clients[clientCount] = new ServerThread(this, socket);
	    try {
		clients[clientCount].open();
		clients[clientCount].start();
		clientCount++;
	    }
	    catch (IOException ioe) {
		frameServer.jtaProses.append("\nError dalam membuka thread : " + ioe);
	    }
	}
	else {
	    frameServer.jtaProses.append("\nKlien ditolak : Maksimum " + clients.length + " klien tercapai.");
	}
    }
}
