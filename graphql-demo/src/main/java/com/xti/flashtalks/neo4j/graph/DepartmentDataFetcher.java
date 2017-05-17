package com.xti.flashtalks.neo4j.graph;

import com.xti.flashtalks.neo4j.department.DepartmentRepository;
import com.xti.flashtalks.neo4j.employee.Employee;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DepartmentDataFetcher implements DataFetcher {
    private DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentDataFetcher(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    @Override
    public Object get(DataFetchingEnvironment dataFetchingEnvironment) {
        Object source = dataFetchingEnvironment.getSource();
        String id = dataFetchingEnvironment.getArgument("id");

        if (source instanceof Employee) {
            Employee employee = (Employee) source;
            return departmentRepository.findByEmployee(employee.getId());
        } else {
            if (id == null) {
                return departmentRepository.load();
            } else {
                return departmentRepository.get(id);
            }
        }
    }
}
