import javax.swing.*;
import java.awt.*;  
import java.awt.event.*;
import java.util.*;

/**
 * This class Design set the GUI layout, 
 * and it stores the inner function go().
 * 
 * @author Benny Wong UID:3035568881
 * @version 1.0
 */
public class Design {
	private JTextField playerName;
	private JFrame frame;
	private JLabel message;
	private JButton submit;
	private ArrayList<JButton> xo;
	
	public Design() {go();}
	
	
	/**
	 * This function constructs the game layout.
	 * It creates all the buttons and menus and panels needed.
	 * 
	 */
	private void go() {
		JPanel BoardPanel = new JPanel();
		BoardPanel.setLayout(new GridLayout(3,3,0,0));
		BoardPanel.setBackground(Color.white);
		message = new JLabel("Enter your player name...");
		Font xofont = new Font("Arial Black", Font.BOLD, 32);
		xo = new ArrayList<JButton>() {
			{
				for  (int i = 0; i < 9; i++) {
					add(new JButton(""));
				}
			}
		};
		
		for (int i = 0; i < 9; i++) {
			if (i==0) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(0, 0, 2, 2, Color.black));
			} else if (i==1) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(0, 2, 2, 2, Color.black));
			} else if (i==2) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(0, 2, 2, 0, Color.black));
			} else if (i==3) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(2, 0, 2, 2, Color.black));
			} else if (i==4) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.black));
			} else if (i==5) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(2, 2, 2, 0, Color.black));
			} else if (i==6) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(2, 0, 0, 2, Color.black));
			} else if (i==7) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(2, 2, 0, 2, Color.black));
			} else if (i==8) {
				xo.get(i).setBorder(BorderFactory.createMatteBorder(2, 2, 0, 0, Color.black));
			}
			xo.get(i).setContentAreaFilled(false);
			xo.get(i).setOpaque(false);
			xo.get(i).setFont(xofont);
			BoardPanel.add(xo.get(i));
		}
		
		JPanel ButtonPanel = new JPanel();
		submit = new JButton("Submit");
		playerName = new JTextField(28);
		ButtonPanel.add(playerName);
		ButtonPanel.add(submit);
		
		JPanel MainPanel = new JPanel();
		MainPanel.setLayout(new BorderLayout(3,1));
		MainPanel.add(message, BorderLayout.NORTH);
		MainPanel.add(BoardPanel, BorderLayout.CENTER);
		MainPanel.add(ButtonPanel, BorderLayout.SOUTH);
		
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JMenuBar menuBar = new JMenuBar();
		JMenu menuA = new JMenu("Control");
		JMenuItem menuItemA = new JMenuItem("Exit");
		
		/**
		 * This function gives the exit function to the Exit button.
		 * 
		 */
		menuItemA.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		menuA.add(menuItemA);
		
		JMenu menuB = new JMenu("Help");
		JMenuItem menuItemB = new JMenuItem("Instruction");
		
		/**
		 * This function makes the Instruction button to display a pop up window showing the rules.
		 * 
		 */
		menuItemB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(frame, "Some information about the game:\r\n" + 
						"Criteria for a valid move:\r\n" + 
						"- The move is not occupied by\r\n" + 
						"- The move is made in the player¡¦s turn.\r\n" + 
						"- The move is made within the 3 x 3 board \r\n" + 
						"The game would continue and switch among the opposite player until it reaches either one of the following conditions:\r\n" + 
						"- Player 1 wins.\r\n" + 
						"- Player 2 wins.\r\n" + 
						"- Draw.");
			}
		});
		menuB.add(menuItemB);
		menuBar.add(menuA);
		menuBar.add(menuB);
		frame.setJMenuBar(menuBar);
		frame.getContentPane().add(MainPanel);
		frame.setTitle("Tic Tac Toe");
		frame.setSize(400, 400);
		frame.setVisible(true);
		
	}
	
	/*
	 * This function returns the player name.
	 */
	public JTextField getName() {
		return playerName;
	}
	
	/*
	 * This function returns the frame.
	 */
	public JFrame getFrame() {
		return frame;
	}
	
	/*
	 * This function returns JLabel message.
	 */
	public JLabel getMessage() {
		return message;
	}
	
	/*
	 * This function returns JButton submit.
	 */
	public JButton getSubmit() {
		return submit;
	}
	
	/*
	 * This function returns ArrayList<JButton> xo.
	 */
	public JButton getxo(int i) {
		return xo.get(i);
	}
}
