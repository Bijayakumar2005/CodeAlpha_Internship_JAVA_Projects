import model.Course;
import controller.GradeController;
import view.GUI;
import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        // Initialize the controller and manager
        GradeController controller = new GradeController();
        
        // Add some default courses
        initializeSampleCourses(controller);
        
        // Uncomment for console interface
        // runConsoleInterface(controller);
        
        // Run GUI interface
        runGUIInterface(controller);
    }
    
    private static void initializeSampleCourses(GradeController controller) {
        // Add some sample courses
        controller.addCourse(new Course("CS101", "Introduction to Programming", 3));
        controller.addCourse(new Course("MATH101", "Calculus I", 4));
        controller.addCourse(new Course("ENG101", "English Composition", 3));
        controller.addCourse(new Course("PHYS101", "Physics Fundamentals", 4));
        controller.addCourse(new Course("CHEM101", "General Chemistry", 4));
    }
    
    private static void runGUIInterface(GradeController controller) {
        // Run the GUI in the Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            try {
                // Set system look and feel
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                
                GUI gui = new GUI(controller);
                gui.setVisible(true);
                
                // Center the window on screen
                gui.setLocationRelativeTo(null);
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, 
                    "Error initializing GUI: " + e.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}