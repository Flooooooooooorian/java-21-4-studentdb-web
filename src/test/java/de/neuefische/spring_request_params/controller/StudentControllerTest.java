package de.neuefische.spring_request_params.controller;

import de.neuefische.spring_request_params.model.Student;
import de.neuefische.spring_request_params.repo.StudentRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class StudentControllerTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepo studentRepo;

    @BeforeEach
    public void clear(){
        studentRepo.clear();
    }

    @Test
    public void listStudentsShouldReturnAllStudents() {

        //GIVEN
        String url = "http://localhost:" + port + "/student";
        studentRepo.add(new Student("1", "Klara"));
        studentRepo.add(new Student("2", "Franz"));

        //WHEN
        ResponseEntity<Student[]> response = restTemplate.getForEntity(url, Student[].class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(), arrayContainingInAnyOrder(
                new Student("1", "Klara"),
                new Student("2", "Franz")
        ));
    }

    @Test
    public void addStudentShouldAddStudentToDbPOST(){
        //GIVEN
        String url = "http://localhost:" + port + "/student/9";
        Student studentToAdd = new Student("9", "Hannes");

        //WHEN
        ResponseEntity<Student> response = restTemplate.postForEntity(url, studentToAdd, Student.class);

        //THEN
        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        assertThat(response.getBody(),is(studentToAdd));
        Optional<Student> optionalSavedStudent = studentRepo.findById("9");
        assertThat(optionalSavedStudent.get(),is(studentToAdd));
    }

    @Test
    public void deleteStudentShouldRemoveStudentFromDb(){
        //GIVEN
        studentRepo.add(new Student("1", "Klara"));
        studentRepo.add(new Student("2", "Franz"));
        String url = "http://localhost:" + port + "/student/2";

        //WHEN
        restTemplate.delete(url);

        //THEN
        assertThat(studentRepo.findById("1"), is(Optional.of(new Student("1", "Klara"))));
        assertThat(studentRepo.findById("2"), is(Optional.empty()));
    }


    //Test PUT Endpoint

//    @Test
//    public void addStudentShouldAddStudentToDbPUT(){
//        //GIVEN
//        String url = "http://localhost:" + port + "/student/9";
//        Student studentToAdd = new Student("9", "Hannes");
//
//        //WHEN
//        HttpEntity<Student> entity = new HttpEntity<>(studentToAdd);
//        ResponseEntity<Student> response = restTemplate.exchange(url, HttpMethod.PUT, entity, Student.class);
//
//        //THEN
//        assertThat(response.getStatusCode(), is(HttpStatus.OK));
//        assertThat(response.getBody(),is(studentToAdd));
//        Optional<Student> optionalSavedStudent = studentRepo.findById("9");
//        assertThat(optionalSavedStudent.get(),is(studentToAdd));
//    }



}