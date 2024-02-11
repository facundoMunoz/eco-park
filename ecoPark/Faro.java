import java.awt.Point;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Faro {

    // Lock escalera
    private Lock lockEscalera = new ReentrantLock(true);
    private Condition esperarEscalera = lockEscalera.newCondition();
    // Lock toboganes
    private Lock lock = new ReentrantLock(true);
    private Condition esperarTobogan = lock.newCondition();
    private Condition esperarVisitante = lock.newCondition();
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
    public void esperarEscaleras() {
        try {
            lockEscalera.lock();
            // Si la escalera está ocupada espera que alguien baje
            if (cantPersonasEscalera == MAX_PERSONAS_ESCALERA) {
                esperarEscalera.await();
            }
            cantPersonasEscalera++;
        } catch (Exception e) {
        } finally {
            lockEscalera.unlock();
        }
    }

    public void dejarEscaleras() {
        lockEscalera.lock();
        cantPersonasEscalera--;
        // Avisa que dejó la escalera
        esperarEscalera.signal();
        lockEscalera.unlock();
    }

    // Toboganes
    public int esperarTobogan() {
        int toboganUsado = CANT_TOBOGANES;
        try {
            lock.lock();
            if (siguienteTobogan == CANT_TOBOGANES) {
                // Avisa al administrador para que revise
                esperarVisitante.signal();
                esperarTobogan.await();
            }
            // El tobogan pasa a estar ocupado
            toboganes[siguienteTobogan] = false;
            toboganUsado = siguienteTobogan;
            siguienteTobogan = CANT_TOBOGANES;
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
        return toboganUsado;
    }

    public void dejarTobogan(int toboganUsado) {
        try {
            lock.lock();
            // El tobogan pasa a estar libre
            toboganes[toboganUsado] = true;
        } catch (Exception e) {
        } finally {
            // Avisa al administrador
            esperarVisitante.signal();
            lock.unlock();
        }
    }

    public void revisarToboganes() {
        int toboganLibre = 0;
        boolean existeLibre = false;
        try {
            lock.lock();
            esperarVisitante.await();
            // El administrador define cuál es el siguiente tobogan a usar
            while (toboganLibre < CANT_TOBOGANES && !existeLibre) {
                if (toboganes[toboganLibre]) {
                    siguienteTobogan = toboganLibre;
                    existeLibre = true;
                } else {
                    toboganLibre++;
                }
            }
        } catch (Exception e) {
        } finally {
            if (toboganLibre != CANT_TOBOGANES) {
                esperarTobogan.signal();
            }
            lock.unlock();
        }
    }

    public int getCantPersonasEscalera() {
        return cantPersonasEscalera;
    }

    public Point getPosTobogan(int nroTobogan) {
        return (nroTobogan == 1) ? POS_TOBOGAN_1 : POS_TOBOGAN_2;
    }

}
