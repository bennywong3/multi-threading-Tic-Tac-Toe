import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.Executors;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * This class Server sets up the server, 
 * builds sockets and limits number of player to 2.
 * It communicates with clients.
 * 
 * @author Benny Wong UID:3035568881
 * @version 1.0
 */
public class Server {
	private ServerSocket serverSocket;
	private int clientCount;
	private int roundCount;
	private Logic logic;
	// The set of all the print writers for all the clients, used for broadcast.
	private Set<PrintWriter> writers = new HashSet<>();
	public Server(ServerSocket serverSocket) {
		this.serverSocket = serverSocket;
		this.logic = new Logic();
	}
	
	/*
	 * This function limits number of clients to2.
	 */
	public void start() {
		var pool = Executors.newFixedThreadPool(200);
		clientCount = 0;
		while (clientCount < 2) {
			try {
				Socket scoket = serverSocket.accept();
				pool.execute(new Handler(scoket));
				System.out.println("Connected to client " + clientCount++);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/*
	 * This class Handler makes sockets and make communication with clients possible.
	 */
	public class Handler implements Runnable {
		private Socket socket;
		private Scanner input;
		private PrintWriter output;

		public Handler(Socket socket) {
			this.socket = socket;
		}

		/*
		 * This method run() adds clients to the broadcast list, helps round counting
		 * and transfer data between server and client.
		 */
		@Override
		public void run() {
			System.out.println("Connected: " + socket);
			try {
				input = new Scanner(socket.getInputStream());
				output = new PrintWriter(socket.getOutputStream(), true);

				// add this client to the broadcast list
				writers.add(output);
				
				for (PrintWriter writer : writers) {
					if (clientCount==1) {
						writer.println(1);
					} else {
						writer.println(2);
					}
				}

				while (input.hasNextLine()) {
					var command = input.nextLine();

					System.out.println("Server Received: " + command);

					if (roundCount %2 == 0) {
						logic.X(Integer.parseInt(command.trim()));
						roundCount++;
					} else {
						logic.O(Integer.parseInt(command.trim()));
						roundCount++;
					}

					// broadcast updated number to all clients
					for (PrintWriter writer : writers) {
						writer.println(logic.getLogic());
					}

					System.out.println("Server Broadcasted: " + logic.getLogic());

				}
			} catch (Exception e) {
				System.out.println(e.getMessage());
			} finally {
				// client disconnected
				if (output != null) {
					writers.remove(output);
					for (PrintWriter writer : writers) {
						writer.println(100);
					}
				}
			}
		}
	}	
}
