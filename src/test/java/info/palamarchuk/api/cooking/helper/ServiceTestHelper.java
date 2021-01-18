package info.palamarchuk.api.cooking.helper;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;

import java.util.Optional;

import org.springframework.stereotype.Component;

import info.palamarchuk.api.cooking.entity.IdNumerableEntity;
import info.palamarchuk.api.cooking.service.ServiceDao;

@Component
public class ServiceTestHelper<T extends IdNumerableEntity> {

    public void assertAdding(ServiceDao<T> service, T entity, EntityDataVerifiable<T> verifier) {
        service.add(entity);
        verifier.verify(getEntity(service, entity).get());
    }

    public void assertUpdating(ServiceDao<T> service, T entity, EntityDataVerifiable<T> verifier) {
        verifier.fill(entity);
        service.update(entity); // ensure the entity object has all the values set after update
        verifier.verify(entity);

        var savedRecipe = getEntity(service, entity);
        verifier.verify(savedRecipe.get());
    }

    public void assertDeleting(ServiceDao<T> service, Long id) {
        var entity = service.getById(id); // ensure ingredient is there
        assertThat(entity.get().getId(), notNullValue());

        service.deleteById(id);
        assertThat(service.getById(id), is(nullValue()));

    }

    private Optional<T> getEntity(ServiceDao<T> service, T entity) {
        return service.getById(entity.getId().longValue());
    }
}
