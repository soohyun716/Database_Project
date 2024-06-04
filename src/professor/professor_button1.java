package professor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;

import javax.swing.JComboBox;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;

public class professor_button1 extends JFrame{

    private JPanel contentPane;

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


    public professor_button1() {
        setTitle("GONG-GANG");
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

        JLabel userLabel = new JLabel("- \uC6D0\uD558\uB294 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22));
        logoPanel.add(userLabel);


        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);

        // options 버튼 붙이는 Panel
        JPanel optionPanel = new JPanel();
        optionPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(optionPanel);
        optionPanel.setPreferredSize(new Dimension(500, 170));

        //comboBox 붙이는 Panel
        JPanel dropdownsPanel = new JPanel();
        optionPanel.add(dropdownsPanel);
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropdownsPanel.setBackground(new Color(255, 255, 255));
        dropdownsPanel.setSize(new Dimension(600, 200));
        dropdownsPanel.setLayout(new GridLayout(3, 2, 40, 10));

        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615   : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usageLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(usageLabel);

        JComboBox usageComboBox = new JComboBox(new String[]{"선택", "교실", "교실 외"});
        usageComboBox.setBackground(new Color(255, 255, 255));
        usageComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(usageComboBox);
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height));

        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218       : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        seatsLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsLabel);

        JComboBox seatsComboBox = new JComboBox(new String[]{"선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seatsComboBox.setBackground(new Color(255, 255, 255));
        seatsComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsComboBox);

        JLabel cameraLabel = new JLabel("\uCE74\uBA54\uB77C \uC720\uD615 :");
        dropdownsPanel.add(cameraLabel);
        cameraLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));

        JComboBox cameraComboBox = new JComboBox(new String[]{"선택", "고정식 카메라", "추적식 카메라"});
        cameraComboBox.setBackground(new Color(255, 255, 255));
        cameraComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(cameraComboBox);

        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER,15,10));
        checkBoxPanel.setBackground(new Color(255, 255, 255));
        optionPanel.add(checkBoxPanel);

        JCheckBox CheckBox1 = new JCheckBox("\uCF58\uC13C\uD2B8");
        CheckBox1.setBackground(new Color(255, 255, 255));
        checkBoxPanel.add(CheckBox1);
        CheckBox1.setFont(new Font("나눔고딕", Font.BOLD, 13));

        JCheckBox CheckBox2 = new JCheckBox("\uBE54\uD504\uB85C\uC81D\uD2B8");
        checkBoxPanel.add(CheckBox2);
        CheckBox2.setBackground(new Color(255, 255, 255));
        CheckBox2.setFont(new Font("나눔고딕", Font.BOLD, 13));

        JCheckBox CheckBox3 = new JCheckBox("\uC608\uC57D \uD544\uC694");
        checkBoxPanel.add(CheckBox3);
        CheckBox3.setBackground(new Color(255, 255, 255));
        CheckBox3.setFont(new Font("나눔고딕", Font.BOLD, 13));

        JCheckBox CheckBox4 = new JCheckBox("\uB179\uD654 \uAC00\uB2A5");
        checkBoxPanel.add(CheckBox4);
        CheckBox4.setBackground(new Color(255, 255, 255));
        CheckBox4.setFont(new Font("나눔고딕", Font.BOLD, 13));

        JCheckBox CheckBox5 = new JCheckBox("\uC2E4\uC2B5 \uAC00\uB2A5");
        checkBoxPanel.add(CheckBox5);
        CheckBox5.setBackground(new Color(255, 255, 255));
        CheckBox5.setFont(new Font("나눔고딕", Font.BOLD, 13));


        // Time table
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(8, 5));
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 교시 선택"));
        timePanel.setBackground(new Color(255,255,255));

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        String[] times = {"1", "2", "3", "4", "5", "6", "7","8"};

        for (String time : times) {
            for (String day : days) {
                String name=day+time;
                timeDictionary.put(name,false);
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.setBackground(new Color(255,255,255));
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeDictionary.put(name, true);

                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                timePanel.add(checkBox);

            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel);
        mainPanel.add(scrollPane);
        scrollPane.setBackground(new Color(255,255,255));



        //resultButton & homeButton
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(ButtonPanel, BorderLayout.SOUTH);
        ButtonPanel.setLayout(new BorderLayout(0, 0));

        JPanel resultPanel = new JPanel(new FlowLayout());
        resultPanel.setBackground(new Color(255, 255, 255));
        ButtonPanel.add(resultPanel, BorderLayout.CENTER);
        JButton resultButton = new JButton("\uAC80\uC0C9");
        resultButton.setFont(new Font("나눔고딕", Font.BOLD, 16));
        resultButton.setBackground(new Color(255, 255, 255));
        resultPanel.add(resultButton);

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage = (String) usageComboBox.getSelectedItem();
                seats = (String) seatsComboBox.getSelectedItem();
                cameraType = (String) cameraComboBox.getSelectedItem();

                outlet = CheckBox1.isSelected();
                projector = CheckBox2.isSelected();
                reservation = CheckBox3.isSelected();
                recording = CheckBox4.isSelected();
                practicable = CheckBox5.isSelected();
                infoArea = new JTextArea(80, 40);

                searchClassroomInfo();

                // 새로운 창을 열어 결과를 보여줌
                // 새 창을 여는 로직
                JFrame newFrame = new JFrame("검색된 정보");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setBackground(new Color(255, 255, 255));
                newFrame.setBounds(100, 100, 1100, 600);
                newFrame.setLocation(50, 50);

                newFrame.getContentPane().setForeground(new Color(255, 255, 255));
                newFrame.getContentPane().setLayout(new BorderLayout(10,10));

                infoArea.setEditable(false);
                newFrame.getContentPane().add(infoArea);
                newFrame.setVisible(true);

                // 로고 붙이는 Panel
                JPanel logoPanel = new JPanel();
                logoPanel.setBackground(new Color(255, 255, 255));
                logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
                newFrame.getContentPane().add(logoPanel, BorderLayout.NORTH);
                logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
                newFrame.getContentPane().setBackground(new Color(255,255,255));

                // GONG-GANG 로고 label 생성
                JLabel logo = new JLabel("Gong-Gang");
                logo.setBackground(new Color(255, 255, 255));
                logo.setHorizontalAlignment(SwingConstants.CENTER);
                logo.setFont(new Font("Arial Black", Font.BOLD, 40));
                logoPanel.add(logo);

                JLabel userLabel = new JLabel("- 결과 -");
                userLabel.setHorizontalAlignment(SwingConstants.CENTER);
                userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22));
                logoPanel.add(userLabel);
            }
        });


        //homebuttom
        JPanel homePanel = new JPanel();
        homePanel.setBackground(new Color(255, 255, 255));
        JButton homeButton = new JButton("HOME");
        ButtonPanel.add(homePanel, BorderLayout.EAST);
        homePanel.setLayout(new BorderLayout(0, 0));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12));
        homePanel.add(homeButton, BorderLayout.SOUTH);

        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });

        // 디자인상 필요한 부분
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(255,255,255));
        ButtonPanel.add(emptyPanel, BorderLayout.WEST);
        emptyPanel.setPreferredSize(new Dimension(90,0));

    }

    private void searchClassroomInfo() {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver";
        final String DB_URL = "jdbc:mysql://localhost:3306/DB2024Team05";
        final String USER = "DB2024Team05";
        final String PASS = "DB2024Team05";
        String message = "검색된 교실의 번호:\n";

        StringBuilder query = new StringBuilder("SELECT * FROM ClassroomView WHERE 1=1");

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
                String Room_name = rs.getString("Room_Name");
                String Location = rs.getString("Location");
                String availableTimes = getAvailableTimes(rs);
                if (!availableTimes.isEmpty()) {
                    message += roomNumber+" "+Room_name+" "+Location + " 가능 시간: " + availableTimes + "\n";
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


}