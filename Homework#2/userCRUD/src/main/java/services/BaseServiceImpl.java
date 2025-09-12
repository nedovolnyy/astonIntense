/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package services;

import entities.BaseEntity;
import lombok.AllArgsConstructor;
import java.util.List;
import repositories.BaseDao;

/**
 *
 * @author AKrot
 * @param <T>
 */
@AllArgsConstructor
public class BaseServiceImpl<T extends BaseEntity> implements BaseService<T> {

    private final BaseDao<T> baseDao;

    @Override
    public T getById(int id) {
        return baseDao.getById(id);
    }

    @Override
    public List<T> getAll() {
        return baseDao.getAll();
    }

    @Override
    public void insert(T entity) {
        baseDao.insert(entity);
    }

    @Override
    public void update(T entity) {
        baseDao.update(entity);
    }

    @Override
    public void delete(T entity) {
        baseDao.delete(entity);
    }
}
