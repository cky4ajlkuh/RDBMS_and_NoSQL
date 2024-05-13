package hibernate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@SequenceGenerator(name = "teacher_id_seq", sequenceName = "teacher_id_seq", allocationSize = 1)
@Entity
@Table(name = "Teacher")
public class Teacher {
    @Id
    @GeneratedValue(generator = "teacher_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "teacher_id")
    private School school;
    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Subject> subjects;

    public Teacher(String name) {
        this.name = name;
        subjects = new ArrayList<>();
    }

    public void addSubject(Subject subject) {
        subject.setTeacher(this);
        subjects.add(subject);
    }

    public void removeSubject(Subject subject) {
        subjects.remove(subject);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public School getArtist() {
        return school;
    }

    public void setSchool(School school) {
        this.school = school;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "Album: " +
                "\n Name: " + name;
    }
}
