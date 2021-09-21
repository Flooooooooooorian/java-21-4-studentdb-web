package de.neuefische.spring_request_params.service;

import de.neuefische.spring_request_params.model.Student;
import de.neuefische.spring_request_params.repo.StudentRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentService {

    private StudentRepo studentRepo = new StudentRepo();

    public List<Student> list() {
        return studentRepo.getStudents();
    }
    public Student findById(String id) {
        Optional<Student> optionalStudent = studentRepo.findById(id);
        if (optionalStudent.isPresent()) {
            return optionalStudent.get();
        }
        throw new IllegalArgumentException("Id not found!");
    }

    public Student addStudent(Student student) {
        return studentRepo.add(student);
    }

    public List<Student> search(String s) {
        List<Student> searchResultList = new ArrayList<>();
        for (Student student: list()) {
            if (student.getName().contains(s)){
                searchResultList.add(student);
            }
        }

        return searchResultList;
    }

    public void delete(String id) {
        Student student = findById(id);
        studentRepo.delete(student);
    }
}
