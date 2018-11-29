package console;

/**
 * Used to tell the main class that the arguments were incorrect
 */
class InvalidArgumentException extends Exception {

    /**
     * Class constructor
     *
     * @param error The error message
     */
    InvalidArgumentException(String error) {
        super(error);
    }
}
