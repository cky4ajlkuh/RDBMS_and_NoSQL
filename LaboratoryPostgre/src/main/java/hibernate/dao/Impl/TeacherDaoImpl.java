package hibernate.dao.Impl;

import hibernate.dao.Dao;
import hibernate.entity.Teacher;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class TeacherDaoImpl implements Dao<Teacher> {

    private final SessionFactory sessionFactory;

    public TeacherDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Teacher findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Teacher.class, id);
        }
    }

    @Override
    public void save(Teacher teacher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(teacher);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Teacher teacher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(teacher);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(Teacher teacher) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(teacher);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Teacher> findAll() {
        return (List<Teacher>) sessionFactory.openSession().createQuery("From Teacher").list();
    }
}
