import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.query.*;
import org.apache.jena.rdf.model.Model;
import org.apache.jena.rdf.model.ModelFactory;
import org.apache.jena.tdb2.TDB2Factory;
import org.apache.jena.*;
//import org.apache.jena.web.DatasetAccessorFactory;

public class EmbeddedFusekiWithTTL {

    public static void main(String[] args) {

        // -------------------------
        // 1. Create a persistent TDB2 dataset
        // -------------------------
        String tdbDirectory = "data/tdb2";   // folder created automatically
        Dataset dataset = TDB2Factory.connectDataset(tdbDirectory);

        // -------------------------
        // 2. Load your TTL file at startup
        // -------------------------
        String ttlFilePath = "esco-v1.2.0.rdf";
//        String ttlFilePath2 = "/ESCOJenaFuseki/resource/model.rdf";

        try {
            Model initModel = ModelFactory.createDefaultModel();
            initModel.read(ttlFilePath, "RDF");
//            initModel.read(ttlFilePath2,"RDF");

            dataset.getDefaultModel().add(initModel);

            System.out.println("TTL file loaded into dataset successfully.");

        } catch (Exception e) {
            System.out.println("Could not load the TTL file:");
            e.printStackTrace();
        }

        // -------------------------
        // 3. Start the embedded Fuseki server
        // -------------------------
        FusekiServer server = FusekiServer.create()
                .add("/ds", dataset)   // dataset endpoint name
                .port(3030)            // http://localhost:3030/ds
                .build();

        server.start();
        System.out.println("Fuseki server running at:");
        System.out.println("  Query:   https://localhost:3030/ds/sparql");
        System.out.println("  Update:  https://localhost:3030/ds/update");
        System.out.println("  Upload:  https://localhost:3030/ds/data");

        // -------------------------
        // 4. OPTIONAL: Upload more TTL files via Java
        // -------------------------
//        try {
//            DatasetAccessor accessor = 
//                    DatasetFactory.createHTTP("http://localhost:3030/ds");
//
//            Model m = ModelFactory.createDefaultModel();
//            m.read("/Users/you/data/more.ttl", "TTL");
//
//            accessor.add(m);   // append data
//            // accessor.putModel(m);  // overwrite
//
//            System.out.println("Additional TTL uploaded successfully.");
//
//        } catch (Exception e) {
//            System.out.println("Optional upload failed (server running fine):");
//        }
    }
}
