/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package repository;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import lombok.AllArgsConstructor;
import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import utils.HibernateUtil;
import utils.enums.Operation;

/**
 *
 * @author AKrot
 */
@AllArgsConstructor
public class UserDaoImpl implements UserDao {

    private final SessionFactory sessionFactory;

    public UserDaoImpl() {
        sessionFactory = HibernateUtil.getSessionFactory();
    }

    public User getById(Integer id) throws SQLException {
        try (var session = sessionFactory.openSession()) {
            return (User) session.find(User.class, id);
        }
    }

    public List<User> getAll() throws SQLException {
        try (var session = sessionFactory.openSession()) {
            var query = session.createQuery("from User", User.class);
            final List<User> tempListT = new LinkedList<>();
            for (final Object o : query.list()) {
                tempListT.add((User) o);
            }
            return tempListT;
        }
    }

    public Operation insert(User user) throws SQLException {
        return choiceMethod(Operation.INSERT, user);
    }

    public Operation update(User user) throws SQLException {
        return choiceMethod(Operation.UPDATE, user);
    }

    public Operation delete(User user) throws SQLException {
        return choiceMethod(Operation.DELETE, user);
    }

    private Operation choiceMethod(Operation operationCode, User user) throws SQLException {
        Transaction transaction = null;
        try (var session = sessionFactory.openSession()) {
            transaction = session.getTransaction();
            transaction.begin();
            switch (operationCode) {
                case INSERT:
                    user.setId(null);
                    session.persist(user);
                    transaction.commit();
                    return Operation.INSERT;
                case UPDATE:
                    session.merge(user);
                    transaction.commit();
                    return Operation.UPDATE;
                case DELETE:
                    session.remove(user);
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
