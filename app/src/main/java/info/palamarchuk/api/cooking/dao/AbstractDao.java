package info.palamarchuk.api.cooking.dao;

import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.beans.Expression;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public abstract class AbstractDao<T extends Serializable> {

    private Class<T> clazz;

    @PersistenceContext
    EntityManager em;

    public final void setClazz(Class<T> clazzToSet) {
        this.clazz = clazzToSet;
    }

    public T findOne(long id) {
        return em.find(clazz, id);
    }

    public T findOne(int id) {
        return em.find(clazz, id);
    }

    public T findOne(short id) {
        return em.find(clazz, id);
    }

    public List<T> findAll() {
        return em.createQuery("from " + clazz.getName()).getResultList();
    }

    public List<T> findByAttributes(Map<String, String> attributes) {
        List<T> results;
        //set up the Criteria query
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<T> cq = cb.createQuery(clazz);
        Root<T> root = cq.from(clazz);

        List<Predicate> predicates = new ArrayList<>();
        for(String s : attributes.keySet()) {
            if(root.get(s) != null) {
                //predicates.add(cb.like((Expression) root.get(s), "%" + attributes.get(s) + "%" ));
                predicates.add(cb.equal(root.get(s), attributes.get(s)));
            }
        }
        cq.where(predicates.toArray(new Predicate[]{}));
        TypedQuery<T> q = em.createQuery(cq);

        results = q.getResultList();
        return results;
    }

    public void create(T entity) {
        em.persist(entity);
    }

    public T update(T entity) {
        return em.merge(entity);
    }

    public void delete(T entity) {
        em.remove(entity);
    }

    public void deleteById(int entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }

    public void deleteById(long entityId) {
        T entity = findOne(entityId);
        delete(entity);
    }
}
