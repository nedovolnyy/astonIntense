/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import model.BaseEntity;
import java.lang.reflect.ParameterizedType;
import java.sql.SQLException;
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
    private final Session session;

    public BaseDaoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
        session = sessionFactory.openSession();
    }

    @Override
    public T getById(int id) throws SQLException, ClassNotFoundException {
        var className = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
        var classT = Class.forName(className);
        return (T) session.getReference(classT, id);
    }

    @Override
    public List<T> getAll() throws SQLException, ClassNotFoundException {
        var className = ((ParameterizedType) this.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
        var classT = Class.forName(className);
        var query = session.createQuery("from " + className, classT);
        final List<T> tempListT = new LinkedList<>();
        for (final Object o : query.list()) {
            tempListT.add((T) o);
        }
        return tempListT;
    }

    @Override
    public void insert(T entity) throws SQLException {
        var transaction = session.getTransaction();
        transaction.begin();
        session.persist(entity);
        transaction.commit();
    }

    @Override
    public void update(T entity) throws SQLException {
        var transaction = session.getTransaction();
        transaction.begin();
        session.merge(entity);
        transaction.commit();
    }

    @Override
    public void delete(T entity) throws SQLException {
        var transaction = session.getTransaction();
        transaction.begin();
        session.remove(entity);
        transaction.commit();
    }
}
