package hibernate.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@SequenceGenerator(name = "subject_id_seq", sequenceName = "subject_id_seq", allocationSize = 1)
@Entity
@Table(name = "Subject")
public class Subject {
    @Id
    @GeneratedValue(generator = "subject_id_seq")
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "teacher_id")
    private Teacher teacher;

    public Subject(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getAlbum() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Song: " +
                "\n Name: " + name;
    }

}
