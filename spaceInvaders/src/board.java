
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;



public class board extends JPanel implements ActionListener, variables {
//© 2016 Pawel Troc i Pawel Kowalczyk z grupy I4Y9S1 
	private Timer timer;
	private ship ship;
    private ArrayList<alien> alienArray;
    private ArrayList<missile> missileArray;
	private boolean inGame;
	private Random generator;
    
	public board() {
		
		initBoard();
	}
//metoda inicjujaca parametry i zmienne potrzebne do rozpoczecia gry	
	private void initBoard() {
		
		addKeyListener(new TAdapter());
		setFocusable(true);
		setBackground(Color.BLACK);
        setDoubleBuffered(true);
		
        inGame = true;
        
        setPreferredSize(new Dimension(BOARD_WIDTH, BOARD_HEIGHT));
        
        generator = new Random();
        
		ship = new ship(INIT_SHIP_X, INIT_SHIP_Y);
		missileArray = new ArrayList<missile>();
		
		initAliens();
		
		timer = new Timer(DELAY, this);
		timer.start();
		//timer wykorzystany jest do aktualizacji stanu gry
	}
	
	//metoda initAliens dodaje do listy obiekty typu alien wraz z parametrami X,Y okreslajacymi
	//poczatkowe polozenie statku w oknie gry
	public void initAliens() {
		alienArray = new ArrayList<>();
		
		for (int i = 0; i < NUMBER_OF_ROWS; i++) {
			
			for (int j = 0; j < NUMBER_OF_COLUMNS; j++) {
				alienArray.add(new alien(BORDER_LEFT + 30*j, 20*i + 100));
			}
		}
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		if(inGame) {
			
			drawObjects(g);
		}
		else drawGameOver(g);
		
        Toolkit.getDefaultToolkit().sync();
	}
	
	//metooda rysujaca pojedyncze obiekty
	private void drawObjects(Graphics g) {
		
		if (ship.isVisible()) {
            g.drawImage(ship.getImage(), ship.getX(), ship.getY(), this);
        }
		
		ArrayList<shot> shotArray = ship.getShotArray();
		
		for (shot s : shotArray) {
			
			if (s.isVisible()) {
				g.drawImage(s.getImage(), s.getX(), s.getY(), this);
			}
		}
		
        for (alien a : alienArray) {
            if (a.isVisible()) {
                g.drawImage(a.getImage(), a.getX(), a.getY(), this);
            }
        }
        
        for (missile m : missileArray) {
        	if (m.isVisible()) {
        		g.drawImage(m.getImage(), m.getX(), m.getY(), this);
        	}
        }
        
        g.setColor(Color.white);
        g.drawString("Aliens left: " + alienArray.size(), 5, 15);
	}
	
	
	//metoda wykorzystana do wypisania w oknie napisu GAME OVER po skonczonej rozgrywce
	private void drawGameOver(Graphics g) {
		
        String msg = "GAME OVER";
        Font czcionka = new Font("Helvetica", Font.BOLD, 14);
        FontMetrics fm = getFontMetrics(czcionka);

        g.setColor(Color.white);
        g.setFont(czcionka);
        g.drawString(msg, (BOARD_WIDTH - fm.stringWidth(msg)) / 2, BOARD_HEIGHT / 2);
    }
	
	//glowna petla gry wywolywana przez timer
	@Override
	public void actionPerformed(ActionEvent e) {

        updateShip();
        updateShots();
        updateAliens();
        updateMissiles();

        checkCollisions();

        repaint();
	}

	
	//aktualizacja polozenia pociskow wystrzelonych przez statki obcych
	public void updateMissiles() {
		
		for (int i = 0; i < missileArray.size(); i++) {
			
			missile m = missileArray.get(i);
			
			if (m.isVisible()) {

                m.move();
            } 
            else {

                missileArray.remove(i);
            }
		}
	}
	
	//aktualizacja polozenia wystrzelonych przez gracza pociskow
	public void updateShots() {
        
		ArrayList<shot> shotArray = ship.getShotArray();

        for (int i = 0; i < shotArray.size(); i++) {

            shot s = shotArray.get(i);

            if (s.isVisible()) {

                s.move();
            } 
            else {

                shotArray.remove(i);
            }
        }
	}
	
	//aktualizacja polozenia statku gracza
	public void updateShip() {
		
		ship.move();
	}
	
	//aktualizacja polozenia statków obcych
	public void updateAliens() {
		
        if (alienArray.isEmpty()) {

            inGame = false;
            return;
        }
        
        for (int i = 0; i < alienArray.size(); i++) {

            alien a = alienArray.get(i);
            if (a.isVisible()) {
            	
                a.move();
            } 
            else {
            	
                alienArray.remove(i);
            }
        }
        
        for (int i = 0; i < alienArray.size(); i++) {

            alien a = alienArray.get(i);
            if (a.getX() > BOARD_WIDTH - BORDER_RIGHT) {
            	
            	for (alien alien : alienArray) {
            		alien.setDirection(false);
            		alien.setY(alien.getY() + 10);
            	}               
            } 
            else if (a.getX() < BORDER_LEFT) {
            	
            	for (alien alien : alienArray) {
            		alien.setDirection(true);
            		alien.setY(alien.getY() + 10);
            	}   
            }
            else a.move();
            
            if (alienArray.size() < 20) {

            	for (alien alien : alienArray) {
            		alien.setSpeed(2);
            	}   
            }
        }
        
        //w tym miejscu generowane sa pociski wystrzelone przez wroga
        //dodawane sa one do wczesniej zainicjalizowanej listy
        if (missileArray.size() < 15) {
        	
            for (int i = 0; i < 15; i++) {
            	
                int rand = generator.nextInt(alienArray.size());
            	alienArray.get(rand).shot();
            	alien a = alienArray.get(rand);
            	missileArray.add(new missile(a.getX(),a.getY()));
            }
        }
	}
	
	//metoda sluzaca do sprawdzania kolizji miedzy obiektami
	//wykorzystuje gotowa funkcje intersects
	//wymiary kazdego obiektu pobierane sa za pomoca metody getBounds()
	//i na podstawie wymiarow tworzony jest prostokat ktory bierze udzial
	//w sprawdzaniu kolizji
	public void checkCollisions() {
		
		Rectangle ship_rect = ship.getBounds();
		
		for (alien alien : alienArray) {
			
			Rectangle alien_rect = alien.getBounds();
			
			if(ship_rect.intersects(alien_rect)) {
				
				ship.setVisible(false);
				alien.setVisible(false);
				inGame = false;
			}
		}
		
		ArrayList<shot> shotArray = ship.getShotArray();
		
        for (shot shot : shotArray) {

            Rectangle shot_rect = shot.getBounds();
            
            for (alien alien : alienArray) {
    			
    			Rectangle alien_rect = alien.getBounds();
    			
    			if(shot_rect.intersects(alien_rect)) {
    				
    				shot.setVisible(false);
    				alien.setVisible(false);
    				
    			}
            }
        }
        
        for (missile missile : missileArray) {
        	
        	Rectangle missile_rect = missile.getBounds();
        	
        	if (missile_rect.intersects(ship_rect)) {
        		
        		missile.setVisible(false);
				ship.setVisible(false);
				inGame = false;
        	}
        }
	}

	public class TAdapter extends KeyAdapter {

		@Override
		public void keyReleased(KeyEvent e) {
			ship.keyReleased(e);
		}

		@Override
		public void keyPressed(KeyEvent e) {
			ship.keyPressed(e);
		}
	}
}
