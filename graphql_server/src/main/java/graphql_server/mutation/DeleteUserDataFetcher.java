package graphql_server.mutation;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.web.client.RestTemplate;

public class DeleteUserDataFetcher implements DataFetcher<String> {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public String get(DataFetchingEnvironment environment) {
        String id = environment.getArgument("id");
        restTemplate.delete(String.format("http://localhost:7000/%s", id));
        return id;
    }
}
