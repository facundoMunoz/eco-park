import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;
import java.awt.Point;

public class CarreraGomones {

    private int gomonesOcupados = 0;
    private boolean iniciada;
    public static final int GOMONES_NECESARIOS = 12;
    private final int ASIENTOS_TREN = 15;
    private CyclicBarrier largada = new CyclicBarrier(GOMONES_NECESARIOS);
    private Semaphore bolsoPertenencias = new Semaphore(GOMONES_NECESARIOS, true);
    // Tren
    private CyclicBarrier subirTren = new CyclicBarrier(ASIENTOS_TREN);
    private CyclicBarrier bajarTren = new CyclicBarrier(ASIENTOS_TREN + 1);
    private Semaphore asientoTren = new Semaphore(ASIENTOS_TREN, true);
    private Semaphore andarTren = new Semaphore(0);
    private int personasTren = 0;
    public static final int MAX_PERSONAS_TREN = 15;
    // Posicion
    public static final Point POS_ENTRADA = new Point(GUI.WIDTH_ACTIVIDADES + 380, 250);
    public static final Point POS_BICIS = new Point(GUI.WIDTH_ACTIVIDADES + 380, 195);
    public static final Point POS_TREN = new Point(GUI.WIDTH_ACTIVIDADES + 480, 195);
    public static final Point POS_INICIO = new Point(GUI.WIDTH_ACTIVIDADES + 380, 135);
    public static final Point POS_LLEGADA = new Point(GUI.WIDTH_ACTIVIDADES + 580, 135);
    public static final Point POS_SALIDA = new Point(GUI.WIDTH_ACTIVIDADES + 580, 250);

    public CarreraGomones() {
        TrenCarrera tren = new TrenCarrera(this);
        tren.start();
    }

    public boolean esperarLargada() {
        try {
            // Deja pertenencias
            bolsoPertenencias.acquire(1);
            dejarPertenencias();
            largada.await(140, TimeUnit.SECONDS);
            if (!iniciada) {
                iniciada = true;
            }
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

    public synchronized void cruzarMeta(int numeroVisitante) {
        if (iniciada) {
            System.out.println("¡" + numeroVisitante + " ganó la carrera!");
            iniciada = false;
        }
    }

    public synchronized void recuperarPertenencias() {
        gomonesOcupados--;
        bolsoPertenencias.release(1);
    }

    // Tren
    public void andarTren() {
        try {
            andarTren.acquire(1);
        } catch (InterruptedException e) {
        }
    }

    public void llegaTren() {
        try {
            bajarTren.await();
            asientoTren.release(ASIENTOS_TREN);
        } catch (Exception e) {
        }
    }

    public boolean subirTren() {
        try {
            asientoTren.acquire(1);
            sumarPersonasTren();
            subirTren.await(120, TimeUnit.SECONDS);
            bajarTren.await();
            return true;
        } catch (Exception e) {
            subirTren.reset();
            return false;
        }
    }

    private synchronized void sumarPersonasTren() {
        personasTren++;
        if (personasTren == MAX_PERSONAS_TREN) {
            andarTren.release(1);
        }
    }

    public synchronized void bajarTren() {
        personasTren--;
    }

    public synchronized int getCantPersonasTren() {
        return personasTren;
    }

    public synchronized int getEsperandoCarrera() {
        return gomonesOcupados;
    }

}
