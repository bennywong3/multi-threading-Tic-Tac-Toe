import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import javax.swing.JOptionPane;
import javax.swing.plaf.metal.MetalButtonUI;

/**
 * This class updates the frame title, 
 * and help display player¡¦s valid mark.
 * It stores inner function start().
 * 
 * @author Benny Wong UID:3035568881
 * @version 1.0
 */
public class Controller {

	private Design design;
	
	private boolean isplayer1 = true;
	private boolean round = true;
	private String xoro = "0";


	private Socket socket;
	private Scanner in;
	private PrintWriter out;
	private MetalButtonUI r;
	private MetalButtonUI g;

	public Controller(Design design) {
		this.design = design;
	}

	/*
	 * This function updates the displayed text,
	 * restricts players to make their move.
	 */
	public void start() {
		try {
			this.socket = new Socket("127.0.0.1", 58901);
			in = new Scanner(socket.getInputStream());
			out = new PrintWriter(socket.getOutputStream(), true);
			
			design.getFrame().setTitle("Tic Tac Toe-Player: "+design.getName().getText());
			design.getMessage().setText("WELCOME " + design.getName().getText());
			design.getSubmit().setEnabled(false);
			design.getName().setEnabled(false);
			for (int i = 0; i < 9; i++) {
				int j=i;
				design.getxo(i).addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						out.println(j);
						for (int i = 0; i < 9; i++) {
							design.getxo(i).setEnabled(false);
						}
					}
				});
				design.getxo(i).setEnabled(false);
			}
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		r = new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.red;
			}
		};
		
		g = new MetalButtonUI() {
			protected Color getDisabledTextColor() {
				return Color.green;
			}
		};

		// Creates a new Thread for reading server messages
		Thread handler = new ClinetHandler(socket);
		handler.start();
	}
	
	/*
	 * This is a class to declare socket.
	 */
	class ClinetHandler extends Thread {
		private Socket socket;

		public ClinetHandler(Socket socket) {
			this.socket = socket;
		}
		
		/*
		 * this is a override function to try to read from server.
		 */
		@Override
		public void run() {
			try {
				readFromServer();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		/*
		 * This is a function that reads from server,
		 * display player marks on board, update text in Jpanel message,
		 * and pop up windows showing the result.
		 */
		public void readFromServer() throws Exception {
			try {
				while (in.hasNextLine()) {
					var command = in.nextLine();
					System.out.println("Client Received: " + command);
					out.flush();
					if (Integer.parseInt(command.trim()) == 100) {
						JOptionPane.showMessageDialog(design.getFrame(), "Game Ends. One of the players left.");
						System.exit(0);
					}
					if (isplayer1) {
						if (Integer.parseInt(command.trim()) == 1) {
							isplayer1 = false;
							xoro = "X";
							//design.getMessage().setText(design.getMessage().getText() + ". You are player 1.");
							round=true;
						} else {
							isplayer1 = false;
							xoro = "O";
							//design.getMessage().setText(design.getMessage().getText() + ". You are player 2. Wait for your opponent's move.");
							round=false;
						}
					} else {
						if (Integer.parseInt(command.trim()) == 2) {
							//design.getMessage().setText("Player 2 is connected. Now is your turn.");
							for (int i = 0; i < 9; i++) {
								if (design.getxo(i).getText()=="") {
									//System.out.println("in loop"+i);
									design.getxo(i).setEnabled(true);
								}
							}			
						} else if (Integer.parseInt(command.trim()) >= 21 && Integer.parseInt(command.trim()) <= 38) {
							if (Integer.parseInt(command.trim()) >= 21 && Integer.parseInt(command.trim()) <= 29) {
								design.getxo(Integer.parseInt(command.trim())-21).setText("X");
								design.getxo(Integer.parseInt(command.trim())-21).setUI(g);
							} else {
								design.getxo(Integer.parseInt(command.trim())-30).setText("O");
								design.getxo(Integer.parseInt(command.trim())-30).setUI(r);
							}
							round = !round;
							if (round) {
								design.getMessage().setText("Your opponent has moved, now is your turn.");
								for (int i = 0; i < 9; i++) {
									if (design.getxo(i).getText()=="") {
										design.getxo(i).setEnabled(true);
									}
								}
							} else {
								design.getMessage().setText("Valid move, wait for your opponent.");
							}
						} else if (Integer.parseInt(command.trim()) >= 3 && Integer.parseInt(command.trim()) <= 11) {
							design.getxo(Integer.parseInt(command.trim())-3).setText("X");
							design.getxo(Integer.parseInt(command.trim())-3).setUI(g);
							if (xoro == "X") {
								JOptionPane.showMessageDialog(design.getFrame(), "Congratulations. You Win.");
							} else {
								JOptionPane.showMessageDialog(design.getFrame(), "You lose.");
							}
							System.exit(0);
						} else if (Integer.parseInt(command.trim()) >= 12 && Integer.parseInt(command.trim()) <= 20) {
							design.getxo(Integer.parseInt(command.trim())-12).setText("O");
							design.getxo(Integer.parseInt(command.trim())-12).setUI(r);
							if (xoro == "O") {
								JOptionPane.showMessageDialog(design.getFrame(), "Congratulations. You Win.");
							} else {
								JOptionPane.showMessageDialog(design.getFrame(), "You lose.");
							}
							System.exit(0);
						} else {
							design.getxo(Integer.parseInt(command.trim())-39).setText("X");
							design.getxo(Integer.parseInt(command.trim())-39).setUI(g);
							JOptionPane.showMessageDialog(design.getFrame(), "Draw.");
							System.exit(0);
						}
					}
					

				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				socket.close();
			}
		}
	}

}

