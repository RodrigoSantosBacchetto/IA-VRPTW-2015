/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw.moacs;

/**
 *
 * @author notebook
 */
public class Metricas {
    
     private static final double SIGMA = 100000.0;

    public Metricas() {
    }

    public double distanciaAlFPO(ParetoMetricas fpo, ParetoMetricas frentePrueba) {
        double total = 0.0;
        for (SolucionMetricas ind : frentePrueba.getFrentePareto()) {
            double dist = Double.MAX_VALUE;
            for (SolucionMetricas indFPO : fpo.getFrentePareto()) {
                if (dist > distancia(ind, indFPO)) {
                    dist = distancia(ind, indFPO);
                }
            }
            total += dist;
        }
        return total / (double) frentePrueba.getFrentePareto().size();
    }

    public double distribucionFrente(ParetoMetricas frentePrueba) {
        double total = 0.0;
        for (SolucionMetricas ind : frentePrueba.getFrentePareto()) {
            double cont = 0.0;
            for (SolucionMetricas indFPO : frentePrueba.getFrentePareto()) {
                if (distancia(ind, indFPO) < SIGMA) {
                    cont = cont + 1;
                }
            }
            total += cont;
        }
        return total / ((double) frentePrueba.getFrentePareto().size() - 1);
    }

    public double extensionFrente(ParetoMetricas frentePrueba) {
        double dist = Double.MIN_VALUE;
        for (SolucionMetricas ind : frentePrueba.getFrentePareto()) {
            for (SolucionMetricas indFPO : frentePrueba.getFrentePareto()) {
                if (distancia(ind, indFPO) > dist) {
                    dist = distancia(ind, indFPO);
                }
            }
        }
        return dist;
    }
    private double distancia(SolucionMetricas x, SolucionMetricas y) {
        int     x1 = x.getF1();
        double  x2 = x.getF2();
        int     y1 = y.getF1();
        double  y2 = y.getF2();

        return Math.sqrt(Math.pow(x1 - y1, 2) + Math.pow(x2 - y2, 2));

    }
}
