package lesson8;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class MainClass {
    public static void main(String[] args) {

        SessionFactory factory = new Configuration()
                .configure("hibernate.cfg.xml")
                .buildSessionFactory();

        StudentRepository repo = new StudentRepository(factory);

        Student stud = new Student(0L, "Иванов Иван", 17);
        repo.save(stud);
        Student st = repo.getById(1L);

        repo.closeFactory();
    }
}
