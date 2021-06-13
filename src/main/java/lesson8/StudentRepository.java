package lesson8;

import org.hibernate.SessionFactory;

public class StudentRepository extends BaseRepository<Student>{

    public StudentRepository(SessionFactory factory) {
        super(factory);
    }
}
