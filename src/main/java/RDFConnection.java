import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryExecution;
import org.apache.jena.query.QueryExecutionFactory;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.query.QuerySolution;
import org.apache.jena.query.ResultSet;
import org.apache.jena.query.ResultSetFormatter;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;

/* 
 * Example of a building a remote connection to a Fuseki server.
 */
public class RDFConnection{
    public static void main(String[]args) throws IOException {
        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination("https://ec.europa.eu/esco/sparql/esco-v1.2.0/query");
        	String q = "SELECT (COUNT(*) AS ?triples) WHERE { ?s ?p ?o }";
        	String p = "PREFIX skos: <http://www.w3.org/2004/02/skos/core#>\n"
        			+ "    PREFIX esco: <http://data.europa.eu/esco/model#>\n"
        			+ "    SELECT ?skill ?label \n"
        			+ "    WHERE {\n"
        			+ "        ?skill a esco:Skill; skos:prefLabel ?label.\n"
        			+ "    FILTER(lang(?label) = \"en\")\n"
        			+ "    }";
        	

        try ( RDFConnectionFuseki conn = (RDFConnectionFuseki)builder.build() ) { 
        	System.out.println("Fuseki Connected");
        	conn.queryResultSet(q,ResultSetFormatter::out);
            conn.queryResultSet(p,ResultSetFormatter::out);
            ResultSet rs = conn.query(p).execSelect();
            ResultSet num = conn.query(q).execSelect();

            // Export to CSV
            FileOutputStream out = new FileOutputStream("skill_df.csv");
            ResultSetFormatter.outputAsCSV(out, rs);
            out.close();

            System.out.println("CSV generated: skill_df.csv");
           

        }
    }
}