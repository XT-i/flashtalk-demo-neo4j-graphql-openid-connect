package com.xti.flashtalks.neo4j;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.neo4j.driver.v1.Session;

public class GenerateData {
    public static void main(String[] args) {
        Driver driver = GraphDatabase.driver("bolt://localhost:7687", AuthTokens.basic("neo4j", "demo"));
        Session session = driver.session();

        //Clear the database
        session.run("MATCH (n) DETACH DELETE n");

        //Create departments
        session.run("CREATE (d:Department {id: 'xti', name: 'XT-i'});");
        session.run("CREATE (d:Department {id: 'ida', name: 'IDA MediaFoundry'});");

        //Create programming languages
        session.run("CREATE (l:ProgrammingLanguage {id: 'java', name: 'Java'})");
        session.run("CREATE (l:ProgrammingLanguage {id: 'swift', name: 'Swift'})");
        session.run("CREATE (l:ProgrammingLanguage {id: 'groovy', name: 'Groovy'})");
        session.run("CREATE (l:ProgrammingLanguage {id: 'kotlin', name: 'Kotlin'})");
        session.run("CREATE (l:ProgrammingLanguage {id: 'javascript', name: 'JavaScript'})");

        //Create employees
        session.run("CREATE (e:Employee {id: 'eric', name: 'Eric'})");
        session.run("CREATE (e:Employee {id: 'jan', name: 'Jan'})");
        session.run("CREATE (e:Employee {id: 'katrien', name: 'Katrien'})");
        session.run("CREATE (e:Employee {id: 'tinne', name: 'Tinne'})");

        //Create relationships between employee and department
        session.run(
                "MATCH (e:Employee {id: 'eric'}), (d:Department {id: 'xti'}) " +
                        "CREATE (e)-[:WORKS_AT]->(d)"
        );
        session.run(
                "MATCH (e:Employee {id: 'jan'}), (d:Department {id: 'ida'}) " +
                        "CREATE (e)-[:WORKS_AT]->(d)"
        );
        session.run(
                "MATCH (e:Employee {id: 'katrien'}), (d:Department {id: 'ida'}) " +
                        "CREATE (e)-[:WORKS_AT]->(d)"
        );
        session.run(
                "MATCH (e:Employee {id: 'tinne'}), (d:Department {id: 'xti'}) " +
                        "CREATE (e)-[:WORKS_AT]->(d)"
        );

        //Create relationship between employee and programming languages
        session.run(
                "MATCH (e:Employee {id: 'eric'}), (l:ProgrammingLanguage {id: 'java'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'eric'}), (l:ProgrammingLanguage {id: 'javascript'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'eric'}), (l:ProgrammingLanguage {id: 'groovy'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'jan'}), (l:ProgrammingLanguage {id: 'swift'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'jan'}), (l:ProgrammingLanguage {id: 'javascript'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'tinne'}), (l:ProgrammingLanguage {id: 'swift'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'tinne'}), (l:ProgrammingLanguage {id: 'javascript'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'tinne'}), (l:ProgrammingLanguage {id: 'kotlin'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'katrien'}), (l:ProgrammingLanguage {id: 'groovy'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );
        session.run(
                "MATCH (e:Employee {id: 'katrien'}), (l:ProgrammingLanguage {id: 'java'}) " +
                        "CREATE (e)-[:KNOWS]->(l)"
        );

        session.close();
        driver.close();
    }
}
