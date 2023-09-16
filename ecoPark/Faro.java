import java.awt.Point;

public class Faro {

    // Escalera
    private int cantPersonasEscalera = 0;
    public static final int MAX_PERSONAS_ESCALERA = 5;
    // Toboganes
    private int siguienteTobogan = 0;
    private final int CANT_TOBOGANES = 2;
    private boolean[] toboganes = new boolean[CANT_TOBOGANES];
    // Posicion
    public static final Point POS_ENTRADA = new Point(250, 400);
    public static final Point POS_SALIDA = new Point(250, 480);
    public static final Point POS_ESCALERAS = new Point(150, 400);
    public static final Point POS_CIMA = new Point(50, 400);
    public static final Point POS_TOBOGAN_1 = new Point(150, 480);
    public static final Point POS_TOBOGAN_2 = new Point(50, 480);

    public Faro() {
        for (int tobogan = 0; tobogan < CANT_TOBOGANES; tobogan++) {
            toboganes[tobogan] = true;
        }
    }

    // Escaleras
    public synchronized void esperarEscaleras() {
        try {
            while (cantPersonasEscalera == MAX_PERSONAS_ESCALERA) {
                this.notifyAll();
                this.wait();
            }
            cantPersonasEscalera++;
        } catch (Exception e) {
        }
    }

    public synchronized void dejarEscaleras() {
        cantPersonasEscalera--;
        this.notifyAll();
    }

    // Toboganes
    public synchronized int esperarTobogan() {
        int toboganUsado = -1;
        try {
            while (siguienteTobogan == -1) {
                this.notifyAll();
                this.wait();
            }
            toboganes[siguienteTobogan] = false;
            toboganUsado = siguienteTobogan;
            siguienteTobogan = -1;
            this.notifyAll();
        } catch (Exception e) {
        }
        return toboganUsado;
    }

    public synchronized void dejarTobogan(int toboganUsado) {
        toboganes[toboganUsado] = true;
        this.notifyAll();
    }

    public synchronized void revisarToboganes() {
        try {
            for (int tobogan = 0; tobogan < CANT_TOBOGANES; tobogan++) {
                if (toboganes[tobogan]) {
                    siguienteTobogan = tobogan;
                }
            }
            this.notifyAll();
            this.wait();
        } catch (Exception e) {
        }
    }

    public int getCantPersonasEscalera() {
        return cantPersonasEscalera;
    }

    public Point getPosTobogan(int nroTobogan) {
        return (nroTobogan == 1) ? POS_TOBOGAN_1 : POS_TOBOGAN_2;
    }

}
