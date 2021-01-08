package gr.athtech.groupName.rentonow.exceptions;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
@Data
public class UploadException extends Exception {
    private static final long serialVersionUID = -451071012316112083L;

    private String message;

    public UploadException() {
        super();
    }

    public UploadException(String message) {
        super();
        this.message = message;
    }
}
