package com.xti.flashtalks.neo4j.graph;

import com.xti.flashtalks.neo4j.department.Department;
import com.xti.flashtalks.neo4j.department.DepartmentRepository;
import com.xti.flashtalks.neo4j.employee.EmployeeRepository;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataFetcher implements DataFetcher {
    private EmployeeRepository employeeRepository;
    private DepartmentRepository departmentRepository;

    @Autowired
    public EmployeeDataFetcher(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
        Object source = dataFetchingEnvironment.getSource();

        if (source instanceof Department) {
            Department department = (Department) source;
            return departmentRepository.findEmployeesForDepartment(department.getId());
        } else {
            return employeeRepository.load();
        }
    }
}
