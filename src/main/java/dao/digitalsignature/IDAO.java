package dao.digitalsignature;

import bean.digitalsignature.Keys;

import java.util.List;

public interface IDAO<T> {
    List<T> getAll();

    T get(int id);

    boolean insert(T t);

    boolean update(T t);


}
