import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Used help from Java22 javadoc linked below
 * Socket - https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/net/Socket.html 
 * ServerSocket - https://docs.oracle.com/en/java/javase/22/docs/api/java.base/java/net/ServerSocket.html
 * 
 * Help from article below showing flow of program
 * https://www.javatpoint.com/socket-programming
 * 
 * Along with that used code pattern and some methods from code in Project 1 for sending and receiving.
 */
public class Magic8BallServer {
	static String[] answers = { "Yes definitely", "No definitely", "Not sure, come again", "Why not" };
	ServerSocket server;
	Socket client;
	int port;
	BufferedReader in;
	PrintWriter out;

	public Magic8BallServer(int port) {
		this.port = port;
		initSocket();
	}

	public void sendMessage(String message) {
		assert out != null;
		out.println(message);

	}

	public String receiveMessage() throws IOException {
		if (in != null) {
			return in.readLine();
		}
		throw new IOException("Error recieving message");
	}

	private void initSocket() {
		try {
			server = new ServerSocket(port);
			System.out.println("Established @ " + port);
		} catch (IOException e) {
			throw new Error(e);
		}
	}

	public void find() {
		try {
			client = server.accept();
			System.out.println("Magic8BallClient found");
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			sendMessage(answers[(int) (Math.round(Math.random() * (answers.length - 1)))] + " ("
					+ InetAddress.getLocalHost().getHostAddress() + ")");
			client.close();
		} catch (IOException e) {
			System.out.println("Error with client I/O");
		}

	}

	public static void main(String[] args) {
		assert args.length == 1:"Port not provided";
		Magic8BallServer s = new Magic8BallServer(Integer.parseInt((args[0])));
		while (true) {
			s.find();
		}
	}
}
