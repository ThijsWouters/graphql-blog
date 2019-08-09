package comment_microservice;

import java.util.Collection;
import java.util.function.Predicate;

public interface CommentRepo {
    Comment get(String id);

    Collection<Comment> all();

    Collection<Comment> all(Predicate<Comment> filter);

    void create(Comment comment);

    void save(Comment comment);

    void delete(String id);
}
