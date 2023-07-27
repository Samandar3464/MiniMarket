package uz.pdp.minimarket.exception;

public class RecordAlreadyExistException extends RuntimeException{
    public RecordAlreadyExistException(String name){
        super(name);
    }
}
