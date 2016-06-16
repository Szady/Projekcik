import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;
//podstawowa klasa zwiazana z modelami wystepujacymi w grze
public class sprite {

    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public sprite(int x, int y) {

        this.x = x;
        this.y = y;
        vis = true;
    }
    //zaladowanie obrazka
    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }
    
    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }    

    public Image getImage() {
        return image;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
    
    public void setY(int y) {
    	this.y = y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }
    //metoda zwracajaca prostokat o wierzcholku w pkt x,y oraz dlugoscia i szerokosica
    //zadana odpowiednimi zmiennymi.
    //Wykorzystywana jest przy detekcji kolizji
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}
