/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package proyectosemantica;

import com.hp.hpl.jena.ontology.OntModel;
import com.hp.hpl.jena.ontology.OntModelSpec;
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
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path ;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class CrearOntology {
    
    String inputFileName = System.getProperty("user.dir")+"/archivos/test_ontology.owl" ;
    String inputFileOrigin = System.getProperty("user.dir")+"/archivos/original.owl" ;
    String []ciudadColombia = {"Bogotá","Medellin","Cali"};
    String [] fundadores = {"Rafael_Garcia_Herreros","Bartolomé_Lobo_Guerrero","Eleanor_France_de_Alum","Juan_Bautista_De_La_Salle"};
    //String [] directorActual = {"Diego Fernando Sanchez","Martha Isabela Franco",""};
    String [] religion = {"Católica","Cristiano-Evangelica","Ninguna"};
    String [] idiomaExtranjero = {"Ingles","Frances","Portugués","Italiano","Mandarín","Chino","Ninguno"};
    String [] direccionCalle = {"Calle","Diagonal","Carrera","Transversal","Avenida","Autopista"};
    String [] jornada = {"Mañana","Tarde","Nocturna","Completa"};
    String [] genero = {"Masculino","Femenino","Mixto","Masculino-Femenino"};
    String [] calendario = {"A","B"};
    Calendar fechaFundacion;
    // create an empty model
    ArrayList<DatosTest> datos = new ArrayList<DatosTest>();
    ArrayList<DatosTest> datosQuery = new ArrayList<DatosTest>();
    
    public  void  crear(int experimentos) throws IOException 
    {      
        String  stament = "" ; 
        String individuo = "";
        long TInicio, TFin, tiempo;
        long TInicioQuery, TFinQuery, tiempoQuery;
        
        int carga = 0;
        String prefix = "http://www.colegiosColombia/Informacion/Ontology#";
        /////////Consulta//////////// 
            // create query string
            String queryString = "PREFIX Colegio: <" + prefix + ">\n";
            queryString += "SELECT DISTINCT ?colegio ?ciudad ?direccion ?fundador ?fundado ?director ?religion ?idiomaExtr ?jornada ?genero ?calendario "+"\n";
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
            this.iniciarArchivo();
            Random aleatorio = new Random(System.currentTimeMillis());

            carga = carga + 1 ;
            for(int j=1;j <=carga; j++  ) {
                individuo ="Colegio_Nombre" + j; 
                String director = "Director" + j;
                stament += "\n";
                String direccion = direccionCalle[aleatorio.nextInt(direccionCalle.length)]+ " " + (aleatorio.nextInt(55)+1) + " N° " + (aleatorio.nextInt(55)+1) + " - " + (aleatorio.nextInt(55)+1);        
                String ciudad = ciudadColombia[aleatorio.nextInt(ciudadColombia.length)];
                String fundador = fundadores[aleatorio.nextInt(fundadores.length)];
                fechaFundacion = Calendar.getInstance();
                fechaFundacion.set (aleatorio.nextInt(117)+1900, aleatorio.nextInt(12)+1, aleatorio.nextInt(28)+1);
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MMMMM/yyyy");
                String fundacion = sdf.format(fechaFundacion.getTime()); 

                String religionProfesa = religion[aleatorio.nextInt(religion.length)];
                String claseEnIdioma = idiomaExtranjero[aleatorio.nextInt(idiomaExtranjero.length)];
                String jornadaColegio = jornada[aleatorio.nextInt(jornada.length)];
                String generoAlumnos = genero[aleatorio.nextInt(genero.length)];
                String calendarioColegio = calendario[aleatorio.nextInt(calendario.length)];
                
                stament += "\n<owl:NamedIndividual rdf:about=\"" + prefix + director + "\">";
                stament += "\n<rdf:type rdf:resource=\"" + prefix + "Director\"/>";
                stament += "\n</owl:NamedIndividual>";
                
                stament += "\n<owl:NamedIndividual rdf:about=\"" + prefix + individuo + "\">";
                stament += "\n<rdf:type rdf:resource=\"" + prefix + "Colegio\"/>";
                stament += "\n<directorEs rdf:resource=\""+ prefix + director + "\"/>";
                stament += "\n<estaEn rdf:resource=\"" + prefix + ciudad + "\"/>";
                stament += "\n<fundadoPor rdf:resource=\""+ prefix + fundador + "\"/>";
                stament += "\n<direccionEs rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">"+direccion+"</direccionEs>";
                stament += "\n<fundadoEl rdf:datatype=\"http://www.w3.org/2001/XMLSchema#dateTime\">"+fundacion+"</fundadoEl>";
                stament += "\n<generoEs rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">"+generoAlumnos+"</generoEs>";
                stament += "\n<imparteClaseEn rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">"+claseEnIdioma+"</imparteClaseEn>";
                stament += "\n<profesaReligion rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">"+religionProfesa+"</profesaReligion>";
                stament += "\n<tieneCalendario rdf:datatype=\"http://www.w3.org/2000/01/rdf-schema#Literal\">"+calendarioColegio+"</tieneCalendario>";
                stament += "\n<tieneJornada rdf:datatype=\"http://www.w3.org/2001/XMLSchema#string\">"+jornadaColegio+"</tieneJornada>";
                stament += "\n</owl:NamedIndividual>"; 
            }
            DatosTest dato = new DatosTest();
            DatosTest datoQuery = new DatosTest();
            
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
            // create ontology Model
            OntModel ontology = ModelFactory.createOntologyModel(OntModelSpec.OWL_MEM_MICRO_RULE_INF);
            //read the ontology
            // use the FileManager to find the input file
            InputStream fileOntology = FileManager.get().open(inputFileName);
            if (fileOntology == null) {
                throw new IllegalArgumentException("File: " + inputFileName + " not found");
            }
            ontology.read(fileOntology, null);
            TFin = System.currentTimeMillis();  
            
            TInicioQuery = System.currentTimeMillis(); 
            // create ARQ query
            Query query = QueryFactory.create(queryString);
            // execute query
            QueryExecution qe = QueryExecutionFactory.create(query, ontology);
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

            System.out.println("Experimento Ontologia (Carga) "+i+" Carga "+carga+" tiempo "+ tiempo); //Mostramos en pantalla el tiempo de ejecución en milisegundos (Carga)
            System.out.println("Experimento Ontologia (Query) "+i+" Carga "+carga+" tiempo "+ tiempoQuery); //Mostramos en pantalla el tiempo de ejecución en milisegundos (Query)
            if(i==experimentos)
                System.out.println(ResultSetFormatter.asText(results));
            stament= "";
                
        } 
    }
     
    public void iniciarArchivo() 
    {    
        String ruta = inputFileName;
        File archivo = new File(ruta);
        BufferedWriter bw;
        if(archivo.exists()) {
            try {
                // El fichero ya existe
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(this.cabeceraOWL ());
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(CrearOntology.class.getName()).log(Level.SEVERE, null, ex);
            }         
        } else {
        // El fichero no existe y hay que crearlo
            try {
                // El fichero ya existe
                bw = new BufferedWriter(new FileWriter(archivo));
                bw.write(this.cabeceraOWL ());
                bw.close();
            } catch (IOException ex) {
                Logger.getLogger(CrearOntology.class.getName()).log(Level.SEVERE, null, ex);
            }
        }                   
    } 
    
    public String cabeceraOWL () throws FileNotFoundException, IOException 
    {
        String cadena;
        String  xml = "";
        FileReader f = new FileReader(inputFileOrigin);
        BufferedReader b = new BufferedReader(f);
        while((cadena = b.readLine())!=null) {
            xml+= cadena+"\n";
        }
        b.close();
        return xml;
    }       
}
