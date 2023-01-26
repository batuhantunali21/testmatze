package Control;

public class Timer {
    private static final long NANO_2_MILLI = 1000000;

    // finale Attribute (Konst.)

    // sonst. Attribute
    private static long starTime = System.nanoTime();

    // Konstruktoren

    private Timer () {

    }

    /**

     This method starts the time measurement
     */
    public static void start() {
        starTime = System.nanoTime();
    }

    /**

     This method returns the elapsed time in milliseconds
     @return elapsed time in milliseconds
     */
    public static long  getElapsedTimeInMilliseconds () {

        return (System.nanoTime() - starTime) / NANO_2_MILLI;
    }
}
