package com.xti.flashtalks.neo4j.department;

import com.xti.flashtalks.neo4j.employee.Employee;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.Record;
import org.neo4j.driver.v1.Session;
import org.neo4j.driver.v1.StatementResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

import static org.neo4j.driver.v1.Values.parameters;

@Repository
public class DepartmentRepository {
    private Driver driver;

    @Autowired
    public DepartmentRepository(Driver driver) {
        this.driver = driver;
    }

    public List<Department> load() {
        List<Department> departments = new ArrayList<>();

        Session session = driver.session();

        StatementResult result = session.run("MATCH (d:Department) RETURN d");
        List<Record> records = result.list();

        for (Record record : records) {
            String id = record.get("d").asNode().get("id").asString();
            String name = record.get("d").asNode().get("name").asString();

            departments.add(new Department(id, name));
        }

        session.close();

        return departments;
    }

    public List<Employee> findEmployeesForDepartment(String departmentId) {
        List<Employee> employees = new ArrayList<>();

        Session session = driver.session();

        StatementResult result = session.run(
                "MATCH (d:Department {id: {id}})<-[:WORKS_AT]-(e:Employee) RETURN e",
                parameters("id", departmentId)
        );
        List<Record> records = result.list();

        for (Record record : records) {
            String id = record.get("e").asNode().get("id").asString();
            String name = record.get("e").asNode().get("name").asString();

            employees.add(new Employee(id, name));
        }

        session.close();

        return employees;
    }

    public List<Employee> findEmployeesForDepartmentThatKnowProgrammingLanguage(String departmentId, String languageId) {
        List<Employee> employees = new ArrayList<>();

        Session session = driver.session();

        StatementResult result = session.run(
                "MATCH (d:Department {id: {departmentId}})" +
                        "<-[:WORKS_AT]-" +
                        "(e:Employee)" +
                        "-[:KNOWS]->" +
                        "(l:ProgrammingLanguage {id: {languageId}}) " +
                        "RETURN e",
                parameters("departmentId", departmentId, "languageId", languageId)
        );
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
