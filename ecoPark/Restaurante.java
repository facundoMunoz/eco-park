import java.awt.Point;
import java.util.concurrent.locks.*;

public class Restaurante {

    private int nroRestaurante;
    private int clientesEsperando = 0;
    private Lock lock = new ReentrantLock(true);
    private Condition esperarComida = lock.newCondition();
    private Condition esperarPedido = lock.newCondition();
    // Posicion
    public final Point POS_FILA;
    public final Point POS_MESAS;

    public Restaurante(int nroRestaurante, Point posFila, Point posMesas) {
        this.nroRestaurante = nroRestaurante;
        this.POS_FILA = posFila;
        this.POS_MESAS = posMesas;
    }

    public int getNroRestaurante() {
        return nroRestaurante;
    }

    public void pedirComida(int idCliente) {
        try {
            lock.lock();
            // Avisa al cocinero
            esperarPedido.signal();
            clientesEsperando++;
            // Espera
            esperarComida.await();
        } catch (Exception e) {
        } finally {
            lock.unlock();
        }
    }

    public void prepararComida() {
        try {
            lock.lock();
            if (clientesEsperando == 0) {
                esperarPedido.await();
            }
            // Hay cliente esperando
            Thread.sleep(5000);
            // Entrega la comida
            esperarComida.signal();
            clientesEsperando--;
        } catch (InterruptedException e) {
        } finally {
            lock.unlock();
        }
    }

}
