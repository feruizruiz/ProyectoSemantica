/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosemantica;

import java.io.IOException;

/**
 *
 * @author Lenovo
 */
public class ProyectoSemantica {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws IOException {
        // TODO code application logic here
       int  nExperimentos  = 1000 ;//Integer.parseInt(args[0]);
       
       System.out.println("Directorio "+System.getProperty("user.dir")); 
      
       CreaRDF rdf = new CreaRDF();
       rdf.crear(nExperimentos);
       
    
        // cargue de una grafica  :  
      LinearChart chart = new LinearChart(
         "Tiempo VS Carga " );
      chart.datos = rdf.datos;
      chart.GenerarLinearChart( "Tiempo VS Carga ","Tiempo VS Carga");

      chart.pack( );
      chart.setVisible( true );
     
    }
    
}
