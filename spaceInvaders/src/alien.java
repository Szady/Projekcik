public class alien extends sprite implements variables{

    private boolean direction = true; //zmienna odpowiedzialna za kierunek poruszania sie statku obcych
    public missile missile;  
    private int speed = 1;
    
	public alien(int x, int y) {
		super(x, y);
		
		initAlien();
	}

	public void initAlien() {
		
		loadImage("res/InvaderA_00.png");
		getImageDimensions();
	}
	
	public void move() {
		
		if (direction == true) x+=speed;
		else x-=speed;
    }
	
    public void setDirection(Boolean dir) {
        direction = dir;
    }
    //wywolanie tej metody powoduje stworzenie nowego obiektu typu missile
    public void shot() {
    	missile = new missile(x + width - 12, y + height);
    }
    
    public missile getShot() {
    	return missile;
    }
    
    public void setSpeed(int spd) {
    	speed = spd;
    }
 
}
