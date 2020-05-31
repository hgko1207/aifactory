package ins.core.service;

import java.util.List;

import ins.core.entity.EntityPage;

public interface InsBaseService <T, C> {
    void insert(T entity);

    void update(T entity);

    void insertOrUpdate(T entity);

    int delete(T entity);

    int deleteAll();

    boolean exist(T entity);

    int searchAllCount();

    EntityPage<T> searchAll(C criterion);

    int searchCount(C criterion);

    EntityPage<T> search(C criterion);

    List<T> listAll(C criterion);

    List<T> list(C criterion);

    T detail(T entity);
}