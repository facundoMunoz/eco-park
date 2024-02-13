import java.awt.Color;
import java.awt.Point;

public class Persona extends Thread {

    private int id;
    private Parque parque;
    // GUI
    private Point posActual;
    private boolean visible = true;
    // Colores
    private static final Color COLOR_ACTIVO = new Color(94, 240, 88);
    private static final Color COLOR_ESPERA = new Color(245, 170, 82);
    private Color colorActual = COLOR_ACTIVO;
    // Tickets restaurantes
    private boolean almuerza = true;
    private boolean merienda = true;

    public Persona(int newId, Parque newParque, GUI newGui) {
        id = newId;
        parque = newParque;
        posActual = new Point((int) (Math.random() * 800), GUI.POS_INICIAL.y);
    }

    public void run() {
        // Entra
        entrarParque();
        // Visita parque
        while (GUI.getHora() < 18)
            decidirActividad();
        // Salen del parque
        caminarHacia(GUI.POS_CENTRO);
        caminarHacia(GUI.POS_INICIAL);
    }

    private void entrarParque() {
        caminarHacia(GUI.POS_INICIAL);
        colorActual = COLOR_ESPERA;
        parque.esperarMolinete();
        colorActual = COLOR_ACTIVO;
        caminarHacia(GUI.POS_MOLINETES);
        parque.dejarMolinete();
    }

    private void decidirActividad() {
        // Va al centro del parque para decidir donde ir
        caminarHacia(GUI.POS_CENTRO);
        switch (((int) (Math.random() * 10)) % 5) {
            case 0:
                irRestaurante();
                break;
            case 1:
                irFaro();
                break;
            case 2:
                irShop();
                break;
            case 4:
                irCarreraGomones();
                break;
            default:
                irSnorkel();
                break;
        }
    }

    private void irRestaurante() {
        // Si es horario de almuerzo/merienda y tiene un ticket
        int hora = GUI.getHora();
        if ((hora > 11 && hora < 14 && almuerza) || (hora > 14 && merienda)) {
            Restaurante[] restaurantes = parque.getRestaurantes();
            int opcionRestaurante = ((int) (Math.random() * 10)) % parque.getCantRestaurantes();

            try {
                caminarHacia(restaurantes[opcionRestaurante].POS_FILA);
                colorActual = COLOR_ESPERA;
                restaurantes[opcionRestaurante].pedirComida(this.id);
                colorActual = COLOR_ACTIVO;
                caminarHacia(restaurantes[opcionRestaurante].POS_MESAS);
                // Comer
                Thread.sleep(5000);
            } catch (Exception e) {
            }
        }
    }

    private void irFaro() {
        Faro faro = parque.getFaro();
        int toboganUsado;
        try {
            caminarHacia(Faro.POS_ENTRADA);
            colorActual = COLOR_ESPERA;
            faro.esperarEscaleras();
            colorActual = COLOR_ACTIVO;
            caminarHacia(Faro.POS_ESCALERAS);
            sleep(2000);
            caminarHacia(Faro.POS_CIMA);
            faro.dejarEscaleras();
            colorActual = COLOR_ESPERA;
            toboganUsado = faro.esperarTobogan();
            colorActual = COLOR_ACTIVO;
            caminarHacia(faro.getPosTobogan(toboganUsado));
            faro.dejarTobogan(toboganUsado);
            caminarHacia(Faro.POS_SALIDA);
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
            colorActual = COLOR_ESPERA;
            shop.esperarCaja();
            colorActual = COLOR_ACTIVO;
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
        boolean siguiente = true;
        int decisionTransporte;
        try {
            caminarHacia(CarreraGomones.POS_ENTRADA);
            decisionTransporte = (int) ((Math.random() * 10) % 2);
            if (decisionTransporte == 0) {
                caminarHacia(CarreraGomones.POS_BICIS);
                caminarHacia(CarreraGomones.POS_INICIO);
            } else {
                caminarHacia(CarreraGomones.POS_TREN);
                visible = false;
                siguiente = carrera.subirTren();
                carrera.bajarTren();
                visible = true;
            }
            if (siguiente) {
                posActual = CarreraGomones.POS_INICIO;
                colorActual = COLOR_ESPERA;
                visible = false;
                siguiente = carrera.esperarLargada(this.id);
                colorActual = COLOR_ACTIVO;
                if (siguiente) {
                    posActual = CarreraGomones.POS_LLEGADA;
                    // Si corre la carrera se debe hacer visible antes de volver
                    visible = true;
                    caminarHacia(CarreraGomones.POS_SALIDA);
                } else {
                    // Sino inmediatamente
                    visible = true;
                }
                // Recupera pertenencias
                carrera.recuperarPertenencias();
            }
            caminarHacia(CarreraGomones.POS_ENTRADA);
        } catch (Exception e) {
        }
    }

    private void irSnorkel() {
        Snorkel snorkel = parque.getSnorkel();
        int equipoUsado;
        try {
            caminarHacia(Snorkel.POS_ENTRADA);
            colorActual = COLOR_ESPERA;
            equipoUsado = snorkel.esperarEquipo();
            colorActual = COLOR_ACTIVO;
            caminarHacia(Snorkel.POS_SNORKEL);
            Thread.sleep((int) (Math.random() * 3000));
            caminarHacia(Snorkel.POS_SALIDA);
            snorkel.dejarEquipo(equipoUsado);
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
                Thread.sleep((int) (Math.random() * 50));
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
        return "" + id;
    }

    public boolean getVisible() {
        return visible;
    }

}