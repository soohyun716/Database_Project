package GUI.professor;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;
import javax.swing.JTextArea;

public class professor_button3 extends JFrame {

    private JPanel contentPane;
    private JTextField roomField;
    private JTextArea professorInfoArea;
    private JCheckBox[] timeCheckBoxes;
    private Connection connection;

    Map<String, Boolean> timeDictionary = new HashMap<>();

    public professor_button3() {
        setTitle("GONG-GANG");
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocation(50, 50);
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 0));
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

        JLabel userLabel = new JLabel("- \uAD50\uC218\uB2D8 \uCC3E\uAE30 -");
        userLabel.setBackground(new Color(255, 255, 255));
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 22));
        logoPanel.add(userLabel);

        //inputPanel & Info display area 붙이는 mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.setPreferredSize(new Dimension(700, 400));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        mainPanel.add(inputPanel, BorderLayout.NORTH);





        JPanel timePanel = new JPanel();
        timePanel.setBorder(BorderFactory.createTitledBorder("강의 시간 선택"));
        timePanel.setBackground(new Color(255, 255, 255));
        timePanel.setLayout(new GridLayout(9,5,10,8)); // Adjusted layout to 6 rows and 9 columns
        timePanel.setPreferredSize(new Dimension(470, 200)); // Adjusted size

        String[] times = {
                "Mon1", "Tue1", "Wed1", "Thu1", "Fri1",
                "Mon2", "Tue2", "Wed2", "Thu2", "Fri2",
                "Mon3", "Tue3", "Wed3", "Thu3", "Fri3",
                "Mon4", "Tue4", "Wed4", "Thu4", "Fri4",
                "Mon5", "Tue5", "Wed5", "Thu5", "Fri5",
                "Mon6", "Tue6", "Wed6", "Thu6", "Fri6",
                "Mon7", "Tue7", "Wed7", "Thu7", "Fri7",
                "Mon8", "Tue8", "Wed8", "Thu8", "Fri8",
                "Mon9", "Tue9", "Wed9", "Thu9", "Fri9"
        };

        timeCheckBoxes = new JCheckBox[times.length];

        for (int i = 0; i < times.length; i++) {
            timeCheckBoxes[i] = new JCheckBox(times[i]);
            timeCheckBoxes[i].setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12)); // Smaller font
            timeCheckBoxes[i].setPreferredSize(new Dimension(50, 20)); // Adjusted size
            timeCheckBoxes[i].setBackground(new Color(255,255,255));
            timePanel.add(timeCheckBoxes[i]);

        }
        inputPanel.add(timePanel);

        JPanel roomPanel = new JPanel();
        roomPanel.setBackground(new Color(255, 255, 255));
        roomPanel.setPreferredSize(new Dimension(350, 100)); // Adjusted height
        inputPanel.add(roomPanel);

        JLabel roomLabel = new JLabel("\uAC15\uC758\uC2E4 \uC704\uCE58    :");
        roomLabel.setHorizontalAlignment(SwingConstants.CENTER);
        roomLabel.setPreferredSize(new Dimension(190, 40)); // Adjusted width
        roomLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
        roomField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        searchButton.setBackground(new Color(255, 255, 255));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProfessorByRoomAndTime();
            }
        });
        roomPanel.add(roomLabel);
        roomPanel.add(roomField);
        roomPanel.add(searchButton);



        // Info display area
        professorInfoArea = new JTextArea(5, 30);
        professorInfoArea.setTabSize(5);
        professorInfoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(professorInfoArea);

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        //home button
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //mainPanel 위치 조정하기 위해 넣은 panel 2개
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(50, 100));
        westPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(50, 100));
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

        connectToDatabase();
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "root", "4542");
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