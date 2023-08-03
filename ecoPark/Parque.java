import java.util.concurrent.Semaphore;

public class Parque {

    // Molinetes
    private static final int cantMolinetes = 5;
    private static int molinetesDisponibles = cantMolinetes;
    private static Semaphore molinetes = new Semaphore(cantMolinetes, true);
    // Restaurantes
    private static final int cantRestaurantes = 3;
    private static Restaurante[] restaurantes = new Restaurante[cantRestaurantes];

    public Parque() {
        for (int nroRestaurante = 0; nroRestaurante < restaurantes.length; nroRestaurante++) {
            restaurantes[nroRestaurante] = new Restaurante(nroRestaurante);
        }
    }

    public void buscarMolinete() {
        try {
            molinetes.acquire(1);
            molinetesDisponibles--;
        } catch (Exception e) {
        }
    }

    public void dejarMolinete() {
        molinetesDisponibles++;
        molinetes.release(1);
    }

    public int getMolinetesDisponibles() {
        return molinetesDisponibles;
    }

    public int getCantRestaurantes() {
        return cantRestaurantes;
    }

    public Restaurante[] getRestaurantes() {
        return restaurantes;
    }

}