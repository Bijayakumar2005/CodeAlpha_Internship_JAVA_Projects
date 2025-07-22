package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import model.Student;
import model.Course;
import controller.GradeController;

public class GUI extends JFrame {
    private GradeController controller;
    private JTextArea reportArea;
    private JComboBox<String> studentComboBox;
    private JTable gradeTable;
    private DefaultTableModel gradeTableModel;

    public GUI(GradeController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Student Grade Management System");
        setSize(1000, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create menu bar
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        setJMenuBar(menuBar);

        // Create tabbed pane
        JTabbedPane tabbedPane = new JTabbedPane();

        // Student Management Tab
        tabbedPane.addTab("Student Management", createStudentPanel());

        // Grade Entry Tab
        tabbedPane.addTab("Grade Entry", createGradePanel());

        // Reports Tab
        tabbedPane.addTab("Reports", createReportPanel());

        add(tabbedPane, BorderLayout.CENTER);
        add(new JLabel("Ready"), BorderLayout.SOUTH);
    }

    private JPanel createStudentPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Add Student Form
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Add New Student"));

        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(idField);
        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel());
        
        JButton addButton = new JButton("Add Student");
        addButton.addActionListener(e -> addStudent(idField, nameField));
        formPanel.add(addButton);

        // Student List
        JTextArea studentListArea = new JTextArea();
        studentListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(studentListArea);

        JButton refreshButton = new JButton("Refresh Student List");
        refreshButton.addActionListener(e -> refreshStudentList(studentListArea));

        JPanel listPanel = new JPanel(new BorderLayout());
        listPanel.add(scrollPane, BorderLayout.CENTER);
        listPanel.add(refreshButton, BorderLayout.SOUTH);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createGradePanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Student Selection
        JPanel selectionPanel = new JPanel(new BorderLayout(5, 5));
        selectionPanel.setBorder(BorderFactory.createTitledBorder("1. Select Student"));
        
        studentComboBox = new JComboBox<>();
        updateStudentComboBox();
        
        selectionPanel.add(new JLabel("Student ID:"), BorderLayout.WEST);
        selectionPanel.add(studentComboBox, BorderLayout.CENTER);
        
        JButton refreshButton = new JButton("Refresh List");
        refreshButton.addActionListener(e -> updateStudentComboBox());
        selectionPanel.add(refreshButton, BorderLayout.EAST);

        // Grade Entry Table
        JPanel gradePanel = new JPanel(new BorderLayout());
        gradePanel.setBorder(BorderFactory.createTitledBorder("2. Enter Grades"));

