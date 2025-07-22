package view;

import controller.HotelController;
import util.StyleUtil;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private HotelController controller;
    private JTabbedPane tabbedPane;

    public MainFrame(HotelController controller) {
        this.controller = controller;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Hotel Reservation System");
        setSize(900, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Create main panel with gradient background
        JPanel mainPanel = StyleUtil.createGradientPanel();
        mainPanel.setLayout(new BorderLayout());

        // Header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Hotel Paradise Resort");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        headerPanel.add(titleLabel);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Create tabbed pane
        tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Rooms", new RoomPanel(controller));
        tabbedPane.addTab("Reservations", new ReservationPanel(controller));
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        add(mainPanel);
    }
}