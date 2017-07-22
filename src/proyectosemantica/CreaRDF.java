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
import java.util.ArrayList;
import java.util.Properties;
/**
 *
 * @author Lenovo
 */
public class CreaRDF {
   
    String inputFileName = System.getProperty("user.dir")+"/archivos/test_file.rdf" ;
    // create an empty model
    ArrayList<DatosTest> datos = new ArrayList<DatosTest>();
    public  void  crear(int experimentos) throws IOException 
    {      
            String personURI = "http://somewhere/JohnSmith";
            String hasA = "hasA";
            String hasB = "hasB"; 
            String hasC = "hasC"; 
            String hasD = "hasD"; 
            String hasE = "hasE";
            String hasF = "hasF";
            String prefix = "http://somewhere/properties#";
            
           long TInicio, TFin, tiempo;
           int carga = 1;
           
            
            // use the FileManager to find the input file
         
            
            for(int i=1 ; i<=experimentos ;i ++)
            {    
                Model model = ModelFactory.createDefaultModel();
            
                InputStream in = FileManager.get().open( inputFileName );
                if (in == null) {
                throw new IllegalArgumentException(
                "File: " + inputFileName + " not found");
                }
                FileWriter out = new FileWriter(inputFileName);


                //create a property
                Property hasAProp   = model.createProperty(prefix+hasA);
                Property hasBProp    = model.createProperty(prefix+hasB);
                Property hasCProp = model.createProperty(prefix+hasC);
                Property hasDProp = model.createProperty(prefix+hasD);
                Property hasEProp   = model.createProperty(prefix+hasE);
                Property hasFProp  = model.createProperty(prefix+hasF);
                
                carga = carga + 1 ;
                 for(int j=1;j <=carga; j++  ) {
                     personURI = "http://somewhere/Persona_"+i+"_"+j;
                     String valA =  "A "+i+"_"+j;
                     String valB =  "B "+i+"_"+j;
                     String valC =  "C "+i+"_"+j;
                     String valD =  "D "+i+"_"+j;
                     String valE =  "E "+i+"_"+j;
                     String valF =  "F "+i+"_"+j;

                     Resource recurso = model.createResource(personURI);
                        // add the property
                     recurso.addProperty(hasAProp, valA);
                     recurso.addProperty(hasBProp, valB);
                     recurso.addProperty(hasCProp, valC);
                     recurso.addProperty(hasDProp, valD);
                     recurso.addProperty(hasEProp, valE);
                     recurso.addProperty(hasFProp, valF);                     
                     // set prefixes
                     model.setNsPrefix("prop", prefix);

                     //System.out.println(" Linea  "+j);
                } 
                 
                /////////Consulta//////////// 
                // create query string
                /*
                String queryString = "SELECT ?x "+"\n";
                queryString += "WHERE {?x ?p ?y}";
                // create ARQ query
                Query query = QueryFactory.create(queryString);
                // execute query
                QueryExecution qe = QueryExecutionFactory.create(query, model);
                // collect results
                ResultSet results = qe.execSelect();
                // print results nicely
                System.out.println(ResultSetFormatter.asText(results));
                */
                DatosTest dato = new DatosTest(); 
                
                TInicio = System.currentTimeMillis();
                try {
                    System.out.println("Escribe archivo  ");    
                    model.write( out, "RDF/XML-ABBREV" );
                }
                finally {
                    try {
                    out.close();
                    }
                    catch (IOException closeException) {
                    // ignore
                    }
                }
               
                TFin = System.currentTimeMillis(); 
                
                tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia
                dato.setCarga(carga);
                dato.setExperimento(i);
                dato.setTiempo(tiempo); 
                datos.add(dato);
                System.out.println("Experimento RDF  "+i+" Carga "+carga+" tiempo "+ tiempo); //Mostramos en pantalla el tiempo de ejecución en milisegundos
                
            }
            // writing RDF
            // write the model in XML form
            //model.write(System.out, "RDF/XML-ABBREV");
             
             
              // read the RDF/XML file
             //model.read(in, null);
    }        
    
}
