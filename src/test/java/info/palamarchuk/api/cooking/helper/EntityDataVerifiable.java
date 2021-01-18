package info.palamarchuk.api.cooking.helper;

public interface EntityDataVerifiable<T> {
    /**
     * Verifies entity's own fields. Assert that it equals the data from this context.
     * Our services do not update related objects in the entities, whereas the foreign keys are updated.
     * This behavior should be considered as on purpose for this app, because the controllers never send data in response
     *  to POST and PATCH requests (only location of an affected resource).
     *  For the tests it means we have to skip verifying relations after performing add/update operations.
     * @param entity
     */
    void verify(T entity);

    /**
     * Fills the entity with the data from this context.
     * @param entity
     */
    void fill(T entity);
}
