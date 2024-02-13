import java.awt.Color;
import java.awt.Point;

public class Gomon extends Thread {

    private int nroGomon;
    private CarreraGomones carrera;
    private int primerVisitante = -1;
    private int segundoVisitante = -1;
    // GUI
    private Point posActual;
    public static final Color COLOR_GOMON = new Color(176, 157, 185);

    public Gomon(CarreraGomones carreraNueva, int nroGomonNuevo) {
        carrera = carreraNueva;
        posActual = CarreraGomones.POS_INICIO;
        nroGomon = nroGomonNuevo;
    }

    public void run() {
        while (true) {
            posActual = CarreraGomones.POS_INICIO;
            carrera.largarGomon();
            caminarHacia(CarreraGomones.POS_LLEGADA);
            carrera.cruzarMeta(nroGomon);
        }
    }

    public synchronized boolean ocuparLugar(int nroVisitante) {
        boolean ocupado = true;
        if (primerVisitante == -1) {
            primerVisitante = nroVisitante;
        } else if (segundoVisitante == -1) {
            segundoVisitante = nroVisitante;
        } else {
            ocupado = false;
        }
        return ocupado;
    }

    public synchronized void desocuparLugar(int nroVisitante) {
        if (nroVisitante == primerVisitante) {
            primerVisitante = -1;
        } else {
            segundoVisitante = -1;
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
                Thread.sleep((int) (Math.random() * 40));
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

    public int getNro() {
        return nroGomon;
    }

    public int getPrimerVisitante() {
        return primerVisitante;
    }

    public int getSegundoVisitante() {
        return segundoVisitante;
    }

}
