public class AdministradorParque {

    // Parque
    private static Parque parque = new Parque();

    public static void main(String[] args) {

        // Personas
        Persona personas[] = new Persona[12];
        for (int persona = 0; persona < 12; persona++) {
            personas[persona] = new Persona(persona, parque);
            personas[persona].start();
        }

        // Restaurantes y cocineros
        Restaurante[] restaurantes = parque.getRestaurantes();
        Cocinero[] cocineros = new Cocinero[parque.getCantRestaurantes()];
        for (int restaurante = 0; restaurante < restaurantes.length; restaurante++) {
            // Asignar un cocinero a cada restaurante
            cocineros[restaurante] = new Cocinero(restaurantes[restaurante]);
            cocineros[restaurante].start();
        }

    }

}
