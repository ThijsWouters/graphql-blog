package post_microservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@RestController
public class PostController {
    private PostRepo postRepo;

    public PostController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @PostMapping
    public ResponseEntity<Post> create(PostData data) {
        Post post = new Post(UUID.randomUUID().toString(),
                data.getAuthorId(),
                data.getTitle(),
                data.getContents());
        postRepo.create(post);
        return ResponseEntity
                .created(URI.create(String.format("http://localhost:8000/%s", post.getId())))
                .body(post);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Post> update(PostData data, @PathVariable String id) {
        Post post = postRepo.get(id);
        if(post == null){
            return ResponseEntity.notFound().build();
        } else {
            post.setAuthorId(data.getAuthorId());
            post.setTitle(data.getTitle());
            post.setContents(data.getContents());
            postRepo.save(post);
            return ResponseEntity.ok(post);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        Post user = postRepo.get(id);
        if (user == null) {
            return ResponseEntity.notFound()
                    .build();
        } else {
            postRepo.delete(id);
            return ResponseEntity.ok()
                    .build();
        }
    }

    @GetMapping("/")
    public Collection<Post> getAll() {
        return postRepo.all();
    }

    @GetMapping("/user/{userId}")
    public Collection<Post> getAllForUser(@PathVariable String userId) {
        return postRepo.all(userId);
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) {
        return postRepo.get(id);
    }
}
