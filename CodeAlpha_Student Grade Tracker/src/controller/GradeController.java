package controller;

import java.util.List;  
import model.Student;
import model.Course;
import model.GradeManager;

public class GradeController {
    private GradeManager gradeManager;
    
    public GradeController() {
        gradeManager = new GradeManager();
    }
    
    public void addStudent(Student student) {
        gradeManager.addStudent(student);
    }
    
    public void addCourse(Course course) {
        gradeManager.addCourse(course);
    }
    
    public Student getStudentById(String id) {
        return gradeManager.getStudentById(id);
    }
    
    public void setStudentGrade(String studentId, int courseIndex, double grade) {
        Student student = gradeManager.getStudentById(studentId);
        if (student != null) {
            student.setGrade(courseIndex, grade);
        }
    }
    
    public List<Student> getAllStudents() {
        return gradeManager.getAllStudents();
    }
    
    public List<Course> getAllCourses() {
        return gradeManager.getAllCourses();
    }
    
    public int getCourseCount() {
        return gradeManager.getAllCourses().size();
    }
    
    public String generateReport() {
        return gradeManager.generateReport();
    }
}