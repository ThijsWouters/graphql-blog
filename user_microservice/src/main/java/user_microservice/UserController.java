package user_microservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
public class UserController {
    private UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping("/")
    public Collection<User> getAll() {
        return userRepo.all();
    }

    @GetMapping("/{id}")
    public User getUser(@PathVariable String id) {
        return userRepo.get(id);
    }
}
