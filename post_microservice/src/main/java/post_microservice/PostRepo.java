package post_microservice;

import java.util.Collection;

public interface PostRepo {
    Post get(String id);

    Collection<Post> all();

    Collection<Post> all(String userId);

    void create(Post post);

    void save(Post post);

    void delete(String id);
}
