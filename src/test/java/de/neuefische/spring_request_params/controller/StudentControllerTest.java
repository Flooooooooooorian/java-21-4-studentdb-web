package de.neuefische.spring_request_params.controller;

import de.neuefische.spring_request_params.model.Student;
import de.neuefische.spring_request_params.repo.StudentRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

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

}