        // Create table model with columns
        String[] columnNames = {"No.", "Course Code", "Course Name", "Grade (0-100)"};
        gradeTableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3; // Only grade column is editable
            }
        };

        gradeTable = new JTable(gradeTableModel);
        gradeTable.getColumnModel().getColumn(0).setPreferredWidth(40);
        gradeTable.getColumnModel().getColumn(1).setPreferredWidth(100);
        gradeTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        gradeTable.getColumnModel().getColumn(3).setPreferredWidth(100);

        // Load courses into table
        refreshGradeTable();

        JScrollPane scrollPane = new JScrollPane(gradeTable);
        gradePanel.add(scrollPane, BorderLayout.CENTER);

        // Submit Button
        JButton submitButton = new JButton("3. Submit Grades");
        submitButton.addActionListener(e -> submitGrades());
        submitButton.setPreferredSize(new Dimension(150, 30));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(submitButton);

        panel.add(selectionPanel, BorderLayout.NORTH);
        panel.add(gradePanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createReportPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        reportArea = new JTextArea();
        reportArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(reportArea);

        JButton studentReportButton = new JButton("Student Report");
        studentReportButton.addActionListener(e -> generateStudentReport());

        JButton classReportButton = new JButton("Class Report");
        classReportButton.addActionListener(e -> reportArea.setText(controller.generateReport()));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(studentReportButton);
        buttonPanel.add(classReportButton);

        panel.add(scrollPane, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private void addStudent(JTextField idField, JTextField nameField) {
        String id = idField.getText().trim();
        String name = nameField.getText().trim();
        
        if (id.isEmpty() || name.isEmpty()) {
            JOptionPane.showMessageDialog(this, "All fields are required", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (controller.getStudentById(id) != null) {
            JOptionPane.showMessageDialog(this, "Student ID already exists", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        controller.addStudent(new Student(id, name, controller.getCourseCount()));
        idField.setText("");
        nameField.setText("");
        updateStudentComboBox();
        JOptionPane.showMessageDialog(this, "Student added successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateStudentComboBox() {
        studentComboBox.removeAllItems();
        List<Student> students = controller.getAllStudents();
        if (students != null) {
            for (Student student : students) {
                studentComboBox.addItem(student.getId());
            }
        }
    }

    private void refreshGradeTable() {
        gradeTableModel.setRowCount(0); // Clear existing rows
        
        List<Course> courses = controller.getAllCourses();
        if (courses != null && !courses.isEmpty()) {
            int counter = 1;
            for (Course course : courses) {
                gradeTableModel.addRow(new Object[]{
                    counter++,
                    course.getCode(),
                    course.getName(),
                    "" // Empty grade field
                });
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "No courses found. Please add courses first.",
                "No Courses", 
                JOptionPane.WARNING_MESSAGE);
        }
    }

    private void refreshStudentList(JTextArea textArea) {
        StringBuilder sb = new StringBuilder();
        List<Student> students = controller.getAllStudents();
        
        if (students == null || students.isEmpty()) {
            textArea.setText("No students found");
            return;
        }
        
        for (Student student : students) {
            sb.append(student.getId()).append(" - ").append(student.getName())
              .append(" (Avg: ").append(String.format("%.2f", student.calculateAverage())).append(")\n");
        }
        textArea.setText(sb.toString());
    }

    private void submitGrades() {
        String studentId = (String) studentComboBox.getSelectedItem();
        if (studentId == null || studentId.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a student", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = controller.getStudentById(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            for (int i = 0; i < gradeTableModel.getRowCount(); i++) {
                String gradeValue = gradeTableModel.getValueAt(i, 3).toString();
                if (!gradeValue.isEmpty()) {
                    double grade = Double.parseDouble(gradeValue);
                    if (grade < 0 || grade > 100) {
                        throw new NumberFormatException("Grades must be between 0 and 100");
                    }
                    controller.setStudentGrade(studentId, i, grade);
                    gradeTableModel.setValueAt("", i, 3); // Clear after submission
                }
            }
            JOptionPane.showMessageDialog(this, "Grades submitted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid grade: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void generateStudentReport() {
        String studentId = (String) studentComboBox.getSelectedItem();
        if (studentId == null) {
            JOptionPane.showMessageDialog(this, "Please select a student", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Student student = controller.getStudentById(studentId);
        if (student == null) {
            JOptionPane.showMessageDialog(this, "Student not found", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        StringBuilder report = new StringBuilder();
        report.append("STUDENT GRADE REPORT\n");
        report.append("====================\n\n");
        report.append("ID: ").append(student.getId()).append("\n");
        report.append("Name: ").append(student.getName()).append("\n\n");
        report.append("COURSE GRADES:\n");
        report.append(String.format("%-10s %-30s %-10s\n", "Code", "Course", "Grade"));
        report.append("----------------------------------------\n");

        List<Course> courses = controller.getAllCourses();
        for (int i = 0; i < courses.size(); i++) {
            report.append(String.format("%-10s %-30s %-10.2f\n", 
                courses.get(i).getCode(), 
                courses.get(i).getName(), 
                student.getGrades()[i]));
        }

        report.append("\nSUMMARY:\n");
        report.append(String.format("Average: %.2f\n", student.calculateAverage()));
        report.append("Status: ").append(student.calculateAverage() >= 60 ? "PASS" : "FAIL");

        reportArea.setText(report.toString());
    }
}