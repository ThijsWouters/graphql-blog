package post_microservice;

import java.util.Collection;

public interface PostRepo {
    Post get(String id);

    Collection<Post> all();
}
