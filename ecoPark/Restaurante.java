import java.util.concurrent.locks.*;

public class Restaurante {

    private int nroRestaurante;
    private int clientesEsperando = 0;
    private Lock lock = new ReentrantLock(true);
    private Condition esperarComida = lock.newCondition();
    private Condition esperarPedido = lock.newCondition();

    public Restaurante(int nroRestaurante) {
        this.nroRestaurante = nroRestaurante;
    }

    public int getNroRestaurante() {
        return nroRestaurante;
    }

    public void pedirComida(int idCliente) {
        try {
            lock.lock();
            // Avisa al cocinero
            esperarPedido.signal();
            System.out.println(idCliente + " avisa cocinero, restaurante " + nroRestaurante);
            clientesEsperando++;
            // Espera
            esperarComida.await();
            System.out.println(idCliente + " recibe comida, restaurante " + nroRestaurante);
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public void prepararComida() {
        try {
            lock.lock();
            if (clientesEsperando == 0) {
                System.out.println("Cocinero espera, restaurante " + nroRestaurante);
                esperarPedido.await();
            }
            // Hay cliente esperando
            System.out.println("Cocinero cocina, restaurante " + nroRestaurante);
            Thread.sleep(3000);
            // Entrega la comida
            esperarComida.signal();
            clientesEsperando--;
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

}
