package view;

import java.util.List; 
import java.util.Scanner;
import model.Student;
import model.Course;
import controller.GradeController;

public class ConsoleUI {
    private GradeController controller;
    private Scanner scanner;
    
    public ConsoleUI(GradeController controller) {
        this.controller = controller;
        this.scanner = new Scanner(System.in);
    }
    
    public void start() {
        System.out.println("Student Grade Management System");
        System.out.println("===============================");
        
        // Setup courses
        setupCourses();
        
        // Main menu
        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Add Student");
            System.out.println("2. Enter Grades");
            System.out.println("3. View Student Report");
            System.out.println("4. View Class Summary");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    enterGrades();
                    break;
                case 3:
                    viewStudentReport();
                    break;
                case 4:
                    viewClassSummary();
                    break;
                case 5:
                    System.out.println("Exiting system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    private void setupCourses() {
        System.out.print("\nEnter number of courses: ");
        int numCourses = scanner.nextInt();
        scanner.nextLine();
        
        for (int i = 0; i < numCourses; i++) {
            System.out.println("\nCourse " + (i + 1) + ":");
            System.out.print("Enter course code: ");
            String code = scanner.nextLine();
            System.out.print("Enter course name: ");
            String name = scanner.nextLine();
            System.out.print("Enter credit hours: ");
            int credits = scanner.nextInt();
            scanner.nextLine();
            
            controller.addCourse(new model.Course(code, name, credits));
        }
    }
    
    private void addStudent() {
        System.out.println("\nAdd New Student");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter student name: ");
        String name = scanner.nextLine();
        
        controller.addStudent(new model.Student(id, name, controller.getCourseCount()));
        System.out.println("Student added successfully!");
    }
    
    private void enterGrades() {
        System.out.println("\nEnter Student Grades");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        
        Student student = controller.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("Entering grades for " + student.getName());
        List<Course> courses = controller.getAllCourses();
        
        for (int i = 0; i < courses.size(); i++) {
            System.out.print("Grade for " + courses.get(i).getName() + ": ");
            double grade = scanner.nextDouble();
            scanner.nextLine();
            controller.setStudentGrade(id, i, grade);
        }
        
        System.out.println("Grades entered successfully!");
    }
    
    private void viewStudentReport() {
        System.out.println("\nStudent Report");
        System.out.print("Enter student ID: ");
        String id = scanner.nextLine();
        
        Student student = controller.getStudentById(id);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }
        
        System.out.println("\nStudent: " + student.getName() + " (" + student.getId() + ")");
        System.out.println("Course Grades:");
        
        List<Course> courses = controller.getAllCourses();
        for (int i = 0; i < courses.size(); i++) {
            System.out.printf("%-20s: %.2f\n", courses.get(i).getName(), student.getGrades()[i]);
        }
        
        System.out.printf("\nAverage Grade: %.2f\n", student.calculateAverage());
        System.out.println("Status: " + (student.calculateAverage() >= 60 ? "Pass" : "Fail"));
    }
    
    private void viewClassSummary() {
        System.out.println("\nClass Summary Report");
        System.out.println(controller.generateReport());
    }
}