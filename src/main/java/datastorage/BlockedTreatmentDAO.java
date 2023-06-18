package datastorage;

import model.Treatment;
import utils.DateConverter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class BlockedTreatmentDAO extends DAOimp<Treatment> {

    public BlockedTreatmentDAO(Connection conn) {
        super(conn);
    }

    @Override
    protected String getCreateStatementString(Treatment treatment) {
        return String.format("INSERT INTO blocked_treatment (tid, pid, treatment_date, begin, end, description, remarks, caregiver, telephone) VALUES " +
                        "(%d, %d, '%s', '%s', '%s', '%s', '%s', '%s', '%s')", treatment.getTid(), treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(),
                treatment.getRemarks(), treatment.getCaregiver(), treatment.getTelephone());
    }

    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM blocked_treatment WHERE tid = %d", key);
    }

    @Override
    protected Treatment getInstanceFromResultSet(ResultSet result) throws SQLException {
        LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
        LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
        LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
        Treatment m = new Treatment(result.getLong(1), result.getLong(2),
                date, begin, end, result.getString(6), result.getString(7), result.getString(8), result.getString(9));
        return m;
    }

    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM blocked_treatment";
    }

    @Override
    protected ArrayList<Treatment> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<>();
        Treatment t;
        while (result.next()) {
            LocalDate date = DateConverter.convertStringToLocalDate(result.getString(3));
            LocalTime begin = DateConverter.convertStringToLocalTime(result.getString(4));
            LocalTime end = DateConverter.convertStringToLocalTime(result.getString(5));
            t = new Treatment(result.getLong(1), result.getLong(2),
                    date, begin, end, result.getString(6), result.getString(7), result.getString(8), result.getString(9));
            list.add(t);
        }
        return list;
    }

    @Override
    protected String getUpdateStatementString(Treatment treatment) {
        return String.format("UPDATE blocked_treatment SET pid = %d, treatment_date ='%s', begin = '%s', end = '%s'," +
                        "description = '%s', remarks = '%s', caregiver = '%s', telephone = '%s' WHERE tid = %d", treatment.getPid(), treatment.getDate(),
                treatment.getBegin(), treatment.getEnd(), treatment.getDescription(), treatment.getRemarks(), treatment.getCaregiver(), treatment.getTelephone(),
                treatment.getTid());
    }

    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM blocked_treatment WHERE tid = %d", key);
    }

    public List<Treatment> readBlockedTreatmentsByPid(long pid) throws SQLException {
        ArrayList<Treatment> list = new ArrayList<Treatment>();
        Treatment object = null;
        Statement st = conn.createStatement();
        ResultSet result = st.executeQuery(getReadAllBlockedTreatmentsOfOnePatientByPid(pid));
        list = getListFromResultSet(result);
        return list;
    }

    private String getReadAllBlockedTreatmentsOfOnePatientByPid(long pid) {
        return String.format("SELECT * FROM blocked_treatment WHERE pid = %d", pid);
    }

    public void deleteByPid(long key) throws SQLException {
        Statement st = conn.createStatement();
        st.executeUpdate(String.format("Delete FROM blocked_treatment WHERE pid = %d", key));
    }
}