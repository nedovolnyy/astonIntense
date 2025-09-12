/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import model.BaseEntity;
import java.util.List;

/**
 *
 * @author AKrot
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {

    T getById(int id) throws SQLException, ClassNotFoundException;

    List<T> getAll() throws SQLException, ClassNotFoundException;

    void insert(T entity)  throws SQLException;

    void update(T entity)  throws SQLException;

    void delete(T entity)  throws SQLException;

}
