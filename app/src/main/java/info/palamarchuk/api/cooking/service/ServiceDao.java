package info.palamarchuk.api.cooking.service;

import java.io.Serializable;
import java.util.List;

public interface ServiceDao<T extends Serializable> {
    public T getById(long id);

    public T add(T entity);

    public void update(T entity);

    public void deleteById(long id);
}
