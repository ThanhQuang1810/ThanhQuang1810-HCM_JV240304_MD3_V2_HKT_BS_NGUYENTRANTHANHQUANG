package ra.service;

import java.util.List;

public interface IGenericService <T,E>{
List <T> findAll();
T findById(E e);
void save(T t);
void delete(E e);
}
