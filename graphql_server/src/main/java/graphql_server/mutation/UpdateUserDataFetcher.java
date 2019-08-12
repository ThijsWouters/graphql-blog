package graphql_server.mutation;

import com.google.common.collect.ImmutableMap;
import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

import static java.net.URI.create;
import static org.springframework.http.RequestEntity.put;

public class UpdateUserDataFetcher implements DataFetcher<Map> {
    private RestTemplate restTemplate = new RestTemplate();

    @Override
    public Map get(DataFetchingEnvironment environment) {
        return restTemplate.exchange(put(create(
                String.format("http://localhost:7000/%s", (String) environment.getArgument("id"))))
                        .body((Map) ImmutableMap.of(
                                "name", (String) environment.getArgument("name"),
                                "age", (Integer) environment.getArgument("age"))
                        ),
                Map.class)
                .getBody();
    }
}
