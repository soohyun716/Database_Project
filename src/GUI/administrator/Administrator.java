package GUI.administrator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;

//import mainFrame.mainGUI;

import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;
import GUI.mainFrame.mainGUI;


/*
 * 데이터베이스 수정 및 관리 위한 코드
 *
 * [투플 값 관리] 추가/수정/삭제 선택
 * -> 5가지 테이블 중 관리할 테이블 선택
 * 추가 -> 데이터 입력받기
 * 수정 -> 변경할 데이터 선택
 * 삭제 -> 삭제할 데이터 선택
 *
 */

public class Administrator { // 메인 실행 함수
    public Administrator(){
        new 관리자();
    }
}

class 관리자 extends JFrame implements ActionListener, MouseListener {

    public JLabel result = new JLabel();
    public JTextField inputField = new JTextField(30); // 사용자 입력을 받기 위한 텍스트 필드 추가

    public JLabel result_wh = new JLabel();
    public JTextField inputWhere = new JTextField(30); // 사용자 입력을 받기 위한 텍스트 필드 추가

    Container ct = getContentPane(); // 컨테이너 객체 생성

    JButton menu1_back = new JButton("뒤로가기");
    JButton input_back = new JButton("뒤로가기");

    boolean AutoCommit_flag = true;
    JTable table;

    final int X = 200;
    final int Y = 50;     // Next = Y * n
    final int Y_gap = 40;

    //input Start Point
    final int XE = 50;    // label Posision
    final int XE2 = 400;  // input Field
    final int YE = 50;    // Next = YE * n
    final int YE_gap = 40;
    JScrollPane scrollPane = null;
    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost/DB2024Team05";
    // Database credentials
    // MySQL 계정과 암호 입력
    static final String USER = "root";
    static final String PASS = "kms1";

    private String tableName;

    String command1 = null;
    String command2 = null;

    TextField idField, passField;
    Button submitButton;

    public Connection conn;
    public Statement stmt;

    public void DatabaseManager() {
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public 관리자() {
        password();
    }

    public void password() {
        setTitle("ID Password Input");
        setSize(1100, 600);
        setLocation(50, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ct.setLayout(new BorderLayout()); // Set layout to BorderLayout
        setVisible(true);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        ct.add(logoPanel, BorderLayout.NORTH);

        JLabel logo1 = new JLabel("Gong-Gang");
        logo1.setFont(new Font("Arial Black", Font.BOLD, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        JLabel logo2 = new JLabel("Administrator LOGIN");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);

        //ID 입력
        Panel p1 = new Panel();
        p1.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Label idLabel = new Label(String.format("%20s :", "ID"));
        p1.add(idLabel);
        TextField idField = new TextField(20);
        p1.add(idField);

        //password 입력
        Panel p2 = new Panel();
        p2.setLayout(new FlowLayout(FlowLayout.RIGHT));
        Label passLabel = new Label(String.format("%20s:", "PASSWORD"));
        p2.add(passLabel);
        TextField passField = new TextField(20);
        passField.setEchoChar('*'); /* 비밀번호 입력 시 '*'로 표시*/
        p2.add(passField);

        //Enter 버튼
        JButton submitButton = new JButton("ENTER");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource() == submitButton) {
                    String id = idField.getText();
                    String password = passField.getText();

                    System.out.println("ID: " + id);
                    System.out.println("Password: " + password);

                    if (id.equals(USER) && password.equals(PASS)) {
                        DatabaseManager();	//한번만 실행한
                        menu1(); // pass
                    }
                }
            }
        });

        Panel p3 = new Panel();
        p3.setLayout(new FlowLayout(FlowLayout.LEFT));
        p3.add(submitButton);

        //Grid Center에 두기 위해 새로은 JPanel 생성
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(null); //위치 커스텀을 위해 null
        centerPanel.setBackground(new Color(255, 255, 255));

        //위치 조정
        p1.setBounds(X, Y * 1, 500, Y_gap); // x, y , Width, Height
        p2.setBounds(X, Y * 2, 500, Y_gap);
        p3.setBounds(500, Y * 3, 500, Y_gap);

        centerPanel.add(p1);
        centerPanel.add(p2);
        centerPanel.add(p3);

