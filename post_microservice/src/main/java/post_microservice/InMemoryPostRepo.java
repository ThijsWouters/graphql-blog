package post_microservice;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

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

    @Override
    public Collection<Post> all(String userId) {
        return all().stream()
                .filter(post -> post.getAuthorId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public void create(Post post) {
        save(post);
    }

    @Override
    public void save(Post post) {
        userMap.put(post.getId(), post);
    }

    @Override
    public void delete(String id) {
        userMap.remove(id);
    }
}
