/*
 * Clase que representa la Tabla de feromonas de la colonia de hormigas
 */
package vrptw.moacs;

/**
 *
 * @author notebook
 */
public class TablaFeromona {

    //cantidad de clientes
    private int cantClientes;
    //matriz para representar la tabla de feromonas
    private double[][] tabla;

    //constructor
    public TablaFeromona(int tam) {
        this.cantClientes = tam;
        this.tabla = new double[tam][tam];
    }
    
    //funcion que retorna el valor la tabla dado un estador oriegen a un estado destino
    public double obtenerValor(int estOrigen, int estDestino) {
        return tabla[estOrigen][estDestino];
    }

    //funcion que actualiza la tabla de feromonas dado las coordenadas de estado origien y destino y el 
    //valor tau que sera el nuevo valor en esa posicion
    public void actualizar(int estOrigen, int estDestino, double tau) {
        tabla[estOrigen][estDestino] = tau;
    }

    //reinicia la tabla de feromonas dado el valor de tau inicial recibido como parametro
    public void reiniciar(double tau0) {
        for (int i = 0; i < cantClientes; i++) {
            for (int j = 0; j < cantClientes; j++) {
                tabla[i][j] = tau0;
            }
        }
    }

    //funcion que muestra en pantalla la tabla de feromonas
    public void imprimir() {
        for (int i = 0; i < cantClientes; i++) {
            for (int j = 0; j < cantClientes; j++) {
                System.out.printf("%lf ", tabla[i][j]);
            }
            System.out.print("\n");
        }
    }


}
