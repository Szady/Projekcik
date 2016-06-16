import javax.swing.JFrame;
import java.awt.EventQueue;


public class application extends JFrame implements variables {
	//inicjalizacja okna
	public application() {
		
		initUI();
	}
	
	private void initUI() {
		
		add(new board());

		setSize(BOARD_WIDTH,BOARD_HEIGHT);
		setResizable(false);
		setTitle("Space Invaders v.1.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		
	}
	
	public static void main(String[] args) {
		
		EventQueue.invokeLater(new Runnable() {
			@Override
            public void run() {
                application exe = new application();
                exe.setVisible(true);
            }
		});
	}
}
