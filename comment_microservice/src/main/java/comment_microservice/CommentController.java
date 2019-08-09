package comment_microservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@RestController
public class CommentController {
    private CommentRepo commentRepo;

    public CommentController(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    @PostMapping
    public ResponseEntity<Comment> create(@RequestBody CommentData data) {
        Comment comment = new Comment(UUID.randomUUID().toString(),
                data.getPostId(),
                data.getAuthorId(),
                data.getContents());
        commentRepo.create(comment);
        return ResponseEntity
                .created(URI.create(String.format("http://localhost:9000/%s", comment.getId())))
                .body(comment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Comment> update(@RequestBody CommentData data, @PathVariable String id) {
        Comment comment = commentRepo.get(id);
        if(comment == null){
            return ResponseEntity.notFound().build();
        } else {
            comment.setPostId(data.getPostId());
            comment.setAuthorId(data.getAuthorId());
            comment.setContents(data.getContents());
            commentRepo.save(comment);
            return ResponseEntity.ok(comment);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Comment comment = commentRepo.get(id);
        if (comment == null) {
            return ResponseEntity.notFound()
                    .build();
        } else {
            commentRepo.delete(id);
            return ResponseEntity.ok()
                    .build();
        }
    }

    @GetMapping("/")
    public Collection<Comment> getAll() {
        return commentRepo.all();
    }

    @GetMapping("/user/{userId}")
    public Collection<Comment> getAllForUser(@PathVariable String userId) {
        return commentRepo.all(comment -> comment.getAuthorId().equals(userId));
    }

    @GetMapping("/post/{postId}")
    public Collection<Comment> getAllForPost(@PathVariable String postId) {
        return commentRepo.all(comment -> comment.getPostId().equals(postId));
    }

    @GetMapping("/{id}")
    public Comment getComment(@PathVariable String id) {
        return commentRepo.get(id);
    }
}
