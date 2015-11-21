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
public class SolucionMetricas {
    private int f1;
    private double f2;

    public SolucionMetricas(int f1, double f2) {
        this.f1 = f1;
        this.f2 = f2;
    }

    public int getF1() {
        return f1;
    }

    public double getF2() {
        return f2;
    }

    @Override
    public String toString() {
        return "{" + "f1=" + f1 + ", f2=" + f2 + '}';
    }
    
}
