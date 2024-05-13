package hibernate.dao.Impl;

import hibernate.dao.Dao;
import hibernate.entity.Subject;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SubjectDaoImpl implements Dao<Subject> {

    private final SessionFactory sessionFactory;

    public SubjectDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Subject findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(Subject.class, id);
        }
    }

    @Override
    public void save(Subject subject) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(subject);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(Subject subject) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(subject);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(Subject subject) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(subject);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<Subject> findAll() {
        return (List<Subject>) sessionFactory.openSession().createQuery("From Subject").list();
    }
}
