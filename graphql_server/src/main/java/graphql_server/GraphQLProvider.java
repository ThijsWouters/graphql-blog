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
                        .dataFetcher("users", environment -> restTemplate
                                .exchange("http://localhost:7000/",
                                        HttpMethod.GET,
                                        null,
                                        listOfMaps)
                                .getBody())
                        .dataFetcher("user", environment -> restTemplate
                                .getForObject(format("http://localhost:7000/%s", (String) environment.getArgument("id")), Map.class)))
                .type(newTypeWiring("Mutation")
                        .dataFetcher("createUser", environment -> restTemplate
                                .exchange(post(create("http://localhost:7000"))
                                                .body((Map) ImmutableMap.of(
                                                        "name", (String) environment.getArgument("name"),
                                                        "age", (Integer) environment.getArgument("age"))
                                                ),
                                        Map.class)
                                .getBody()))
                .type(newTypeWiring("User")
                        .dataFetcher("posts", environment -> {
                            Map<String, Object> user = environment.getSource();
                            return restTemplate
                                    .exchange(
                                            String.format("http://localhost:8000/user/%s", user.get("id")),
                                            HttpMethod.GET,
                                            null,
                                            listOfMaps)
                                    .getBody();
                        }))
                .build();
    }
}
