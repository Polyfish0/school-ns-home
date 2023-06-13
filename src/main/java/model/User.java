package model;

public class User {
    private long uid;
    private String username;
    private String password;

    public User(long uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    public long getUid() {
        return uid;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "uid: " + uid +
                "\nusername: " + username +
                "\npassword hash: " + password;
    }
}
