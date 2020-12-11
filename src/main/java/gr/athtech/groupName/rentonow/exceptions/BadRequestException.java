package gr.athtech.groupName.rentonow.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class BadRequestException extends Exception {
    private static final long serialVersionUID = -451071012316112083L;

    private String message;
    private HttpStatus status;

    public BadRequestException(HttpStatus badRequest, String s) {
        super();
    }

    public BadRequestException(String message) {
        super();
        this.message = message;
        this.status = HttpStatus.INTERNAL_SERVER_ERROR;
    }
}
