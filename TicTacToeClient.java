import javax.swing.SwingUtilities;
import java.awt.event.*;

/**
 * This class TicTacToeClient initialize the game, 
 * calls the design class and controller class.
 * 
 * @author Benny Wong UID:3035568881
 * @version 1.0
 */
public class TicTacToeClient {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				Design design = new Design();
				Controller controller = new Controller(design);
				design.getSubmit().addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e) {
						controller.start();
					}
				});
			}
		});
	}
}