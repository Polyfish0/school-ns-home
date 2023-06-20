package controller;

import datastorage.DAOFactory;
import datastorage.PatientDAO;
import datastorage.TreatmentDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Caregiver;
import model.Patient;
import model.Treatment;
import utils.DateConverter;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * The <code>TreatmentController</code> contains the entire logic of the single treatment view. It determines which data is displayed and how to react to events.
 */
public class TreatmentController {
    @FXML
    private Label lblPatientName;
    @FXML
    private Label lblCarelevel;
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
    @FXML
    private Button btnChange;
    @FXML
    private Button btnCancel;

    private AllTreatmentController controller;
    private Stage stage;
    private Patient patient;
    private Treatment treatment;
    private HashMap<String, Caregiver> name2caregiver = new HashMap<>();

    /**
     * initalize a single treatment from given params
     * @param controller
     * @param stage
     * @param treatment
     */
    public void initializeController(AllTreatmentController controller, Stage stage, Treatment treatment) {
        this.stage = stage;
        this.controller = controller;
        PatientDAO pDao = DAOFactory.getDAOFactory().createPatientDAO();
        try {
            this.patient = pDao.read((int) treatment.getPid());
            this.treatment = treatment;
            showData();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * shows the data
     */
    private void showData() {
        setupCaregiverCombobox();

        this.lblPatientName.setText(patient.getSurname() + ", " + patient.getFirstName());
        this.lblCarelevel.setText(patient.getCareLevel());
        LocalDate date = DateConverter.convertStringToLocalDate(treatment.getDate());
        this.datepicker.setValue(date);
        this.txtBegin.setText(this.treatment.getBegin());
        this.txtEnd.setText(this.treatment.getEnd());
        this.txtDescription.setText(this.treatment.getDescription());
        this.taRemarks.setText(this.treatment.getRemarks());
        this.treatment.setTelephone(name2caregiver.get(comboboxCaregiver.getValue().toString()).getTelephone());
    }

    /**
     * sets the caregiver combobox
     */
    private void setupCaregiverCombobox() {
        try {
            name2caregiver.clear();

            ArrayList<Caregiver> caregivers = (ArrayList<Caregiver>) DAOFactory.getDAOFactory().createCaregiverDAO().readAll();
            ArrayList<String> caregiverList = new ArrayList<>();
            int indexCounter = 0;

            for (int i = 0; i < caregivers.size(); i++) {
                Caregiver caregiver = caregivers.get(i);

                String name = caregiver.getFirstName() + " " + caregiver.getSurname();
                caregiverList.add(name);
                name2caregiver.put(name, caregiver);

                if (name == treatment.getCaregiver())
                    indexCounter = i;
            }

            comboboxCaregiver.setItems(FXCollections.observableList(caregiverList));
            comboboxCaregiver.getSelectionModel().select(indexCounter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * handels if a information is changed
     */
    @FXML
    public void handleChange() {
        this.treatment.setDate(this.datepicker.getValue().toString());
        this.treatment.setBegin(txtBegin.getText());
        this.treatment.setEnd(txtEnd.getText());
        this.treatment.setDescription(txtDescription.getText());
        this.treatment.setRemarks(taRemarks.getText());
        this.treatment.setCaregiver(comboboxCaregiver.getValue().toString());
        this.treatment.setTelephone(name2caregiver.get(comboboxCaregiver.getValue().toString()).getTelephone());
        doUpdate();
        controller.readAllAndShowInTableView();
        stage.close();
    }

    /**
     * updates the DAO
     */
    private void doUpdate() {
        TreatmentDAO dao = DAOFactory.getDAOFactory().createTreatmentDAO();
        try {
            dao.update(treatment);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * cancels the action
     */
    @FXML
    public void handleCancel() {
        stage.close();
    }
}