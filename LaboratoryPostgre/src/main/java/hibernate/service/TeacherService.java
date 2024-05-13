package hibernate.service;

import hibernate.dao.Impl.TeacherDaoImpl;
import hibernate.entity.Teacher;

import java.util.List;

public class TeacherService {

    private final TeacherDaoImpl teacherDao;

    public TeacherService(TeacherDaoImpl teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher findSubjectById(int id) {
        return teacherDao.findById(id);
    }

    public void saveAlbum(Teacher teacher) {
        teacherDao.save(teacher);
    }

    public void updateAlbum(Teacher teacher) {
        teacherDao.update(teacher);
    }

    public void deleteAlbum(Teacher teacher) {
        teacherDao.delete(teacher);
    }

    public List<Teacher> findAllAlbums() {
        return teacherDao.findAll();
    }

}
