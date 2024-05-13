package hibernate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SequenceGenerator(name = "school_id_seq", sequenceName = "school_id_seq", allocationSize = 1)
@Entity
@Table(name = "artists")
public class School {
    @Id
    @GeneratedValue(generator = "school_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "artist", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Teacher> teachers;

    public School(String name) {
        this.name = name;
        teachers = new ArrayList<>();
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public void addTeacher(Teacher teacher) {
        teacher.setSchool(this);
        teachers.add(teacher);
    }

    public void removeAlbum(Teacher teacher) {
        teachers.remove(teacher);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    @Override
    public String toString() {
        return "Artist: " +
                "\n Name: " + name + "\n";
    }

}
