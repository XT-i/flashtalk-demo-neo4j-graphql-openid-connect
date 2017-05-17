package com.xti.flashtalks.neo4j.graph;

import graphql.Scalars;
import graphql.schema.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static graphql.schema.GraphQLArgument.newArgument;
import static graphql.schema.GraphQLFieldDefinition.newFieldDefinition;
import static graphql.schema.GraphQLObjectType.newObject;

@Component
public class SchemaFactory {
    private DepartmentDataFetcher departmentDataFetcher;
    private EmployeeDataFetcher employeeDataFetcher;

    @Autowired
    public SchemaFactory(DepartmentDataFetcher departmentDataFetcher, EmployeeDataFetcher employeeDataFetcher) {
        this.departmentDataFetcher = departmentDataFetcher;
        this.employeeDataFetcher = employeeDataFetcher;
    }

    public GraphQLSchema createSchema() {
        return GraphQLSchema.newSchema()
                .query(rootType())
                .build();
    }

    private GraphQLObjectType rootType() {
        return newObject()
                .name("Root")
                .field(
                        newFieldDefinition()
                                .name("departments")
                                .type(
                                        new GraphQLList(departmentType())
                                )
                                .dataFetcher(departmentDataFetcher)
                                .build()
                )
                .field(
                        newFieldDefinition()
                                .name("department")
                                .type(departmentType())
                                .argument(
                                        newArgument()
                                                .name("id")
                                                .type(new GraphQLNonNull(Scalars.GraphQLString))
                                                .build()
                                )
                                .dataFetcher(departmentDataFetcher)
                )
                .field(
                        newFieldDefinition()
                                .name("employees")
                                .type(
                                        new GraphQLList(employeeType())
                                )
                                .dataFetcher(employeeDataFetcher)
                                .build()
                )
                .field(
                        newFieldDefinition()
                                .name("employee")
                                .type(employeeType())
                                .argument(
                                        newArgument()
                                                .name("id")
                                                .type(new GraphQLNonNull(Scalars.GraphQLString))
                                                .build()
                                )
                                .dataFetcher(employeeDataFetcher)
                )
                .build();
    }

    private GraphQLObjectType departmentType() {
        return newObject()
                .name("Department")
                .field(
                        newFieldDefinition()
                                .name("id")
                                .type(Scalars.GraphQLString)
                                .build()
                )
                .field(
                        newFieldDefinition()
                                .name("name")
                                .type(Scalars.GraphQLString)
                                .build()
                )
                .field(
                        newFieldDefinition()
                                .name("employees")
                                .type(new GraphQLList(
                                        new GraphQLTypeReference("Employee")
                                ))
                                .dataFetcher(employeeDataFetcher)
                                .build()
                )
                .build();
    }

    private GraphQLObjectType employeeType() {
        return newObject()
                .name("Employee")
                .field(
                        newFieldDefinition()
                                .name("id")
                                .type(Scalars.GraphQLString)
                                .build()
                )
                .field(
                        newFieldDefinition()
                                .name("name")
                                .type(Scalars.GraphQLString)
                                .build()
                )
                .field(
                        newFieldDefinition()
                                .name("department")
                                .type(new GraphQLTypeReference("Department"))
                                .dataFetcher(departmentDataFetcher)
                                .build()
                )
                .build();
    }
}
