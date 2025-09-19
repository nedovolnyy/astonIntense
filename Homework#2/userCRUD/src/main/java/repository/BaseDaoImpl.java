/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import model.BaseEntity;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import utils.enums.Operation;

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

    public T getById(Integer id) throws SQLException, ClassNotFoundException {
        try (var session = sessionFactory.openSession()) {
            var className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            var classT = Class.forName(className);
            return (T) session.find(classT, id);
        }
    }

    public T getById(String uuid) throws SQLException, ClassNotFoundException {
        try (var session = sessionFactory.openSession()) {
            var className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            var classT = Class.forName(className);
            return (T) session.getReference(classT, uuid);
        }
    }

    public List<T> getAll() throws SQLException, ClassNotFoundException {
        try (var session = sessionFactory.openSession()) {
            var className = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
            var classT = Class.forName(className);
            var query = session.createQuery("from " + className, classT);
            final List<T> tempListT = new LinkedList<>();
            for (final Object o : query.list()) {
                tempListT.add((T) o);
            }
            return tempListT;
        }
    }

    public Operation insert(T entity) throws SQLException {
        return choiceMethod(Operation.INSERT, entity);
    }

    public Operation update(T entity) throws SQLException {
        return choiceMethod(Operation.UPDATE, entity);
    }

    public Operation delete(T entity) throws SQLException {
        return choiceMethod(Operation.DELETE, entity);
    }

    private Operation choiceMethod(Operation operationCode, T entity) throws SQLException {
        Transaction transaction = null;
        try (var session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            switch (operationCode) {
                case INSERT:
                    session.persist(entity);
                    transaction.commit();
                    return Operation.INSERT;
                case UPDATE:
                    session.merge(entity);
                    transaction.commit();
                    return Operation.UPDATE;
                case DELETE:
                    session.remove(entity);
                    transaction.commit();
                    return Operation.DELETE;
                default: {
                }
            }
        } catch (Exception e) {
            if (transaction != null && transaction.getRollbackOnly()) {
                transaction.rollback();
            }
            return Operation.QUIT;
        }
        return Operation.NONE;
    }
}
