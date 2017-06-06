package info.palamarchuk.api.cooking;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Temporary class for holding the response.
 */
public class ResponseData<T> {

    private T data;

    public ResponseData() {
    }

    public ResponseData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public ResponseEntity<ResponseData<T>> export() {
        return ResponseEntity.ok(this);
    }
}
