package uz.pdp.minimarket.exception;

public class FirebaseConnectionException extends RuntimeException {
    public FirebaseConnectionException(String firebaseException) {
        super(firebaseException);
    }
}
