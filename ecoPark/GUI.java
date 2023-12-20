import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JPanel {

    // GUI
    private static JLabel labelReloj = new JLabel();
    // Reloj
    private static int hora = 12;
    private static int minutos = 0;
    // Elementos
    Parque parque = new Parque();
    // Hilos
    private static final int TAM_PERSONAS = 20;
    private static Persona[] personas = new Persona[50];
    // Colores
    private static final Color COLOR_FONDO = new Color(240, 236, 199);
    private static final Color COLOR_ACTIVIDAD = new Color(209, 203, 148);
    private static final Color COLOR_ELEMENTO = new Color(145, 141, 100);
    private static final Color COLOR_TEXTO = new Color(40, 40, 40);
    // Posiciones
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;
    public static final int WIDTH_ACTIVIDADES = 320;
    public static final int HEIGHT_ACTIVIDADES = 200;
    public static final Point POS_INICIAL = new Point(WIDTH / 2, HEIGHT - 50);
    public static final Point POS_MOLINETES = new Point(WIDTH / 2, HEIGHT - 110);
    public static final Point POS_CENTRO = new Point(WIDTH / 2, HEIGHT / 2);

    public GUI() {
        setDoubleBuffered(true);
        // Reloj
        labelReloj.setFont(new Font(labelReloj.getFont().getName(), Font.PLAIN, 50));
        add(labelReloj);
        // Personas
        for (int persona = 0; persona < personas.length; persona++) {
            personas[persona] = new Persona(persona + 1, parque, this);
            personas[persona].start();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fondo
        pintarFondo(g);
        // Actividades
        pintarActividades(g);
        // Personas
        pintarPersonas(g);
    }

    private void pintarFondo(Graphics g) {
        // Limpia el rastro anterior
        g.clearRect(0, 0, getWidth(), getHeight());
        // Fondo
        g.setColor(COLOR_FONDO);
        g.fillRect(0, 0, getWidth(), getHeight());
    }

    private void pintarActividades(Graphics g) {
        g.setFont(new Font(labelReloj.getFont().getName(), Font.BOLD, 15));
        // Entrada al parque
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) POS_INICIAL.getX(), (int) POS_INICIAL.getY(), 10, 10);
        // Centro del parque
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) POS_CENTRO.getX(), (int) POS_CENTRO.getY(), 10, 10);
        // Molinetes
        pintarMolinetes(g);
        // Restaurantes
        pintarRestaurantes(g);
        // Faro
        pintarFaro(g);
        // Shop
        pintarShop(g);
        // Carrera
        pintarCarrera(g);
        // Snorkel
        pintarSnorkel(g);
    }

    private void pintarMolinetes(Graphics g) {
        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(0, HEIGHT - 140, WIDTH, 70);
        g.setColor(COLOR_TEXTO);
        g.drawString("Molinetes disponibles: " + parque.getMolinetesDisponibles(), 0, HEIGHT - 125);
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) POS_MOLINETES.getX(), (int) POS_MOLINETES.getY(), 10, 10);
    }

    private void pintarRestaurantes(Graphics g) {
        Restaurante[] restaurantes = parque.getRestaurantes();

        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(0, 75, WIDTH_ACTIVIDADES, HEIGHT_ACTIVIDADES);
        g.setColor(COLOR_TEXTO);
        g.drawString("Restaurantes", 0, 90);
        // Pintar cada restaurante
        for (int restaurante = 0; restaurante < restaurantes.length; restaurante++) {
            g.setColor(COLOR_ELEMENTO);
            g.fillOval((int) restaurantes[restaurante].POS_MESAS.getX(),
                    (int) restaurantes[restaurante].POS_MESAS.getY(),
                    10, 10);
            g.fillOval((int) restaurantes[restaurante].POS_FILA.getX(), (int) restaurantes[restaurante].POS_FILA.getY(),
                    10, 10);
            g.setColor(COLOR_TEXTO);
            g.drawString("" + (restaurante + 1), (int) restaurantes[restaurante].POS_MESAS.getX(),
                    (int) restaurantes[restaurante].POS_MESAS.getY() - 20);
        }
    }

    private void pintarFaro(Graphics g) {
        Faro faro = parque.getFaro();

        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(0, 325, WIDTH_ACTIVIDADES, HEIGHT_ACTIVIDADES);
        g.setColor(COLOR_TEXTO);
        g.drawString("Faro-Mirador", 0, 340);
        // Entrada
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Faro.POS_ENTRADA.getX(), (int) Faro.POS_ENTRADA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Entrada", (int) Faro.POS_ENTRADA.getX() - 25, (int) Faro.POS_ENTRADA.getY() - 15);
        // Salida
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Faro.POS_SALIDA.getX(), (int) Faro.POS_SALIDA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Salida", (int) Faro.POS_SALIDA.getX() - 20, (int) Faro.POS_SALIDA.getY() - 15);
        // Escaleras
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Faro.POS_ESCALERAS.getX(), (int) Faro.POS_ESCALERAS.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Escalera (" + faro.getCantPersonasEscalera() + "/" + Faro.MAX_PERSONAS_ESCALERA + ")",
                (int) Faro.POS_ESCALERAS.getX() - 44, (int) Faro.POS_ESCALERAS.getY() - 15);
        // Cima
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Faro.POS_CIMA.getX(), (int) Faro.POS_CIMA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Cima", (int) Faro.POS_CIMA.getX() - 20, (int) Faro.POS_CIMA.getY() - 15);
        // Tobogan 1
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Faro.POS_TOBOGAN_1.getX(), (int) Faro.POS_TOBOGAN_1.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Tobogán 1", (int) Faro.POS_TOBOGAN_1.getX() - 24, (int) Faro.POS_TOBOGAN_1.getY() - 15);
        // Tobogan 2
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Faro.POS_TOBOGAN_2.getX(), (int) Faro.POS_TOBOGAN_2.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Tobogán 2", (int) Faro.POS_TOBOGAN_2.getX() - 24, (int) Faro.POS_TOBOGAN_2.getY() - 15);
    }

    private void pintarShop(Graphics g) {
        Shop shop = parque.getShop();

        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(WIDTH_ACTIVIDADES + 5, 75, WIDTH_ACTIVIDADES - 10, HEIGHT_ACTIVIDADES);
        g.setColor(COLOR_TEXTO);
        g.drawString("Shop", WIDTH_ACTIVIDADES + 5, 90);
        // Entrada
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Shop.POS_ENTRADA.getX(), (int) Shop.POS_ENTRADA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Entrada", (int) Shop.POS_ENTRADA.getX() - 25, (int) Shop.POS_ENTRADA.getY() - 20);
        // Shop
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Shop.POS_SHOP.getX(), (int) Shop.POS_SHOP.getY(), 10, 10);
        // Fila
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Shop.POS_FILA.getX(), (int) Shop.POS_FILA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Fila", (int) Shop.POS_FILA.getX() - 10, (int) Shop.POS_FILA.getY() - 20);
        // Caja
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) Shop.POS_CAJAS.getX(), (int) Shop.POS_CAJAS.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Cajas " + shop.getCantCajasDisponibles() + "/" + shop.getMaxCajas(),
                (int) Shop.POS_CAJAS.getX() - 25, (int) Shop.POS_CAJAS.getY() - 20);
    }

    private void pintarCarrera(Graphics g) {
        CarreraGomones carrera = parque.getCarreraGomones();

        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(WIDTH_ACTIVIDADES * 2, 75, WIDTH_ACTIVIDADES, HEIGHT_ACTIVIDADES);
        g.setColor(COLOR_TEXTO);
        g.drawString("Carrera de gomones", WIDTH_ACTIVIDADES * 2, 90);
        // Entrada
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) CarreraGomones.POS_ENTRADA.getX(), (int) CarreraGomones.POS_ENTRADA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Entrada", (int) CarreraGomones.POS_ENTRADA.getX() - 25,
                (int) CarreraGomones.POS_ENTRADA.getY() - 20);
        // Stand bicis
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) CarreraGomones.POS_BICIS.getX(), (int) CarreraGomones.POS_BICIS.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Bicis", (int) CarreraGomones.POS_BICIS.getX() - 16,
                (int) CarreraGomones.POS_BICIS.getY() - 20);
        // Tren
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) CarreraGomones.POS_TREN.getX(), (int) CarreraGomones.POS_TREN.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Tren (" + carrera.getCantPersonasTren() + "/" + CarreraGomones.MAX_PERSONAS_TREN + ")",
                (int) CarreraGomones.POS_TREN.getX() - 35,
                (int) CarreraGomones.POS_TREN.getY() - 20);
        // Inicio
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) CarreraGomones.POS_INICIO.getX(), (int) CarreraGomones.POS_INICIO.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Inicio (" + carrera.getEsperandoCarrera() + "/" + CarreraGomones.GOMONES_NECESARIOS + ")",
                (int) CarreraGomones.POS_INICIO.getX() - 16,
                (int) CarreraGomones.POS_INICIO.getY() - 20);
        // Llegada
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) CarreraGomones.POS_LLEGADA.getX(), (int) CarreraGomones.POS_LLEGADA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Llegada", (int) CarreraGomones.POS_LLEGADA.getX() - 25,
                (int) CarreraGomones.POS_LLEGADA.getY() - 20);
        // Salida
        g.setColor(COLOR_ELEMENTO);
        g.fillOval((int) CarreraGomones.POS_SALIDA.getX(), (int) CarreraGomones.POS_SALIDA.getY(), 10, 10);
        g.setColor(COLOR_TEXTO);
        g.drawString("Salida", (int) CarreraGomones.POS_SALIDA.getX() - 16,
                (int) CarreraGomones.POS_SALIDA.getY() - 20);
    }

    private void pintarSnorkel(Graphics g) {
        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(WIDTH_ACTIVIDADES * 2, 325, WIDTH_ACTIVIDADES, HEIGHT_ACTIVIDADES);
        g.setColor(COLOR_TEXTO);
        g.drawString("Snorkel", WIDTH_ACTIVIDADES * 2, 340);
    }

    private void pintarPersonas(Graphics g) {
        g.setFont(new Font(labelReloj.getFont().getName(), Font.PLAIN, 13));
        for (Persona persona : personas) {
            // Círculo
            g.setColor(persona.getColor());
            g.fillOval(persona.getPosX(), persona.getPosY(), TAM_PERSONAS, TAM_PERSONAS);
            // Label
            g.setColor(GUI.COLOR_TEXTO);
            g.drawOval(persona.getPosX(), persona.getPosY(), TAM_PERSONAS, TAM_PERSONAS);
            // Número 2 píxeles arriba de la persona
            g.drawString(persona.getLabel(), persona.getPosX() + 4, persona.getPosY() - 2);
        }
    }

    public static int getHora() {
        return hora;
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Parque ECO-PCS");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(new GUI(), BorderLayout.CENTER);
        // Hilos
        SwingWorker<Void, String> workerReloj = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                String horaActual;
                while (true) {
                    Thread.sleep(1);

                    horaActual = (minutos > 9) ? hora + ":" + minutos : hora + ":0" + minutos;
                    publish(horaActual);
                }
            }

            @Override
            protected void process(List<String> chunks) {
                String nuevaHora = chunks.get(chunks.size() - 1);

                // Actualizar el frame junto con el reloj
                labelReloj.setText(nuevaHora);
                frame.repaint();
            }
        };
        workerReloj.execute();
        try {
            while (true) {
                // 800 ms corresponden a 1 minuto del parque
                Thread.sleep(800);
                minutos += 1;
                if (minutos > 59) {
                    hora++;
                    minutos = 0;
                    if (hora > 23) {
                        hora = 0;
                    }
                }
            }
        } catch (Exception e) {
        }
    }
}
