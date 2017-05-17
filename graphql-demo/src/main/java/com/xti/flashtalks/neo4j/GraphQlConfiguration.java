package com.xti.flashtalks.neo4j;

import com.xti.flashtalks.neo4j.graph.SchemaFactory;
import graphql.GraphQLError;
import graphql.execution.ExecutionStrategy;
import graphql.execution.SimpleExecutionStrategy;
import graphql.schema.GraphQLSchema;
import graphql.servlet.GraphQLContext;
import graphql.servlet.GraphQLOperationListener;
import graphql.servlet.SimpleGraphQLServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Configuration
public class GraphQlConfiguration {
    @Autowired
    private SchemaFactory schemaFactory;

    @Bean
    public GraphQLSchema graphQLSchema() {
        return schemaFactory.createSchema();
    }

    @Bean
    public ExecutionStrategy executionStrategy() {
        return new SimpleExecutionStrategy();
    }

    @Bean
    public List<GraphQLOperationListener> operationListeners() {
        return Collections.singletonList(new GraphQLOperationListener() {
            @Override
            public void beforeGraphQLOperation(GraphQLContext context, String operationName, String query, Map<String, Object> variables) {
                System.out.println("Executing " + operationName);
            }

            @Override
            public void onSuccessfulGraphQLOperation(GraphQLContext context, String operationName, String query, Map<String, Object> variables, Object data) {
                System.out.println("Execution of " + operationName + " completed successfully");
            }

            @Override
            public void onFailedGraphQLOperation(GraphQLContext context, String operationName, String query, Map<String, Object> variables, Object data, List<GraphQLError> errors) {
                System.out.println("Execution of " + operationName + " failed");
            }
        });
    }

    @Bean
    public ServletRegistrationBean graphQLServletRegistrationBean(GraphQLSchema schema, ExecutionStrategy executionStrategy) {
        return new ServletRegistrationBean(new SimpleGraphQLServlet(schema, executionStrategy, operationListeners(), new ArrayList<>()), "/graphql");
    }
}
