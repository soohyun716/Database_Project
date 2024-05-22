import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Student_ProfessorInfo {
    private JFrame frame;
    private JTextField nameField;
    private JTextArea infoArea;

    public Student_ProfessorInfo() {
        // Frame setup
        frame = new JFrame("GONG-GANG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setLayout(new BorderLayout());

        // Title and setup
        JLabel titleLabel = new JLabel("GONG-GANG", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(titleLabel, BorderLayout.NORTH);

        // Input Panel
        JPanel inputPanel = new JPanel();
        JLabel nameLabel = new JLabel("교수님 이름:");
        nameField = new JTextField(20);
        JButton searchButton = new JButton("검색");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProfessorInfo(nameField.getText().trim());
            }
        });
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(searchButton);

        // Info display area
        infoArea = new JTextArea(10, 40);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        // Adding components to frame
        frame.add(inputPanel, BorderLayout.CENTER);
        frame.add(scrollPane, BorderLayout.SOUTH);

        // Set frame visible
        frame.setVisible(true);
    }

    private void searchProfessorInfo(String name) {

        //JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        //Database credentials
// MySQL 계정과 암호 입력
        final String user = "root";
        final String password = "각자의 비밀번호로...";

        String query = "SELECT Email, Lab_Location, Phone FROM DB2024_Professor WHERE Name = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("Email");
                String Lab_Location = rs.getString("Lab_Location");
                String Phone = rs.getString("Phone");
                infoArea.setText("Email: " + email + "\nLab_Location: " + Lab_Location + "\nPhone Number: " + Phone);
            } else {
                infoArea.setText("No information found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            infoArea.setText("Error retrieving data.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Student_ProfessorInfo();
            }
        });
    }
}

