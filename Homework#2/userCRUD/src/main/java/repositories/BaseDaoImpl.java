/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repositories;

import entities.BaseEntity;
import java.lang.reflect.ParameterizedType;
import java.util.LinkedList;
import java.util.List;
import utils.HibernateUtil;
import lombok.AllArgsConstructor;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author AKrot
 * @param <T>
 */
@AllArgsConstructor
public class BaseDaoImpl<T extends BaseEntity> implements BaseDao<T> {

    private final SessionFactory sessionFactory;

    public BaseDaoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    @Override
    public T getById(int id) {
        try (var session = sessionFactory.openSession()) {
            try {
                var className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
                var classT = Class.forName(className);
                return (T) session.getReference(classT, id);
            } catch (Exception e) {
                throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
            }
        }
    }

    @Override
    public List<T> getAll() {
        try {
            var className = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            var classT = Class.forName(className);
            try (var session = sessionFactory.openSession()) {
                var query = session.createQuery("from " + className, classT);
                final List<T> tempListT = new LinkedList<>();
                for (final Object o : query.list()) {
                    tempListT.add((T) o);
                }
                return tempListT;
            }
        } catch (Exception e) {
            throw new IllegalStateException("Class is not parametrized with generic type!!! Please use extends <> ");
        }
    }

    @Override
    public void insert(T entity) {
        try (Session session = sessionFactory.openSession()) {
            var transaction = session.getTransaction();
            transaction.begin();
            session.persist(entity);
            transaction.commit();
        }
    }

    @Override
    public void update(T entity) {
        try (Session session = sessionFactory.openSession()) {
            var transaction = session.getTransaction();
            transaction.begin();
            session.merge(entity);
            transaction.commit();
        }
    }

    @Override
    public void delete(T entity) {
        try (var session = sessionFactory.openSession()) {
            var transaction = session.getTransaction();
            transaction.begin();
            session.remove(entity);
            transaction.commit();
        }
    }
}
