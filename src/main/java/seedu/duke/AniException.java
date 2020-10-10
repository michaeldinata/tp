package seedu.duke;

public class AniException extends Exception {
    protected String message;

    /**
     * Constructor for exception message.
     *
     * @param message exception message.
     */
    public AniException(String message) {
        super(message);
        this.message = message;
    }
}
