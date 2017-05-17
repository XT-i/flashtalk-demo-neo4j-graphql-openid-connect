package com.xti.flashtalks.neo4j.programminglanguage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/programming-language")
public class ProgrammingLanguageController {
    private ProgrammingLanguageRepository repository;

    @Autowired
    public ProgrammingLanguageController(ProgrammingLanguageRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<ProgrammingLanguage> list() {
        return repository.load();
    }
}
