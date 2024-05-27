package GUI.professor;

import GUI.mainFrame.mainGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Professor_findProf extends JFrame {

    private JPanel contentPane;
    private JTextField roomField;
    private JCheckBox[] timeCheckBoxes;
    private JTextArea professorInfoArea;
    private Connection connection;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Professor_findProf frame = new Professor_findProf();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public Professor_findProf() {
        // Establish database connection
        connectToDatabase();

        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocation(50, 50);
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // Header Panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setLayout(new BorderLayout());

        // Title
        JLabel titleLabel = new JLabel("강의실 및 시간으로 교수님 정보 찾기");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        headerPanel.add(titleLabel, BorderLayout.NORTH);

        contentPane.add(headerPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JLabel roomLabel = new JLabel("강의실 위치:");
        roomLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchPanel.add(roomLabel);

        roomField = new JTextField();
        roomField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        roomField.setColumns(10);
        searchPanel.add(roomField);

        JLabel timeLabel = new JLabel("강의 시간:");
        timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchPanel.add(timeLabel);

        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(0, 5));
        String[] times = {
                "월1", "월2", "월3", "월4", "월5", "월6", "월7", "월8", "월9",
                "화1", "화2", "화3", "화4", "화5", "화6", "화7", "화8", "화9",
                "수1", "수2", "수3", "수4", "수5", "수6", "수7", "수8", "수9",
                "목1", "목2", "목3", "목4", "목5", "목6", "목7", "목8", "목9",
                "금1", "금2", "금3", "금4", "금5", "금6", "금7", "금8", "금9"
        };
        timeCheckBoxes = new JCheckBox[times.length];
        for (int i = 0; i < times.length; i++) {
            timeCheckBoxes[i] = new JCheckBox(times[i]);
            timePanel.add(timeCheckBoxes[i]);
        }
        searchPanel.add(timePanel);

        JButton searchButton = new JButton("검색");
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchProfessorByRoomAndTime();
            }
        });
        searchPanel.add(searchButton);

        contentPane.add(searchPanel, BorderLayout.CENTER);

        // Professor Info Area
        professorInfoArea = new JTextArea();
        professorInfoArea.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        professorInfoArea.setEditable(false);
        JScrollPane infoScrollPane = new JScrollPane(professorInfoArea);
        infoScrollPane.setPreferredSize(new Dimension(800, 200));
        contentPane.add(infoScrollPane, BorderLayout.SOUTH);

        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "root", "root");
            System.out.println("Database connected successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void searchProfessorByRoomAndTime() {
        String room = roomField.getText().trim();
        List<String> selectedTimes = new ArrayList<>();
        for (JCheckBox checkBox : timeCheckBoxes) {
            if (checkBox.isSelected()) {
                selectedTimes.add(checkBox.getText());
            }
        }

        if (room.isEmpty() || selectedTimes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "강의실 위치와 강의 시간을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            List<ProfessorInfo> professors = getProfessorsByRoomAndTime(room, selectedTimes);
            if (!professors.isEmpty()) {
                updateProfessorInfoArea(professors);
            } else {
                JOptionPane.showMessageDialog(this, "해당 시간에 사용 중인 교수님의 정보를 찾을 수 없습니다.", "경고",
                        JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<ProfessorInfo> getProfessorsByRoomAndTime(String room, List<String> times) throws SQLException {
        List<ProfessorInfo> professors = new ArrayList<>();

        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT p.Name, p.Lab_Location, p.Phone, p.Email "
                + "FROM DB2024_Professor p "
                + "JOIN DB2024_Lecture l ON p.Professor_Num = l.Professor_Num "
                + "WHERE l.Room_Number = ? AND (");

        for (int i = 0; i < times.size(); i++) {
            queryBuilder.append("l.Lecture_Time1 = ? OR l.Lecture_Time2 = ?");
            if (i < times.size() - 1) {
                queryBuilder.append(" OR ");
            }
        }
        queryBuilder.append(")");

        PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString());
        stmt.setString(1, room);
        int index = 2;
        for (String time : times) {
            stmt.setString(index++, time);
            stmt.setString(index++, time);
        }

        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            ProfessorInfo professor = new ProfessorInfo();
            professor.setName(rs.getString("Name"));
            professor.setLabLocation(rs.getString("Lab_Location"));
            professor.setPhone(rs.getString("Phone"));
            professor.setEmail(rs.getString("Email"));
            professors.add(professor);
        }
        return professors;
    }

    private void updateProfessorInfoArea(List<ProfessorInfo> professors) {
        StringBuilder info = new StringBuilder();
        for (ProfessorInfo professor : professors) {
            info.append("교수님 이름: ").append(professor.getName()).append("\n");
            if (professor.getLabLocation() != null) {
                info.append("연구실: ").append(professor.getLabLocation()).append("\n");
            } else {
                info.append("연구실: 정보 없음\n");
            }
            if (professor.getPhone() != null) {
                info.append("연락처: ").append(professor.getPhone()).append("\n");
            } else {
                info.append("연락처: 정보 없음\n");
            }
            if (professor.getEmail() != null) {
                info.append("이메일: ").append(professor.getEmail()).append("\n");
            } else {
                info.append("이메일: 정보 없음\n");
            }
            info.append("\n");
        }
        professorInfoArea.setText(info.toString());
    }

    class ProfessorInfo {
        private String name;
        private String labLocation;
        private String phone;
        private String email;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLabLocation() {
            return labLocation;
        }

        public void setLabLocation(String labLocation) {
            this.labLocation = labLocation;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
    }
}
