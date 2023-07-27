package uz.pdp.minimarket.exception;

public class SmsSendingFailException extends RuntimeException {
    public SmsSendingFailException(String massage) {
        super(massage);
    }
}
