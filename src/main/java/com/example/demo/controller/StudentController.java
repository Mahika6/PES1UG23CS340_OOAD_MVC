package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;

@Controller
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("student", new Student());
        return "index";
    }

    @PostMapping("/students")
    public String addStudent(@ModelAttribute Student student, Model model) {
        try {
            studentService.addStudent(student);
        } catch (RuntimeException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("student", student);
            model.addAttribute("students", studentService.getAllStudents());
            return "index";
        }
        return "redirect:/";
    }

    @GetMapping("/api/students")
    @ResponseBody
    public List<Student> getStudents(@RequestParam(required = false) String course) {
        if (course != null && !course.isBlank()) {
            return studentService.getStudentsByCourse(course);
        }
        return studentService.getAllStudents();
    }

    @GetMapping("/api/students/{id}")
    @ResponseBody
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(studentService.getStudentById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/api/students")
    @ResponseBody
    public ResponseEntity<?> createStudent(@RequestBody Student student) {
        try {
            return ResponseEntity.ok(studentService.addStudent(student));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}