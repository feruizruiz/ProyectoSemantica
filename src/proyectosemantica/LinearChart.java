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
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

public class LinearChart extends  JFrame {
   
    ArrayList<DatosTest> datos = new ArrayList<DatosTest>();
    
    public  LinearChart (String applicationTitle ) {
        super(applicationTitle);      
   }
  
   public void GenerarLinearChart ( String applicationTitle , String chartTitle ) {
      JFreeChart lineChart = ChartFactory.createLineChart(
         chartTitle,
         "Carga","Tiempo",
         createDataset(),
         PlotOrientation.VERTICAL,
         true,true,false);
         
      ChartPanel chartPanel = new ChartPanel( lineChart );
      chartPanel.setPreferredSize( new java.awt.Dimension( 560 , 367 ) );
      setContentPane( chartPanel );
   }

   private DefaultCategoryDataset createDataset() {
      DefaultCategoryDataset dataset = new DefaultCategoryDataset( );

      Iterator <DatosTest> itrDatos = datos.iterator();
      while(itrDatos.hasNext()){
          DatosTest obj = itrDatos.next();
          dataset.addValue(obj.getTiempo(), "carga" ,  String.valueOf(obj.getCarga()));
      }

      return dataset;
   }
}