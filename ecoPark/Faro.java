public class Faro {

    // Escalera
    private int cantPersonasEscalera = 0;
    private final int MAX_PERSONAS_ESCALERA = 5;
    // Toboganes
    private int siguienteTobogan = 0;
    private final int CANT_TOBOGANES = 2;
    private boolean[] toboganes = new boolean[CANT_TOBOGANES];

    public Faro() {
        for (int tobogan = 0; tobogan < CANT_TOBOGANES; tobogan++) {
            toboganes[tobogan] = true;
        }
    }

    // Escaleras
    public synchronized void esperarEscaleras() {
        try {
            while (cantPersonasEscalera == MAX_PERSONAS_ESCALERA)
                this.wait();
            cantPersonasEscalera++;
        } catch (Exception e) {
        }
    }

    public synchronized void dejarEscaleras() {
        cantPersonasEscalera--;
        this.notifyAll();
    }

    // Toboganes
    public synchronized int esperarTobogan() {
        int toboganUsado = -1;
        try {
            while (siguienteTobogan == -1) {
                this.notifyAll();
                this.wait();
            }
            toboganes[siguienteTobogan] = false;
            toboganUsado = siguienteTobogan;
            siguienteTobogan = -1;
            this.notifyAll();
        } catch (Exception e) {
        }
        return toboganUsado;
    }

    public synchronized void dejarTobogan(int toboganUsado) {
        toboganes[toboganUsado] = true;
        this.notifyAll();
    }

    public synchronized void revisarToboganes() {
        try {
            for (int tobogan = 0; tobogan < CANT_TOBOGANES; tobogan++) {
                if (toboganes[tobogan]) {
                    siguienteTobogan = tobogan;
                }
            }
            this.notifyAll();
            this.wait();
        } catch (Exception e) {
        }
    }

}
