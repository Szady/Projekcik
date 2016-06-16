
public class missile extends sprite {
	
	private final int BOARD_HEIGHT = 600;
	private final int MISSILE_SPEED = 2;

	public missile(int x, int y) {
		super(x, y);

		initMissile();
	}

	public void initMissile() {
		
		loadImage("res/missile.png");
        getImageDimensions();
	}
	
    public void move() {
        
        y += MISSILE_SPEED;
        
        if (y > BOARD_HEIGHT) {
            vis = false;
        }
    }

}
