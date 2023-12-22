import java.awt.Point;

public class Snorkel {

    // Equipos de snorkel
    private int siguienteEquipo = 0;
    private int cantEquiposOcupados = 0;
    public static final int MAX_EQUIPOS_SNORKEL = 8;
    private boolean[] equipos = new boolean[MAX_EQUIPOS_SNORKEL];
    // Posicion
    public static final Point POS_ENTRADA = new Point(GUI.WIDTH_ACTIVIDADES + 380, 400);
    public static final Point POS_SNORKEL = new Point(GUI.WIDTH_ACTIVIDADES + 550, 440);
    public static final Point POS_SALIDA = new Point(GUI.WIDTH_ACTIVIDADES + 380, 480);

    public Snorkel() {
        for (int posEquipo = 0; posEquipo < MAX_EQUIPOS_SNORKEL; posEquipo++) {
            equipos[posEquipo] = true;
        }
    }

    // Equipos
    public synchronized int esperarEquipo() {
        int equipoUsado = -1;
        try {
            while (siguienteEquipo == -1) {
                this.notifyAll();
                this.wait();
            }
            cantEquiposOcupados++;
            equipos[siguienteEquipo] = false;
            equipoUsado = siguienteEquipo;
            siguienteEquipo = -1;
            this.notifyAll();
        } catch (Exception e) {
        }
        return equipoUsado;
    }

    public synchronized void dejarEquipo(int equipoUsado) {
        cantEquiposOcupados--;
        equipos[equipoUsado] = true;
        this.notifyAll();
    }

    public synchronized void revisarLaguna() {
        try {
            for (int posEquipo = 0; posEquipo < MAX_EQUIPOS_SNORKEL; posEquipo++) {
                if (equipos[posEquipo]) {
                    siguienteEquipo = posEquipo;
                }
            }
            this.notifyAll();
            this.wait();
        } catch (Exception e) {
        }
    }

    public int getCantPersonasEscalera() {
        return cantEquiposOcupados;
    }

}
