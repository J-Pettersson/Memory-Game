package exceptions;

public class IllegalGameException extends IllegalArgumentException {
    public IllegalGameException(String str) {
        super(str);
    }
}