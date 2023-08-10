import java.awt.Point;
import java.util.concurrent.Semaphore;

public class Shop {

    private final int MAX_CAJAS = 2;
    private int cantCajasDisponibles = MAX_CAJAS;
    private Semaphore cajas = new Semaphore(MAX_CAJAS, true);
    public static final Point POS_ENTRADA = new Point(GUI.WIDTH_ACTIVIDADES + 80, 250);
    public static final Point POS_SHOP = new Point(GUI.WIDTH_ACTIVIDADES + 80, 150);
    public static final Point POS_FILA = new Point(GUI.WIDTH_ACTIVIDADES + 230, 150);
    public static final Point POS_CAJAS = new Point(GUI.WIDTH_ACTIVIDADES + 230, 250);

    public void esperarCaja() {
        try {
            cajas.acquire(1);
            cantCajasDisponibles--;
        } catch (Exception e) {
        }
    }

    public void dejarCaja() {
        cantCajasDisponibles++;
        cajas.release(1);
    }

    public int getCantCajasDisponibles() {
        return cantCajasDisponibles;
    }

    public int getMaxCajas() {
        return MAX_CAJAS;
    }

}
