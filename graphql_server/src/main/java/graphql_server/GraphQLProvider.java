package graphql_server;

import com.google.common.base.Charsets;
import com.google.common.collect.ImmutableMap;
import com.google.common.io.Resources;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import graphql_server.mutation.CreateUserDataFetcher;
import graphql_server.query.AuthorDataFetcher;
import graphql_server.query.UserDataFetcher;
import graphql_server.query.UserPostsDataFetcher;
import graphql_server.query.UsersDataFetcher;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import static graphql.schema.idl.TypeRuntimeWiring.newTypeWiring;
import static java.lang.String.format;
import static java.net.URI.create;
import static org.springframework.http.RequestEntity.post;

@Component
public class GraphQLProvider {
    private GraphQL graphQL;

    @Bean
    public GraphQL graphQL() {
        return graphQL;
    }

    @PostConstruct
    public void init() throws IOException {
        URL url = Resources.getResource("schema.graphqls");
        String sdl = Resources.toString(url, Charsets.UTF_8);
        GraphQLSchema graphQLSchema = buildSchema(sdl);
        this.graphQL = GraphQL.newGraphQL(graphQLSchema).build();
    }

    private GraphQLSchema buildSchema(String sdl) {
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(sdl);
        RuntimeWiring wiring = buildWiring();
        return new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
    }

    private RuntimeWiring buildWiring() {
        RestTemplate restTemplate = new RestTemplate();
        ParameterizedTypeReference<List<Map>> listOfMaps = new ParameterizedTypeReference<List<Map>>() {
        };

        return RuntimeWiring.newRuntimeWiring()
                .type(newTypeWiring("Query")
                        .dataFetcher("users", new UsersDataFetcher())
                        .dataFetcher("user", new UserDataFetcher()))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createUser", new CreateUserDataFetcher()))
                .type(newTypeWiring("User")
                        .dataFetcher("posts", new UserPostsDataFetcher()))
                .type(newTypeWiring("Post")
                .dataFetcher("author", new AuthorDataFetcher()))
                .build();
    }
}
