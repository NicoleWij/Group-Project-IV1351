package java.se.kth.iv1351.soundgoodmusicschool.integration;


public class SchoolDBException extends Exception {
    /**
     * Creates a new exception based on the cause
     * @param reason The cause of the exception
     */
    public SchoolDBException(String reason) {
        super(reason);
    }
    /**
     * Create a new instance thrown because of the specified reason and exception.
     *
     * @param reason    Why the exception was thrown.
     * @param rootCause The exception that caused this exception to be thrown.
     */
    public SchoolDBException(String reason, Throwable rootCause) {
        super(reason, rootCause);
    }
}
