package torre.de;

import javax.swing.*;
import java.util.List;

public class AnimadorDeMovimientos extends Thread {

    private Torre torreOrigen;
    private Torre torreAuxiliar;
    private Torre torreDestino;
    private List<String> movimientos;
    private int anillosOrigen;
    public int pasos;

    public AnimadorDeMovimientos(Torre torreOrigen, Torre torreAuxiliar, Torre torreDestino, List<String> movimientos) {
        this.torreOrigen = torreOrigen;
        this.torreAuxiliar = torreAuxiliar;
        this.torreDestino = torreDestino;
        this.movimientos = movimientos;
        this.anillosOrigen = torreOrigen.getComponentCount();
    }

    @Override
    public void run() {
        pasos = 0;
        for (String movimiento : movimientos) {           
            // Dividir la cadena de movimiento en números de torre individuales
            String[] torres = movimiento.split(" ");

            // Verificar si hay exactamente dos torres en el movimiento
            if (torres.length!= 2) {
                System.out.println("Error: Movimiento inválido.");
                continue; // Pasar al siguiente movimiento
            }

            // Convertir cada número de torre a entero
            int origen = Integer.parseInt(torres[0]);
            int destino = Integer.parseInt(torres[1]);

            // Verificar si los valores de origen y destino son válidos (1, 2 o 3)
            if (origen < 1 || origen > 3 || destino < 1 || destino > 3) {
                System.out.println("Error: Número de torre inválido.");
                continue; // Pasar al siguiente movimiento
            }

            moverAnilloVisualmente(origen, destino);
            pausar(1000); // Pausa de 1 segundo entre movimientos
        }
        JOptionPane.showMessageDialog(null, "Minimo de pasos:" + pasos );
        if (torreOrigen.getComponentCount() == 0) {
            JOptionPane.showMessageDialog(null, "¡Juego terminado!");
        }
    }

    private void moverAnilloVisualmente(int origen, int destino) {
        pasos++;
        System.out.println("Movimiento: " + origen + " -> " + destino);
        //JOptionPane.showMessageDialog(null, "Paso:" + pasos );

        // Obtener las torres de origen y destino
        Torre torreOrigen = obtenerTorre(origen);
        Torre torreDestino = obtenerTorre(destino);

        if (torreOrigen.getComponentCount() > 0) {
            Anillos anillo = (Anillos) torreOrigen.getComponent(torreOrigen.getComponentCount() - 1);

            // Verificar si el anillo a mover es más grande que el anillo superior en la torre de destino
            if (torreDestino.getComponentCount() > 0) {
                Anillos anilloSuperior = (Anillos) torreDestino.getComponent(torreDestino.getComponentCount() - 1);
                if (anillo.getWidth() > anilloSuperior.getWidth()) {
                    System.out.println("Movimiento inválido: el anillo no puede colocarse sobre otro más pequeño.");
                    // Mover el anillo de regreso a la torre de origen
                    moverAnilloVisualmente(destino, origen);
                    return;
                }
            }

            // Mover el anillo hacia la torre de destino
            int xDestino = torreDestino.getX() + 90 + (torreDestino.getWidth() - anillo.getWidth()) / 2;
            int yDestino = torreDestino.getY() + 250 - torreDestino.getComponentCount() * 20 - anillo.getHeight();

            while (anillo.getY() > yDestino) {
                anillo.setLocation(anillo.getX(), anillo.getY() - 5);
                torreDestino.repaint();
                esperar(20);
            }
            while (anillo.getX() < xDestino) {
                anillo.setLocation(anillo.getX() + 5, yDestino);
                torreDestino.repaint();
                esperar(20);
            }

            // Agregar el anillo a la torre de destino
            torreDestino.add(anillo);
            torreDestino.repaint();
            torreOrigen.repaint();
            reorganizarTorre(torreOrigen);
            reorganizarTorre(torreDestino);
            
        } else {
            System.out.println("Error: La torre de origen está vacía.");
        }
    }

    private void reorganizarTorre(Torre torre) {
        for (int i = 0; i < torre.getComponentCount(); i++) {
            Anillos anillo = (Anillos) torre.getComponent(i);
            int x = 70;
            int y = 250 - (20 * i);
            anillo.setBounds(x, y, anillo.getWidth(), anillo.getHeight());
        }
    }

    private void pausar(long milisegundos) {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void esperar(int milisegundos) {
        try {
            Thread.sleep(8);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean verificar(Anillos aroPresente, Anillos aroAMover) {
        int w = aroPresente.getWidth();
        int w2 = aroAMover.getWidth();

        return w2 <= w; // Permitir que un anillo pequeño se coloque sobre un anillo grande
    }

    private Torre obtenerTorre(int numeroTorre) {
        switch (numeroTorre) {
            case 1:
                return torreOrigen;
            case 2:
                return torreAuxiliar;
            case 3:
                return torreDestino;
            default:
                System.out.println("Número de torre inválido: " + numeroTorre);
                return null;
        }
    }
}