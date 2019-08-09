package user_microservice;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.Collection;
import java.util.UUID;

@RestController
public class UserController {
    private UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @PostMapping("/")
    public ResponseEntity<User> createUser(@RequestBody UserData data) {
        User user = new User(
                UUID.randomUUID().toString(),
                data.getName(),
                data.getAge());
        userRepo.create(user);
        return ResponseEntity
                .created(URI.create(String.format("http://localhost:7000/%s", user.getId())))
                .body(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@RequestBody UserData data, @PathVariable String id) {
        User user = userRepo.get(id);
        if(user == null){
            return ResponseEntity.notFound().build();
        } else {
            user.setName(data.getName());
            user.setAge(data.getAge());
            userRepo.save(user);
            return ResponseEntity.ok(user);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        User user = userRepo.get(id);
        if (user == null) {
            return ResponseEntity.notFound()
                    .build();
        } else {
            userRepo.delete(id);
            return ResponseEntity.ok()
                    .build();
        }
    }

    @GetMapping("/")
    public Collection<User> getAll() {
        return userRepo.all();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUser(@PathVariable String id) {
        User user = userRepo.get(id);
        if(user == null) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(user);
        }
    }
}
