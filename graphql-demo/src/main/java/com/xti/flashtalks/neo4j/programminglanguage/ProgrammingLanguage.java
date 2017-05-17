package com.xti.flashtalks.neo4j.programminglanguage;

public class ProgrammingLanguage {
    private String id;
    private String name;

    public ProgrammingLanguage(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
