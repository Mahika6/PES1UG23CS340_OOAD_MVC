package com.example.demo.service;

import com.example.demo.model.Student;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student addStudent(Student student) {
        if (student.getName() == null || student.getName().isBlank()) {
            throw new RuntimeException("Name is required.");
        }
        if (student.getEmail() == null || student.getEmail().isBlank()) {
            throw new RuntimeException("Email is required.");
        }
        if (student.getCourse() == null || student.getCourse().isBlank()) {
            throw new RuntimeException("Course is required.");
        }
        if (studentRepository.existsByEmail(student.getEmail())) {
            throw new RuntimeException("Email already registered!");
        }
        return studentRepository.save(student);
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student not found with id: " + id));
    }

    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }

}