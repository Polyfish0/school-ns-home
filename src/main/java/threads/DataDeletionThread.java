package threads;

import datastorage.BlockedPatientDAO;
import datastorage.BlockedTreatmentDAO;
import datastorage.DAOFactory;
import model.BlockedPatient;
import utils.TimeUtils;

import java.sql.SQLException;

public class DataDeletionThread extends Thread {
    /**
     * This function checks if the conditions for deletion are satisfied and when they are, the data will be deleted.
     * This happens inside a second thread so that the user do not notice it, even when the program has to check a lot of entries.
     */
    @Override
    public void run() {
        //if(!TimeUtils.monthForDataDeletion())
            //return;

        BlockedPatientDAO bpDAO = DAOFactory.getDAOFactory().createBlockedPatientDAO();
        BlockedTreatmentDAO btDAO = DAOFactory.getDAOFactory().createBlockedTreatmentDAO();

        try {
            for(BlockedPatient patient : bpDAO.readAll()) {
                if(!TimeUtils.checkIfOldEnoughForDeletion(patient.getTreatmentEnded()))
                    return;

                btDAO.deleteByPid(patient.getPid());
                bpDAO.deleteById(patient.getPid());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
