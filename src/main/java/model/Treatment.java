package model;

import utils.DateConverter;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Both patient and caregiver are displayed in a treatment.
 * There is a short description and a long description.
 */
public class Treatment {
    private long tid;
    private long pid;
    private LocalDate date;
    private LocalTime begin;
    private LocalTime end;
    private String description;
    private String remarks;
    private String caregiver;
    private String telephone;

    /**
     * constructs a treatment from the given params
     * @param pid
     * @param date
     * @param begin
     * @param end
     * @param description
     * @param remarks
     * @param caregiver
     * @param telephone
     */
    public Treatment(long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, String caregiver, String telephone) {
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.caregiver = caregiver;
        this.telephone = telephone;
    }

    /**
     * constructs a treatment from the given params
     * @param tid
     * @param pid
     * @param date
     * @param begin
     * @param end
     * @param description
     * @param remarks
     * @param caregiver
     * @param telephone
     */
    public Treatment(long tid, long pid, LocalDate date, LocalTime begin,
                     LocalTime end, String description, String remarks, String caregiver, String telephone) {
        this.tid = tid;
        this.pid = pid;
        this.date = date;
        this.begin = begin;
        this.end = end;
        this.description = description;
        this.remarks = remarks;
        this.caregiver = caregiver;
        this.telephone = telephone;
    }

    /**
     * @return treatment id
     */
    public long getTid() {
        return tid;
    }

    /**
     * @return patient id
     */
    public long getPid() {
        return this.pid;
    }

    /**
     * @return date
     */
    public String getDate() {
        return date.toString();
    }

    /**
     * @return begin of treatment
     */
    public String getBegin() {
        return begin.toString();
    }

    /**
     * @return end of treatment
     */
    public String getEnd() {
        return end.toString();
    }

    /**
     * @param s_date
     */
    public void setDate(String s_date) {
        LocalDate date = DateConverter.convertStringToLocalDate(s_date);
        this.date = date;
    }

    /**
     * @param begin
     */
    public void setBegin(String begin) {
        LocalTime time = DateConverter.convertStringToLocalTime(begin);
        this.begin = time;
    }

    /**
     * @param end
     */
    public void setEnd(String end) {
        LocalTime time = DateConverter.convertStringToLocalTime(end);
        this.end = time;
    }

    /**
     * @return caregiver
     */
    public String getCaregiver() {
        return caregiver;
    }

    /**
     * @param caregiver
     */
    public void setCaregiver(String caregiver) {
        this.caregiver = caregiver;
    }

    /**
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    /**
     * @return the params as a String
     */
    public String toString() {
        return "\nBehandlung" + "\nTID: " + this.tid +
                "\nPID: " + this.pid +
                "\nDate: " + this.date +
                "\nBegin: " + this.begin +
                "\nEnd: " + this.end +
                "\nDescription: " + this.description +
                "\nRemarks: " + this.remarks +
                "\nCaregiver: " + this.caregiver +
                "\nTelephone: " + this.telephone + "\n";
    }
}