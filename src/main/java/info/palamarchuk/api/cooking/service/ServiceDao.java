package info.palamarchuk.api.cooking.service;

import java.io.Serializable;
import java.util.Optional;

public interface ServiceDao<T extends Serializable> {
    Optional<T> getById(long id);

    T add(T entity);

    void update(T entity);

    void deleteById(long id);

    boolean existsById(long id);
}
