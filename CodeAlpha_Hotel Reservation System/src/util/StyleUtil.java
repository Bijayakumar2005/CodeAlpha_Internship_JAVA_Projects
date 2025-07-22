package util;

import javax.swing.*;
import java.awt.*;

public class StyleUtil {
    // Modern, accessible color palette
    public static final Color PRIMARY_COLOR = new Color(0, 119, 182); // Deep blue
    public static final Color PRIMARY_LIGHT = new Color(72, 202, 228); // Light blue
    public static final Color SECONDARY_COLOR = new Color(255, 183, 3); // Golden yellow
    public static final Color ACCENT_COLOR = new Color(251, 133, 0); // Orange
    public static final Color DARK_COLOR = new Color(33, 37, 41); // Dark gray (unchanged)
    public static final Color LIGHT_COLOR = new Color(242, 242, 242); // Off-white
    public static final Color SUCCESS_COLOR = new Color(40, 167, 69); // Green
    public static final Color ERROR_COLOR = new Color(220, 53, 69); // Red (unchanged)
    public static final Color WARNING_COLOR = new Color(255, 193, 7); // Amber (unchanged)

    // Text colors
    public static final Color TEXT_DARK = DARK_COLOR;
    public static final Color TEXT_LIGHT = LIGHT_COLOR;
    public static final Color TEXT_ON_PRIMARY = Color.WHITE;

    // Button colors
    public static final Color BUTTON_COLOR = PRIMARY_COLOR;
    public static final Color BUTTON_HOVER = PRIMARY_LIGHT;
    public static final Color BUTTON_PRESSED = PRIMARY_COLOR.darker();
    public static final Color BUTTON_TEXT_COLOR = Color.BLACK;

   public static void styleButton(JButton button) {
    button.setFont(new Font("Segoe UI", Font.BOLD, 14));
    button.setBackground(BUTTON_COLOR);
    button.setForeground(BUTTON_TEXT_COLOR);
    button.setFocusPainted(false);
    button.setBorder(BorderFactory.createCompoundBorder(
        BorderFactory.createLineBorder(PRIMARY_COLOR.darker(), 2),
        BorderFactory.createEmptyBorder(10, 25, 10, 25)
    ));
    button.setCursor(new Cursor(Cursor.HAND_CURSOR));

    // Smooth hover effect using mouse listener
    button.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseEntered(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_HOVER);
            button.setForeground(Color.BLACK); // Better contrast on hover
        }
        public void mouseExited(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_COLOR);
            button.setForeground(BUTTON_TEXT_COLOR);
        }
        public void mousePressed(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_PRESSED);
        }
        public void mouseReleased(java.awt.event.MouseEvent evt) {
            button.setBackground(BUTTON_HOVER);
        }
    });
}

    public static void styleLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        label.setForeground(TEXT_DARK);
    }

    public static JPanel createGradientPanel() {
        return new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, PRIMARY_COLOR,
                        getWidth(), getHeight(), PRIMARY_LIGHT);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
    }

    public static void styleTextArea(JTextArea textArea) {
        textArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        textArea.setForeground(TEXT_DARK);
        textArea.setBackground(new Color(255, 255, 255, 240)); // Semi-transparent white
        textArea.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200, 100), 1),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
    }

    public static JScrollPane createStyledScrollPane(JComponent component, String title) {
        JScrollPane scrollPane = new JScrollPane(component);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createTitledBorder(
                        BorderFactory.createLineBorder(PRIMARY_COLOR, 1),
                        title,
                        javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                        javax.swing.border.TitledBorder.DEFAULT_POSITION,
                        new Font("Segoe UI Semibold", Font.PLAIN, 14),
                        TEXT_DARK),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        return scrollPane;
    }

    public static void styleHeaderLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.BOLD, 18));
        label.setForeground(PRIMARY_COLOR);
    }

    public static void styleErrorLabel(JLabel label) {
        label.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        label.setForeground(ERROR_COLOR);
    }
}