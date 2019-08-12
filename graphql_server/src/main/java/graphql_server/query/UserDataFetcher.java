package graphql_server.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.lang.String.format;

public class UserDataFetcher implements DataFetcher<Map> {
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map get(DataFetchingEnvironment environment) {
        return restTemplate.getForObject(
                format("http://localhost:7000/%s",
                        (String) environment.getArgument("id")), Map.class);
    }
}
