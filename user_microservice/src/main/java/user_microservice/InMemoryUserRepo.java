package user_microservice;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Service
public class InMemoryUserRepo implements UserRepo {
    private Map<String, User> userMap = new HashMap<>();

    @Override
    public User get(String id) {
        return userMap.get(id);
    }

    @Override
    public Collection<User> all() {
        return userMap.values();
    }
}
