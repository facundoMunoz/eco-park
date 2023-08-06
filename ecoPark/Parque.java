import java.util.concurrent.Semaphore;

public class Parque {

    // Molinetes
    private static final int cantMolinetes = 5;
    private static int molinetesDisponibles = cantMolinetes;
    private static Semaphore molinetes = new Semaphore(cantMolinetes, true);
    // Restaurantes
    private static final int cantRestaurantes = 3;
    private static Restaurante[] restaurantes = new Restaurante[cantRestaurantes];
    // Faro
    private static Faro faro = new Faro();
    // Shop
    private static Shop shop = new Shop();

    public Parque() {
        for (int nroRestaurante = 0; nroRestaurante < restaurantes.length; nroRestaurante++) {
            restaurantes[nroRestaurante] = new Restaurante(nroRestaurante);
        }
    }

    // Molitenes
    public void esperarMolinete() {
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

    // Restaurantes
    public int getCantRestaurantes() {
        return cantRestaurantes;
    }

    public Restaurante[] getRestaurantes() {
        return restaurantes;
    }

    // Faro
    public Faro getFaro() {
        return faro;
    }

    // Shop
    public Shop getShop() {
        return shop;
    }

}