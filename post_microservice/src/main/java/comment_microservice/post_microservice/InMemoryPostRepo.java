package post_microservice;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryPostRepo implements PostRepo {
    private Map<String, Post> userMap = new HashMap<>();

    @Override
    public Post get(String id) {
        return userMap.get(id);
    }

    @Override
    public Collection<Post> all() {
        return userMap.values();
    }
}
