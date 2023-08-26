package ra.project_md4_shopnoithat.service;

import java.util.List;

public interface IGenericService<T,E> {
    List<T> findAll();
    void save(T t);
    T findById(E id);
    void delete(E id);
}
