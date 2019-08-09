package comment_microservice;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
}
