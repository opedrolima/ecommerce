package org.example.Repository;

import java.util.List;

public interface IRepository<T> {
    T save(T entity);
    T findByID(String id);
    void deleteByID(String id);
    List<T> findAll();
    void update(T entity);
}

