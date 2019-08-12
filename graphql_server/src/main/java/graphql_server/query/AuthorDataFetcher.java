package graphql_server.query;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

public class AuthorDataFetcher implements DataFetcher<Map> {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map get(DataFetchingEnvironment environment) {
        Map<String, Object> post = environment.getSource();
        return restTemplate.getForObject(
                String.format("http://localhost:7000/%s", post.get("authorId")),
                Map.class);
    }
}
