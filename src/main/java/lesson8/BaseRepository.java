package lesson8;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.ParameterizedType;

public class BaseRepository <T> implements Repositoriable<T> {

    protected SessionFactory factory;
    Class parameterClass;

    private void CalculateParameterClass() {
        Class actualClass = this.getClass();
        ParameterizedType type = (ParameterizedType)actualClass.getGenericSuperclass();
        System.out.println(type); // java.util.ArrayList<java.lang.Float>
        parameterClass = (Class)type.getActualTypeArguments()[0];
    }

    // что еще за новости, фабрику не передавать при конструировании ?? запретим конструктор
    private BaseRepository() {}

    public BaseRepository(SessionFactory factory) {
        this.factory = factory;

        // Вычисляем класс параметра, для использования внутри hibernate/session методов
        CalculateParameterClass();
    }

    public void closeFactory() {
        if (factory != null) {
            factory.close();
        }
    }
    @Override
    public T getById(Long id) {
        if (factory != null) {
            Session session = factory.getCurrentSession();
            try {
                session.beginTransaction();
                T result = (T) session.get(parameterClass, id);
                session.getTransaction().commit();
                return result;
            } finally {
                session.close();
            }
        }
        return null;
    }

    @Override
    public void save(T entity) {
        if (factory != null) {
            Session session = factory.getCurrentSession();
            try {
                session.beginTransaction();
                session.save(entity);
                session.getTransaction().commit();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                session.close();
            }
        }
    }

    @Override
    public void delete(T entity) {
        if (factory != null) {
            Session session = factory.getCurrentSession();
            try {
                session.beginTransaction();
                session.delete(entity);
                session.getTransaction().commit();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
            finally {
                session.close();
            }
        }
    }
}
