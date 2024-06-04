package GUI.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
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

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import GUI.mainFrame.mainGUI;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Component;
import javax.swing.UIManager;

/*
검색 ClassroomView를 통해 하도록 수정
출력하면에 번호, 이름, 위치, 가능시간 4개가 뜨도록 함.
*/

public class student_button1 extends JFrame{

    private JPanel contentPane;

    String usage;
    String seats;
    boolean content;
    boolean project;
    boolean eat;
    boolean computer;
    Map<String, Boolean> timeDictionary=new HashMap<>();
    private JTextArea infoArea;

    public student_button1() {

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

        //옵션들 붙이는 mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));

        //combobox와 checkbox 붙이는 subMainPanel
        JPanel subMainPanel = new JPanel();
        subMainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(subMainPanel);
        subMainPanel.setLayout(new GridLayout(2, 0, 10, 20));
        subMainPanel.setPreferredSize(new Dimension(400, 170));


        //checkBox Panel
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(checkBoxPanel);

        JCheckBox contentCheckBox = new JCheckBox("콘센트");
        contentCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        contentCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        contentCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox beamProjectCheckBox = new JCheckBox("빔프로젝트");
        beamProjectCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        beamProjectCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox eatCheckBox = new JCheckBox("식사");
        eatCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        eatCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        eatCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox computerCheckBox=new JCheckBox("컴퓨터");
        computerCheckBox.setFont(new Font("나눔고딕", Font.BOLD, 13));
        computerCheckBox.setBackground(new Color(255, 255, 255));
        checkBoxPanel.setLayout(new GridLayout(2, 2, 0, 2));
        checkBoxPanel.add(contentCheckBox);
        checkBoxPanel.add(beamProjectCheckBox);
        checkBoxPanel.add(eatCheckBox);
        checkBoxPanel.add(computerCheckBox);

        //dropdown Panel
        JPanel dropdownsPanel = new JPanel();
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropdownsPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(dropdownsPanel);
        dropdownsPanel.setSize(new Dimension(800,200));
        dropdownsPanel.setLayout(new GridLayout(2, 2, 100, 10));


        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615 : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usageLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(usageLabel);

        JComboBox usageComboBox = new JComboBox(new String[]{"선택", "교실", "교실 외"});
        usageComboBox.setBackground(new Color(255, 255, 255));
        usageComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(usageComboBox);
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height));

        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218    : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        seatsLabel.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsLabel);

        JComboBox seatsComboBox = new JComboBox(new String[]{"선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seatsComboBox.setBackground(new Color(255, 255, 255));
        seatsComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14));
        dropdownsPanel.add(seatsComboBox);


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

        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(255,255,255));
        ButtonPanel.add(emptyPanel, BorderLayout.WEST);
        emptyPanel.setPreferredSize(new Dimension(90,0));

        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage=(String)usageComboBox.getSelectedItem();
                seats=(String)seatsComboBox.getSelectedItem();

                content= contentCheckBox.isSelected();
                project= beamProjectCheckBox.isSelected();
                eat= eatCheckBox.isSelected();
                computer= computerCheckBox.isSelected();
                infoArea=new JTextArea(1100,600);
                if(usage.equals("교실")) searchClassroomInfo(seats, content, project, eat, computer);


                //else searchClassroomExternalInfo(content, project, eat, computer);
                // 새 창을 여는 로직
                JFrame newFrame = new JFrame("검색된 정보");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setBackground(new Color(255, 255, 255));
                newFrame.setBounds(100, 100, 1100, 600);
                newFrame.setLocation(50, 50);

                newFrame.getContentPane().setForeground(new Color(255, 255, 255));
                newFrame.getContentPane().setLayout(new BorderLayout(10,10));


                infoArea.setEditable(false);
                //infoArea.setPreferredSize(new Dimension(700,400));
                newFrame.getContentPane().add(infoArea, BorderLayout.CENTER);
                newFrame.setVisible(true);


                //newFrame.add(contentPane);


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




    }


    private void searchClassroomInfo(String seats, boolean content, boolean project, boolean eat, boolean computer) {

        //JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        //Database credentials

        // MySQL 계정과 암호 입력
        final String user = "DB2024Team05";
        final String password = "DB2024Team05";
        String message = "검색된 교실의 번호: \n";


        String query;
        if (eat) {infoArea.setText("교실에서는 취식이 불가능합니다. 재선택 해주세요"); return;};
        if (computer)
            if (project) {
                query = "SELECT * FROM ClassroomView WHERE Projector='빔 있음' AND Practicable='실습가능'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    // 결과 집합을 순회하며 모든 행 처리
                    while (rs.next()) {
                        // for boolean empty=rs.getBoolean()
                        String Room_number = rs.getString("Room_number");
                        String Room_name = rs.getString("Room_Name");
                        String Location = rs.getString("Location");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " +Room_name+ " " +Location+ " " + key+" 가능\n";
                            };
                        }

                    }


                    // 결과 문자열을 텍스트 영역에 설정
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("원하는 교실이 없습니다. 조건을 재선택하세요.");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다. 다시 확인하세요");
                }
                return;


            } else {
                query = "SELECT * FROM ClassroomView WHERE Practicable='실습가능'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");
                        String Room_name = rs.getString("Room_Name");
                        String Location = rs.getString("Location");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " +Room_name+ " " +Location+ " " + key+" 가능\n";
                            };
                        }
                    }



                    // 결과 문자열을 텍스트 영역에 설정
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("원하는 교실이 없습니다. 조건을 재선택하세요.");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다. 다시 확인하세요");
                }return;
            }
        else {
            if(project){
                query = "SELECT * FROM ClassroomView WHERE Projector='빔 있음'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");
                        String Room_name = rs.getString("Room_Name");
                        String Location = rs.getString("Location");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " +Room_name+ " " +Location+ " " + key+" 가능\n";
                            };
                        }
                    }


                    // 결과 문자열을 텍스트 영역에 설정
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("원하는 교실이 없습니다. 조건을 재선택하세요.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다. 다시 확인하세요");
                }return;
            }else {
                query = "SELECT * FROM ClassroomView";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");
                        String Room_name = rs.getString("Room_Name");
                        String Location = rs.getString("Location");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " +Room_name+ " " +Location+ " " + key+" 가능\n";
                            };
                        }
                    }


                    // 결과 문자열을 텍스트 영역에 설정
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("원하는 교실이 없습니다. 조건을 재선택하세요.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다. 다시 확인하세요");
                }return;
            }


        }

    }
    //private void searchClassroomExternalInfo(boolean content, boolean project, boolean eat, boolean computer){
    // }
    public static boolean isNumberInRange(String range, int number) {
        String[] parts = range.split("-");



        int start = Integer.parseInt(parts[0].trim());
        int end = Integer.parseInt(parts[1].trim());

        return number >= start && number <= end;

    }


}