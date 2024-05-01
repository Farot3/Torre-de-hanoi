package torre.de;

import java.util.ArrayList;
import java.util.List;

public class ResoluciónAutomatica {
    private List<String> movimientos;

    public ResoluciónAutomatica(int numAros) {
        this.movimientos = calcularMovimientosOptimos(numAros);
    }

    private List<String> calcularMovimientosOptimos(int numAros) {
        List<String> movimientos = new ArrayList<>();
        calcularMovimientosOptimosRecursivo(numAros, 1, 3, 2, movimientos);
        return movimientos;
    }

    private void calcularMovimientosOptimosRecursivo(int numAros, int origen, int destino, int auxiliar, List<String> movimientos) {
        if (numAros == 1) {
            movimientos.add(origen + " " + destino);
        } else {
            calcularMovimientosOptimosRecursivo(numAros - 1, origen, auxiliar, destino, movimientos);
            movimientos.add(origen + " " + destino);
            calcularMovimientosOptimosRecursivo(numAros - 1, auxiliar, destino, origen, movimientos);
        }
    }

    public List<String> getMovimientos() {
        return movimientos;
    }
}