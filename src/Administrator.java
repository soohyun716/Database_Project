//Import required packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.util.Scanner;

/*
 * 데이터베이스 수정 및 관리 위한 코드
 *
 * [투플 값 관리] 추가/수정/삭제 or 투플보기 선택
 * -> 5가지 테이블 중 관리할 테이블 선택
 * 추가 -> 데이터 입력받기
 * 수정 -> 변경할 데이터 선택
 * 삭제 -> 삭제할 데이터 선택
 * or 투플보기
 */

public class Administrator { // 메인 실행 함수
    public static void main(String[] args) {
        new AdministratorPage();
        // new addCommonComponents();
    }
}

class AdministratorPage extends JFrame implements ActionListener {
    Scanner input = new Scanner(System.in);
    public JLabel result = new JLabel();
    public JTextField inputField = new JTextField(30); // 사용자 입력을 받기 위한 텍스트 필드 추가
    Container ct = getContentPane(); // 컨테이너 객체 생성
    private String tableName;
    DefaultListModel<String> listModel = new DefaultListModel<>(); // JList에 사용할 모델

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/DB_2024";
    // Database credentials
    // MySQL 계정과 암호 입력
    static final String USER = "root";
    static final String PASS = "rootroot";

    String command1 = null;
    String command2 = null;

    public AdministratorPage() {
        // 컨테이너 생성 후 컴포넌트를 추가
        ct.setLayout(new FlowLayout()); // 배치관리자 설정

        JButton jb_insert = new JButton("투플추가");
        jb_insert.addActionListener(this);
        ct.add(jb_insert);

        JButton jb_modify = new JButton("투플수정");
        jb_modify.addActionListener(this);
        ct.add(jb_modify);

        JButton jb_delete = new JButton("투플삭제");
        jb_delete.addActionListener(this);
        ct.add(jb_delete);

        JButton jb_show = new JButton("투플보기");
        jb_show.addActionListener(this);
        ct.add(jb_show);

        setTitle("관리자");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫을 때 프로그램이 종료
        setSize(800, 400);
        setVisible(true);
    }

    public void addCommonComponents() {
        ct.removeAll();

        // 공통으로 사용할 컴포넌트를 추가하는 메서드
        ct.setLayout(new FlowLayout());

        JButton jb1 = new JButton("Space_Info");
        jb1.addActionListener(this);
        ct.add(jb1);

        JButton jb2 = new JButton("Classroom");
        jb2.addActionListener(this);
        ct.add(jb2);

        JButton jb3 = new JButton("Classroom_External");
        jb3.addActionListener(this);
        ct.add(jb3);

        JButton jb4 = new JButton("Professor");
        jb4.addActionListener(this);
        ct.add(jb4);

        JButton jb5 = new JButton("Lecture");
        jb5.addActionListener(this);
        ct.add(jb5);

        setVisible(true);
    }

    // ActionEvent 클래스와 ActionListener 리스너 인터페이스를 사용해서 버튼 누르는 이벤트를 처리
    @Override
    public void actionPerformed(ActionEvent e) {

        String command = e.getActionCommand();

        if (command.equals("투플추가")) {
            command1 = command;
            addCommonComponents();
        } else if (command.equals("투플수정")) {
            command1 = command;
            addCommonComponents();
        } else if (command.equals("투플삭제")) {
            command1 = command;
            addCommonComponents();
        } else if (command.equals("투플보기")) {
            command1 = command;
            addCommonComponents();
        } else {
            command2 = command;

            if ("Space_Info".equals(command2) || "Classroom".equals(command2) || "Classroom_External".equals(command2)
                    || "Professor".equals(command2) || "Lecture".equals(command2)) {
                tableName = getTableName(command2);
            }
            run_prog(command1, tableName);
        }
    }

    // ActionEvent 클래스와 ActionListener 리스너 인터페이스를 사용해서 버튼 누르는 이벤트를 처리

