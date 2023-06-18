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
    private TextField txtTelephone;
    @FXML
    private ComboBox comboboxCaregiver;
    @FXML
    private DatePicker datepicker;

    private AllTreatmentController controller;
    private Patient patient;
    private Stage stage;

    public void initialize(AllTreatmentController controller, Stage stage, Patient patient) {
        this.controller= controller;
        this.patient = patient;
        this.stage = stage;

        setupCaregiverCombobox();
        showPatientData();
    }

    private void setupCaregiverCombobox() {
        try {
            ArrayList<String> caregiverList = new ArrayList<>();

            for(Caregiver caregiver : DAOFactory.getDAOFactory().createCaregiverDAO().readAll()) {
                String name = caregiver.getFirstName() + " " + caregiver.getSurname();
                caregiverList.add(name);
            }

            comboboxCaregiver.setItems(FXCollections.observableList(caregiverList));
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void showPatientData(){
        this.lblFirstname.setText(patient.getFirstName());
        this.lblSurname.setText(patient.getSurname());
    }

    @FXML
    public void handleAdd(){
        LocalDate date = this.datepicker.getValue();
        String s_begin = txtBegin.getText();
        LocalTime begin = DateConverter.convertStringToLocalTime(txtBegin.getText());
        LocalTime end = DateConverter.convertStringToLocalTime(txtEnd.getText());
        String description = txtDescription.getText();
        String remarks = taRemarks.getText();
        String caregiver = comboboxCaregiver.getSelectionModel().getSelectedItem().toString();
        String telephone = txtTelephone.getText();
        Treatment treatment = new Treatment(patient.getPid(), date,
                begin, end, description, remarks, caregiver, telephone);
        createTreatment(treatment);
        controller.readAllAndShowInTableView();
        stage.close();
    }

    private void createTreatment(Treatment treatment) {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.create(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleCancel(){
        stage.close();
    }
}