import java.util.concurrent.CyclicBarrier;

public class CarreraGomones {

    private final int GOMONES_NECESARIOS = 12;
    private CyclicBarrier largada = new CyclicBarrier(GOMONES_NECESARIOS);

    public void esperarLargada() {
        try {
            largada.await();
        } catch (Exception e) {
        }
    }

}
