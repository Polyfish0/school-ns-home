package model;

/**
 * The user is for the login
 */
public class User {
    private long uid;
    private String username;
    private String password;

    /**
     * constructs a user from the given params
     * @param uid
     * @param username
     * @param password
     */
    public User(long uid, String username, String password) {
        this.uid = uid;
        this.username = username;
        this.password = password;
    }

    /**
     * @return user id
     */
    public long getUid() {
        return uid;
    }

    /**
     * @return username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @return the params as a string
     */
    @Override
    public String toString() {
        return "uid: " + uid +
                "\nusername: " + username +
                "\npassword hash: " + password;
    }
}