        ct.add(centerPanel, BorderLayout.CENTER);


    }

    public void menu1() {
        ct.removeAll();
        //revalidate();
        repaint();
        ct.setLayout(new BorderLayout()); // Set layout to BorderLayout
        setVisible(true);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        ct.add(logoPanel, BorderLayout.NORTH);

        // 공간이 필요하다면? 로고 label
        JLabel logo1 = new JLabel("공간이 필요하다면?");
        logo1.setFont(new Font("������� ExtraBold", Font.PLAIN, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        // GONG-GANG 로고 label
        JLabel logo2 = new JLabel("Gong-Gang");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);

        //관리자 이미지 가져오기
        ImageIcon adminImage_temp = new ImageIcon(Administrator.class.getResource("/images/administratorImage.png"));
        Image admin_img = adminImage_temp.getImage();
        Image admin_Changing = admin_img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon adminImage = new ImageIcon(admin_Changing);


        //관리자 임을 나타내는 이미지와 글
        JPanel adminPanel = new JPanel();
        adminPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 60));
        adminPanel.setBackground(new Color(255, 255, 255));
        ct.add(adminPanel, BorderLayout.CENTER);

        JLabel adminLabel = new JLabel("Administrator", adminImage, SwingConstants.CENTER);
        adminLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        adminLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        adminLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
        adminLabel.setBackground(new Color(255, 255, 255));
        adminPanel.add(adminLabel);


        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        adminPanel.add(panel);
        panel.setLayout(new GridLayout(3, 2, 20, 20));



        JCheckBox chkbox = new JCheckBox("AutoCommit");
        chkbox.addActionListener(this);
        chkbox.setBackground(new Color(255, 255, 255));
        chkbox.setSelected(AutoCommit_flag);


        JButton jb_insert = new JButton("투플추가");
        jb_insert.setBackground(new Color(255, 255, 255));
        jb_insert.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22));
        jb_insert.addActionListener(this);

        JButton jb_modify = new JButton("투플수정");
        jb_modify.setBackground(new Color(255, 255, 255));
        jb_modify.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        jb_modify.addActionListener(this);

        JButton jb_delete = new JButton("투플삭제");
        jb_delete.setBackground(new Color(255, 255, 255));
        jb_delete.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        jb_delete.addActionListener(this);

        JButton jb_commit = new JButton("Commit");
        jb_commit.setBackground(new Color(255, 255, 255));
        jb_commit.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        jb_commit.addActionListener(this);


        JButton jb_rollback = new JButton("Rollback");
        jb_rollback.setBackground(new Color(255, 255, 255));
        jb_rollback.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        jb_rollback.addActionListener(this);

        panel.add(jb_insert);
        panel.add(chkbox);
        panel.add(jb_modify);
        panel.add(jb_commit);
        panel.add(jb_delete);
        panel.add(jb_rollback);

        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        ct.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        homeButton.addActionListener(this);


        chkbox.setBounds(   X + 150, Y * 2, 200, Y_gap);

        jb_insert.setBounds(X + 150, Y * 5, 400, Y_gap);
        jb_modify.setBounds(X + 150, Y * 6, 400, Y_gap);
        jb_delete.setBounds(X + 150, Y * 7, 400, Y_gap);

        jb_commit.setBounds(X + 150, Y * 10, 200, Y_gap);
        jb_rollback.setBounds(X + 150, Y * 11, 200, Y_gap);

        setTitle("관리자");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫을 때 프로그램이 종료
        setVisible(true);
    }

    public void addCommonComponents() {
        ct.removeAll();
        //revalidate();
        repaint();
        ct.setLayout(new BorderLayout()); // Set layout to BorderLayout
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        ct.add(logoPanel, BorderLayout.NORTH);

        JLabel logo1 = new JLabel("Gong-Gang");
        logo1.setFont(new Font("Arial Black", Font.BOLD, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        JLabel logo2 = new JLabel("관리할 테이블을 골라주세요.");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("������� ExtraBold", Font.BOLD, 40));
        logoPanel.add(logo2);

        JPanel p = new JPanel();
        p.setBackground(new Color(255,255,255));
        ct.add(p,BorderLayout.CENTER);
        p.setSize(300,250);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        p.add(panel,BorderLayout.CENTER);
        panel.setLayout(new GridLayout(6,1,20,20));

        JButton jb1 = new JButton("Space_Info");
        jb1.setBackground(new Color(255, 255, 255));
        jb1.setFont(new Font("Arial Black", Font.PLAIN, 20));
        jb1.addActionListener(this);
        panel.add(jb1);

        JButton jb2 = new JButton("Classroom");
        jb2.setBackground(new Color(255, 255, 255));
        jb2.setFont(new Font("Arial Black", Font.PLAIN, 20));
        jb2.addActionListener(this);
        panel.add(jb2);

        JButton jb3 = new JButton("Classroom_External");
        jb3.setBackground(new Color(255, 255, 255));
        jb3.setFont(new Font("Arial Black", Font.PLAIN, 20));
        jb3.addActionListener(this);
        panel.add(jb3);

        JButton jb4 = new JButton("Professor");
        jb4.setBackground(new Color(255, 255, 255));
        jb4.setFont(new Font("Arial Black", Font.PLAIN, 20));
        jb4.addActionListener(this);
        panel.add(jb4);

        JButton jb5 = new JButton("Lecture");
        jb5.setBackground(new Color(255, 255, 255));
        jb5.setFont(new Font("Arial Black", Font.PLAIN, 20));
        jb5.addActionListener(this);
        panel.add(jb5);

        menu1_back.setBackground(new Color(255, 255, 255));
        menu1_back.setFont(new Font("������� ExtraBold", Font.PLAIN, 20));
        panel.add(menu1_back);
        menu1_back.addActionListener(this);

        setTitle("튜플 선택");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫을 때 프로그램이 종료
        setVisible(true);
    }

    // ActionEvent 클래스와 ActionListener 리스너 인터페이스를 사용해서 버튼 누르는 이벤트를 처리
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch(command) {
            case "투플추가":
            case "투플수정":
            case "투플삭제":
                command1 = command;
                addCommonComponents();
                break;

            case "AutoCommit":
                JCheckBox chkbox = (JCheckBox)e.getSource();
                try {
                    AutoCommit_flag = chkbox.isSelected();
                    conn.setAutoCommit(AutoCommit_flag);
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                break;

            case "Commit":
                try {
                    conn.commit(); // transaction succeed
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                break;

            case "Rollback":
                try {
                    conn.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                break;

            case "뒤로가기":
                if( e.getSource() == menu1_back) {
                    menu1();
                }
                else if (e.getSource() == input_back) {
                    addCommonComponents();
                }
                break;

            case "HOME":
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame

            case "Space_Info":
            case "Classroom":
            case "Classroom_External":
            case "Professor":
            case "Lecture":
                command2 = command;
                tableName = getTableName(command);
                showInputField();
                break;
        }
    }


    private void showInputField() {
        ct.removeAll();

        ct.setLayout(new BorderLayout()); // Set layout to BorderLayout

        run_prog(command1, tableName);

        input_back.setBackground(new Color(255, 255, 255));
        input_back.setFont(new Font("������� ExtraBold", Font.PLAIN, 15));
        input_back.setBounds( XE2+100, YE*3,  100, YE_gap );
        input_back.addActionListener(this);
        ct.add(input_back);

        결과보기(tableName); // select *

        revalidate();
        repaint();
    }

    public void run_prog(String command1, String tableName) {
        if (command1.equals("투플추가")) {
            // 투플 추가 로직 실행
            insertTuple(tableName);
        } else if (command1.equals("투플수정")) {
            // 투플 수정 로직 실행
            updateTuple(tableName);
        } else if (command1.equals("투플삭제")) {
            // 투플 삭제 로직 실행
            deleteTuple(tableName);
        }
    }

    private void insertTuple(String tableName) {
        // 기존 컴포넌트 초기화
        ct.setLayout(null);
        ct.setBackground(Color.white);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        ct.add(logoPanel);

        JLabel logo1 = new JLabel("Gong-Gang");
        logo1.setFont(new Font("Arial Black", Font.BOLD, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        JLabel logo2 = new JLabel("Insert");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);


        // 결과 텍스트를 선언 및 초기화
        result.setText("INSERT INTO " + tableName + " VALUES : ");

        // 결과 텍스트와 입력 필드를 컨테이너에 추가
        ct.add(result);
        ct.add(inputField);

        JButton confirmButton = new JButton("확인");
        confirmButton.setBackground(new Color(255, 255, 255));
        confirmButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 15));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (
                        Statement stmt = conn.createStatement()) {

                    String tupleInfo = inputField.getText();
                    if( tupleInfo.length() != 0) {
                        inputField.setText(""); // 입력 필드 초기화
                        String query = "INSERT INTO " + tableName + " VALUES (" + tupleInfo + ")";
                        System.out.println(query); // 디버깅을 위해 출력

                        // 쿼리 실행
                        stmt.executeUpdate(query);

                        // 결과를 다시 보여주기 위해 결과보기 메서드 호출
                        결과보기(tableName);
                    }

                } catch (SQLException se) {
                    se.printStackTrace();
                    if (AutoCommit_flag == false) {// 트랜잭션이 시작되었으므로 실패시 rollback 해야 한다.
                        // rollback here
                        try {
                            if (conn != null)
                                conn.rollback();
                        } catch (SQLException se2) {
                            se2.printStackTrace();
                        }
                    }

                }
            }
        });
        ct.add(confirmButton);

        logoPanel.setBounds(0,0,1100,100);
        result.setBounds(     XE,  YE*2,  400, YE_gap );
        inputField.setBounds( XE2, YE*2,  500, YE_gap );
        confirmButton.setBounds( XE2, YE*3,  100, YE_gap );

        setTitle("튜플추가");
        // 컨테이너 변경사항 적용 및 화면 다시 그리기
        ct.revalidate();
        ct.repaint();
    }

    private void updateTuple(String tableName) {
        // 기존 컴포넌트 초기화
        // see, https://blog.naver.com/PostView.nhn?blogId=javaking75&logNo=140157948347
        ct.setLayout(null);
        ct.setBackground(Color.white);
        inputWhere.setText(""); // 입력 필드 초기화
        inputField.setText(""); // 입력 필드 초기화

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        ct.add(logoPanel);

        JLabel logo1 = new JLabel("Gong-Gang");
        logo1.setFont(new Font("Arial Black", Font.BOLD, 10));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        JLabel logo2 = new JLabel("Update");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 20));
        logoPanel.add(logo2);


        // where condition
        result_wh.setText("UPDATE " + tableName + " WHERE Condition:");
        ct.add(result_wh);
        ct.add(inputWhere);

        //update data
        result.setText("UPDATE " + tableName + " SET Values  : ");
        ct.add(result);
        ct.add(inputField);

        JButton confirmButton = new JButton("확인");
        confirmButton.setBackground(new Color(255, 255, 255));
        confirmButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 15));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try (
                        Statement stmt = conn.createStatement()) {

                    String	condition_txt = inputWhere.getText();
                    String	tupleInfo = inputField.getText();

                    if( condition_txt.length() != 0  && tupleInfo.length() != 0) {
                        String query = "UPDATE " + tableName + " SET " + tupleInfo + " WHERE " + condition_txt;
                        System.out.println(query); // 디버깅을 위해 출력

                        stmt.executeUpdate(query);

                        // 결과를 다시 보여주기 위해 결과보기 메서드 호출
                        결과보기(tableName);
                    }
                    else {
                        System.out.println(tupleInfo); // 디버깅을 위해 출력
                        System.out.println(condition_txt); // 디버깅을 위해 출력
                    }


                } catch (SQLException se) {
                    se.printStackTrace();
                    if(AutoCommit_flag == false) {//트랜잭션이 시작되었으므로 실패시 rollback 해야 한다.
                        // rollback here
                        try {
                            if (conn != null) conn.rollback();
                        } catch (SQLException se2) {
                            se2.printStackTrace();
                        }
                    }

                }
            }
        });

        ct.add(confirmButton);

        logoPanel.setBounds(0,0,1100, 50);
        result.setBounds(      XE, YE*1,  400, YE_gap );
        inputField.setBounds( XE2, YE*1,  500, YE_gap );

        result_wh.setBounds(   XE, YE*2,  400, YE_gap );
        inputWhere.setBounds( XE2, YE*2,  500, YE_gap );

        confirmButton.setBounds( XE2, YE*3,  100, YE_gap );

        setTitle("튜플수정");
        // 컨테이너 변경사항 적용 및 화면 다시 그리기
        ct.revalidate();
        ct.repaint();
    }

    private void deleteTuple(String tableName) {
        // 기존 컴포넌트 초기화
        // ct.removeAll();
        ct.setLayout(null);
        ct.setBackground(Color.white);

        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        ct.add(logoPanel);

        JLabel logo1 = new JLabel("Gong-Gang");
        logo1.setFont(new Font("Arial Black", Font.BOLD, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        JLabel logo2 = new JLabel("Delete");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);
        // 결과 텍스트를 선언 및 초기화
        result.setText("DELETE " + tableName + " WHERE Condition: ");

        // 결과 텍스트와 입력 필드를 컨테이너에 추가
        ct.add(result);
        ct.add(inputField);

        JButton confirmButton = new JButton("확인");
        confirmButton.setBackground(new Color(255, 255, 255));
        confirmButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 15));
        confirmButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try (
                        Statement stmt = conn.createStatement()) {

                    String tupleInfo = inputField.getText();
                    if (tupleInfo.length() != 0) {
                        inputField.setText(""); // 입력 필드 초기화
                        String query = "DELETE FROM " + tableName + " WHERE " + tupleInfo;
                        System.out.println(query); // 디버깅을 위해 출력

                        // 쿼리 실행
                        stmt.executeUpdate(query);

                        // 결과를 다시 보여주기 위해 결과보기 메서드 호출
                        결과보기(tableName);
                    }

                } catch (SQLException se) {
                    se.printStackTrace();
                    if (AutoCommit_flag == false) { //트랜잭션이 시작되었으므로 실패시 rollback 해야 한다.
                        // rollback here
                        try {
                            if (conn != null)
                                conn.rollback();
                        } catch (SQLException se2) {
                            se2.printStackTrace();
                        }
                    }

                }
            }
        });
        ct.add(confirmButton);

        logoPanel.setBounds(0, 0, 1100, 100);
        result.setBounds(XE, YE * 2, 400, YE_gap);
        inputField.setBounds(XE2, YE * 2, 500, YE_gap);
        confirmButton.setBounds(XE2, YE * 3, 100, YE_gap);

        setTitle("튜플삭제");
        // 컨테이너 변경사항 적용 및 화면 다시 그리기
        ct.revalidate();
        ct.repaint();
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


    public void 결과보기(String tableName){
        String query2 = "SELECT * FROM " + tableName;
        System.out.println(query2); // 디버깅을 위해 출력

        DefaultTableModel tableModel = new DefaultTableModel();

        try (
                Statement stmt1 = conn.createStatement();
                ResultSet rs = stmt1.executeQuery(query2)) {

            // Get column names from ResultSet metadata
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            Vector<String> columnNames = new Vector<>();

            for (int column = 1; column <= columnCount; column++) {
                columnNames.add(metaData.getColumnName(column));
            }

            // Add column names to table model
            tableModel.setColumnIdentifiers(columnNames);

            // Get row data from ResultSet
            while (rs.next()) {
                Vector<Object> rowData = new Vector<>();
                for (int column = 1; column <= columnCount; column++) {
                    rowData.add(rs.getObject(column));
                }
                tableModel.addRow(rowData);
            }

        } catch (SQLException se) {
            se.printStackTrace();
        }

        // Create JTable with the table model
        JTable table = new JTable(tableModel);
        this.table = table;

        scrollPane = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        scrollPane.setAutoscrolls(true);

        Dimension size = ct.getSize();
        System.out.println("W:" + size.width + " H:" + size.height); // 디버깅을 위해 출력
        scrollPane.setBounds(10,200,size.width-20,size.height - 200);

        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        scrollPane.setAutoscrolls(true);

        // resize event  -> resize ScrollPane
        ct.addComponentListener(new ComponentAdapter(){
            public void componentResized(ComponentEvent e) {

                //scrollPane.setPreferredSize(new Dimension(size.width-20, size.height - 200));
                if( scrollPane != null) {
                    Dimension size = ct.getSize();
                    System.out.println("W:" + size.width + " H:" + size.height); // 디버깅을 위해 출력
                    scrollPane.setBounds(10,200,size.width-20,size.height - 200);
                    scrollPane.revalidate();
                    scrollPane.repaint();

                    ct.revalidate();
                    ct.repaint();
                }
            }

        });
        table.addMouseListener(this); /* --> mouseClicked */

        ct.add(scrollPane, FlowLayout.CENTER);

        scrollPane.setVisible(true);
        ct.revalidate();
        ct.repaint();
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        int row = table.getSelectedRow();
        int col = table.getSelectedColumn();
        String result="";
        String sel="";
		 /*
		 for (int i = 0; i < table.getColumnCount(); i++) {
			 result += table.getColumnName(i) + "=" + table.getModel().getValueAt(row, i);
			 if ( i < (table.getColumnCount()-1)) result += ",";
		 }
		 */
        System.out.println(row + "," + col + ":" + result);


        // set inputField
        sel = table.getColumnName(col) + "=" + table.getModel().getValueAt(row, col); //칼럼 이름 = 값을 inputField에 자동 입력 .
        switch(command1) {
            case "투플수정":
                if(inputField.getText().length() == 0  ) {
                    inputField.setText(sel);
                }
                else {
                    inputWhere.setText(sel);
                }
                break;
            case "투플삭제":
            case "투플추가":
                inputField.setText(sel);
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }


} // end main
