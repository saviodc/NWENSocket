import java.io.*;
import java.net.*;
import java.util.Scanner;
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
			socket = new Socket(address,port);
			 out = new PrintWriter(socket.getOutputStream(), true);
	         in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}catch(Exception e) {
			
		}
		
	}
	public void closeSocket(){
		try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();
            //System.out.println("Socket closed successfully.");
        } catch (IOException e) {
            System.err.println("Error closing socket: " + e.getMessage());
            throw new UnsupportedOperationException("Failed to close the socket", e);
        }
	}
	// Method to send a message to the server
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
	
    
    public static void main(String[] args) {
    	String source = "3.86.247.185";//"embassy.ecs.vuw.ac.nz";
    	int port = 3000;
    	Client c = new Client(source,port);
    	//System.out.println("Input question: ");
    	Scanner in = new Scanner(System.in);
    	String request = "Is today a good day?";//in.nextLine();
    
    	c.sendMessage(request);
    	System.out.println("Magic8BallClient "+ source + " "+ port + " \""+ request+"\"");
    	try {
			String input = c.receiveMessage();
			System.out.println("Magic 8  Ball says: "+ input);
			
		} catch (IOException e) {
			System.out.println("Error Reading: \n"+e.getMessage());	
		}
    	c.closeSocket();
    	in.close();
    }
}

