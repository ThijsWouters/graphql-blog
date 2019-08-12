package graphql_server.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

public class UserPostsDataFetcher implements DataFetcher<List<Map>> {
    private RestTemplate restTemplate = new RestTemplate();
    private ParameterizedTypeReference<List<Map>> listOfMaps = new ParameterizedTypeReference<List<Map>>() {
    };

    @Override
    public List<Map> get(DataFetchingEnvironment environment) {
        Map<String, Object> user = environment.getSource();
        return restTemplate
                .exchange(
                        String.format("http://localhost:8000/user/%s", user.get("id")),
                        HttpMethod.GET,
                        null,
                        listOfMaps)
                .getBody();
    }
}
