package uz.pdp.minimarket.exception;

public class SmsServiceBroken extends RuntimeException {
  public SmsServiceBroken(String s){
        super(s);
    }
}
