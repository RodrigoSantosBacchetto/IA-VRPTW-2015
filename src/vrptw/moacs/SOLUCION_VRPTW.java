/*
 * Clase que representa la solucion al problema del VRPTW
 */
package vrptw.moacs;

import java.io.PrintStream;

/**
 *
 * @author notebook
 */
public class SOLUCION_VRPTW {
    
    //numero de camiones de la solucion
    private int camiones;
    //tamanho actual de camion
    private int sizeActual;
    //vector de clientes
    private int clientes[];
    //tamanho del vector de clientes
    private int size;

    public SOLUCION_VRPTW(int tam) {
        //crea el vector de clientes con el tamanho recibido como parametro
        clientes = new int[tam];
        //incializa con -1 el vector de clientes
        for (int i = 0; i < tam; i++) {
            clientes[i] = -1;
        }
        size = tam;
        
        camiones = 1;
        sizeActual = 0;
    }

    //incrementa la cantidad de camiones
    public void incrementarCamiones() {
        camiones++;
    }

    //retorna la cantidad de camiones
    public int getCamiones() {
        return camiones;
    }

    //
    public void add(int valor) {
        if (sizeActual + 1 >= size) {
            duplicarTamanho();
        }
        clientes[sizeActual] = valor;
        sizeActual++;
    }

    public int getSizeActual() {
        return sizeActual;
    }
    
    //imprime
    public void imprimir(PrintStream f) {
        for (int i = 0; i < sizeActual - 1; i++) {
            f.print(clientes[i] + "-");
        }
        f.print(clientes[sizeActual - 1] + "-");
    }

    //resetear con los valores iniciales por defecto
    public void resetear() {
        for (int i = 0; i < size; i++) {
            clientes[i] = -1;
        }
        sizeActual = 0;
        camiones = 0;
    }
    
    //copia en el vector de clientes todos los que son soluciones
    public void copiarSolucion(SOLUCION_VRPTW sol) {
        for (int i = 0; i < sol.getSizeActual(); i++) {
            clientes[i] = sol.get(i);
        }
        //setea las demas posiciones a -1
        for (int i = sol.getSizeActual(); i < size; i++) {
            clientes[i] = -1;
        }
        sizeActual = sol.getSizeActual();
        camiones = sol.getCamiones();
    }

    //duplica el tamanho del vector de clientes
    private void duplicarTamanho() {
        int arrayAnterior[] = clientes;
        clientes = new int[size * 2];
        //carga nuevamente con los valores que tenia anteriormente
        for (int i = 0; i < size; i++) {
            clientes[i] = arrayAnterior[i];
        }
        //setea las demas posiciones a -1
        for (int i = size; i < size * 2; i++) {
            clientes[i] = -1;
        }
        //size ahora es el doble de su valor anterior
        size *= 2;
    }

    @Override
    public String toString() {
        String ret = "SolucionVRP{" + "camiones=" + camiones + ", sizeActual=" + sizeActual;
        ret = ret.concat(", array=[");
        for (int i = 0; i < sizeActual; i++) {
            ret = ret.concat(clientes[i]+";");
        }
        ret = ret.concat("]");
        return ret;
    }
    
    public void set(int pos, int valor) {
        clientes[pos] = valor;
    }
    
    public int get(int pos) {
        return clientes[pos];
    }
    
    public int getSize() {
        return size;
    }

}

