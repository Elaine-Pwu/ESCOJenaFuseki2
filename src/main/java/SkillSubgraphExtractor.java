import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.jena.query.*;
import org.apache.jena.rdfconnection.RDFConnectionFuseki;
import org.apache.jena.rdfconnection.RDFConnectionRemoteBuilder;
//import org.apache.jena.sparql.engine.http.QueryEngineHTTP;

public class SkillSubgraphExtractor {

    public static void main(String[] args) throws IOException {

        // ESCO SPARQL endpoint
        String endpoint = "https://ec.europa.eu/esco/sparql/esco-v1.2.0/query";

        RDFConnectionRemoteBuilder builder = RDFConnectionFuseki.create().destination(endpoint);

        // Triple extraction query (URI-to-URI only)
        String q = """
            PREFIX skos: <http://www.w3.org/2004/02/skos/core#>
            PREFIX esco: <http://data.europa.eu/esco/model#>

            SELECT ?subject ?predicate ?object
            WHERE {
                {
                    ?subject a esco:Skill .
                    ?subject ?predicate ?object .
                    FILTER(isURI(?object))
                }
                UNION
                {
                    ?object a esco:Skill .
                    ?subject ?predicate ?object .
                    FILTER(isURI(?subject))
                }
            }
        """;

        try (RDFConnectionFuseki conn = (RDFConnectionFuseki) builder.build()) {
            System.out.println("Connected to ESCO SPARQL Endpoint");

            // Execute query
            Query query = QueryFactory.create(q);

            QueryExecution qexec = conn.query(query);
            ResultSet rs = qexec.execSelect();

//            // Save as TSV

            try (FileWriter writer = new FileWriter("skill_subgraph_from_sparql.tsv")) {

                // Write header (optional)
                writer.write("subject\tpredicate\tobject\n");

                while (rs.hasNext()) {
                    QuerySolution sol = rs.nextSolution();

                    String s = sol.get("subject").toString().replace("<", "").replace(">", "");
                    String p = sol.get("predicate").toString().replace("<", "").replace(">", "");
                    String o = sol.get("object").toString().replace("<", "").replace(">", "");

                    // Write TSV row
                    writer.write(s + "\t" + p + "\t" + o + "\n");
                }
            }

            System.out.println("✓ TSV generated: skill_subgraph_from_sparql.tsv");
        }
    }
}
