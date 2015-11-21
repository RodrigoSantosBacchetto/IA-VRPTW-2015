/*
 * Clase principal
 */
package vrptw.moacs;

/**
 *
 * @author notebook
 */
public class MAIN_VRPTW {
    
    public static void main(String[] args) {
        //para ejecutar 5 veces el algoritmo
        int cantIteraciones = 5;
        String parametros = "";
        String pr = "";
        //algitmo moacs que resuelve el problema
        ALGO_MOACS alg;
        //problema del vrptw a resolver
        PROBLEM_VRPTW prob;
        //archivos de instancias a resolver
        String[] arrayArchivoProblema = {"vrptw_c101.txt", "vrptw_rc101.txt"};
        String ruta = "instancias\\";
        String subRuta = "resultados\\";
        String archivoParametros = "parametros.txt";
        String archivoProblema;
        System.out.println("*******************Iniciando ejecucion*********************************");
        
        SolucionMetricas FPOc101 = new SolucionMetricas(9, 857.4002096877956);      //Frente Pareto Optimo para la instancia c101
        
        SolucionMetricas FPOrc101_1 = new SolucionMetricas(11, 1532.2782176724243);   //Frente Pareto Optimo para la instancia rc101
        SolucionMetricas FPOrc101_2 = new SolucionMetricas(12, 1520.4586161710367);   //Frente Pareto Optimo para la instancia rc101
        
        ParetoMetricas paretoC101 = new ParetoMetricas();
        paretoC101.agregarNoDominado(FPOc101);
        
        ParetoMetricas paretoRC101 = new ParetoMetricas();
        paretoRC101.agregarNoDominado(FPOrc101_1);
        paretoRC101.agregarNoDominado(FPOrc101_2);
        
        Metricas metricas = new Metricas();
            
        //para recorrer primero la instantia 1 y luego la 2
        for (int m = 0; m < arrayArchivoProblema.length; m++) { //recorrer array de ArchivoProblema

            parametros = ruta + archivoParametros;
            pr = arrayArchivoProblema[m];
            archivoProblema = pr;
            
            System.out.println("****************************************************");
            System.out.println();
            System.out.println("Instancia a resolver = " + archivoProblema);

            String cadenaYtrue = subRuta + "YTRUE-" + pr + ".txt";
             ParetoMetricas conjuntoSoluciones = new ParetoMetricas();
            
             ParetoMetricas paretoActual = new ParetoMetricas();
            //inicio de iteracion 
            for (int i = 0; i < cantIteraciones; i++) {
                System.out.println("Corrida: " + i);
                prob = new PROBLEM_VRPTW(ruta + archivoProblema);
                alg = new ALGO_MOACS(prob, parametros);
                //llama a la funcion que realiza la ejecucion del algoritmo
                alg.ejecutar();
                alg.pareto.agregarSolucionesVRP(prob, cadenaYtrue);
            
                
                
                //recorre para mostrar todas las soluciones encontradas
                for (int j = 0; j < alg.pareto.getCantSoluciones(); j++) {
                    SOLUCION_VRPTW solucionVRP = alg.pareto.getSolucionVRP(j);
                    int camiones = solucionVRP.getCamiones();
                    double funcionObjetivo2 = alg.getVRPTW().funcionObjetivo2(solucionVRP);
                    SolucionMetricas actual = new SolucionMetricas(camiones, funcionObjetivo2);
                    System.out.println("\tSolucion "+j);
                    System.out.println("\tF1: "+camiones);
                    System.out.println("\tF2: "+funcionObjetivo2);
                    paretoActual.agregarNoDominado(actual);
                    conjuntoSoluciones.frentePareto.add(actual);
                }
                
                System.out.println();
            }
                double m1 = metricas.distanciaAlFPO((m==0 ? paretoC101 : paretoRC101), paretoActual);
                double m2 = metricas.distribucionFrente(paretoActual);
                double m3 = metricas.extensionFrente(paretoActual);
                double error = conjuntoSoluciones.error((m==0 ? paretoC101 : paretoRC101));
                
                
                System.out.println("M1: "+m1+"\nM2: "+m2+"\nM3: "+m3+"\nError: "+error);
        }
        System.out.println("\n*******************Fin de ejecucion*********************************");
            
    }
    
}
