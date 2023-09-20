import java.awt.Point;
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
    // Carrera gomones
    private static CarreraGomones carrera = new CarreraGomones();
    // Shop
    private static Shop shop = new Shop();

    public Parque() {
        AdministradorFaro adminFaro = new AdministradorFaro(this);
        for (int nroRestaurante = 0; nroRestaurante < restaurantes.length; nroRestaurante++) {
            restaurantes[nroRestaurante] = new Restaurante(nroRestaurante, new Point(50 + (nroRestaurante * 100), 240),
                    new Point(50 + (nroRestaurante * 100), 150));
            // Asignamos un cocinero a cada restaurante
            Cocinero cocinero = new Cocinero(restaurantes[nroRestaurante]);
            cocinero.start();
        }
        adminFaro.start();
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

    // Carrera
    public CarreraGomones getCarreraGomones() {
        return carrera;
    }

    // Shop
    public Shop getShop() {
        return shop;
    }

}