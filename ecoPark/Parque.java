import java.util.concurrent.Semaphore;

public class Parque {

    // Molinetes
    private static final int cantMolinetes = 5;
    private static Semaphore molinetes = new Semaphore(cantMolinetes, true);
    // Restaurantes
    private static final int cantRestaurantes = 3;
    private static Restaurante[] restaurantes = new Restaurante[cantRestaurantes];

    public Parque() {
        for (int nroRestaurante = 0; nroRestaurante < restaurantes.length; nroRestaurante++) {
            restaurantes[nroRestaurante] = new Restaurante(nroRestaurante);
        }
    }

    public void cruzarMolinete() {
        try {
            // Toma 1 segundo para cruzar el molinete
            molinetes.acquire(1);
            Thread.sleep(1000);
            molinetes.release(1);
        } catch (Exception e) {
        }
    }

    public int getCantRestaurantes() {
        return cantRestaurantes;
    }

    public Restaurante[] getRestaurantes() {
        return restaurantes;
    }

}