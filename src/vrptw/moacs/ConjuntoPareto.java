/*
 * Clase que representa el Conjunto pareto de las soluciones optimas encontradas
 */
package vrptw.moacs;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintStream;

/**
 *
 * @author notebook
 */
public class ConjuntoPareto {
    
    public static final int PARETO = 50;
    //cantidad actual de soluciones
    private int cantSoluciones; 
    //tamano del array de soluciones
    private int tamano; 
    // array que contiene las soluciones del frente pareto
    protected SOLUCION_VRPTW[] soluciones;

    //constructor
    public ConjuntoPareto(int numSoluciones) {
        cantSoluciones = 0;
        tamano = numSoluciones;
        soluciones = new SOLUCION_VRPTW[numSoluciones];
    }
    
    //Dado la solucion encontrada se verifica si la misma es domina por alguna solucion del conjunto
    //o si es una solucion no dominada
    public int agregarNoDominado(SOLUCION_VRPTW sol, PROBLEM_VRPTW prob) {
        double solfuncion1 = prob.funcionObjetivo1(sol); // Evaluacion de la solucion respecto
        double solfuncion2 = prob.funcionObjetivo2(sol); // a las funciones obetivo del problema

        //axuliares
        double solauxfuncion1; // Evaluacion de los objetivos de alguna solucion del conjunto
        double solauxfuncion2;

        for (int i = 0; i < cantSoluciones; i++) {
            solauxfuncion1 = prob.funcionObjetivo1(soluciones[i]);
            solauxfuncion2 = prob.funcionObjetivo2(soluciones[i]);
            // ambas funciones objetivo siempre se minimizan
            //condicion que verifica la dominancia
            if (solauxfuncion1 <= solfuncion1 && solauxfuncion2 <= solfuncion2) {
                return 0; //la solucion sol es dominada por una solucion del conjunto

            }
        }
        //Aumentar el tamaño del conjunto Pareto si este esta lleno
        if (cantSoluciones == tamano) {
            SOLUCION_VRPTW[] listaAux = soluciones;
            tamano = tamano * 2;
            soluciones = new SOLUCION_VRPTW[tamano];
            for (int i = 0; i < cantSoluciones; i++) {
                soluciones[i] = listaAux[i];
            }
        }
        if (soluciones[cantSoluciones] == null) {
            soluciones[cantSoluciones] = new SOLUCION_VRPTW(sol.getSize());
        }
        soluciones[cantSoluciones].copiarSolucion(sol);
        cantSoluciones++;
        return 1;
    }

    //al tener una nueva solucion y entra al conjunto, este puede dominar a otras soluciones que ya se
    //encuentrar en el conjunto, por lo tanto deben ser eliminadas.
    public void eliminarDominados(SOLUCION_VRPTW sol, PROBLEM_VRPTW prob) {
        double solfuncion1 = prob.funcionObjetivo1(sol); // Evaluacion de la solucion respecto

        double solfuncion2 = prob.funcionObjetivo2(sol); // a las funciones obetivo del problema

        //auxiliare
        double solauxfuncion1; // Evaluacion de los objetivos de alguna solucion del conjunto

        double solauxfuncion2;
       

        for (int i = 0; i < cantSoluciones; i++) {
            solauxfuncion1 = prob.funcionObjetivo1(soluciones[i]);
            solauxfuncion2 = prob.funcionObjetivo2(soluciones[i]);
            // ambas funciones objetivo siempre se minimizan
            if ((solauxfuncion1 > solfuncion1 && solauxfuncion2 >= solfuncion2) || (solauxfuncion1 >= solfuncion1 && solauxfuncion2 > solfuncion2)) {
                //elim=soluciones[i];
                soluciones[i] = soluciones[cantSoluciones - 1];
                soluciones[cantSoluciones - 1] = null; //liberar puntero

                cantSoluciones--;
                i--;
            }
        }
    }

    public void listarSolucionesVRP(PROBLEM_VRPTW prob, String file) {
        try {
            PrintStream output = new PrintStream(new FileOutputStream(file));
            output.println(cantSoluciones);
            for (int i = 0; i < cantSoluciones; i++) {
                output.println(prob.funcionObjetivo1(soluciones[i]) + "\t" + prob.funcionObjetivo2(soluciones[i]));
            }
            output.close();
        } catch (java.io.IOException e) {
            System.out.println("Error al leer archivo");
            e.printStackTrace();
        }
    }

    //Agrega las soluciones del pareto en el archivo para utilizarlo luego en las metricas
    public void agregarSolucionesVRP(PROBLEM_VRPTW prob, String file) {
        try {
            FileWriter fstream = new FileWriter(file, true);
            BufferedWriter output = new BufferedWriter(fstream);
            for (int i = 0; i < cantSoluciones; i++) {
                output.write(prob.funcionObjetivo2(soluciones[i]) + "\t" + prob.funcionObjetivo1(soluciones[i]));
                output.newLine();
            }
            output.close();
        } catch (java.io.IOException e) {
            System.out.println("Error al leer archivo");
            e.printStackTrace();
        }
    }

    public int getCantSoluciones() {
        return cantSoluciones;
    }

    public SOLUCION_VRPTW getSolucionVRP(int i) {
        return soluciones[i];
    }

    @Override
    public String toString() {
        String ret = "ConjuntoPareto{" + "cantSoluciones=" + cantSoluciones + ", tamano=" + tamano + ", listaVRP=";
        ret = ret.concat("[");
        for (int i = 0; i < cantSoluciones; i++) {
            SOLUCION_VRPTW solucionVRP = soluciones[i];
            ret = ret.concat(solucionVRP+" , ");
        }
        ret = ret.concat("]");
        ret = ret.concat(" }");
        return ret;
    }

    
}
