package gr.athtech.groupName.rentonow.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class NotFoundException extends Exception {
    private static final long serialVersionUID = -451071012316112083L;

    private String message;
    private HttpStatus status;

    public NotFoundException() {
        super();
    }

    public NotFoundException(String message) {
        super();
        this.message = message;
    }
}
