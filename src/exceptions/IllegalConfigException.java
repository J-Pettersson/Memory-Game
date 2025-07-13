package exceptions;

public class IllegalConfigException extends IllegalArgumentException {
    public IllegalConfigException(String str) {
        super(str);
    }
}