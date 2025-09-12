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

/**
 *
 * @author AKrot
 * @param <T>
 */
@AllArgsConstructor
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private final BaseDao<T> baseDao;

    @Override
    public T getById(int id) throws SQLException, ClassNotFoundException {
        return baseDao.getById(id);
    }

    @Override
    public List<T> getAll() throws SQLException, ClassNotFoundException {
        return baseDao.getAll();
    }

    @Override
    public void insert(T entity) throws SQLException {
        baseDao.insert(entity);
    }

    @Override
    public void update(T entity) throws SQLException {
        baseDao.update(entity);
    }

    @Override
    public void delete(T entity) throws SQLException {
        baseDao.delete(entity);
    }
}
