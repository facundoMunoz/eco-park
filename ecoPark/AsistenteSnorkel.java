public class AsistenteSnorkel extends Thread {

    Parque parque;

    public AsistenteSnorkel(Parque newParque) {
        parque = newParque;
    }

    @Override
    public void run() {
        Snorkel snorkel = parque.getSnorkel();

        try {
            while (true) {
                snorkel.revisarLaguna();
                sleep((int) (Math.random() * 1000));
            }
        } catch (Exception e) {
        }
    }

}
