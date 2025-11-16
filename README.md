# ESCOJenaFuseki

This is a ready-to-run Maven project to start an **embedded Apache Jena Fuseki server** and load a TTL file at startup.

## How to use

### Prerequisites
- Java 21 or newer
- Maven
- Eclipse (optional)

### Import into Eclipse (Maven)
1. File → Import → Existing Maven Projects → select the `ESCOJenaFuseki` folder.
2. Wait for Maven to download dependencies.

### Run
You can run the `EmbeddedFusekiWithTTL` class from Eclipse or with Maven:

- From Eclipse: run `EmbeddedFusekiWithTTL.main`
- From command line:
  ```bash
  mvn clean package
  java -jar target/ESCOJenaFuseki-1.0-SNAPSHOT-jar-with-dependencies.jar
  ```

### TTL file
Default TTL file path used by the starter class:
`src/main/resources/data/sample.ttl`

You can replace that file with your TTL file (keep the same path), or run the program with a command-line argument pointing to your TTL:
```bash
java -jar ... path/to/yourfile.ttl
```

### Default endpoints
- Query: `http://localhost:3030/ds/sparql`
- Update: `http://localhost:3030/ds/update`
- Upload: `http://localhost:3030/ds/data`
- UI: `http://localhost:3030/#/dataset/ds/query`

If you get a `tdb.lock` error, make sure no other process is using the `data/tdb2` folder, or change `tdbDirectory` variable.

