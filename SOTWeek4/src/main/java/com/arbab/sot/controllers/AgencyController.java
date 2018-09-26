package com.arbab.sot.controllers;

import com.arbab.sot.dao.ApartmentDao;
import com.arbab.sot.dao.StudentDao;
import com.arbab.sot.models.Apartment;
import com.arbab.sot.models.Student;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class AgencyController {

    private ApartmentDao apartmentDao = new ApartmentDao();
    private StudentDao studentDao = new StudentDao();

    @GetMapping("/students")
    public ResponseEntity getStudents() {
        return new ResponseEntity(studentDao.getStudentList(), HttpStatus.OK);
    }

    @GetMapping("/apartments")
    public ResponseEntity getApartments() {
        return new ResponseEntity( apartmentDao.getApartmentList(), HttpStatus.OK);
    }

    // "/apartments/search?city=Eindhoven"
    @GetMapping("/apartments/search")
    public List<Apartment> getApartmentsByCity(@RequestParam(value = "city") String city) {
        return apartmentDao.getApartmentList().stream()
                .filter(apartment -> apartment.getCity().equals(city))
                .collect(Collectors.toList());
    }

    @PostMapping(value = "/apartments", consumes={"application/json"})
    public ResponseEntity createApartment(@RequestBody Apartment apartment) {
        apartmentDao.getApartmentList().add(apartment);
        return new ResponseEntity(apartment, HttpStatus.CREATED);
    }

    @PutMapping("/apartments/{apartmentId}/reserve/{studentId}")
    public ResponseEntity reserveApartment(@PathVariable int apartmentId, @PathVariable int studentId) {
        Apartment apartment = apartmentDao.getApartmentById(apartmentId);
        Student student = studentDao.getStudentById(studentId);

        if (apartment == null) {
            return new ResponseEntity("Can't find apartemtn", HttpStatus.NOT_FOUND);
        } else if (student == null) {
            return new ResponseEntity("Can't find user", HttpStatus.NOT_FOUND);
        }

        apartment.setAvailable(false);
        student.setAssignedApartment(apartment.getId());

        return new ResponseEntity("Apartment reserved!", HttpStatus.OK);
    }

    @DeleteMapping("/apartments/{id}")
    public ResponseEntity deleteApartment(@PathVariable int id) {
        Apartment apartment = apartmentDao.getApartmentById(id);
        apartmentDao.getApartmentList().remove(apartment);

        return new ResponseEntity("Apartment deleted!", HttpStatus.OK);
    }

    @DeleteMapping("/students/{id}")
    public boolean deleteStudent(@PathVariable int id) {
        Student student = studentDao.getStudentById(id);
        studentDao.getStudentList().remove(student);
        return true;
    }

}
