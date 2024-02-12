import java.awt.Color;
import java.awt.Point;

public class TrenCarrera extends Thread {

    private CarreraGomones carrera;
    // GUI
    private static Point posActual;
    public static final Color COLOR_TREN = new Color(255, 50, 50);

    public TrenCarrera(CarreraGomones carreraNueva){
        carrera = carreraNueva;
        posActual = CarreraGomones.POS_TREN;
    }

    public void run() {
        while (true) {
            carrera.andarTren();
            caminarHacia(CarreraGomones.POS_INICIO);
            carrera.llegaTren();
            caminarHacia(CarreraGomones.POS_TREN);
        }
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
                // Se mueve a velocidad constante
                Thread.sleep(20);
            }
        } catch (Exception e) {
        }
    }

    public static int getPosX() {
        return posActual.x;
    }

    public static int getPosY() {
        return posActual.y;
    }

}
