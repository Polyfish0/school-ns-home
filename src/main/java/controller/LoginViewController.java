package controller;

import datastorage.DAOFactory;
import datastorage.UsersDAO;
import exceptions.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.AlertBuilder;
import utils.Password;

import java.io.IOException;

/**
 * The <code>LoginViewController</code> contains the entire logic of the login view. It determines which data is displayed and how to react to events.
 */
public class LoginViewController {
    @FXML
    private TextField usernameInput;

    @FXML
    private PasswordField passwordInput;

    /**
     * Checks if the input fields are filled correctly
     * @return True => Everything is fine, False => Something is wrong
     */
    private boolean checkUserInput() {
        if(usernameInput.getText().equals("")) {
            AlertBuilder.AlertDialog(
                    Alert.AlertType.ERROR,
                    "Kein Benutzername angegeben",
                    "Bitte gebe einen Benutzernamen ein"
            );
            return false;
        }

        if(passwordInput.getText().equals("")) {
            AlertBuilder.AlertDialog(
                    Alert.AlertType.ERROR,
                    "Kein Passwort angegeben",
                    "Bitte gebe einen Passwort ein"
            );
            return false;
        }
        return true;
    }

    /**
     * This function handles the onPressEvent from the login button. When the Login was successful,
     * the main program view will be displayed and when the login failed the user gets an alert that something is wrong.
     * @param actionEvent JavaFX button event
     */
    public void handleLogin(ActionEvent actionEvent) {
        if(!checkUserInput())
            return;

        UsersDAO usersDAO = DAOFactory.getDAOFactory().createUsersDAO();

        try {
            if(Password.checkPassword(passwordInput.getText(), usersDAO.getUserByUsername(usernameInput.getText()).getPassword())) {
                FXMLLoader loader = new FXMLLoader(Main.class.getResource("/MainWindowView.fxml"));
                Scene scene = new Scene(loader.load());
                Stage mainWindowStage = (Stage) usernameInput.getScene().getWindow();
                mainWindowStage.setScene(scene);
                mainWindowStage.show();

                return;
            }

            throw new UserNotFoundException();
        } catch (UserNotFoundException e) {
            AlertBuilder.AlertDialog(
                    Alert.AlertType.ERROR,
                    "Benutzer nicht gefunden!",
                    "Der angegebene Benutzer oder das angegebene\nPasswort ist falsch!"
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
