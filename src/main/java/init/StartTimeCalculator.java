package init;

import static config.CONFIG.DAY;

public class StartTimeCalculator {

    public static int startTimeCal(int arrivalTime, int start, int end) {
        int quotient = arrivalTime / DAY;
        int remainder = arrivalTime % DAY;

        if (start < end) {
            if (start <= remainder && remainder <= end) {
                return arrivalTime;
            } else if (remainder < start) {
                return quotient * DAY + start;
            } else { // end < remainder
                return (quotient + 1) * DAY + start;
            }
        } else {
            if (end < remainder && remainder < start) {
                return quotient * DAY + start;
            } else {
                return arrivalTime;
            }
        }
    }
}

