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
import com.hp.hpl.jena.query.QuerySolution;
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
import com.hp.hpl.jena.shared.ReificationStyle;
import com.hp.hpl.jena.util.FileManager;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Calendar;
import java.util.Random;
import java.text.SimpleDateFormat;
import java.util.Iterator;
/**
 *
 * @author Lenovo
 */
public class CreaRDF {
   
    String inputFileName = System.getProperty("user.dir")+"/archivos/test_file.rdf" ;
    String FileNameDataExp = System.getProperty("user.dir")+"/archivos/data_experiment.rdf" ;
    // create an empty model
    ArrayList<DatosTest> datos = new ArrayList<DatosTest>();
    ArrayList<DatosTest> datosQuery = new ArrayList<DatosTest>();
    public  void  crear(int experimentos) throws IOException 
    {      
            String colegioURI = "http://www.colegiosColombia.com/Informacion#Colegio_Nombre";
            String estaEn = "estaEn";
            String direccionEs = "direccionEs"; 
            String fundadoPor = "fundadoPor"; 
            String fundadoEl = "fundadoEl"; 
            String directorEs = "directorEs";
            String profesaReligion = "profesaReligion";
            String imparteClaseEn = "imparteClaseEn";
            String tieneJornada = "tieneJornada";
            String generoEs = "generoEs";
            String tieneCalendario = "tieneCalendario";
            String prefix = "http://www.colegiosColombia.com/Informacion#";
            
            long TInicio, TFin, tiempo;
            long TInicioQuery, TFinQuery, tiempoQuery;
            int carga = 0;
            // use the FileManager to find the input file
            String []ciudadColombia = {"Bogota,DC","Chia,Cundinamarca","Medellin,Antioquia","Cali,Valle_del_Cauca","Popayan,Cauca",
                "Pasto,Nariño","Bucaramanga,Santander","Barranquilla,Atlantico","Cartagena,Bolivar","Santa_Marta,Magdalena"};
            String [] fundadores = {"Rafael_Garcia_Herreros","Bartolomé_Lobo_Guerrero","Eleanor_France_de_Alum","Juan_Bautista_De_La_Salle"};
            //String [] directorActual = {"Diego Fernando Sanchez","Martha Isabela Franco",""};
            String [] religion = {"Católica","Cristiano-Evanngelico","Ninguna"};
            String [] idiomaExtranjero = {"Ingles","Frances","Portugués","Italiano","Mandarín","Chino","Ninguno"};
            String [] direccionCalle = {"Calle","Diagonal","Carrera","Transversal","Avenida","Autopista"};
            String [] jornada = {"Mañana","Tarde","Nocturna","Completa"};
            String [] genero = {"Masculino","Femenino","Mixto","Masculino-Femenino"};
            String [] calendario = {"A","B"};
            Calendar fechaFundacion;
            Random aleatorio = new Random(System.currentTimeMillis());
            
            /////////Consulta//////////// 
            // create query string
            String queryString = "PREFIX Colegio: <"+ prefix + ">\n";
            queryString += "SELECT ?colegio ?ciudad ?direccion ?fundador ?fundado ?director ?religion ?idiomaExtr ?jornada ?genero ?calendario "+"\n";
            queryString += "WHERE {?colegio Colegio:estaEn ?ciudad. \n"
                                + "?colegio Colegio:direccionEs ?direccion. \n"
                                + "?colegio Colegio:fundadoPor ?fundador. \n"
                                + "?colegio Colegio:fundadoEl ?fundado. \n"
                                + "?colegio Colegio:directorEs ?director. \n"
                                + "?colegio Colegio:profesaReligion ?religion. \n"
                                + "?colegio Colegio:imparteClaseEn ?idiomaExtr. \n"
                                + "?colegio Colegio:tieneJornada ?jornada. \n"
                                + "?colegio Colegio:generoEs ?genero. \n"
                                + "?colegio Colegio:tieneCalendario ?calendario. \n"
                                + "}";
            
            for(int i=1 ; i<=experimentos ;i ++)
            {    
                Model model = ModelFactory.createDefaultModel();
                //create a property
                Property estaEnProp = model.createProperty(prefix+estaEn);
                Property direccionEsProp = model.createProperty(prefix+direccionEs);
                Property fundadoPorProp = model.createProperty(prefix+fundadoPor);
                Property fundadoElProp = model.createProperty(prefix+fundadoEl);
                Property directorEsProp = model.createProperty(prefix+directorEs);
                Property profesaReligionProp = model.createProperty(prefix+profesaReligion);
                Property imparteClaseEnProp = model.createProperty(prefix+imparteClaseEn);
                Property tieneJornadaProp = model.createProperty(prefix+tieneJornada);
                Property generoEsProp = model.createProperty(prefix+generoEs);
                Property tieneCalendarioProp = model.createProperty(prefix+tieneCalendario);
                
                carga = carga + 1 ;
                for(int j=1; j<=carga; j++) {
                    
                    colegioURI = "http://www.colegiosColombia.com/Informacion#Colegio_Nombre" + j;
                    String ciudad = ciudadColombia[aleatorio.nextInt(ciudadColombia.length)];
                    Resource ciudadURI = model.createResource("http://www.ciudadesColombia.com/Informacion#"+ciudad);
                    String direccion = direccionCalle[aleatorio.nextInt(direccionCalle.length)]+ " " + (aleatorio.nextInt(55)+1) + " N° " + (aleatorio.nextInt(55)+1) + " - " + (aleatorio.nextInt(55)+1);
                    String fundador = fundadores[aleatorio.nextInt(fundadores.length)];
                    Resource fundadorURI = model.createResource("http://www.colegiosColombia.com/Fundador#"+fundador);
                    
                    fechaFundacion = Calendar.getInstance();
                    fechaFundacion.set (aleatorio.nextInt(117)+1900, aleatorio.nextInt(12)+1, aleatorio.nextInt(28)+1);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy");
                    String fundacion = sdf.format(fechaFundacion.getTime());
                    
                    String director = "Nombre"+ j;
                    Resource directorURI = model.createResource("http://www.colegiosColombia.com/Director#"+director);
                    String religionProfesa = religion[aleatorio.nextInt(religion.length)];
                    String claseEnIdioma = idiomaExtranjero[aleatorio.nextInt(idiomaExtranjero.length)];
                    String jornadaColegio = jornada[aleatorio.nextInt(jornada.length)];
                    String generoAlumnos = genero[aleatorio.nextInt(genero.length)];
                    String calendarioColegio = calendario[aleatorio.nextInt(calendario.length)];
                    
                    Resource colegio = model.createResource(colegioURI);
                       // add the property
                    model.add(colegio, estaEnProp, ciudadURI);
                    colegio.addProperty(direccionEsProp, direccion);
                    model.add(colegio, fundadoPorProp, fundadorURI);
                    colegio.addProperty(fundadoElProp, fundacion);
                    model.add(colegio, directorEsProp, directorURI);
                    colegio.addProperty(profesaReligionProp, religionProfesa);
                    colegio.addProperty(imparteClaseEnProp, claseEnIdioma); 
                    colegio.addProperty(tieneJornadaProp, jornadaColegio);
                    colegio.addProperty(generoEsProp, generoAlumnos);
                    colegio.addProperty(tieneCalendarioProp, calendarioColegio);
                    // set prefixes
                    model.setNsPrefix("Colegio", prefix);

                     //System.out.println(" Linea  "+j);
                } 
                               
                DatosTest dato = new DatosTest();
                DatosTest datoQuery = new DatosTest();
                
                InputStream in = FileManager.get().open( inputFileName );
                if (in == null) {
                throw new IllegalArgumentException(
                "File: " + inputFileName + " not found");
                }
                FileWriter out = new FileWriter(inputFileName);
                
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
                // create an empty model
                Model modeloQuery = ModelFactory.createDefaultModel();
                // read the RDF/XML file
                modeloQuery.read(in, null);
                TFin = System.currentTimeMillis();  
                TInicioQuery = System.currentTimeMillis();
                // create ARQ query
                Query query = QueryFactory.create(queryString);
                // execute query
                QueryExecution qe = QueryExecutionFactory.create(query, modeloQuery);
                // collect results
                ResultSet results = qe.execSelect();
                // print results nicely                     
                TFinQuery = System.currentTimeMillis();
                
                tiempo = TFin - TInicio; //Calculamos los milisegundos de diferencia (Carga)
                tiempoQuery = TFinQuery - TInicioQuery;  //Calculamos los milisegundos de diferencia (Query)
                
                dato.setCarga(carga);
                dato.setExperimento(i);
                dato.setTiempo(tiempo); 
                datos.add(dato);
                
                datoQuery.setCarga(carga);
                datoQuery.setExperimento(i);
                datoQuery.setTiempo(tiempoQuery);         
                datosQuery.add(datoQuery);
                
                System.out.println("Experimento RDF(Carga) "+i+" Carga "+carga+" tiempo "+ tiempo); //Mostramos en pantalla el tiempo de ejecución en milisegundos
                System.out.println("Experimento RDF(Query) "+i+" Carga "+carga+" tiempo "+ tiempoQuery); //Mostramos en pantalla el tiempo de ejecución en milisegundos
                if(i==experimentos)
                    System.out.println(ResultSetFormatter.asText(results));
            }
            // writing RDF
            // write the model in XML form
            //model.write(System.out, "RDF/XML-ABBREV");
             
             
              // read the RDF/XML file
             //model.read(in, null);
    }        
    
}
