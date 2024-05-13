package hibernate.dao.Impl;

import hibernate.dao.Dao;
import hibernate.entity.School;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class SchoolDaoImpl implements Dao<School> {

    private final SessionFactory sessionFactory;

    public SchoolDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public School findById(int id) {
        try (Session session = sessionFactory.openSession()) {
            return session.get(School.class, id);
        }
    }

    @Override
    public void save(School school) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(school);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void update(School school) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(school);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public void delete(School school) {
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(school);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public List<School> findAll() {
        return (List<School>) sessionFactory.openSession().createQuery("From School").list();
    }
}
