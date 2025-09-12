/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package services;

import entities.BaseEntity;
import java.util.List;

/**
 *
 * @author AKrot
 * @param <T>
 */
public interface BaseService<T extends BaseEntity> {

    T getById(int id);

    List<T> getAll();

    void insert(T entity);

    void update(T entity);

    void delete(T entity);

}
