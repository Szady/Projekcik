
public class shot extends sprite {
	
	private final int BOARD_HEIGHT = 600;
	private final int SHOT_SPEED = 2;

	public shot(int x, int y) {
		super(x, y);

		initShot();
	}

	public void initShot() {
		
		loadImage("res/shot.png");
        getImageDimensions();
	}
	
    public void move() {
        
        y -= SHOT_SPEED;
        
        if (y > BOARD_HEIGHT) {
            vis = false;
        }
    }
}
