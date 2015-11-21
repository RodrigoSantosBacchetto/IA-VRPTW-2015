/*
 * Clase que representa a cada cliente a los cuales se debe llegar
 */
package vrptw.moacs;

/**
 *
 * @author notebook
 */
public class Cliente {
    
    // identificador del cliente
    private int id;
    // Coodenadas X
    private double x;
    // Coodenadas Y
    private double y;
    // Tiempo que se requiere para atender al cliente. Tiempo de servicio en el cliente
    private double serviceTime;
    // Cantidad del producto demandado
    private double demand;
    // Tiempo inicial en el cual el cliente puede recibir los productos. Incio de ventana
    private double readyTime;
    // Tiempo final hasta el cual el cliente puede recibir los productos. Fin de ventana
    private double dueTime;

    //constructor
    public Cliente(int id, double x, double y, double serviceTime, double demand, double readyTime, double dueTime) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.serviceTime = serviceTime;
        this.demand = demand;
        this.readyTime = readyTime;
        this.dueTime = dueTime;
    }
    //getters
    public int getId() {
        return id;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getServiceTime() {
        return serviceTime;
    }

    public double getDemand() {
        return demand;
    }

    public double getReadyTime() {
        return readyTime;
    }

    public double getDueTime() {
        return dueTime;
    }
    //setters
    public void setId(int id) {
        this.id = id;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setServiceTime(double serviceTime) {
        this.serviceTime = serviceTime;
    }

    public void setDemand(double demand) {
        this.demand = demand;
    }

    public void setReadyTime(double readyTime) {
        this.readyTime = readyTime;
    }

    public void setDueTime(double dueTime) {
        this.dueTime = dueTime;
    }
    
    
}
