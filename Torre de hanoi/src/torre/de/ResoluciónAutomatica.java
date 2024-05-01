package torre.de;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class ResoluciónAutomatica implements ActionListener {
    private TorresDeHanoi hanoi;
    private mundo panelMundo;
    private int currentMove;
    private Timer timer;
    private int delay;
    private int arosJuego;
    private boolean movimientosLibres;
    private int torreOrigenLibre;

    // Constructor que recibe una instancia de TorresDeHanoi, mundo y el delay
    public ResoluciónAutomatica(TorresDeHanoi hanoi, mundo panelMundo, int delay) {
        this.hanoi = hanoi;
        this.panelMundo = panelMundo;
        this.delay = delay;
        currentMove = 0;
        timer = new Timer(delay, this);
        arosJuego = panelMundo.arosJuego;
        movimientosLibres = false;
        torreOrigenLibre = 0;
    }

    // Método para iniciar la resolución automática
    public void iniciarResolucion() {
        currentMove = 0;
        timer.start();
    }

    // Método que maneja el siguiente movimiento en la resolución
    private void realizarSiguienteMovimiento() {
        if (currentMove < hanoi.getCadenas().size()) {
            String movimiento = hanoi.getCadenas().get(currentMove);
            realizarMovimiento(movimiento);
            currentMove++;
        } else {
            // Resolución óptima
            resolverOptimo();
        }
    }

    private void resolverOptimo() {
        int numAros = panelMundo.arosJuego;
        int origen = 1; // Torre de origen
        int destino = 3; // Torre de destino
        int auxiliar = 2; // Torre auxiliar

        resolverOptimoRecursivo(numAros, origen, destino, auxiliar);
    }

    private void resolverOptimoRecursivo(int numAros, int origen, int destino, int auxiliar) {
        if (numAros == 1) {
            // Mover el aro de la torre de origen a la torre de destino
            moverAro(obtenerTorrePorNumero(origen), obtenerTorrePorNumero(destino));
        } else {
            // Mover n-1 aros de la torre de origen a la torre auxiliar
            resolverOptimoRecursivo(numAros - 1, origen, auxiliar, destino);

            // Mover el aro restante de la torre de origen a la torre de destino
            moverAro(obtenerTorrePorNumero(origen), obtenerTorrePorNumero(destino));

            // Mover los n-1 aros de la torre auxiliar a la torre de destino
            resolverOptimoRecursivo(numAros - 1, auxiliar, destino, origen);
        }
    }

    // Método para verificar si una torre está ordenada
    private boolean verificarTorreOrdenada(Torre torre) {
        if (torre.getComponentCount() == arosJuego) {
            Anillos[] aros = new Anillos[arosJuego];
            for (int i = 0; i < torre.getComponentCount(); i++) {
                aros[i] = (Anillos) torre.getComponent(i);
            }
            for (int i = 0; i < aros.length - 1; i++) {
                if (aros[i].getWidth() < aros[i + 1].getWidth()) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    // Método para realizar un movimiento
    private void realizarMovimiento(String movimiento) {
        // Eliminar espacios en blanco y dividir el movimiento en partes
        String[] partes = movimiento.trim().split(" ");
        int origen = Integer.parseInt(partes[4]); // El índice 4 contiene el número de la torre de origen
        int destino = Integer.parseInt(partes[8]); // El índice 8 contiene el número de la torre de destino

        // Obtener las torres correspondientes
        Torre torreOrigen = obtenerTorrePorNumero(origen);
        Torre torreDestino = obtenerTorrePorNumero(destino);

        // Realizar el movimiento en la interfaz gráfica
        if (!movimientosLibres) {
            moverAro(torreOrigen, torreDestino);
        } else {
            // Permitir que los aros se sigan moviendo hasta que se cumplan las condiciones de finalización del juego
            moverAro(torreOrigen, torreDestino);
        }
    }

    // Método para mover un aro de una torre a otra
    private void moverAro(Torre origen, Torre destino) {
        // Verificar que la torre de origen tenga al menos un aro
        if (origen.getComponentCount() > 0) {
            // Obtener el componente a mover
            Anillos aro = (Anillos) origen.getComponent(origen.getComponentCount() - 1);

            // Verificar si el movimiento es válido
            if (destino.getComponentCount() == 0 || aro.getWidth() < ((Anillos) destino.getComponent(destino.getComponentCount() - 1)).getWidth()) {
                // Calcular la nueva posición del anillo en la torre de destino
                int newY = (destino.getComponentCount() == 0) ? 250 : destino.getComponent(destino.getComponentCount() - 1).getY() - 20;

                // Establecer la nueva posición del anillo
                aro.setLocation(aro.getX(), newY);

                // Agregar el anillo a la torre de destino y eliminarlo de la torre de origen
                origen.remove(aro);
                destino.add(aro);

                // Actualizar la interfaz gráfica
                destino.revalidate();
                destino.repaint();
                origen.revalidate();
                origen.repaint();

                // Verificar si todos los aros están ordenados en la torre 3 después del movimiento
                if (destino == panelMundo.torre3 && verificarTorreOrdenada(destino)) {
                    System.out.println("¡Juego resuelto!");
                    // Muestra un mensaje de que el juego ha terminado
                    panelMundo.mostrarMensaje("¡Juego resuelto!");
                    timer.stop(); // Detener el temporizador porque el juego ha terminado
                }
            } else {
                System.out.println("Movimiento inválido: " + origen + " -> " + destino);
            }
        }
    }

    // Método para obtener la torre correspondiente a partir de su número
    private Torre obtenerTorrePorNumero(int numeroTorre) {
        switch (numeroTorre) {
            case 1:
                return panelMundo.torre1;
            case 2:
                return panelMundo.torre2;
            case 3:
                return panelMundo.torre3;
            default:
                return null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        realizarSiguienteMovimiento();
    }
}