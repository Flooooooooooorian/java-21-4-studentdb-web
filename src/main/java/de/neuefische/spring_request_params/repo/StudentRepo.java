package de.neuefische.spring_request_params.repo;

import de.neuefische.spring_request_params.model.Student;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class StudentRepo {
    private List<Student> students = new ArrayList<>();

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public Student add(Student student) {
        students.add(student);
        return student;
    }

    public void clear(){
        students.clear();
    }

    public Optional<Student> findById(String id) {
        for (Student student: students) {
            if (student.getId().equals(id)) {
                return Optional.of(student);
            }
        }
        return Optional.empty();
    }

    public void delete(Student student) {
        students.remove(student);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentRepo that = (StudentRepo) o;
        return Objects.equals(students, that.students);
    }

    @Override
    public int hashCode() {
        return Objects.hash(students);
    }


    @Override
    public String toString() {
        return "StudentRepo{" +
                "students=" + students +
                '}';
    }
}
