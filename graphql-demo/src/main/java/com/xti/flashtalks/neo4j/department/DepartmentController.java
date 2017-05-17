package com.xti.flashtalks.neo4j.department;

import com.xti.flashtalks.neo4j.employee.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/department")
public class DepartmentController {
    private DepartmentRepository repository;

    @Autowired
    public DepartmentController(DepartmentRepository repository) {
        this.repository = repository;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Department> list() {
        return repository.load();
    }

    @RequestMapping("/{id}/employee")
    public List<Employee> listEmployeesForDepartment(@PathVariable("id") String id) {
        return repository.findEmployeesForDepartment(id);
    }

    @RequestMapping(value = "/{id}/employee", params = "knows")
    public List<Employee> listEmployeesForDepartment(@PathVariable("id") String id, @RequestParam("knows") String knows) {
        return repository.findEmployeesForDepartmentThatKnowProgrammingLanguage(id, knows);
    }
}
