package lesson8;

public interface Repositoriable<T> {
    T getById(Long id);
    void save(T entity);
    void delete(T entity);
}
