public class Cocinero extends Thread {

    private Restaurante restaurante;

    public Cocinero(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public void run() {
        while (true) {
            restaurante.prepararComida();
        }
    }

}
