/**
 *
 */
package ch.wellernet.hibernate.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaQuery;

import ch.wellernet.hibernate.model.IdentifyableObject;

/**
 * @author Lucien Weller <lucien@wellernet.ch>
 */
public abstract class AbstractJpaDaoImpl<ID, T extends IdentifyableObject<ID>> implements GenericDao<ID, T> {

    @PersistenceContext
    protected EntityManager entityManager;

    /**
     * @see ch.wellernet.hibernate.dao.GenericDao#attach(ch.wellernet.hometv.util.model.IdentifyableObject)
     */
    @Override
    public void attach(T object) {
        entityManager.persist(object);
    }

    /**
     * @see ch.wellernet.hibernate.dao.GenericDao#delete(java.lang.Object)
     */
    @Override
    public void delete(ID id) {
        entityManager.remove(find(id));
    }

    /**
     * @see ch.wellernet.hibernate.dao.GenericDao#find(java.lang.Object)
     */
    @Override
    public T find(ID id) {
        return entityManager.find(getType(), id);
    }

    /**
     * @see ch.wellernet.hibernate.dao.GenericDao#findAll()
     */
    @Override
    public List<T> findAll() {
        CriteriaQuery<T> query = entityManager.getCriteriaBuilder().createQuery(getType());
        query.from(getType());
        return entityManager.createQuery(query).getResultList();
    }

    protected abstract Class<T> getType();
}
