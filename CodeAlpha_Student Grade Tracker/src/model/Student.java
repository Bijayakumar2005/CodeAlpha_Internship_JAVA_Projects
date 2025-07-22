package model;

public class Student {
    private String id;
    private String name;
    private double[] grades;
    
    public Student(String id, String name, int numCourses) {
        this.id = id;
        this.name = name;
        this.grades = new double[numCourses];
    }
    
    // Getters and setters
    public String getId() { return id; }
    public String getName() { return name; }
    public double[] getGrades() { return grades; }
    
    public void setGrade(int courseIndex, double grade) {
        if (courseIndex >= 0 && courseIndex < grades.length) {
            grades[courseIndex] = grade;
        }
    }
    
    public double calculateAverage() {
        if (grades.length == 0) return 0;
        double sum = 0;
        for (double grade : grades) {
            sum += grade;
        }
        return sum / grades.length;
    }
}