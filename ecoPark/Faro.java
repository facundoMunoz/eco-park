public class Faro {

    private int cantPersonasEscalera = 0;
    private final int MAX_PERSONAS_ESCALERA = 5;

    public synchronized void subirEscaleras() {
        try {
            while (cantPersonasEscalera == MAX_PERSONAS_ESCALERA)
                this.wait();
            cantPersonasEscalera++;
        } catch (Exception e) {
        }
    }

    public synchronized void bajarEscaleras() {
        cantPersonasEscalera--;
    }

    public void avisarEscaleras() {
        this.notifyAll();
    }

}
