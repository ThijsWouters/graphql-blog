package post_microservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class PostController {
    private PostRepo postRepo;

    public PostController(PostRepo postRepo) {
        this.postRepo = postRepo;
    }

    @GetMapping("/")
    public Collection<Post> getAll() {
        return postRepo.all();
    }

    @GetMapping("/{id}")
    public Post getPost(@PathVariable String id) {
        return postRepo.get(id);
    }
}
