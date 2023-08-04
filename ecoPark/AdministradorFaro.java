public class AdministradorFaro extends Thread {

    Parque parque;

    public AdministradorFaro(Parque newParque) {
        parque = newParque;
    }

    @Override
    public void run() {
        Faro faro = parque.getFaro();

        while (true) {
            faro.revisarToboganes();
        }
    }

}
