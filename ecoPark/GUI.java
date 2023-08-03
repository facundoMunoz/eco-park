import javax.swing.*;
import java.awt.*;
import java.util.List;

public class GUI extends JPanel {

    // GUI
    private static JLabel labelReloj = new JLabel();
    // Reloj
    private static int hora = 7;
    private static int minutos = 0;
    // Elementos
    Parque parque = new Parque();
    // Hilos
    private static final int TAM_PERSONAS = 20;
    private static Persona[] personas = new Persona[10];
    // Colores
    private static final Color COLOR_FONDO = new Color(240, 236, 199);
    private static final Color COLOR_ACTIVIDAD = new Color(209, 203, 148);
    private static final Color COLOR_TEXTO = new Color(40, 40, 40);
    // Posiciones
    private static final int WIDTH = 960;
    private static final int HEIGHT = 720;
    public static final Point POS_INICIAL = new Point(WIDTH / 2, HEIGHT - 50);
    public static final Point POS_MOLINETES = new Point(WIDTH / 2, HEIGHT - 110);

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
        // Molinetes
        g.setColor(COLOR_ACTIVIDAD);
        g.fillRect(0, HEIGHT - 140, WIDTH, 70);
        g.setColor(COLOR_TEXTO);
        g.drawString("Molinetes: " + parque.getMolinetesDisponibles(), 0, HEIGHT - 125);
        g.setColor(Color.RED);
        g.fillOval((int) POS_MOLINETES.getX(), (int) POS_MOLINETES.getY(), 5, 5);
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
            // Texto 5 píxeles debajo de la persona
            g.drawString(persona.getLabel(), persona.getPosX() - 10, persona.getPosY() - 5);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Parque ECO-PCS");

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(960, 720));
        frame.setResizable(false);
        frame.setVisible(true);
        frame.add(new GUI(), BorderLayout.CENTER);
        // Hilos
        SwingWorker<Void, String> workerReloj = new SwingWorker<Void, String>() {
            @Override
            protected Void doInBackground() throws Exception {
                String horaActual;

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
    }
}
