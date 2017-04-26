package info.palamarchuk.api.cooking.dao;

import java.io.Serializable;
import java.util.List;

public interface IGenericDAO<T extends Serializable> {
    public void setClazz(Class<T> clazzToSet);

    T findOne(final int id);

    T findOne(final long id);

    List<T> findAll();

    void create(final T entity);

    T update(final T entity);

    void delete(final T entity);

    void deleteById(final long entityId);
}