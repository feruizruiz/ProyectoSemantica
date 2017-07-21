/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosemantica;

/**
 *
 * @author Lenovo
 */
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JFrame;

import java.awt.Color; 
import java.awt.BasicStroke; 

import org.jfree.chart.ChartPanel; 
import org.jfree.chart.JFreeChart; 
import org.jfree.data.xy.XYDataset; 
import org.jfree.data.xy.XYSeries; 
import org.jfree.ui.ApplicationFrame; 
import org.jfree.ui.RefineryUtilities; 
import org.jfree.chart.plot.XYPlot; 
import org.jfree.chart.ChartFactory; 
import org.jfree.chart.plot.PlotOrientation; 
import org.jfree.data.xy.XYSeriesCollection; 
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;


public class LinearChart extends  JFrame {
   
    ArrayList<DatosTest> datosRDF = new ArrayList<DatosTest>();
    ArrayList<DatosTest> datosOWL = new ArrayList<DatosTest>();
    
    public  LinearChart (String applicationTitle ) {
        super(applicationTitle);      
   }
  
   public void GenerarLinearChart ( String applicationTitle , String chartTitle ) {
        JFreeChart xylineChart = ChartFactory.createXYLineChart(
         chartTitle ,
         "Carga" ,
         "Tiempo" ,
         createDataset() ,
         PlotOrientation.VERTICAL ,
         true , true , false);
        
        
      ChartPanel chartPanel = new ChartPanel( xylineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      final XYPlot plot = xylineChart.getXYPlot( );
      
      XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer( );
      renderer.setSeriesPaint( 0 , Color.RED );
      renderer.setSeriesPaint( 1 , Color.GREEN );
      renderer.setSeriesStroke( 0 , new BasicStroke( 4.0f ) );
      renderer.setSeriesStroke( 1 , new BasicStroke( 3.0f ) );
      plot.setRenderer( renderer ); 
      setContentPane( chartPanel );
      
   }

   private  XYDataset createDataset() {
    
      final XYSeries rdf = new XYSeries( "RDF" );          
      Iterator <DatosTest> itrDatosRDF = datosRDF.iterator();
      while(itrDatosRDF.hasNext()){
          DatosTest obj = itrDatosRDF.next();
          rdf.add(obj.getCarga(),obj.getTiempo()); 
          System.out.println(" carga RDF "+obj.getCarga()+" Tiempo "+obj.getTiempo());
      }
      
      final XYSeries owl = new XYSeries( "OWL" );          
      Iterator <DatosTest> itrDatosOWL = datosOWL.iterator();
      while(itrDatosOWL.hasNext()){
          DatosTest obj = itrDatosOWL.next();
          owl.add(obj.getCarga(),obj.getTiempo());  
          System.out.println(" carga OWL "+obj.getCarga()+" Tiempo "+obj.getTiempo());
      }         
       
      final XYSeriesCollection dataset = new XYSeriesCollection( );          
      dataset.addSeries(rdf);          
      dataset.addSeries(owl);          
      return dataset;
   }
}