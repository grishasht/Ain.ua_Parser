package model.jdbc;

import java.util.List;

public interface Dao<T> extends AutoCloseable{
    void create(T entity);

    List<T> readAll();

    Integer getId(T entity);
}
