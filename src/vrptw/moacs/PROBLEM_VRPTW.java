/*
 * Clase que representa el problema del VRPTW a resolver
 */
package vrptw.moacs;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 *
 * @author notebook
 */
public class PROBLEM_VRPTW {
  
    //capacidad de vehiculo
    private int capacidad;
    //vector clientes a ser atendidos
    private Cliente clientes[];
    //cantidad de clientes
    protected int cantClientes;
    //matriz de adyacencia de los clientes
    protected double[][] matrizAdy;

    //constructor
    public PROBLEM_VRPTW(String file) {
        //inicializa con cargando estado, parseando desde el archivo todos los atributos
        cargarEstado(file);
    }

    public int getCantClientes() {
        return cantClientes;
    }
    
    public double getDistancia(int i, int j) {
        return matrizAdy[i][j];
    }

    private void cargarEstado(String file) {
        // El archivo file posee: la cantidad de customers, la capacidad de los camiones
        // y los datos de cada customer: coordenadas, demanda, ventana y tiempo de servicio
        
        //valores inciales por defecto
        int id = 0;
        double x = 0.0;
        double y = 0.0;
        double demanda = 0.0;
        double readyT = 0.0;
        double dueT = 0.0;
        double serviceT = 0.0;
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
        String linea = "";
        try {
            reader.readLine(); //CUSTOMERS
            //parsea la cantidad de clientes
            cantClientes = Integer.parseInt(reader.readLine());
            //crea la matriz de adyacencia con tamanho igual a la cantidad de clientes
            matrizAdy = new double[cantClientes][cantClientes];
            //incializa el vector de clientes a la cantidad de clientes que se tiene en el archivo
            clientes = new Cliente[cantClientes];
            reader.readLine(); //CAPACITY
            //se parsea la capacidad de cada vehículo
            capacidad = Integer.parseInt(reader.readLine());
            reader.readLine(); //CUST NO.  XCOORD.   YCOORD.    DEMAND   READY TIME  DUE DATE   SERVICE TIME
            int count = 0;
            String[] subCadena;
            //recorre el archivo, registro a registro para parsear los demas datos
            while ((linea = reader.readLine()) != null) {
                if (count < cantClientes) {
                    subCadena = linea.split("\\s+");
                    for (int i = 0; i < subCadena.length; i++) {
                        switch (i) {
                            case 0:
                                id          = Integer.parseInt(subCadena[i]);
                                break;
                            case 1:
                                x           = Double.parseDouble(subCadena[i]);
                                break;
                            case 2:
                                y           = Double.parseDouble(subCadena[i]);
                                break;
                            case 3:
                                demanda     = Double.parseDouble(subCadena[i]);
                                break;
                            case 4:
                                readyT      = Double.parseDouble(subCadena[i]);
                                break;
                            case 5:
                                dueT        = Double.parseDouble(subCadena[i]);
                                break;
                            case 6:
                                serviceT    = Double.parseDouble(subCadena[i]);
                                break;
                            default:
                                ;
                        }
                    }
                    //carga el vector de clientes con los atributos correspondientes
                    clientes[count] = new Cliente(id, x, y, serviceT, demanda, readyT, dueT);
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //genera la matriz de adyacencia de los clientes a partir de las coordenadas x y
        generarMatrizAdyacencia();
        
        //imprimir();
    }

    //genera la matriz de adyacencia de los clientes
    private void generarMatrizAdyacencia() {
        double x;
        double y;
        double aux;

        for (int i = 0; i < cantClientes; i++) {
            for (int j = i + 1; j < cantClientes; j++) {
                //x^2
                x = Math.pow((clientes[i].getX() - clientes[j].getX()), 2);
                //y^2
                y = Math.pow((clientes[i].getY() - clientes[j].getY()), 2);
                //(x^2) + (y^2)
                aux = x + y;
                matrizAdy[i][j] = Math.sqrt(aux);
                matrizAdy[j][i] = matrizAdy[i][j];
            }
            matrizAdy[i][i] = 0;
        }
    }

    //Funcion objetivo 1 a minimiza// devuelve la cantidad camiones
    public double funcionObjetivo1(SOLUCION_VRPTW sol) {
        return ((SOLUCION_VRPTW) sol).getCamiones(); 
    }

    //funcion objetivo 2 a minimizar // devolver el "Total Travel Distance"
    //se asume que la distancia que se calcula para cada par de clientes representa 
    //el tiempo que se tarda entre ese par de clientes. Se utiliza la matriz de adyacencia
    public double funcionObjetivo2(SOLUCION_VRPTW solucion) {
        int i;
        double suma = 0;
        for (i = 0; i < solucion.getSizeActual() - 1; i++) {
            suma += matrizAdy[solucion.get(i)][solucion.get(i + 1)];
        }
        suma += matrizAdy[solucion.get(solucion.getSizeActual() - 1)][0];

        return suma; 
    }
    
    //funciones de heuristicas. heuristica1 simplemente retorna 1
    public double heuristica1(int estOrigen, int estDest) {
        return 1;
    }
    //heuristica2 retorna un valor de uno sobre el valor del tiempo dado un estado origen y un estado destino
    public double heuristica2(int estOrigen, int estDest) {
        return 1.0 / matrizAdy[estOrigen][estDest];
    }

    public int getCapacidad() {
        return capacidad;
    }
        public double getDemand(int customer) {
        return clientes[customer].getDemand();
    }

    public double getReadyTime(int customer) {
        return clientes[customer].getReadyTime();
    }

    public double getDueTime(int customer) {
        return clientes[customer].getDueTime();
    }
    //tiempo de servicio
    public double getServiceTime(int customer) {
        return clientes[customer].getServiceTime();
    }

    
}
