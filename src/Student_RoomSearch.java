import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class Student_RoomSearch {
    String usage;
    String seats;
    boolean content;
    boolean project;
    boolean eat;
    boolean computer;
    Map<String, Boolean> timeDictionary=new HashMap<>();
    private JTextArea infoArea;

    public Student_RoomSearch() {
        // Create frame
        JFrame frame = new JFrame("GONG-GANG");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1200, 700);
        frame.setLayout(new BorderLayout());

        // Title
        JLabel title = new JLabel("GONG-GANG", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        frame.add(title, BorderLayout.NORTH);

        // Content panel
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        frame.add(contentPanel, BorderLayout.CENTER);

        // Dropdowns
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new GridLayout(2, 2, 10, 10));
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usageLabel = new JLabel("공간 유형:");
        JComboBox<String> usageComboBox = new JComboBox<>(new String[]{"선택", "교실", "교실 외"});


        JLabel seatsLabel = new JLabel("좌석 수:");
        JComboBox<String> seatsComboBox = new JComboBox<>(new String[]{"선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seats=(String)seatsComboBox.getSelectedItem();

        dropdownPanel.add(usageLabel);
        dropdownPanel.add(usageComboBox);
        dropdownPanel.add(seatsLabel);
        dropdownPanel.add(seatsComboBox);
        contentPanel.add(dropdownPanel);

        // Checkboxes
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(3, 1));

        JCheckBox contentCheckBox = new JCheckBox("콘센트");
        JCheckBox beamProjectCheckBox = new JCheckBox("빔프로젝트");
        JCheckBox eatCheckBox = new JCheckBox("식사");
        JCheckBox computerCheckBox=new JCheckBox("컴퓨터");

        checkBoxPanel.add(contentCheckBox);
        checkBoxPanel.add(beamProjectCheckBox);
        checkBoxPanel.add(eatCheckBox);
        checkBoxPanel.add(computerCheckBox);
        contentPanel.add(checkBoxPanel);

        // Time table
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(8, 5));
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 시간 찾기"));

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        String[] times = {"1", "2", "3", "4", "5", "6", "7","8"};

        for (String time : times) {
            for (String day : days) {
                String name=day+time;
                timeDictionary.put(name,false);
                JCheckBox checkBox = new JCheckBox(day + " " + time);
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
        contentPanel.add(scrollPane);


        // Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());

        JButton backButton = new JButton("Back");
        JButton nextButton = new JButton("Next");
        nextButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage=(String)usageComboBox.getSelectedItem();
                seats=(String)seatsComboBox.getSelectedItem();

                content= contentCheckBox.isSelected();
                project= beamProjectCheckBox.isSelected();
                eat= eatCheckBox.isSelected();
                computer= computerCheckBox.isSelected();
                infoArea=new JTextArea(80,40);
                if(usage.equals("교실")) searchClassroomInfo(seats, content, project, eat, computer);

                //else searchClassroomExternalInfo(content, project, eat, computer);
                // 새 창을 여는 로직
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
        buttonPanel.add(nextButton);
        frame.add(buttonPanel, BorderLayout.SOUTH);

        // Set frame visible
        frame.setVisible(true);
    }
    private void searchClassroomInfo(String seats, boolean content, boolean project, boolean eat, boolean computer) {
//JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        //Database credentials
// MySQL 계정과 암호 입력
        final String user = "root";
        final String password = "kms1";
        String message = "검색된 교실의 번호: \n";



        String query;
        if (eat) {infoArea.setText("교실에서는 취식이 불가능합니다 재선택 해주세요"); return;};
        if (computer)
            if (project) {
                query = "SELECT * FROM DB2024_Classroom WHERE Projector='빔 있음' AND Practicable='실습가능'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    // 결과 집합을 순회하며 모든 행 처리
                    while (rs.next()) {
                        // for boolean empty=rs.getBoolean()
                        String Room_number = rs.getString("Room_number");



                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" 가능\n";
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
                query = "SELECT * FROM DB2024_Classroom WHERE Practicable='실습가능'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" 가능\n";
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
                query = "SELECT * FROM DB2024_Classroom WHERE Projector='빔 있음'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" 가능\n";
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
                query = "SELECT * FROM DB2024_Classroom";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" 가능\n";
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
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Student_RoomSearch();
            }
        });
    }
}

