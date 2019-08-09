package comment_microservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class CommentController {
    private CommentRepo commentRepo;

    public CommentController(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @GetMapping("/")
    public Collection<Comment> getAll() {
        return commentRepo.all();
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable String id) {
        return commentRepo.get(id);
    }
}
