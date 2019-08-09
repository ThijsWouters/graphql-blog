package user_microservice;

import java.util.Collection;

public interface UserRepo {
    User get(String id);

    Collection<User> all();

    void create(User user);

    void delete(String id);

    void save(User user);
}
