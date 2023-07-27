package uz.pdp.minimarket.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(String userNotFound) {
    }
}
