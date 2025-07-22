package view;

import controller.HotelController;
import util.StyleUtil;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ReservationPanel extends JPanel {
    private HotelController controller;
    private JTextArea reservationsArea;

    public ReservationPanel(HotelController controller) {
        this.controller = controller;
        setOpaque(false);
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Reservations display area
        reservationsArea = new JTextArea();
        reservationsArea.setEditable(false);
        StyleUtil.styleTextArea(reservationsArea);
        reservationsArea.setLineWrap(true);
        reservationsArea.setWrapStyleWord(true);
        JScrollPane scrollPane = StyleUtil.createStyledScrollPane(reservationsArea, "Current Reservations");
        add(scrollPane, BorderLayout.CENTER);

        // Button panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setOpaque(false);

        JButton viewButton = createHoverButton("View All Reservations", StyleUtil.PRIMARY_COLOR);
        JButton cancelButton = createHoverButton("Cancel Reservation", StyleUtil.ERROR_COLOR);

        viewButton.addActionListener(this::viewReservations);
        cancelButton.addActionListener(this::cancelReservation);

        buttonPanel.add(viewButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private JButton createHoverButton(String text, Color hoverColor) {
        JButton button = new JButton(text);
        StyleUtil.styleButton(button);
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(hoverColor);
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(StyleUtil.BUTTON_COLOR);
                button.setForeground(StyleUtil.BUTTON_PRESSED);
            }
        });
        
        return button;
    }

    private void viewReservations(ActionEvent e) {
        String allReservations = controller.getAllReservations();
        reservationsArea.setText(allReservations);
    }

    private void cancelReservation(ActionEvent e) {
        String reservationIdStr = JOptionPane.showInputDialog(this, "Enter reservation ID to cancel:");
        if (reservationIdStr == null || reservationIdStr.trim().isEmpty()) {
            return;
        }

        try {
            int reservationId = Integer.parseInt(reservationIdStr);
            String result = controller.cancelReservation(reservationId);
            reservationsArea.setText(result);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid reservation ID", 
                    "Invalid Input", JOptionPane.ERROR_MESSAGE);
        }
    }
}