package comment_microservice;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class InMemoryCommentRepo implements CommentRepo {
    private Map<String, Comment> commentMap = new HashMap<>();

    @Override
    public Comment get(String id) {
        return commentMap.get(id);
    }

    @Override
    public Collection<Comment> all() {
        return commentMap.values();
    }

    @Override
    public Collection<Comment> all(Predicate<Comment> filter) {
        return all().stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    @Override
    public void create(Comment comment) {
        save(comment);
    }

    @Override
    public void save(Comment comment) {
        commentMap.put(comment.getId(), comment);
    }

    @Override
    public void delete(String id) {
        commentMap.remove(id);
    }
}
