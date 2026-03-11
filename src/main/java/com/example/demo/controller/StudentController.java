package com.example.demo.controller;

import com.example.demo.model.Student;
import com.example.demo.service.StudentService;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
    public String addStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/";
    }

    @GetMapping("/api/students")
    @ResponseBody
    public List<Student> getStudents() {
        return studentService.getAllStudents();
    }

    @PostMapping("/api/students")
    @ResponseBody
    public Student createStudent(@RequestBody Student student) {
        return studentService.addStudent(student);
    }
}