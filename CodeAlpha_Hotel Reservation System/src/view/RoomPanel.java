package view;

import controller.HotelController;
import model.RoomType;
import util.DateUtil;
import util.StyleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDate;

public class RoomPanel extends JPanel {
    private HotelController controller;
    private JComboBox<RoomType> roomTypeCombo;
    private JSpinner checkInSpinner;
    private JSpinner checkOutSpinner;
    private JTextArea resultArea;

    public RoomPanel(HotelController controller) {
        this.controller = controller;
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Input panel
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        inputPanel.setOpaque(false);

        // Room type selection
        StyleUtil.styleLabel(new JLabel("Room Type:"));
        inputPanel.add(createStyledLabel("Room Type:"));
        roomTypeCombo = new JComboBox<>(RoomType.values());
        inputPanel.add(roomTypeCombo);

        // Check-in date
        inputPanel.add(createStyledLabel("Check-In Date:"));
        checkInSpinner = DateUtil.createDateSpinner();
        inputPanel.add(checkInSpinner);

        // Check-out date
        inputPanel.add(createStyledLabel("Check-Out Date:"));
        checkOutSpinner = DateUtil.createDateSpinner();
        inputPanel.add(checkOutSpinner);

        add(inputPanel, BorderLayout.NORTH);

        // Result area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        resultArea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Available Rooms"));
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton searchButton = new JButton("Search Rooms");
        JButton bookButton = new JButton("Book Selected Room");
        StyleUtil.styleButton(searchButton);
        StyleUtil.styleButton(bookButton);

        searchButton.addActionListener(this::searchRooms);
        bookButton.addActionListener(this::bookRoom);

        buttonPanel.add(searchButton);
        buttonPanel.add(bookButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        StyleUtil.styleLabel(label);
        return label;
    }

    private void searchRooms(ActionEvent e) {
        RoomType selectedType = (RoomType) roomTypeCombo.getSelectedItem();
        LocalDate checkIn = DateUtil.toLocalDate((java.util.Date) checkInSpinner.getValue());
        LocalDate checkOut = DateUtil.toLocalDate((java.util.Date) checkOutSpinner.getValue());

        if (checkOut.isBefore(checkIn)) {
            JOptionPane.showMessageDialog(this, "Check-out date must be after check-in date", 
                    "Invalid Dates", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String availableRooms = controller.searchAvailableRooms(selectedType, checkIn, checkOut);
        resultArea.setText(availableRooms);
    }

    private void bookRoom(ActionEvent e) {
        String guestName = JOptionPane.showInputDialog(this, "Enter your name:");
        if (guestName == null || guestName.trim().isEmpty()) {
            return;
        }

        RoomType selectedType = (RoomType) roomTypeCombo.getSelectedItem();
        LocalDate checkIn = DateUtil.toLocalDate((java.util.Date) checkInSpinner.getValue());
        LocalDate checkOut = DateUtil.toLocalDate((java.util.Date) checkOutSpinner.getValue());

        String result = controller.bookRoom(guestName, selectedType, checkIn, checkOut);
        resultArea.setText(result);
    }
}