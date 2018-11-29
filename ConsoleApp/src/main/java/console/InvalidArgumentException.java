package console;

/**
 * Used to tell the main class that the arguments were incorrect
 */
class InvalidArgumentException extends Exception {

    private final ArgumentExceptions mCode;

    InvalidArgumentException(String error, ArgumentExceptions code) {
        super(error);
        this.mCode = code;
    }

    ArgumentExceptions getCode() {
        return this.mCode;
    }
}
