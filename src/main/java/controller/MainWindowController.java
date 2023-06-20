package controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * The <code>MainWindowController</code> contains the entire logic of the main window view. It determines which data is displayed and how to react to events.
 */
public class MainWindowController {

    @FXML
    private BorderPane mainBorderPane;

    /**
     * loads the <code>AllPatientView</code>
     * @param e
     */
    @FXML
    private void handleShowAllPatient(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllPatientView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllPatientController controller = loader.getController();
    }

    /**
     * loads the <code>AllTreatmentView.fxml</code>
     * @param e
     */
    @FXML
    private void handleShowAllTreatments(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllTreatmentView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllTreatmentController controller = loader.getController();
    }

    /**
     * loads the <code>AllCaregiverView.fxml</code>
     * @param e
     */
    @FXML
    private void handleShowAllCaregiver(ActionEvent e) {
        FXMLLoader loader = new FXMLLoader(Main.class.getResource("/AllCaregiverView.fxml"));
        try {
            mainBorderPane.setCenter(loader.load());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        AllCaregiverController controller = loader.getController();
    }

    /**
     * loads the <code>LogonView.fxml</code>
     * @param e
     */
    @FXML
    private void handleLogout(ActionEvent e) {
        try {
            FXMLLoader loader = new FXMLLoader(Main.class.getResource("/LoginView.fxml"));
            Scene scene = new Scene(loader.load());
            Stage mainWindowStage = (Stage) mainBorderPane.getScene().getWindow();
            mainWindowStage.setScene(scene);
            mainWindowStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
            Platform.exit();
        }
    }
}
