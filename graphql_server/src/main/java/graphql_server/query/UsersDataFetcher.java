package graphql_server.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class UsersDataFetcher implements DataFetcher<List<Map>> {
    private final RestTemplate restTemplate = new RestTemplate();
    private ParameterizedTypeReference<List<Map>> listOfMaps = new ParameterizedTypeReference<List<Map>>() {
    };

    @Override
    public List<Map> get(DataFetchingEnvironment environment) {
        return restTemplate
                .exchange("http://localhost:7000/",
                        HttpMethod.GET,
                        null,
                        listOfMaps)
                .getBody();
    }
}