    public void run_prog(String command1, String tableName) {

        if (command1.equals("투플보기")) {
            String query3 = "SELECT * FROM " + tableName;
            System.out.println(query3); // 디버깅을 위해 출력

            try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                 Statement stmt1 = conn.createStatement();) {

                // 결과 확인
                ResultSet rs = stmt1.executeQuery(query3); // 바로 디비 반영
                listModel.clear();
                StringBuilder sb = new StringBuilder();
                if (tableName.equals("DB2024_Space_Info")) {
                    listModel.addElement("Room_Number:\tRoom_Name:\t\tLocataion:");

                    while (rs.next()) {
                        String Room_Number = rs.getString(1);
                        String Room_Name = rs.getString(2);
                        String Location = rs.getString(3);

                        String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Location;
                        listModel.addElement(row);
                    }
                }

                else if (tableName.equals("DB2024_Classroom")) {
                    listModel.addElement(
                            "Room_Number:\tPracticable:\t\tSeatCount:\t\tProjector:\t\tOutletCount:\t\tReservationRequired:\t\tRecordingAvailable:\t\tCameraType:");

                    while (rs.next()) {
                        String Room_Number = rs.getString(1);
                        String Practicable = rs.getString(2);
                        int SeatCount = rs.getInt(3);
                        String Projector = rs.getString(4);
                        int OutletCount = rs.getInt(5);
                        String ReservationRequired = rs.getString(6);
                        String RecordingAvailable = rs.getString(7);
                        String CameraType = rs.getString(8);

                        String row = Room_Number + "\t\t\t" + Practicable + "\t\t\t" + SeatCount + "\t\t\t" + Projector
                                + "\t\t\t" + OutletCount + "\t\t\t" + ReservationRequired + "\t\t\t"
                                + RecordingAvailable + "\t\t\t" + CameraType;
                        listModel.addElement(row);
                    }

                } else if (tableName.equals("DB2024_Classroom_External")) {
                    listModel.addElement(
                            "Room_Number:\tRoom_Name:\t\tEat_Available:\t\tNoise_Level:\t\tReservation_Needed:\t\tLocation:\t\tSeat_Count:\t\tOutlet_Count:");

                    while (rs.next()) {
                        String Room_Number = rs.getString(1);
                        String Room_Name = rs.getString(2);
                        boolean Eat_Available = rs.getBoolean(3);
                        boolean Noise_Level = rs.getBoolean(4);
                        boolean Reservation_Needed = rs.getBoolean(5);
                        String Location = rs.getString(6);
                        int Seat_Count = rs.getInt(7);
                        int Outlet_Count = rs.getInt(8);
                        String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Eat_Available + "\t\t\t"
                                + Noise_Level + "\t\t\t" + Reservation_Needed + "\t\t\t" + Location + "\t\t"
                                + Seat_Count + "\t\t\t" + Outlet_Count;
                        listModel.addElement(row);
                    }

                } else if (tableName.equals("DB2024_Professor")) {
                    listModel.addElement("Professor_Num:\tName:\t\tLab_Location:\t\tPhone:\t\tEmail:");
                    while (rs.next()) {
                        int Professor_Num = rs.getInt(1);
                        String Name = rs.getString(2);
                        String Lab_Location = rs.getString(3);
                        String Phone = rs.getString(4);
                        String Email = rs.getString(5);

                        String row = Professor_Num + "\t\t\t" + Name + "\t\t\t" + Lab_Location + "\t\t\t" + Phone
                                + "\t\t\t" + Email;
                        listModel.addElement(row);
                    }

                } else if (tableName.equals("DB2024_Lecture")) {
                    listModel.addElement(
                            "Lecture_Num:\tClass_Num:\t\tLecture_Name:\t\tProfessor_Num:\t\tProfessor_Name:\t\tRoom_Number:\t\tLecture_Time1:\t\tLecture_Time2:");
                    while (rs.next()) {
                        int Lecture_Num = rs.getInt(1);
                        int Class_Num = rs.getInt(2);
                        String Lecture_Name = rs.getString(3);
                        int Professor_Num = rs.getInt(4);
                        String Professor_Name = rs.getString(5);
                        String Room_Number = rs.getString(6);
                        String Lecture_Time1 = rs.getString(7);
                        String Lecture_Time2 = rs.getString(8);

                        String row = Lecture_Num + "\t\t\t" + Class_Num + "\t\t\t" + Lecture_Name + "\t\t\t"
                                + Professor_Num + "\t\t\t" + Professor_Name + "\t\t\t" + Room_Number + "\t\t"
                                + Lecture_Time1 + "\t\t\t" + Lecture_Time2;
                        listModel.addElement(row);
                    }

                }
                rs.close();

                // JList와 JScrollPane 설정
                JList<String> list = new JList<>(listModel);
                list.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 리스트의 글꼴을 모노스페이스로 설정하여 탭 정렬을 맞추기 용이하게 함
                JScrollPane scrollPane = new JScrollPane(list);

                // 결과를 JPanel에 추가
                JPanel resultPanel = new JPanel();
                resultPanel.setLayout(new BorderLayout());
                resultPanel.add(scrollPane, BorderLayout.CENTER);

                ct.removeAll(); // 기존 컴포넌트 제거
                ct.add(resultPanel); // JPanel을 프레임에 추가

                ct.revalidate(); // 레이아웃을 재검증
                ct.repaint(); // 컴포넌트 다시 그리기

            } catch (SQLException se) {
                se.printStackTrace();
            }

        } else if (command1.equals("투플추가")) {
            // 테이블 이름을 선택하는 버튼 추가

            ct.removeAll();

            // 투플 정보를 입력받는 필드 추가
            result.setText("추가하고 싶은 투플정보를 입력하세요: ");
            ct.add(result);
            ct.add(inputField);

            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tupleInfo = inputField.getText();
                    inputField.setText(""); // 입력 필드 초기화
                    String query1 = "INSERT INTO " + tableName + " VALUES (" + tupleInfo + ")";
                    System.out.println(query1); // 디버깅을 위해 출력
                    String query2 = "SELECT * FROM " + tableName;
                    System.out.println(query2); // 디버깅을 위해 출력
                    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                         PreparedStatement pStmt = conn.prepareStatement(query1);
                         Statement stmt1 = conn.createStatement();) {
                        pStmt.executeUpdate();

                        // 결과 확인
                        ResultSet rs = stmt1.executeQuery(query2); // 바로 디비 반영
                        listModel.clear();
                        StringBuilder sb = new StringBuilder();
                        if (tableName.equals("DB2024_Space_Info")) {
                            listModel.addElement("Room_Number:\tRoom_Name:\t\tLocataion:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Room_Name = rs.getString(2);
                                String Location = rs.getString(3);

                                String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Location;
                                listModel.addElement(row);
                            }
                        }

                        else if (tableName.equals("DB2024_Classroom")) {
                            listModel.addElement(
                                    "Room_Number:\tPracticable:\t\tSeatCount:\t\tProjector:\t\tOutletCount:\t\tReservationRequired:\t\tRecordingAvailable:\t\tCameraType:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Practicable = rs.getString(2);
                                int SeatCount = rs.getInt(3);
                                String Projector = rs.getString(4);
                                int OutletCount = rs.getInt(5);
                                String ReservationRequired = rs.getString(6);
                                String RecordingAvailable = rs.getString(7);
                                String CameraType = rs.getString(8);

                                String row = Room_Number + "\t\t\t" + Practicable + "\t\t\t" + SeatCount + "\t\t\t"
                                        + Projector + "\t\t\t" + OutletCount + "\t\t\t" + ReservationRequired + "\t\t\t"
                                        + RecordingAvailable + "\t\t\t" + CameraType;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Classroom_External")) {
                            listModel.addElement(
                                    "Room_Number:\tRoom_Name:\t\tEat_Available:\t\tNoise_Level:\t\tReservation_Needed:\t\tLocation:\t\tSeat_Count:\t\tOutlet_Count:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Room_Name = rs.getString(2);
                                boolean Eat_Available = rs.getBoolean(3);
                                boolean Noise_Level = rs.getBoolean(4);
                                boolean Reservation_Needed = rs.getBoolean(5);
                                String Location = rs.getString(6);
                                int Seat_Count = rs.getInt(7);
                                int Outlet_Count = rs.getInt(8);
                                String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Eat_Available + "\t\t\t"
                                        + Noise_Level + "\t\t\t" + Reservation_Needed + "\t\t\t" + Location + "\t\t"
                                        + Seat_Count + "\t\t\t" + Outlet_Count;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Professor")) {
                            listModel.addElement("Professor_Num:\tName:\t\tLab_Location:\t\tPhone:\t\tEmail:");
                            while (rs.next()) {
                                int Professor_Num = rs.getInt(1);
                                String Name = rs.getString(2);
                                String Lab_Location = rs.getString(3);
                                String Phone = rs.getString(4);
                                String Email = rs.getString(5);

                                String row = Professor_Num + "\t\t\t" + Name + "\t\t\t" + Lab_Location + "\t\t\t"
                                        + Phone + "\t\t\t" + Email;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Lecture")) {
                            listModel.addElement(
                                    "Lecture_Num:\tClass_Num:\t\tLecture_Name:\t\tProfessor_Num:\t\tProfessor_Name:\t\tRoom_Number:\t\tLecture_Time1:\t\tLecture_Time2:");
                            while (rs.next()) {
                                int Lecture_Num = rs.getInt(1);
                                int Class_Num = rs.getInt(2);
                                String Lecture_Name = rs.getString(3);
                                int Professor_Num = rs.getInt(4);
                                String Professor_Name = rs.getString(5);
                                String Room_Number = rs.getString(6);
                                String Lecture_Time1 = rs.getString(7);
                                String Lecture_Time2 = rs.getString(8);

                                String row = Lecture_Num + "\t\t\t" + Class_Num + "\t\t\t" + Lecture_Name + "\t\t\t"
                                        + Professor_Num + "\t\t\t" + Professor_Name + "\t\t\t" + Room_Number + "\t\t"
                                        + Lecture_Time1 + "\t\t\t" + Lecture_Time2;
                                listModel.addElement(row);
                            }

                        }
                        rs.close();

                        // JList와 JScrollPane 설정
                        JList<String> list = new JList<>(listModel);
                        list.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 리스트의 글꼴을 모노스페이스로 설정하여 탭 정렬을 맞추기 용이하게 함
                        JScrollPane scrollPane = new JScrollPane(list);

                        // 결과를 JPanel에 추가
                        JPanel resultPanel = new JPanel();
                        resultPanel.setLayout(new BorderLayout());
                        resultPanel.add(scrollPane, BorderLayout.CENTER);

                        ct.removeAll(); // 기존 컴포넌트 제거
                        ct.add(resultPanel); // JPanel을 프레임에 추가

                        ct.revalidate(); // 레이아웃을 재검증
                        ct.repaint(); // 컴포넌트 다시 그리기

                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            });
            ct.revalidate();
            ct.repaint();

        }

        else if (command1.equals("투플수정")) {
            // 테이블 이름을 선택하는 버튼 추가
            ct.removeAll();

            // 투플 정보를 입력받는 필드 추가
            result.setText("수정하고 싶은 투플정보를 입력하세요(SET 이후부터 WHERE 포함해서 작성하시오) : ");
            ct.add(result);
            ct.add(inputField);
            inputField.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent e) {
                    String tupleInfo = inputField.getText();
                    result.setText("UPDATE " + tableName + " SET ");
                    inputField.setText(""); // 입력 필드 초기화

                    String query1 = "UPDATE " + tableName + " SET " + tupleInfo;
                    System.out.println(query1); // 디버깅을 위해 출력
                    String query2 = "SELECT * FROM " + tableName;
                    System.out.println(query2); // 디버깅을 위해 출력

                    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                         PreparedStatement pStmt = conn.prepareStatement(query1);
                         Statement stmt1 = conn.createStatement();) {
                        pStmt.executeUpdate();

                        // 결과 확인
                        ResultSet rs = stmt1.executeQuery(query2); // 바로 디비 반영
                        listModel.clear();
                        StringBuilder sb = new StringBuilder();
                        if (tableName.equals("DB2024_Space_Info")) {
                            listModel.addElement("Room_Number:\tRoom_Name:\t\tLocataion:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Room_Name = rs.getString(2);
                                String Location = rs.getString(3);

                                String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Location;
                                listModel.addElement(row);
                            }
                        }

                        else if (tableName.equals("DB2024_Classroom")) {
                            listModel.addElement(
                                    "Room_Number:\tPracticable:\t\tSeatCount:\t\tProjector:\t\tOutletCount:\t\tReservationRequired:\t\tRecordingAvailable:\t\tCameraType:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Practicable = rs.getString(2);
                                int SeatCount = rs.getInt(3);
                                String Projector = rs.getString(4);
                                int OutletCount = rs.getInt(5);
                                String ReservationRequired = rs.getString(6);
                                String RecordingAvailable = rs.getString(7);
                                String CameraType = rs.getString(8);

                                String row = Room_Number + "\t\t\t" + Practicable + "\t\t\t" + SeatCount + "\t\t\t"
                                        + Projector + "\t\t\t" + OutletCount + "\t\t\t" + ReservationRequired + "\t\t\t"
                                        + RecordingAvailable + "\t\t\t" + CameraType;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Classroom_External")) {
                            listModel.addElement(
                                    "Room_Number:\tRoom_Name:\t\tEat_Available:\t\tNoise_Level:\t\tReservation_Needed:\t\tLocation:\t\tSeat_Count:\t\tOutlet_Count:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Room_Name = rs.getString(2);
                                boolean Eat_Available = rs.getBoolean(3);
                                boolean Noise_Level = rs.getBoolean(4);
                                boolean Reservation_Needed = rs.getBoolean(5);
                                String Location = rs.getString(6);
                                int Seat_Count = rs.getInt(7);
                                int Outlet_Count = rs.getInt(8);
                                String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Eat_Available + "\t\t\t"
                                        + Noise_Level + "\t\t\t" + Reservation_Needed + "\t\t\t" + Location + "\t\t"
                                        + Seat_Count + "\t\t\t" + Outlet_Count;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Professor")) {
                            listModel.addElement("Professor_Num:\tName:\t\tLab_Location:\t\tPhone:\t\tEmail:");
                            while (rs.next()) {
                                int Professor_Num = rs.getInt(1);
                                String Name = rs.getString(2);
                                String Lab_Location = rs.getString(3);
                                String Phone = rs.getString(4);
                                String Email = rs.getString(5);

                                String row = Professor_Num + "\t\t\t" + Name + "\t\t\t" + Lab_Location + "\t\t\t"
                                        + Phone + "\t\t\t" + Email;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Lecture")) {
                            listModel.addElement(
                                    "Lecture_Num:\tClass_Num:\t\tLecture_Name:\t\tProfessor_Num:\t\tProfessor_Name:\t\tRoom_Number:\t\tLecture_Time1:\t\tLecture_Time2:");
                            while (rs.next()) {
                                int Lecture_Num = rs.getInt(1);
                                int Class_Num = rs.getInt(2);
                                String Lecture_Name = rs.getString(3);
                                int Professor_Num = rs.getInt(4);
                                String Professor_Name = rs.getString(5);
                                String Room_Number = rs.getString(6);
                                String Lecture_Time1 = rs.getString(7);
                                String Lecture_Time2 = rs.getString(8);

                                String row = Lecture_Num + "\t\t\t" + Class_Num + "\t\t\t" + Lecture_Name + "\t\t\t"
                                        + Professor_Num + "\t\t\t" + Professor_Name + "\t\t\t" + Room_Number + "\t\t"
                                        + Lecture_Time1 + "\t\t\t" + Lecture_Time2;
                                listModel.addElement(row);
                            }

                        }
                        rs.close();

                        // JList와 JScrollPane 설정
                        JList<String> list = new JList<>(listModel);
                        list.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 리스트의 글꼴을 모노스페이스로 설정하여 탭 정렬을 맞추기 용이하게 함
                        JScrollPane scrollPane = new JScrollPane(list);

                        // 결과를 JPanel에 추가
                        JPanel resultPanel = new JPanel();
                        resultPanel.setLayout(new BorderLayout());
                        resultPanel.add(scrollPane, BorderLayout.CENTER);

                        ct.removeAll(); // 기존 컴포넌트 제거
                        ct.add(resultPanel); // JPanel을 프레임에 추가

                        ct.revalidate(); // 레이아웃을 재검증
                        ct.repaint(); // 컴포넌트 다시 그리기
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            });
            ct.revalidate();
            ct.repaint();
        }

        else if (command1.equals("투플삭제")) {
            // 테이블 이름을 선택하는 버튼 추가
            ct.removeAll();

            // 투플 정보를 입력받는 필드 추가
            result.setText("삭제하고 싶은 투플정보를 입력하세요: ");
            ct.add(result);
            ct.add(inputField);
            inputField.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String tupleInfo = inputField.getText();
                    result.setText("DELETE FROM " + tableName + " WHERE ");
                    inputField.setText(""); // 입력 필드 초기화

                    String query1 = "DELETE FROM " + tableName + " WHERE " + tupleInfo;
                    System.out.println(query1); // 디버깅을 위해 출력
                    String query2 = "SELECT * FROM " + tableName;
                    System.out.println(query2); // 디버깅을 위해 출력

                    try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                         PreparedStatement pStmt = conn.prepareStatement(query1);
                         Statement stmt1 = conn.createStatement();) {
                        pStmt.executeUpdate();

                        // 결과 확인
                        ResultSet rs = stmt1.executeQuery(query2); // 바로 디비 반영
                        listModel.clear();
                        StringBuilder sb = new StringBuilder();
                        if (tableName.equals("DB2024_Space_Info")) {
                            listModel.addElement("Room_Number:\tRoom_Name:\t\tLocataion:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Room_Name = rs.getString(2);
                                String Location = rs.getString(3);

                                String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Location;
                                listModel.addElement(row);
                            }
                        }

                        else if (tableName.equals("DB2024_Classroom")) {
                            listModel.addElement(
                                    "Room_Number:\tPracticable:\t\tSeatCount:\t\tProjector:\t\tOutletCount:\t\tReservationRequired:\t\tRecordingAvailable:\t\tCameraType:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Practicable = rs.getString(2);
                                int SeatCount = rs.getInt(3);
                                String Projector = rs.getString(4);
                                int OutletCount = rs.getInt(5);
                                String ReservationRequired = rs.getString(6);
                                String RecordingAvailable = rs.getString(7);
                                String CameraType = rs.getString(8);

                                String row = Room_Number + "\t\t\t" + Practicable + "\t\t\t" + SeatCount + "\t\t\t"
                                        + Projector + "\t\t\t" + OutletCount + "\t\t\t" + ReservationRequired + "\t\t\t"
                                        + RecordingAvailable + "\t\t\t" + CameraType;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Classroom_External")) {
                            listModel.addElement(
                                    "Room_Number:\tRoom_Name:\t\tEat_Available:\t\tNoise_Level:\t\tReservation_Needed:\t\tLocation:\t\tSeat_Count:\t\tOutlet_Count:");

                            while (rs.next()) {
                                String Room_Number = rs.getString(1);
                                String Room_Name = rs.getString(2);
                                boolean Eat_Available = rs.getBoolean(3);
                                boolean Noise_Level = rs.getBoolean(4);
                                boolean Reservation_Needed = rs.getBoolean(5);
                                String Location = rs.getString(6);
                                int Seat_Count = rs.getInt(7);
                                int Outlet_Count = rs.getInt(8);
                                String row = Room_Number + "\t\t\t" + Room_Name + "\t\t\t" + Eat_Available + "\t\t\t"
                                        + Noise_Level + "\t\t\t" + Reservation_Needed + "\t\t\t" + Location + "\t\t"
                                        + Seat_Count + "\t\t\t" + Outlet_Count;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Professor")) {
                            listModel.addElement("Professor_Num:\tName:\t\tLab_Location:\t\tPhone:\t\tEmail:");
                            while (rs.next()) {
                                int Professor_Num = rs.getInt(1);
                                String Name = rs.getString(2);
                                String Lab_Location = rs.getString(3);
                                String Phone = rs.getString(4);
                                String Email = rs.getString(5);

                                String row = Professor_Num + "\t\t\t" + Name + "\t\t\t" + Lab_Location + "\t\t\t"
                                        + Phone + "\t\t\t" + Email;
                                listModel.addElement(row);
                            }

                        } else if (tableName.equals("DB2024_Lecture")) {
                            listModel.addElement(
                                    "Lecture_Num:\tClass_Num:\t\tLecture_Name:\t\tProfessor_Num:\t\tProfessor_Name:\t\tRoom_Number:\t\tLecture_Time1:\t\tLecture_Time2:");
                            while (rs.next()) {
                                int Lecture_Num = rs.getInt(1);
                                int Class_Num = rs.getInt(2);
                                String Lecture_Name = rs.getString(3);
                                int Professor_Num = rs.getInt(4);
                                String Professor_Name = rs.getString(5);
                                String Room_Number = rs.getString(6);
                                String Lecture_Time1 = rs.getString(7);
                                String Lecture_Time2 = rs.getString(8);

                                String row = Lecture_Num + "\t\t\t" + Class_Num + "\t\t\t" + Lecture_Name + "\t\t\t"
                                        + Professor_Num + "\t\t\t" + Professor_Name + "\t\t\t" + Room_Number + "\t\t"
                                        + Lecture_Time1 + "\t\t\t" + Lecture_Time2;
                                listModel.addElement(row);
                            }

                        }
                        rs.close();

                        // JList와 JScrollPane 설정
                        JList<String> list = new JList<>(listModel);
                        list.setFont(new Font("Monospaced", Font.PLAIN, 12)); // 리스트의 글꼴을 모노스페이스로 설정하여 탭 정렬을 맞추기 용이하게 함
                        JScrollPane scrollPane = new JScrollPane(list);

                        // 결과를 JPanel에 추가
                        JPanel resultPanel = new JPanel();
                        resultPanel.setLayout(new BorderLayout());
                        resultPanel.add(scrollPane, BorderLayout.CENTER);

                        ct.removeAll(); // 기존 컴포넌트 제거
                        ct.add(resultPanel); // JPanel을 프레임에 추가

                        ct.revalidate(); // 레이아웃을 재검증
                        ct.repaint(); // 컴포넌트 다시 그리기
                    } catch (SQLException se) {
                        se.printStackTrace();
                    }
                }
            });
            ct.revalidate();
            ct.repaint();
        }

    }

    private String getTableName(String command2) {
        // 버튼에 따른 테이블 이름을 반환하는 메서드
        switch (command2) {
            case "Space_Info":
                return "DB2024_Space_Info";
            case "Classroom":
                return "DB2024_Classroom";
            case "Classroom_External":
                return "DB2024_Classroom_External";
            case "Professor":
                return "DB2024_Professor";
            case "Lecture":
                return "DB2024_Lecture";
            default:
                return null;
        }
    }

}// end main
