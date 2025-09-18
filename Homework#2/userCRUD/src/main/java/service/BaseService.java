/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package service;

import java.sql.SQLException;
import java.util.List;
import model.BaseEntity;
import utils.enums.Operation;

/**
 *
 * @author AKrot
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {

    T getById(int id) throws SQLException, ClassNotFoundException;
    
    T getById(String id) throws SQLException, ClassNotFoundException;

    List<T> getAll() throws SQLException, ClassNotFoundException;

    Operation insert(T entity) throws SQLException;

    Operation update(T entity) throws SQLException;

    Operation delete(T entity) throws SQLException;

}
