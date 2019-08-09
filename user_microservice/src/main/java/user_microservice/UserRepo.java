package user_microservice;

import java.util.Collection;

public interface UserRepo {
    User get(String id);

    Collection<User> all();
}
