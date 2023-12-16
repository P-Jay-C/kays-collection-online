package edu.tcu.cs.kayscollectiononline.artifact;

public class ArtifactNotFoundException extends RuntimeException{
    public ArtifactNotFoundException() {
        super();
    }

    public ArtifactNotFoundException(String message) {
        super(message);
    }

    public ArtifactNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArtifactNotFoundException(Throwable cause) {
        super(cause);
    }

    protected ArtifactNotFoundException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
