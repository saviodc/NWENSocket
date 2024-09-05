import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	static String[] answers = {"Yes definitely", "No definitely", "Not sure, come again", "Why not"};
	ServerSocket server;
	Socket client;
	int port;
	BufferedReader in;
	PrintWriter out;
	public Server(int port) {
		this.port = port;
    	initSocket();
	}
	 public void sendMessage(String message) {
	        if (out != null) {
	            out.println(message);
	        }
	    }

	    // Method to read a response from the server
	    public String receiveMessage() throws IOException {
	        if (in != null) {
	            return in.readLine();
	        }
	        return null;
	    }
	private void initSocket() {
		try {
			server = new ServerSocket(port);
			System.out.println("Established @ "+ port);
			
				client = server.accept();
				System.out.println("Client found");
				out = new PrintWriter(client.getOutputStream(), true);
		        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
		        receiveMessage();
		        sendMessage(answers[((int)Math.random()*answers.length)]);
		        
		        
			
		} catch (IOException e) {
			
		}
		
	}
	
	public void find() {
		try {
			client = server.accept();
		
		System.out.println("Client found");
		out = new PrintWriter(client.getOutputStream(), true);
        in = new BufferedReader(new InputStreamReader(client.getInputStream()));
        System.out.println(receiveMessage());
        sendMessage(answers[((int)Math.random()*answers.length)]);
        client.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		Server s = new Server(3000);
		while(true) {
			s.find();
		}
	}
}
