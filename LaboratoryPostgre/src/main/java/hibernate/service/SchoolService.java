package hibernate.service;

import hibernate.dao.Impl.SchoolDaoImpl;
import hibernate.entity.School;

import java.util.List;

public class SchoolService {
    private final SchoolDaoImpl schoolDao;

    public SchoolService(SchoolDaoImpl schoolDao) {
        this.schoolDao = schoolDao;
    }

    public School findSchoolById(int id) {
        return schoolDao.findById(id);
    }

    public void saveAlbum(School school) {
        schoolDao.save(school);
    }

    public void updateAlbum(School school) {
        schoolDao.update(school);
    }

    public void deleteAlbum(School school) {
        schoolDao.delete(school);
    }

    public List<School> findAllAlbums() {
        return schoolDao.findAll();
    }

}
