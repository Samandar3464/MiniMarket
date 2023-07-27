package uz.pdp.minimarket.exception;

public class RecordNotFoundException extends RuntimeException{
    public RecordNotFoundException(String name){
        super(name);
    }
}
