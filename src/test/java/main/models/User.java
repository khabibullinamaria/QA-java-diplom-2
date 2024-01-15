package main.models;

public class User extends Login{
    private String name;

    public User(String email, String name, String password) {
        super(email, password);
        this.name = name;
    }

    public User(){
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
