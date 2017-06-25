package info.palamarchuk.api.cooking.helper;

import java.io.Serializable;

public interface EntityDataVerifiable<T> {
    /**
     * Asserts the entity contains exactly the data from this context.
     * @param entity
     */
    void verify(T entity);

    /**
     * Fills the entity with the data from this context.
     * @param entity
     */
    void fill(T entity);
}
