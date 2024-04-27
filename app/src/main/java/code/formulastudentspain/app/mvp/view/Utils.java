package code.formulastudentspain.app.mvp.view;

public class Utils {

    /**
     * Method to format milliseconds to 00:00:000 format
     * @param time
     * @return
     */
    public static String chronoFormatter(long time){

        int seconds = (int) (time / 1000);
        int minutes = seconds / 60;
        seconds = seconds % 60;
        int milliSeconds = (int) (time % 1000);
        return String.format("%02d", minutes)+ ":"
                        + String.format("%02d", seconds) + ":"
                        + String.format("%03d", milliSeconds);
    }
}
