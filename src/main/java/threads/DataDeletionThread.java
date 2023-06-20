package threads;

import datastorage.BlockedPatientDAO;
import datastorage.BlockedTreatmentDAO;
import datastorage.DAOFactory;
import datastorage.TreatmentDAO;
import model.BlockedPatient;
import model.Treatment;
import utils.DateConverter;
import utils.TimeUtils;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.TimeZone;

public class DataDeletionThread extends Thread {
    /**
     * This function checks if the conditions for deletion are satisfied and when they are, the data will be deleted.
     * This happens inside a second thread so that the user do not notice it, even when the program has to check a lot of entries.
     */
    @Override
    public void run() {
        if(!TimeUtils.monthForDataDeletion())
            return;

        TreatmentDAO tDAO = DAOFactory.getDAOFactory().createTreatmentDAO();
        BlockedPatientDAO bpDAO = DAOFactory.getDAOFactory().createBlockedPatientDAO();
        BlockedTreatmentDAO btDAO = DAOFactory.getDAOFactory().createBlockedTreatmentDAO();

        try {
            for(Treatment treatment : tDAO.readAll()) {
                LocalDate date = LocalDate.of(Integer.parseInt(treatment.getDate().split("-")[0]) + 1, 1, 1);
                if(TimeUtils.checkIfOldEnoughForDeletion(date.atStartOfDay(ZoneOffset.UTC).toEpochSecond()))
                    tDAO.deleteById(treatment.getTid());
            }

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
