package controller;

import datastorage.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.stage.FileChooser;
import model.BlockedPatient;
import model.Patient;
import model.Treatment;
import utils.AlertBuilder;
import utils.DateConverter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


/**
 * The <code>AllPatientController</code> contains the entire logic of the patient view. It determines which data is displayed and how to react to events.
 */
public class AllPatientController {
    @FXML
    private TableView<Patient> tableView;
    @FXML
    private TableColumn<Patient, Integer> colID;
    @FXML
    private TableColumn<Patient, String> colFirstName;
    @FXML
    private TableColumn<Patient, String> colSurname;
    @FXML
    private TableColumn<Patient, String> colDateOfBirth;
    @FXML
    private TableColumn<Patient, String> colCareLevel;
    @FXML
    private TableColumn<Patient, String> colRoom;

    @FXML
    Button btnAdd;
    @FXML
    TextField txtSurname;
    @FXML
    TextField txtFirstname;
    @FXML
    TextField txtBirthday;
    @FXML
    TextField txtCarelevel;
    @FXML
    TextField txtRoom;

    private ObservableList<Patient> tableviewContent = FXCollections.observableArrayList();
    private PatientDAO dao;

    /**
     * Initializes the corresponding fields. Is called as soon as the corresponding FXML file is to be displayed.
     */
    public void initialize() {
        readAllAndShowInTableView();

        this.colID.setCellValueFactory(new PropertyValueFactory<Patient, Integer>("pid"));

        //CellValuefactory zum Anzeigen der Daten in der TableView
        this.colFirstName.setCellValueFactory(new PropertyValueFactory<Patient, String>("firstName"));
        //CellFactory zum Schreiben innerhalb der Tabelle
        this.colFirstName.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colSurname.setCellValueFactory(new PropertyValueFactory<Patient, String>("surname"));
        this.colSurname.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colDateOfBirth.setCellValueFactory(new PropertyValueFactory<Patient, String>("dateOfBirth"));
        this.colDateOfBirth.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colCareLevel.setCellValueFactory(new PropertyValueFactory<Patient, String>("careLevel"));
        this.colCareLevel.setCellFactory(TextFieldTableCell.forTableColumn());

        this.colRoom.setCellValueFactory(new PropertyValueFactory<Patient, String>("roomnumber"));
        this.colRoom.setCellFactory(TextFieldTableCell.forTableColumn());


        //Anzeigen der Daten
        this.tableView.setItems(this.tableviewContent);
    }

    /**
     * handles new firstname value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditFirstname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setFirstName(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new surname value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditSurname(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setSurname(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new birthdate value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditDateOfBirth(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setDateOfBirth(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new carelevel value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditCareLevel(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setCareLevel(event.getNewValue());
        doUpdate(event);
    }

    /**
     * handles new roomnumber value
     *
     * @param event event including the value that a user entered into the cell
     */
    @FXML
    public void handleOnEditRoomnumber(TableColumn.CellEditEvent<Patient, String> event) {
        event.getRowValue().setRoomnumber(event.getNewValue());
        doUpdate(event);
    }

