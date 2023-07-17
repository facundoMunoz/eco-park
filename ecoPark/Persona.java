public class Persona extends Thread {

    private int id;
    private Parque parque;

    public Persona(int id, Parque parque) {
        this.id = id;
        this.parque = parque;
    }

    public void run() {
        // Entra al parque
        parque.cruzarMolinete();
        System.out.println(id + " entra al parque");
        // Decide dónde ir
        irRestaurante();
    }

    private void irRestaurante() {
        try {
            Restaurante[] restaurantes = parque.getRestaurantes();
            int opcionRestaurante = ((int) (Math.random() * 10)) % parque.getCantRestaurantes();
            restaurantes[opcionRestaurante].pedirComida(this.id);
            Thread.sleep(4000);
        } catch (Exception e) {
        }
    }

}