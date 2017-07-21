/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosemantica;

import com.hp.hpl.jena.query.Query;
import com.hp.hpl.jena.query.QueryExecution;
import com.hp.hpl.jena.query.QueryExecutionFactory;
import com.hp.hpl.jena.query.QueryFactory;
import com.hp.hpl.jena.query.ResultSet;
import com.hp.hpl.jena.query.ResultSetFormatter;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.rdf.model.Property;
import com.hp.hpl.jena.rdf.model.RDFNode;
import com.hp.hpl.jena.rdf.model.Resource;
import com.hp.hpl.jena.rdf.model.StmtIterator;
import com.hp.hpl.jena.rdf.model.Statement;
import java.io.File;
import java.io.InputStream;
import com.hp.hpl.jena.rdf.model.Model;
import com.hp.hpl.jena.rdf.model.ModelFactory;
import com.hp.hpl.jena.util.FileManager;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path ;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Properties;

/**
 *
 * @author Lenovo
 */
public class CrearOntlogy {
    
    String inputFileName = System.getProperty("user.dir")+"/archivos/test_ontology.owl" ;
    // create an empty model
    ArrayList<DatosTest> datos = new ArrayList<DatosTest>();
    
     public  void  crear(int experimentos) throws IOException 
    {      
        String  stament = "" ; 
        String individuo = "";
        long TInicio, TFin, tiempo;
        int carga = 1;
        for(int i=1 ; i<=experimentos ;i ++)
        {
                 carga = carga + 10 ;
                 for(int j=1;j <=carga; j++  ) {
                       individuo ="individuo_"+i+"_"+j; 
                        stament += "\n"+
                          "<owl:NamedIndividual rdf:about=\"http://www.semanticweb.org/lenovo/ontologies/2017/6/untitled-ontology-12#"+individuo+"\">";
                        stament += "<rdf:type rdf:resource=\"http://www.semanticweb.org/lenovo/ontologies/2017/6/untitled-ontology-12#Person\"/>";
                        stament += "</owl:NamedIndividual>\n";
                 }
                DatosTest dato = new DatosTest(); 
                TInicio = System.currentTimeMillis();

                Path path = Paths.get(inputFileName) ;
                Charset charset = StandardCharsets.UTF_8;  

                String content;
                try {
                  content = new String (Files.readAllBytes(path),charset);
                  content = content.replaceAll("</rdf:RDF>", stament+" </rdf:RDF>");
                  Files.write(path,content.getBytes(charset));
                }catch (IOException e){
                  e.printStackTrace();
                }
                TFin = System.currentTimeMillis(); 
                tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
                dato.setCarga(carga);
                dato.setExperimento(i);
                dato.setTiempo(tiempo); 
                datos.add(dato);
                System.out.println("Experimento Ontologia "+i+" Carga "+carga+" tiempo "+ tiempo); 
                stament= "";
        } 
       
    }          
}
