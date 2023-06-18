package utils;

import java.time.*;

public class TimeUtils {
    /**
     * This function takes a unix timestamp and checks if it is old enough for the data deletion
     * @param unixTimestamp The unix timestamp to check
     * @return
     */
    public static boolean checkIfOldEnoughForDeletion(long unixTimestamp) {
        LocalDateTime timestamp = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTimestamp), ZoneOffset.UTC);
        LocalDateTime currentTimestamp = LocalDateTime.now(ZoneOffset.UTC);
        LocalDateTime tenYearsAgo = currentTimestamp.minusYears(10);

        return timestamp.isBefore(tenYearsAgo);
    }

    /**
     * This function checks if we have currently the right month for the data deletion
     * @return
     */
    public static boolean monthForDataDeletion() {
        LocalDate currentDate = LocalDate.now();
        Month currentMonth = currentDate.getMonth();

        return currentMonth == Month.JANUARY || currentMonth == Month.FEBRUARY;
    }
}
