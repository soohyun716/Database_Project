
package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;

/*
LectureInfo 뷰를 사용
결과 창에 강의실 번호, 이름, 위치 출력하도록 수정
*/


public class student_button2 extends JFrame{

    private JPanel contentPane;

    private JTextField lectureNumberField;
    private JButton searchButton;
    private JFrame resultFrame;
    private JTextField textField;

    public student_button2() {

        // main frame 설정
        setTitle("GONG-GANG");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocation(50, 50);

        // main JPanel 생성
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // 로고 붙이는 Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));

        // GONG-GANG 로고 label 생성
        JLabel logo = new JLabel("Gong-Gang");
        logo.setBackground(new Color(255, 255, 255));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo);
        // - 수업 강의실 찾기 - 로고 label 생성
        JLabel userLabel = new JLabel("- \uC218\uC5C5 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22));
        logoPanel.add(userLabel);


        // inputPanel & resultPanel 포함하는 mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(0, 1, 0, 0));

        // 입력 받는 inputPanel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255,255,255));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        mainPanel.add(inputPanel);

        // 학수번호를 입력하세요' Label 생성
        JLabel inputLabel = new JLabel("\uD559\uC218\uBC88\uD638\uB97C \uC785\uB825\uD558\uC138\uC694 (ex 14349-1)");
        inputLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        inputLabel.setPreferredSize(new Dimension(1050, 80));
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 21));
        inputLabel.setBounds(200, 400, WIDTH, HEIGHT);
        inputPanel.add(inputLabel);

        // 결과 보여주는 TextField 생성
        lectureNumberField = new JTextField(17);
        inputPanel.add(lectureNumberField);

        // 결과 출력을 위한 searchButton 생성
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        searchButton.setBackground(new Color(255, 255, 255));
        inputPanel.add(searchButton);

        // 결과 보여주는 Panel 생성
        JPanel resultPanel = new JPanel();
        resultPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(resultPanel);

        //학수번호를 입력후 값을 search 버튼을 눌렀을 경우
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lectureNumber = lectureNumberField.getText();//텍스트를 받아온 뒤에
                if (!lectureNumber.isEmpty()) {
                    showRoomNumber(lectureNumber);//해당 강의의 공간번호를 보여주는 함수 호출
                } else {
                    JOptionPane.showMessageDialog(null, "학수 번호를 입력하세요.");
                }
            }
        });


        //home button
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        JPanel westPanel = new JPanel();
        westPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        eastPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(eastPanel, BorderLayout.EAST);

        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });
    }

    //강의번호에 해당하는 공간번호를 보여주는 코드
    private void showRoomNumber(String lectureNumber) {
        String dbUrl = "jdbc:mysql://localhost/DB2024Team05";
        String dbUser = "DB2024Team05";
        String dbPass = "DB2024Team05";

        //학수번호와 함께 받아와기에 분리해주기
        String[] parts = lectureNumber.split("-");
        if (parts.length != 2) {
            JOptionPane.showMessageDialog(this, "입력값을 확인하세요");
            return;//두 개로 분리가 안 된경우 처리하는 법
        }

        //사용자로부터 받아온 강의번호와 학수번호를 통해서 원하는 정보 검색
        String query = "SELECT Room_Number,Room_Name,Location FROM LectureInfo WHERE Lecture_Num = ? AND Class_Num = ?";

        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            //DBD연결을 맺고 쿼리를 실행

            stmt.setString(1, parts[0]); // Lecture_Num
            stmt.setString(2, parts[1]); // Class_Num
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    String roomNumber = rs.getString("Room_Number");
                    String Room_name = rs.getString("Room_Name");
                    String Location = rs.getString("Location");
                    displayResult(roomNumber,Room_name,Location);//결과를 보여주는 함수 호출
                } else {
                    JOptionPane.showMessageDialog(this, "학수번호를 다시 확인하세요");//테이블 결과 검색되지 않는 경우
                }
            }
        } catch (SQLException ex) {
            //DB와 연결에서 문제가 생기는 경우
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
        }
    }


    private void displayResult(String roomNumber, String Room_name, String Location) {
        //결과를 새 창에 표시
        if (resultFrame != null) {
            resultFrame.dispose();
        }
        resultFrame = new JFrame("Classroom Information");
        resultFrame.setSize(300, 100);
        resultFrame.getContentPane().setLayout(new GridLayout(3,0));
        //mainPanel.add(new JLabel("강의실 번호: " + roomNumber));
        resultFrame.getContentPane().add(new JLabel("강의실 번호: " + roomNumber));
        resultFrame.getContentPane().add(new JLabel("강의실 이름: " + Room_name));
        resultFrame.getContentPane().add(new JLabel("강의실 위치: " + Location));
        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        resultFrame.setVisible(true);
    }

}