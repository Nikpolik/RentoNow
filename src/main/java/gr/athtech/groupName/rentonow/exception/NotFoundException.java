package gr.athtech.groupName.rentonow.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class NotFoundException extends Exception {
    private static final long serialVersionUID = -451071012316112083L;

    private String message;
    private HttpStatus status;

    public NotFoundException(HttpStatus notFound, String s) {
        super();
    }

    public NotFoundException(String message) {
        super();
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
