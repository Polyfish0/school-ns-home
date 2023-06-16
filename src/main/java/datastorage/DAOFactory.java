package datastorage;

public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {

    }

    public static DAOFactory getDAOFactory() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    public TreatmentDAO createTreatmentDAO() {
        return new TreatmentDAO(ConnectionBuilder.getConnection());
    }

    public BlockedTreatmentDAO createBlockedTreatmentDAO() {
        return new BlockedTreatmentDAO(ConnectionBuilder.getConnection());
    }

    public PatientDAO createPatientDAO() {
        return new PatientDAO(ConnectionBuilder.getConnection());
    }

    public BlockedPatientDAO createBlockedPatientDAO() {
        return new BlockedPatientDAO(ConnectionBuilder.getConnection());
    }

    public UsersDAO createUsersDAO() {
        return new UsersDAO(ConnectionBuilder.getConnection());
    }

    public CaregiverDAO createCaregiverDAO() {
        return new CaregiverDAO(ConnectionBuilder.getConnection());
    }
}