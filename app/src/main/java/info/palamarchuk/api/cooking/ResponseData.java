package info.palamarchuk.api.cooking;

/**
 * Temporary class for holding the response.
 */
public class ResponseData<T> {

    private T data;

    /**
     * Constructror.
     * @param data the data
     */
    public ResponseData(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
