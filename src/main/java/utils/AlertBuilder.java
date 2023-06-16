package utils;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class AlertBuilder {

    /**
     * A helper function to generate AlertDialogs
     * @param type The type of alert
     * @param title The title of the alert which should be shown
     * @param content The content of the alert which should be shown
     */
    public static void AlertDialog(Alert.AlertType type, String title, String content) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.show();
    }

    public static Optional<ButtonType> ConfirmationDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setContentText(content);

        return alert.showAndWait();
    }
}
