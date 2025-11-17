# ESCOJenaFuseki

This is a ready-to-run Maven project to start an **embedded Apache Jena Fuseki server** and load a TTL file at startup.

## How to use

### Prerequisites
- Java 15 or newer
- Maven
- Eclipse
- VSCode 

### Import into Eclipse (Maven)
1. File → Import → Existing Maven Projects → select the folder.
2. Wait for Maven to download dependencies.

### Run
#### Use Eclipse to run
1. RDFConnection.java: To get `df_skill.csv`
2. SkillSubgraphExtractor.java: To get `skill_subgraph_from_sparql.tsv`(must get it first)
#### Use VScode to run
4. extract_subgraph_v2.ipynb: Use `skill_subgraph_from_sparql.tsv` to run the forward analysis
5. graph_embed_skills_v2.ipynb: Use `skill_subgraph_from_sparql.tsv` to get metadata
6. Clustering.ipynb: Use `df_skill.csv` to do cluster evaluation
7. Clustering_Analysis.ipynb: BERT-based cluster
