import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class MedExpertUI {
    public static void main(String[] args) {
        JFrame frame = new JFrame("MedExpert - Diagnosis Assistant");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        JTextArea symptomArea = new JTextArea(5, 40);
        symptomArea.setLineWrap(true);
        symptomArea.setWrapStyleWord(true);

        JButton diagnoseButton = new JButton("Diagnose");
        JTextArea resultArea = new JTextArea(10, 40);
        resultArea.setEditable(false);
        resultArea.setLineWrap(true);
        resultArea.setWrapStyleWord(true);

        diagnoseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = symptomArea.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Please enter symptoms.");
                    return;
                }

                List<String> symptoms = Arrays.asList(input.toLowerCase().split(",\\s*"));
                try {
                    String diagnosis = DiagnosisClient.getDiagnosis(symptoms);
                    resultArea.setText(diagnosis);
                } catch (Exception ex) {
                    resultArea.setText("Error: " + ex.getMessage());
                }
            }
        });

        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.add(new JLabel("Enter symptoms (comma-separated):"), BorderLayout.NORTH);
        inputPanel.add(new JScrollPane(symptomArea), BorderLayout.CENTER);
        inputPanel.add(diagnoseButton, BorderLayout.SOUTH);

        frame.getContentPane().add(inputPanel, BorderLayout.NORTH);
        frame.getContentPane().add(new JScrollPane(resultArea), BorderLayout.CENTER);
        frame.setVisible(true);
    }
}