import java.awt.Color;
import java.awt.Point;

public class Persona extends Thread {

    private int id;
    private Parque parque;
    // GUI
    private GUI gui;
    private Point posActual;
    private String estado = "Espera";
    private boolean visible = true;
    // Colores
    private static final Color COLOR_ACTIVO = new Color(94, 240, 88);
    private static final Color COLOR_ESPERA = new Color(245, 170, 82);
    private Color colorActual = COLOR_ESPERA;

    public Persona(int newId, Parque newParque, GUI newGui) {
        id = newId;
        parque = newParque;
        gui = newGui;
        posActual = new Point((int) (Math.random() * 800), GUI.POS_INICIAL.y);
    }

    public void run() {
        // Entra
        entrarParque();
        // Visita parque
        // TODO: while en horario parque
        while (true)
            decidirActividad();
    }

    private void entrarParque() {
        caminarHacia(GUI.POS_INICIAL);
        parque.esperarMolinete();
        estado = "Entra";
        colorActual = COLOR_ACTIVO;
        caminarHacia(GUI.POS_MOLINETES);
        parque.dejarMolinete();
    }

    private void decidirActividad() {
        caminarHacia(GUI.POS_CENTRO);
        // TODO: switch actividad
        irShop();
    }

    private void irRestaurante() {
        Restaurante[] restaurantes = parque.getRestaurantes();
        int opcionRestaurante = ((int) (Math.random() * 10)) % parque.getCantRestaurantes();
        try {
            restaurantes[opcionRestaurante].pedirComida(this.id);
            // Comer
            Thread.sleep(4000);
        } catch (Exception e) {
        }
    }

    private void irFaro() {
        Faro faro = parque.getFaro();
        int toboganUsado;
        try {
            // TODO: caminar hacia entrada faro
            faro.esperarEscaleras();
            // TODO: caminar hacia el administrador
            toboganUsado = faro.esperarTobogan();
            // TODO: caminar hacia la salida
            faro.dejarTobogan(toboganUsado);
        } catch (Exception e) {
        }
    }

    private void irShop() {
        Shop shop = parque.getShop();
        try {
            caminarHacia(Shop.POS_ENTRADA);
            caminarHacia(Shop.POS_SHOP);
            // Elige qué comprar
            sleep((int) (Math.random() * 10000));
            caminarHacia(Shop.POS_FILA);
            shop.esperarCaja();
            caminarHacia(Shop.POS_CAJAS);
            // Paga en caja
            sleep((int) (Math.random() * 8000));
            shop.dejarCaja();
            caminarHacia(Shop.POS_ENTRADA);
        } catch (Exception e) {
        }
    }

    private void irCarreraGomones() {
        CarreraGomones carrera = parque.getCarreraGomones();
        try {
            // TODO: caminar hacia elección transporte
            // TODO: elegir transporte e ir al inicio
            carrera.esperarLargada();
            // TODO: ir al final de la carrera
            // TODO: reiniciar CyclicBarrier ?
        } catch (Exception e) {
        }
    }

    private void caminarHacia(Point posNueva) {
        int movX;
        int movY;
        try {
            while (posActual.x != posNueva.x || posActual.y != posNueva.y) {
                if (posActual.x != posNueva.x) {
                    movX = (posActual.x > posNueva.x) ? -1 : 1;
                    posActual = new Point(posActual.x + movX, posActual.y);
                }
                if (posActual.y != posNueva.y) {
                    movY = (posActual.y > posNueva.y) ? -1 : 1;
                    posActual = new Point(posActual.x, posActual.y + movY);
                }
                Thread.sleep((int) (Math.random() * 100));
            }
        } catch (Exception e) {
        }
    }

    public int getPosX() {
        return posActual.x;
    }

    public int getPosY() {
        return posActual.y;
    }

    public Color getColor() {
        return colorActual;
    }

    public String getLabel() {
        return id + " " + estado;
    }

    public boolean getVisible() {
        return visible;
    }

}