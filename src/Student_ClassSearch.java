import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Student_ClassSearch extends JFrame {
    private JTextField lectureNumberField;
    private JButton searchButton;
    private JFrame resultFrame;

    public Student_ClassSearch() {
        setTitle("GONG-GANG");
        setSize(500, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // 사용자로부터 학수번호 입력 받기
        lectureNumberField = new JTextField(10);
        add(new JLabel("찾기를 원하는 학수번호를 입력하세요 (ex 14349-1):"));
        add(lectureNumberField);

        searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lectureNumber = lectureNumberField.getText();
                if (!lectureNumber.isEmpty()) {
                    showRoomNumber(lectureNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "올바른 학수 번호를 입력하세요.");
                }
            }
        });
        add(searchButton);

        setVisible(true);
    }

    private void showRoomNumber(String lectureNumber) {
        String dbUrl = "jdbc:mysql://localhost/DB2024Team05";
        String dbUser = "root";
        String dbPass = "mint1241";

        // Assuming lectureNumber is entered as '14349-1'
        String[] parts = lectureNumber.split("-");
        if (parts.length != 2) {
            JOptionPane.showMessageDialog(this, "입력값을 확인하세요");
            return;
        }

        String query = "SELECT Room_Number FROM DB2024_Lecture WHERE Lecture_Num = ? AND Class_Num = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, parts[0]); // Lecture_Num
            stmt.setString(2, parts[1]); // Class_Num
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String roomNumber = rs.getString("Room_Number");
                    displayResult(roomNumber);
                } else {
                    JOptionPane.showMessageDialog(this, "학수번호를 다시 확인하세요");
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }

    private void displayResult(String roomNumber) {
        // 결과를 새 창에 표시
        if (resultFrame != null) {
            resultFrame.dispose();
        }
        resultFrame = new JFrame("Classroom Information");
        resultFrame.setSize(300, 100);
        resultFrame.setLayout(new FlowLayout());
        resultFrame.add(new JLabel("강의실 번호: " + roomNumber));
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setVisible(true);
    }

    public static void main(String[] args) {
        new Student_ClassSearch();
    }
}
