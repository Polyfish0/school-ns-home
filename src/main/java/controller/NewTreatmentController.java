package controller;

import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The <code>NewTreatmentController</code> contains the entire logic of the new treatment view. It determines which data is displayed and how to react to events.
 */
public class NewTreatmentController {
    @FXML
    private Label lblSurname;
    @FXML
    private Label lblFirstname;
    @FXML
    private TextField txtBegin;
    @FXML
    private TextField txtEnd;
    @FXML
    private TextField txtDescription;
    @FXML
    private TextArea taRemarks;
    @FXML
    private ComboBox comboboxCaregiver;
    @FXML
    private DatePicker datepicker;

    private AllTreatmentController controller;
    private Patient patient;
    private Stage stage;
    private HashMap<String, Caregiver> name2caregiver = new HashMap<>();

    /**
     * initalizes the new treatment from the params
     * @param controller
     * @param stage
     * @param patient
     */
    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;

        setupCaregiverCombobox();
        showPatientData();
    }

    /**
     * sets the caregiver combobox
     */
    private void setupCaregiverCombobox() {
        try {
            name2caregiver.clear();

            ArrayList<String> caregiverList = new ArrayList<>();

            for(Caregiver caregiver : DAOFactory.getDAOFactory().createCaregiverDAO().readAll()) {
                String name = caregiver.getFirstName() + " " + caregiver.getSurname();
                caregiverList.add(name);

                name2caregiver.put(name, caregiver);
            }

            comboboxCaregiver.setItems(FXCollections.observableList(caregiverList));
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * shows the patient
     */
    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    /**
     * adding a treatment
     */
    @FXML
    public void handleAdd(){
        LocalDate date = this.datepicker.getValue();
        String s_begin = txtBegin.getText();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        String caregiver = comboboxCaregiver.getValue().toString();
        String telephone = name2caregiver.get(comboboxCaregiver.getValue().toString()).getTelephone();
        Treatment treatment = new Treatment(patient.getPid(), date,
                begin, end, description, remarks, caregiver, telephone);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * creates a treatment
     * @param treatment
     */
    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * cancels the action
     */
    @FXML
    public void handleCancel(){
        stage.close();
    }
}