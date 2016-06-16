import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class ship extends sprite implements variables{

	private int dx;
	private ArrayList<shot> shotArray;
	boolean shotFired = false;
	
	public ship(int x, int y) {		
		super(x, y);
		
		initShip();
	}
	
	private void initShip() {
		
		shotArray = new ArrayList<>();
		loadImage("res/ship.png");
		getImageDimensions();
	}
	
	public void move() {
		
		x += dx;	
		
		if(x < BORDER_LEFT) x = BORDER_LEFT;
		if(x > BOARD_WIDTH - BORDER_RIGHT) x = BOARD_WIDTH - BORDER_RIGHT;
	}
	
    public ArrayList<shot> getShotArray() {
        return shotArray;
    }
	//metoda odpowiedzialna za odpowiednie reagowanie na wcisniete klawisze
	public void keyPressed (KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = -2;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 2;
		}
		
		if (key == KeyEvent.VK_SPACE && shotFired == false) {
		    shotFired = true;
			fire();
		}
	}
	
	public void keyReleased (KeyEvent e) {
		
		int key = e.getKeyCode();
		
		if (key == KeyEvent.VK_LEFT) {
			dx = 0;
		}
		
		if (key == KeyEvent.VK_RIGHT) {
			dx = 0;
		}
		
		if (key == KeyEvent.VK_SPACE) {
		    shotFired = false;
		}
	}	
	//metoda powodujaca dodanie do listy nowego obiektu typu shot
    public void fire() {
        shotArray.add(new shot(x + width - 15, y + height - 16));
    }
}
