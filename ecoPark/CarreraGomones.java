import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.awt.Point;

public class CarreraGomones {

    private int gomonesOcupados = 0;
    public static final int GOMONES_NECESARIOS = 12;
    private final int ASIENTOS_TREN = 15;
    private CyclicBarrier largada = new CyclicBarrier(GOMONES_NECESARIOS);
    private CyclicBarrier viajarTren = new CyclicBarrier(ASIENTOS_TREN);
    private Semaphore bolsoPertenencias = new Semaphore(GOMONES_NECESARIOS, true);
    private Semaphore asientoTren = new Semaphore(ASIENTOS_TREN, true);
    // Tren
    private int personasTren = 0;
    public static final int MAX_PERSONAS_TREN = 15;
    // Posicion
    public static final Point POS_ENTRADA = new Point(GUI.WIDTH_ACTIVIDADES + 380, 250);
    public static final Point POS_BICIS = new Point(GUI.WIDTH_ACTIVIDADES + 380, 195);
    public static final Point POS_TREN = new Point(GUI.WIDTH_ACTIVIDADES + 480, 195);
    public static final Point POS_INICIO = new Point(GUI.WIDTH_ACTIVIDADES + 380, 135);
    public static final Point POS_LLEGADA = new Point(GUI.WIDTH_ACTIVIDADES + 580, 135);
    public static final Point POS_SALIDA = new Point(GUI.WIDTH_ACTIVIDADES + 580, 250);

    public boolean esperarLargada() {
        try {
            // Deja pertenencias
            bolsoPertenencias.acquire(1);
            dejarPertenencias();
            largada.await(180, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            largada.reset();
            return false;
        }
    }

    public synchronized void dejarPertenencias() {
        // Recibe gomón y deja pertenencias
        gomonesOcupados++;
    }

    public synchronized void recuperarPertenencias() {
        gomonesOcupados--;
        bolsoPertenencias.release(1);
    }

    // Tren
    public boolean subirTren() {
        try {
            asientoTren.acquire(1);
            sumarPersonasTren();
            viajarTren.await(120, TimeUnit.SECONDS);
            return true;
        } catch (Exception e) {
            viajarTren.reset();
            return false;
        }
    }

    private synchronized void sumarPersonasTren() {
        personasTren++;
    }

    public synchronized void bajarTren() {
        personasTren--;
        asientoTren.release(1);
    }

    public synchronized int getCantPersonasTren() {
        return personasTren;
    }

    public synchronized int getEsperandoCarrera() {
        return gomonesOcupados;
    }

}
