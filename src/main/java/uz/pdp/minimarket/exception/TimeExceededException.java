package uz.pdp.minimarket.exception;

public class TimeExceededException extends RuntimeException{
    public TimeExceededException(String massage) {
        super(massage);
    }
}
