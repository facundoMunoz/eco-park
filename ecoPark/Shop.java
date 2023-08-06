import java.util.concurrent.Semaphore;

public class Shop {

    private final int MAX_CAJAS = 2;
    private int cantCajasDisponibles = MAX_CAJAS;
    private Semaphore cajas = new Semaphore(MAX_CAJAS, true);

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
