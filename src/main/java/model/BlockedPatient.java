package model;

import utils.DateConverter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Patients live in a NURSING home and are treated by nurses.
 */
public class BlockedPatient extends Patient {
    private long treatmentEnded;

    /**
     * constructs a patient from the given params.
     * @param pid
     * @param firstName
     * @param surname
     * @param dateOfBirth
     * @param careLevel
     * @param roomnumber
     * @param treatmentEnded
     */
    public BlockedPatient(long pid, String firstName, String surname, LocalDate dateOfBirth, String careLevel, String roomnumber, long treatmentEnded) {
        super(pid, firstName, surname, dateOfBirth, careLevel, roomnumber);
        this.treatmentEnded = treatmentEnded;
    }

    public long getTreatmentEnded() {
        return treatmentEnded;
    }

    @Override
    public String toString() {
        return super.toString() + "Treatment Ended (Unix Timestamp): " + getTreatmentEnded() + "\n";
    }
}