package datastorage;

import model.Caregiver;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Implements the Interface <code>DAOImp</code>. Overrides methods to generate specific caregiver-SQL-queries.
 */
public class CaregiverDAO extends DAOimp<Caregiver> {

    /**
     * constructs Object. Calls the Constructor from <code>DAOImp</code> to store the connection.
     * @param conn
     */
    public CaregiverDAO(Connection conn) {
        super(conn);
    }

    /**
     * generates a <code>INSERT INTO</code>-Statement for a given caregiver
     * @param caregiver for which a specific INSERT INTO is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getCreateStatementString(Caregiver caregiver) {
        return String.format("INSERT INTO caregiver (firstname, surname, telephone) VALUES ('%s', '%s', '%s')", caregiver.getFirstName(), caregiver.getSurname(), caregiver.getTelephone());
    }

    /**
     * generates a <code>select</code>-Statement for a given key
     * @param key for which a specific SELECT is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadByIDStatementString(long key) {
        return String.format("SELECT * FROM caregiver WHERE cid = %d", key);
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Caregiver</code>
     * @param result ResultSet with a single row. Columns will be mapped to a caregiver-object.
     * @return caregiver with the data from the resultSet.
     */
    @Override
    protected Caregiver getInstanceFromResultSet(ResultSet result) throws SQLException {
        return new Caregiver(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
    }

    /**
     * generates a <code>SELECT</code>-Statement for all caregiver.
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getReadAllStatementString() {
        return "SELECT * FROM caregiver";
    }

    /**
     * maps a <code>ResultSet</code> to a <code>Caregiver-List</code>
     * @param result ResultSet with a multiple rows. Data will be mapped to caregiver-object.
     * @return ArrayList with caregivers from the resultSet.
     */
    @Override
    protected ArrayList<Caregiver> getListFromResultSet(ResultSet result) throws SQLException {
        ArrayList<Caregiver> list = new ArrayList<Caregiver>();
        Caregiver c = null;
        while (result.next()) {
            c = new Caregiver(result.getInt(1), result.getString(2), result.getString(3), result.getString(4));
            list.add(c);
        }
        return list;
    }

    /**
     * generates a <code>UPDATE</code>-Statement for a given caregiver
     * @param caregiver for which a specific update is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getUpdateStatementString(Caregiver caregiver) {
        return String.format("UPDATE caregiver SET firstname = '%s', surname = '%s', telephone = '%s' WHERE cid = %d", caregiver.getFirstName(), caregiver.getSurname(), caregiver.getTelephone(), caregiver.getCid());
    }

    /**
     * generates a <code>delete</code>-Statement for a given key
     * @param key for which a specific DELETE is to be created
     * @return <code>String</code> with the generated SQL.
     */
    @Override
    protected String getDeleteStatementString(long key) {
        return String.format("Delete FROM caregiver WHERE cid=%d", key);
    }
}
