package comment_microservice;

import java.util.Collection;

public interface CommentRepo {
    Comment get(String id);

    Collection<Comment> all();
}
