package com.xti.flashtalks.neo4j.programminglanguage;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProgrammingLanguageRepository {
    private Driver driver;

    @Autowired
    public ProgrammingLanguageRepository(Driver driver) {
        this.driver = driver;
    }

    public List<ProgrammingLanguage> load() {
        List<ProgrammingLanguage> programmingLanguages = new ArrayList<>();

        Session session = driver.session();

        StatementResult result = session.run("MATCH (l:ProgrammingLanguage) RETURN l");
        List<Record> records = result.list();

        for (Record record : records) {
            String id = record.get("l").asNode().get("id").asString();
            String name = record.get("l").asNode().get("name").asString();

            programmingLanguages.add(new ProgrammingLanguage(id, name));
        }

        session.close();

        return programmingLanguages;
    }
}
