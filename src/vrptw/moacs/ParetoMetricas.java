/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vrptw.moacs;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author notebook
 */
public class ParetoMetricas {
    
    public List<SolucionMetricas> frentePareto = new ArrayList<SolucionMetricas>();
    
    public boolean agregarNoDominado(SolucionMetricas nuevo) {
        for (SolucionMetricas actual : frentePareto) {
            if(actual.getF1() <= nuevo.getF1() && actual.getF2() <= nuevo.getF2()) {
                return false;
            }
        }
        frentePareto.add(nuevo);
        return true;
    }
    
    public void eliminarDominados(SolucionMetricas nuevo) {
        Iterator<SolucionMetricas> iterator = frentePareto.iterator();
        while (iterator.hasNext()) {
            SolucionMetricas actual = iterator.next();
            if((actual.getF1() > nuevo.getF1() && actual.getF2() >= nuevo.getF2()) || (actual.getF1() >= nuevo.getF1() && actual.getF2() > nuevo.getF2())) {
                iterator.remove();
            }
        }
    }

    public double error(ParetoMetricas paretoOptimo)
    {
        int count = 0;
        for(SolucionMetricas solucion: this.frentePareto){
            if(paretoOptimo.frentePareto.contains(solucion)){
                count++;
            }
        }
        return 1-(count/this.frentePareto.size());
    }
    public List<SolucionMetricas> getFrentePareto() {
        return frentePareto;
    }
}
