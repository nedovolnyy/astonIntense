/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import java.sql.SQLException;
import model.BaseEntity;
import lombok.AllArgsConstructor;
import java.util.List;
import repository.BaseDao;
import utils.enums.Operation;

/**
 *
 * @author AKrot
 * @param <T>
 */
@AllArgsConstructor
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private final BaseDao<T> baseDao;

    public T getById(int id) throws SQLException, ClassNotFoundException {
        return baseDao.getById(id);
    }

    public T getById(String id) throws SQLException, ClassNotFoundException {
        return baseDao.getById(id);
    }

    public List<T> getAll() throws SQLException, ClassNotFoundException {
        return baseDao.getAll();
    }

    public Operation insert(T entity) throws SQLException {
        return baseDao.insert(entity);
    }

    public Operation update(T entity) throws SQLException {
        return baseDao.update(entity);
    }

    public Operation delete(T entity) throws SQLException {
        return baseDao.delete(entity);
    }
}