    /**
     * updates a patient by calling the update-Method in the {@link PatientDAO}
     *
     * @param t row to be updated by the user (includes the patient)
     */
    private void doUpdate(TableColumn.CellEditEvent<Patient, String> t) {
        try {
            dao.update(t.getRowValue());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * calls readAll in {@link PatientDAO} and shows patients in the table
     */
    private void readAllAndShowInTableView() {
        this.tableviewContent.clear();
        this.dao = DAOFactory.getDAOFactory().createPatientDAO();
        List<Patient> allPatients;
        try {
            allPatients = dao.readAll();
            for (Patient p : allPatients) {
                this.tableviewContent.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles a delete-click-event. Calls the delete methods in the {@link PatientDAO} and {@link TreatmentDAO}
     */
    @FXML
    public void handleDeleteRow() {
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();
        try {
            tDao.deleteByPid(selectedItem.getPid());
            dao.deleteById(selectedItem.getPid());
            this.tableView.getItems().remove(selectedItem);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handles an add-click-event. Creates a patient and calls the create method in the {@link PatientDAO}
     */
    @FXML
    public void handleAdd() {
        String surname = this.txtSurname.getText();
        String firstname = this.txtFirstname.getText();
        String birthday = this.txtBirthday.getText();
        LocalDate date = DateConverter.convertStringToLocalDate(birthday);
        String carelevel = this.txtCarelevel.getText();
        String room = this.txtRoom.getText();
        try {
            Patient p = new Patient(firstname, surname, date, carelevel, room);
            dao.create(p);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        readAllAndShowInTableView();
        clearTextfields();
    }

    /**
     * Handels an ExportClickEvent. It lets you export a patient's data
     */
    @FXML
    public void handleExport() {
        File saveLocation = getSaveFile();

        if (saveLocation == null) {
            AlertBuilder.AlertDialog(
                    Alert.AlertType.ERROR,
                    "Bitte gebe eine Datei an!",
                    "Bitte gebe eine Datei an, in welcher die Informationen gespeichert werden sollen."
            );

            return;
        }

        saveStringToFile(saveLocation, prepareExportString());
    }

    /**
     * This function writes a string to a {@link File}
     *
     * @param saveLocation The file where the content should be written to
     * @param content      The file content
     */
    private void saveStringToFile(File saveLocation, String content) {
        try {
            saveLocation.createNewFile();

            if (!saveLocation.canWrite()) {
                AlertBuilder.AlertDialog(
                        Alert.AlertType.ERROR,
                        "Die Datei ist nicht beschreibbar!",
                        "Diese Datei kann nicht beschrieben werden!\nBitte wähle eine andere Datei oder einen anderen Speicherort."
                );

                return;
            }

            FileWriter fw = new FileWriter(saveLocation.getAbsolutePath());
            fw.write(content);
            fw.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This function asks the user where the file should be stored.
     *
     * @return Returns an instance of {@link File} for the selected file
     */
    private File getSaveFile() {
        FileChooser saveDialog = new FileChooser();
        saveDialog.setTitle("Speichern");
        saveDialog.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Datei", "*.txt"));
        return saveDialog.showSaveDialog(tableView.getScene().getWindow());
    }

    /**
     * This functions generates a String with every information, that is stored inside the database, about a patient
     *
     * @return Returns the information's of the patient
     */
    private String prepareExportString() {
        TreatmentDAO tDao = DAOFactory.getDAOFactory().createTreatmentDAO();
        Patient selectedItem = this.tableView.getSelectionModel().getSelectedItem();

        StringBuilder sb = new StringBuilder();
        sb.append(selectedItem.toString());
        sb.append("\n");

        try {
            int treatmentCounter = 1;
            for (Treatment treatment : tDao.readTreatmentsByPid(selectedItem.getPid())) {
                sb.append(treatment.toString().replace("Behandlung", "Behandlung " + treatmentCounter++));
                sb.append("\n");
            }
        } catch (SQLException e) {
            sb.append("Keine Behandlungsdaten Gefunden\n");
        }

        return sb.toString().replace("null", "Keine Daten");
    }

    /**
     * handels the event of ending a treatment
     */
    @FXML
    public void handleEndTreatment() {
        Patient selectedPatient = tableView.getSelectionModel().getSelectedItem();
        if (selectedPatient == null) {
            AlertBuilder.AlertDialog(
                    Alert.AlertType.ERROR,
                    "Kein Patient ausgewählt",
                    "Bitte wähle erst einen Patienten in der Liste aus!"
            );

            return;
        }

        if (AlertBuilder.ConfirmationDialog(
                "Behandlung beenden?",
                "Sind sie sich sicher, dass sie die Behandlung beenden wollen?"
        ).get() == ButtonType.CANCEL)
            return;

        endPatientTreatment(selectedPatient);
    }

    /**
     * This function ends the treatment of a patient and moves every patient information to another database table, which is not shown inside the GUI
     * @param patient
     */
    private void endPatientTreatment(Patient patient) {
        PatientDAO bDAO = DAOFactory.getDAOFactory().createPatientDAO();
        BlockedPatientDAO bpDAO = DAOFactory.getDAOFactory().createBlockedPatientDAO();
        TreatmentDAO tDAO = DAOFactory.getDAOFactory().createTreatmentDAO();
        BlockedTreatmentDAO btDAO = DAOFactory.getDAOFactory().createBlockedTreatmentDAO();

        try {
            bpDAO.createFromPatient(patient);
            tDAO.readTreatmentsByPid(patient.getPid()).forEach(treatment -> {
                try {
                    btDAO.create(treatment);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            });

            tDAO.deleteByPid(patient.getPid());
            bDAO.deleteById(patient.getPid());

            readAllAndShowInTableView();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * removes content from all textfields
     */
    private void clearTextfields() {
        this.txtFirstname.clear();
        this.txtSurname.clear();
        this.txtBirthday.clear();
        this.txtCarelevel.clear();
        this.txtRoom.clear();
    }
}