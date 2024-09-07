import java.io.*;
import java.net.*;

public class Client {
	Socket socket;
	int port;
	BufferedReader in;
	PrintWriter out;

	public Client(String address, int port) {
		this.port = port;
		establishConnection(address);
	}

	private void establishConnection(String address) {
		try {
			socket = new Socket(address, port);
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (Exception e) {
			throw new Error(e);
		}

	}

	public void closeSocket() {
		try {
			if (in != null)
				in.close();
			if (out != null)
				out.close();
			if (socket != null && !socket.isClosed())
				socket.close();
		} catch (IOException e) {
			throw new UnsupportedOperationException("Failed to close the socket", e);
		}
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

	public static void main(String[] args) {
		assert args.length == 3 : "Invalid amount of args passed";
		String source = args[0];
		int port = Integer.parseInt(args[1]);
		Client c = new Client(source, port);
		String request = args[2];

		c.sendMessage(request);

		try {
			String input = c.receiveMessage();
			System.out.println("Magic 8 Ball says: " + input);
		} catch (IOException e) {
			throw new Error("Error Reading: \n" + e.getMessage());
		} finally {
			c.closeSocket();
		}
	}
}
