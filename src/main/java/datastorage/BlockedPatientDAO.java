package datastorage;

import model.BlockedPatient;
import model.Patient;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific patient-SQL-queries.
 */
public class BlockedPatientDAO extends DAOimp<BlockedPatient> {

    /**
     * constructs Onbject. Calls the Constructor from <code>DAOImp</code> to store the connection.
     *
     * @param conn
     */
    public BlockedPatientDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given patient
     *
     * @param patient for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(BlockedPatient patient) {
        return String.format("INSERT INTO blocked_patient (pid, firstname, surname, dateOfBirth, carelevel, roomnumber, treatment_ended) VALUES ('%d', '%s', '%s', '%s', '%s', '%s', '%d')",
                patient.getPid(), patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(), patient.getCareLevel(), patient.getRoomnumber(), Instant.now().getEpochSecond());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     *
     * @param key for which a specific SELECTis to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM blocked_patient WHERE pid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient</code>
     *
     * @param result ResultSet with a single row. Columns will be mapped to a patient-object.
     * @return patient with the data from the resultSet.
     */
    @Override
    protected BlockedPatient getInstanceFromResultSet(ResultSet result) throws SQLException {
        BlockedPatient p;
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
        p = new BlockedPatient(result.getInt(1), result.getString(2),
                result.getString(3), date, result.getString(5),
                result.getString(6), result.getLong(7));
        return p;
    }

    /**
     * generates a <code>SELECT</code>-Statement for all patients.
     *
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM blocked_patient";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Patient-List</code>
     *
     * @param result ResultSet with a multiple rows. Data will be mapped to patient-object.
     * @return ArrayList with patients from the resultSet.
     */
    @Override
    protected ArrayList<BlockedPatient> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<BlockedPatient> list = new ArrayList<BlockedPatient>();
        BlockedPatient p;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(4));
            p = new BlockedPatient(result.getInt(1), result.getString(2),
                    result.getString(3), date,
                    result.getString(5), result.getString(6), result.getLong(7));
            list.add(p);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given patient
     *
     * @param patient for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(BlockedPatient patient) {
        return String.format("UPDATE blocked_patient SET firstname = '%s', surname = '%s', dateOfBirth = '%s', carelevel = '%s', " +
                        "roomnumber = '%s', WHERE pid = %d", patient.getFirstName(), patient.getSurname(), patient.getDateOfBirth(),
                patient.getCareLevel(), patient.getRoomnumber(), patient.getPid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     *
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM blocked_patient WHERE pid=%d", key);
    }

    public void createFromPatient(Patient patient) throws SQLException {
        create(new BlockedPatient(
                patient.getPid(),
                patient.getFirstName(),
                patient.getSurname(),
                DateConverter.convertStringToLocalDate(patient.getDateOfBirth()),
                patient.getCareLevel(),
                patient.getRoomnumber(),
                0l
        ));
    }
}
