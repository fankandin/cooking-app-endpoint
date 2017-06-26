package info.palamarchuk.api.cooking.helper;

import info.palamarchuk.api.cooking.entity.IdNumerableEntity;
import info.palamarchuk.api.cooking.service.ServiceDao;
import org.springframework.stereotype.Component;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@Component
public class ServiceTestHelper<T extends IdNumerableEntity> {
    public void assertAdding(ServiceDao<T> service, T entity, EntityDataVerifiable<T> data) {
        service.add(entity);
        T savedEntity = getEntity(service, entity);
        data.verify(savedEntity);
    }

    public void assertUpdating(ServiceDao<T> service, T entity, EntityDataVerifiable<T> data) {
        data.fill(entity);
        service.update(entity); // ensure the entity object has all the values set after update
        data.verify(entity);

        T savedRecipe = getEntity(service, entity);
        data.verify(savedRecipe);
    }

    public void assertDeleting(ServiceDao<T> service, Long id) {
        T entity = service.getById(id); // ensure ingredient is there
        assertThat(entity.getId(), notNullValue());

        service.deleteById(id);
        assertThat(service.getById(id), is(nullValue()));

    }

    private T getEntity(ServiceDao<T> service, T entity) {
        Number id = entity.getId();
        return service.getById(id.longValue());
    }
}
