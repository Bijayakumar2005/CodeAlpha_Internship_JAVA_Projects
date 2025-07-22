package model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class GradeManager {
    private List<Student> students;
    private List<Course> courses;
    
    public GradeManager() {
        students = new ArrayList<>();
        courses = new ArrayList<>();
    }
    
    public void addCourse(Course course) {
        courses.add(course);
    }
    
    public void addStudent(Student student) {
        students.add(student);
    }
    
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }
    
    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }
    
    public Student getStudentById(String id) {
        for (Student student : students) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }
    
    public double getClassAverage() {
        if (students.isEmpty()) return 0;
        double sum = 0;
        for (Student student : students) {
            sum += student.calculateAverage();
        }
        return sum / students.size();
    }
    
    public Student getTopStudent() {
        if (students.isEmpty()) return null;
        return Collections.max(students, Comparator.comparing(Student::calculateAverage));
    }
    
    public Student getLowestStudent() {
        if (students.isEmpty()) return null;
        return Collections.min(students, Comparator.comparing(Student::calculateAverage));
    }
    
    public String generateReport() {
        StringBuilder report = new StringBuilder();
        report.append("Grade Summary Report\n");
        report.append("====================\n");
        report.append(String.format("%-10s %-20s %-10s %-10s\n", "ID", "Name", "Average", "Status"));
        
        for (Student student : students) {
            double avg = student.calculateAverage();
            String status = avg >= 60 ? "Pass" : "Fail";
            report.append(String.format("%-10s %-20s %-10.2f %-10s\n", 
                student.getId(), student.getName(), avg, status));
        }
        
        report.append("\nClass Statistics:\n");
        report.append(String.format("Class Average: %.2f\n", getClassAverage()));
        report.append(String.format("Top Student: %s (%.2f)\n", 
            getTopStudent().getName(), getTopStudent().calculateAverage()));
        report.append(String.format("Lowest Student: %s (%.2f)\n", 
            getLowestStudent().getName(), getLowestStudent().calculateAverage()));
        
        return report.toString();
    }
}