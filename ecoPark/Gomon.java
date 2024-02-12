import java.awt.Color;
import java.awt.Point;

public class Gomon extends Thread {

    private CarreraGomones carrera;
    // GUI
    private Point posActual;
    public static final Color COLOR_GOMON = new Color(176, 157, 185);

    public Gomon(CarreraGomones carreraNueva){
        carrera = carreraNueva;
        posActual = CarreraGomones.POS_TREN;
    }
    
    public boolean ocuparLugar(){
        return false;
    }

    private void caminarHacia(Point posNueva) {
        int movX;
        int movY;
        try {
            while (posActual.x != posNueva.x || posActual.y != posNueva.y) {
                if (posActual.x != posNueva.x) {
                    movX = (posActual.x > posNueva.x) ? -1 : 1;
                    posActual = new Point(posActual.x + movX, posActual.y);
                }
                if (posActual.y != posNueva.y) {
                    movY = (posActual.y > posNueva.y) ? -1 : 1;
                    posActual = new Point(posActual.x, posActual.y + movY);
                }
                Thread.sleep((int) (Math.random() * 30));
            }
        } catch (Exception e) {
        }
    }

    public int getPosX() {
        return posActual.x;
    }

    public int getPosY() {
        return posActual.y;
    }

}
