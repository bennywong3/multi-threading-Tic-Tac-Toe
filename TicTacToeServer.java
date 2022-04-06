import java.io.IOException;
import java.net.ServerSocket;
//import java.util.concurrent.Executors;

/**
 * This class TicTacToeServer initialize the server, 
 * calls the Server class to run.
 * 
 * @author Benny Wong UID:3035568881
 * @version 1.0
 */
public class TicTacToeServer {

	public static void main(String[] args) throws IOException {
		System.out.println("Server is Running...");
		Runtime.getRuntime().addShutdownHook(new Thread(new Runnable() {
			public void run() {
				System.out.println("Server Stopped.");
			}
		}));

		try (var listener = new ServerSocket(58901)) {
			Server myServer = new Server(listener);
			myServer.start();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

}