package edu.tcu.cs.kayscollectiononline.wizard;

public class WizardNotFoundException extends RuntimeException{
    public WizardNotFoundException() {
        super();
    }

    public WizardNotFoundException(String message) {
        super(message);
    }

    public WizardNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public WizardNotFoundException(Throwable cause) {
        super(cause);
    }

    protected WizardNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
