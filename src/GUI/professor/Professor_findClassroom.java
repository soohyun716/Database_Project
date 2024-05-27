package GUI.professor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class Professor_findClassroom {
    String usage;
    String seats;
    String cameraType;
    boolean outlet;
    boolean projector;
    boolean reservation;
    boolean recording;
    boolean practicable;
    Map<String, Boolean> timeDictionary = new HashMap<>();
    private JTextArea infoArea;

    public Professor_findClassroom() {
        // Create frame
        JFrame frame = new JFrame("Classroom Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 600);
        frame.setLocation(50,50);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("Classroom Search", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Dropdowns
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new GridLayout(3, 2, 10, 10));
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usageLabel = new JLabel("공간 유형:");
        JComboBox<String> usageComboBox = new JComboBox<>(new String[] { "선택", "교실", "교실 외" });

        JLabel seatsLabel = new JLabel("좌석 수:");
        JComboBox<String> seatsComboBox = new JComboBox<>(
                new String[] { "선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70" });

        JLabel cameraTypeLabel = new JLabel("카메라 유형:");
        JComboBox<String> cameraTypeComboBox = new JComboBox<>(new String[] { "선택", "고정식 카메라", "추적식 카메라" });

        dropdownPanel.add(usageLabel);
        dropdownPanel.add(usageComboBox);
        dropdownPanel.add(seatsLabel);
        dropdownPanel.add(seatsComboBox);
        dropdownPanel.add(cameraTypeLabel);
        dropdownPanel.add(cameraTypeComboBox);
        contentPanel.add(dropdownPanel);

        // Checkboxes
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(3, 2));

        JCheckBox outletCheckBox = new JCheckBox("콘센트");
        JCheckBox projectorCheckBox = new JCheckBox("빔프로젝트");
        JCheckBox reservationCheckBox = new JCheckBox("예약 필요");
        JCheckBox recordingCheckBox = new JCheckBox("녹화 가능");
        JCheckBox practicableCheckBox = new JCheckBox("실습 가능");

        checkBoxPanel.add(outletCheckBox);
        checkBoxPanel.add(projectorCheckBox);
        checkBoxPanel.add(reservationCheckBox);
        checkBoxPanel.add(recordingCheckBox);
        checkBoxPanel.add(practicableCheckBox);
        contentPanel.add(checkBoxPanel);

        // Time table
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(8, 5));
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 시간 찾기"));

        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri" };
        String[] times = { "1", "2", "3", "4", "5", "6", "7", "8" };

        for (String time : times) {
            for (String day : days) {
                String name = day + time;
                timeDictionary.put(name, false);
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeDictionary.put(name, checkBox.isSelected());
                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                timePanel.add(checkBox);
            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel);
        contentPanel.add(scrollPane);

        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Back");
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                professor_choiceGUI choiceFrame = new professor_choiceGUI(); // Assuming choiceGUI is another frame class
                choiceFrame.setVisible(true); // Open the choiceGUI frame

            }
        });
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage = (String) usageComboBox.getSelectedItem();
                seats = (String) seatsComboBox.getSelectedItem();
                cameraType = (String) cameraTypeComboBox.getSelectedItem();

                outlet = outletCheckBox.isSelected();
                projector = projectorCheckBox.isSelected();
                reservation = reservationCheckBox.isSelected();
                recording = recordingCheckBox.isSelected();
                practicable = practicableCheckBox.isSelected();
                infoArea = new JTextArea(80, 40);

                searchClassroomInfo();

                // 새로운 창을 열어 결과를 보여줌
                JFrame newFrame = new JFrame("검색된 정보");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setSize(800, 400);
                newFrame.setLayout(new FlowLayout());

                infoArea.setEditable(false);
                newFrame.add(infoArea);
                newFrame.setVisible(true);
            }
        });

        buttonPanel.add(backButton);
        buttonPanel.add(searchButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame visible
        frame.setVisible(true);
    }

    private void searchClassroomInfo() {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/DB2024Team05";
        final String USER = "root";
        final String PASS = "root";
        String message = "검색된 교실의 번호:\n";

        StringBuilder query = new StringBuilder("SELECT * FROM DB2024_Classroom WHERE 1=1");

        String[] seatRange = seats.split("-");
        if (!seats.equals("선택")) {
            query.append(" AND SeatCount BETWEEN ").append(Integer.parseInt(seatRange[0])).append(" AND ")
                    .append(Integer.parseInt(seatRange[1]));
        }
        if (!cameraType.equals("선택")) {
            query.append(" AND CameraType = '").append(cameraType).append("'");
        }
        if (outlet)
            query.append(" AND OutletCount > 0");
        if (projector)
            query.append(" AND Projector = '빔 있음'");
        if (reservation)
            query.append(" AND ReservationRequired = '예약 필요'");
        if (recording)
            query.append(" AND RecordingAvailable = '가능'");
        if (practicable)
            query.append(" AND Practicable = '실습 가능'");

        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String roomNumber = rs.getString("Room_Number");
                String availableTimes = getAvailableTimes(rs);
                if (!availableTimes.isEmpty()) {
                    message += roomNumber + " 가능 시간: " + availableTimes + "\n";
                }
            }

            if (!message.equals("검색된 교실의 번호:\n")) {
                infoArea.setText(message);
            } else {
                infoArea.setText("원하는 교실이 없습니다. 조건을 재선택하세요.");
            }

        } catch (SQLException e) {
            e.printStackTrace();
            infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다. 다시 확인하세요.");
        }
    }

    private String getAvailableTimes(ResultSet rs) throws SQLException {
        StringBuilder availableTimes = new StringBuilder();
        for (String time : timeDictionary.keySet()) {
            if (timeDictionary.get(time) && rs.getBoolean(time)) {
                availableTimes.append(time).append(" ");
            }
        }
        return availableTimes.toString().trim();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Professor_findClassroom();
            }
        });
    }
}
