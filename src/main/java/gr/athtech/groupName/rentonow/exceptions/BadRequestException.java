package gr.athtech.groupName.rentonow.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
@Data
public class BadRequestException extends Exception {
    private static final long serialVersionUID = -451071012316112083L;

    private String message;
    private HttpStatus status;

    public BadRequestException() {
        super();
    }

    public BadRequestException(String message) {
        super();
        this.message = message;
    }
}
