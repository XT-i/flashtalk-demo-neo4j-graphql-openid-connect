package com.xti.flashtalks.neo4j.employee;

import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class EmployeeRepository {
    private Driver driver;

    @Autowired
    public EmployeeRepository(Driver driver) {
        this.driver = driver;
    }

    public List<Employee> load() {
        List<Employee> employees = new ArrayList<>();

        Session session = driver.session();

        StatementResult result = session.run("MATCH (e:Employee) RETURN e");
        List<Record> records = result.list();

        for (Record record : records) {
            String id = record.get("e").asNode().get("id").asString();
            String name = record.get("e").asNode().get("name").asString();

            employees.add(new Employee(id, name));
        }

        session.close();

        return employees;
    }
}
