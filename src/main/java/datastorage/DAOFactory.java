package datastorage;

public class DAOFactory {

    private static DAOFactory instance;

    private DAOFactory() {

    }

    /**
     * @return an instance of an object according to the singelton-pattern
     */
    public static DAOFactory getDAOFactory() {
        if (instance == null) {
            instance = new DAOFactory();
        }
        return instance;
    }

    /**
     * @return new instance of the Treatment-DAO
     */
    public TreatmentDAO createTreatmentDAO() {
        return new TreatmentDAO(ConnectionBuilder.getConnection());
    }

    /**
     * @return new instance of the blocked-Treatment-DAO
     */
    public BlockedTreatmentDAO createBlockedTreatmentDAO() {
        return new BlockedTreatmentDAO(ConnectionBuilder.getConnection());
    }

    /**
     * @return new instance of the Patient-DAO
     */
    public PatientDAO createPatientDAO() {
        return new PatientDAO(ConnectionBuilder.getConnection());
    }

    /**
     * @return new instance of the blocked-Patient-DAO
     */
    public BlockedPatientDAO createBlockedPatientDAO() {
        return new BlockedPatientDAO(ConnectionBuilder.getConnection());
    }

    /**
     * @return new instance of the User-DAO
     */
    public UsersDAO createUsersDAO() {
        return new UsersDAO(ConnectionBuilder.getConnection());
    }

    /**
     * @return new instance of the Caregiver-DAO
     */
    public CaregiverDAO createCaregiverDAO() {
        return new CaregiverDAO(ConnectionBuilder.getConnection());
    }
}