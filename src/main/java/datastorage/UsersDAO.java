package datastorage;

import exceptions.UserNotFoundException;
import model.User;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsersDAO extends DAOimp<User> {
    public UsersDAO(Connection conn) {
        super(conn);
    }

    /**
     * This function generates a SQL statement to add a new user to the database
     * @param user A user object where everything is already set. The uid will be ignored, because the database sets it
     * @return The SQL statement
     */
    @Override
    protected String getCreateStatementString(User user) {
        return String.format("INSERT INTO USER (USERNAME, PASSWORD) VALUES (%s, %s)", user.getUsername(), user.getPassword());
    }

    /**
     * This function generates a SQL statement to retrieve a user, identified by its uid, from the database
     * @param key The user uid
     * @return The SQL statement
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM USER WHERE UID = %d", key);
    }

    /**
     * This function generates the SQL statement to read every user from the Database
     * @return The finish SQL statement
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM USER";
    }

    /**
     * This functions generates one user instance from the given ResultSet
     * @param set The ResultSet you want a user instance from
     * @return The instance of the user you want
     * @throws SQLException Is thrown when the ResultSet is faulty or empty
     */
    @Override
    protected User getInstanceFromResultSet(ResultSet set) throws SQLException {
        return new User(set.getLong(1), set.getString(2), set.getString(3));
    }

    /**
     * This function generates an ArrayList from the ResultSet of an SQL query
     * @param result The SQL query
     * @return The ArrayList of the users from the ResultSet
     * @throws SQLException Is thrown when the ResultSet is faulty or empty
     */
    @Override
    protected ArrayList<User> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<User> list = new ArrayList<>();
        User c;
        while (result.next()) {
            c = new User(result.getInt(1), result.getString(2), result.getString(3));
            list.add(c);
        }
        return list;
    }

    /**
     * This function create the update SQL statement to update a user
     * @param user The user with it's updated values
     * @return The finished SQL statement
     */
    @Override
    protected String getUpdateStatementString(User user) {
        return String.format("UPDATE USER SET USERNAME = %s, PASSWORD = %s WHERE UID = %d", user.getUsername(), user.getPassword(), user.getUid());
    }

    /**
     * This function creates the delete SQL statement to delete a user identified with his uid
     * @param key The uid of the user you want to delete
     * @return The finished SQL statement
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("DELETE FROM USER WHERE UID = %d", key);
    }

    /**
     * With this function, you can retrieve a user by its username from the database
     * @param username The username of the user you want to have from the database
     * @return If a user was found, it gets returned
     * @throws UserNotFoundException If no user was found, this exception will be thrown
     */
    public User getUserByUsername(String username) throws UserNotFoundException {
        try {
            for(User user : readAll()) {
                if(user.getUsername().equals(username))
                    return user;
            }

            throw new UserNotFoundException();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
