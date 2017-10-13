package model;

public class UserResult {

    public User u;
    public String state;
    public int retry = 0;

    public UserResult(User u, String state) {
        this.u = u;
        this.state = state;
    }
}
