package user_microservice;

public class UserData {
    private String name;
    private int age;

    public UserData(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }
}
