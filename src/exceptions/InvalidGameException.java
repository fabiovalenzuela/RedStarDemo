package exceptions;

import java.io.IOException;

public class InvalidGameException extends IOException {
    public InvalidGameException() {
        super();
    }

    public InvalidGameException(String msg) {
        super(msg);
    }

}
