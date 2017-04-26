package info.palamarchuk.api.cooking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ErrorResponseData extends ResponseData {

    private List<Error> errors;

    public ErrorResponseData(List<ObjectError> errors) {
        this.errors = errors.stream().map(x -> {
            Error exportError = new Error(x.getCode());
            if(x instanceof FieldError) {
                FieldError fieldError = (FieldError)x;
                exportError.setField(fieldError.getField());
            }
            return exportError;

        }).collect(Collectors.toList());
    }

    public List getErrors() {
        return errors;
    }

    public ResponseEntity export(HttpStatus httpStatus) {
        return new ResponseEntity(this, httpStatus);
    }
}
