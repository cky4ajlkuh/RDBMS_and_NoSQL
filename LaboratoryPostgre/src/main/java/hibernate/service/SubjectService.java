package hibernate.service;

import hibernate.dao.Impl.SubjectDaoImpl;
import hibernate.entity.Subject;

import java.util.List;

public class SubjectService {

    private final SubjectDaoImpl subjectDao;

    public SubjectService(SubjectDaoImpl subjectDao) {
        this.subjectDao = subjectDao;
    }

    public Subject findSubjectById(int id) {
        return subjectDao.findById(id);
    }

    public void saveAlbum(Subject subject) {
        subjectDao.save(subject);
    }

    public void updateAlbum(Subject subject) {
        subjectDao.update(subject);
    }

    public void deleteAlbum(Subject subject) {
        subjectDao.delete(subject);
    }

    public List<Subject> findAllAlbums() {
        return subjectDao.findAll();
    }

}
