package professor;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import java.sql.*;
import java.util.Calendar;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import mainFrame.mainGUI;

public class professor_button2 extends JFrame {

    private JPanel contentPane;
    private JTextField searchField;
    private JTable lectureTable;
    private JTextArea professorInfoArea;
    private Connection connection;


    /**
     * Create the frame.
     */
    public professor_button2() {
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

        // Header Panel containing logo and search panel
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 255, 255));
        headerPanel.setLayout(new BorderLayout());

        // Logo Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel

        JLabel logo1 = new JLabel("공간이 필요하다면?");
        logo1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        JLabel logo2 = new JLabel("Gong-Gang");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);

        headerPanel.add(logoPanel, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(255, 255, 255));
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        JLabel searchLabel = new JLabel("교수님 이름:");
        searchLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchPanel.add(searchLabel);

        searchField = new JTextField();
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchField.setColumns(20);
        searchPanel.add(searchField);

        JButton searchButton = new JButton("검색");
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchProfessor();
            }
        });
        searchPanel.add(searchButton);

        headerPanel.add(searchPanel, BorderLayout.SOUTH);

        contentPane.add(headerPanel, BorderLayout.NORTH);


        //홈 버튼 생성
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("Arial ExtraBold", Font.PLAIN, 12));
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



        // 결과 영역 (강의 테이블)
        String[] columnNames = { "", "월", "화", "수", "목", "금" }; // 테이블 컬럼 이름 설정
        String[][] data = new String[9][6]; // 테이블 데이터 배열 생성, 9교시 기준
        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1); // 교시 번호 설정
        }
        lectureTable = new JTable(data, columnNames); // 테이블 생성
        lectureTable.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        lectureTable.setRowHeight(50); // 행 높이 설정
        JScrollPane scrollPane = new JScrollPane(lectureTable); // 스크롤 패널 생성
        contentPane.add(scrollPane, BorderLayout.CENTER); // 메인 패널에 추가
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 로드
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "DB2024Team05", "DB2024Team05"); // 데이터베이스
            // 연결
            System.out.println("Database connected successfully."); // 연결 성공 메시지 출력
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    private void searchProfessor() {
        String professorName = searchField.getText().trim(); // 검색어 가져오기
        if (professorName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "교수님 이름을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE); // 검색어 없을 시 경고 메시지
            return;
        }

        try {
            ProfessorInfo professorInfo = getProfessorInfo(professorName); // 교수 정보 가져오기
            if (professorInfo != null) {
                updateLectureTable(professorName); // 테이블 업데이트
            } else {
                JOptionPane.showMessageDialog(this, "해당 교수님의 정보를 찾을 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE); // 교수
                // 정보
                // 없을
                // 시
                // 경고
                // 메시지
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 발생 시 스택 트레이스 출력
        }
    }

    private ProfessorInfo getProfessorInfo(String professorName) throws SQLException {
        ProfessorInfo professorInfo = null;

        String professorQuery = "SELECT Name, Lab_Location, Phone, Email FROM DB2024_Professor WHERE Name = ?"; // 교수 정보
        // 쿼리
        PreparedStatement professorStmt = connection.prepareStatement(professorQuery); // 쿼리 준비
        professorStmt.setString(1, professorName); // 검색어 설정
        ResultSet professorRs = professorStmt.executeQuery(); // 쿼리 실행

        if (professorRs.next()) {
            professorInfo = new ProfessorInfo();
            professorInfo.setName(professorRs.getString("Name")); // 이름 설정
        }

        return professorInfo; // 교수 정보 반환
    }

    private void updateLectureTable(String professorName) {
        // 테이블 초기화
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j < 6; j++) {
                lectureTable.setValueAt("", i, j); // 테이블 셀 초기화
            }
        }

        try {
            String lectureQuery = "SELECT Lecture_Name, Lecture_Time1, Lecture_Time2 FROM DB2024_Lecture WHERE Professor_Name = ?";
            PreparedStatement lectureStmt = connection.prepareStatement(lectureQuery);
            lectureStmt.setString(1, professorName);
            ResultSet lectureRs = lectureStmt.executeQuery();

            while (lectureRs.next()) {
                String lectureName = lectureRs.getString("Lecture_Name");
                String lectureTime1 = lectureRs.getString("Lecture_Time1");
                String lectureTime2 = lectureRs.getString("Lecture_Time2");

                int dayIndex = getDayIndex(lectureTime1);
                if (dayIndex != -1) {
                    int period = Integer.parseInt(lectureTime1.substring(1)) - 1;
                    lectureTable.setValueAt(lectureName, period, dayIndex);
                }

                if (lectureTime2 != null) {
                    dayIndex = getDayIndex(lectureTime2);
                    if (dayIndex != -1) {
                        int period = Integer.parseInt(lectureTime2.substring(1)) - 1;
                        lectureTable.setValueAt(lectureName, period, dayIndex);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // 현재 요일과 교시 강조 표시
        highlightCurrentDayAndPeriod();
    }

    private int getDayIndex(String time) {
        switch (time.charAt(0)) {
            case '월':
                return 1;
            case '화':
                return 2;
            case '수':
                return 3;
            case '목':
                return 4;
            case '금':
                return 5;
            default:
                return -1;
        }
    }

    private void highlightCurrentDayAndPeriod() {
        Calendar calendar = Calendar.getInstance(); // 현재 날짜와 시간을 가져오는 캘린더 인스턴스 생성
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK); // 현재 요일 가져오기
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY); // 현재 시간 가져오기

        int currentDayIndex = -1; // 현재 요일 인덱스 초기화
        switch (dayOfWeek) { // 요일에 따른 인덱스 설정
            case Calendar.MONDAY:
                currentDayIndex = 1;
                break;
            case Calendar.TUESDAY:
                currentDayIndex = 2;
                break;
            case Calendar.WEDNESDAY:
                currentDayIndex = 3;
                break;
            case Calendar.THURSDAY:
                currentDayIndex = 4;
                break;
            case Calendar.FRIDAY:
                currentDayIndex = 5;
                break;
        }

        int currentPeriod = -1; // 현재 교시 인덱스 초기화
        if (hourOfDay >= 8 && hourOfDay < 21) { // 8시부터 21시까지 시간 범위 확인
            currentPeriod = (int) Math.floor((hourOfDay - 8) / 1.5); // 8시부터 1.5시간 단위 교시 계산
        }

        // 새로운 렌더러 적용
        for (int i = 0; i < lectureTable.getRowCount(); i++) {
            for (int j = 0; j < lectureTable.getColumnCount(); j++) {
                lectureTable.getColumnModel().getColumn(j).setCellRenderer(new DefaultTableCellRenderer()); // 기본 셀 렌더러로
                // 초기화
            }
        }

        if (currentDayIndex != -1) { // 현재 요일이 유효한 경우
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.PINK, -1)); // 현재 요일 컬럼 강조
        }
        if (currentPeriod != -1 && currentDayIndex != -1) { // 현재 교시와 요일이 유효한 경우
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.PINK, currentPeriod));
            lectureTable.getColumnModel().getColumn(currentDayIndex)
                    .setCellRenderer(new CustomRenderer(Color.RED, currentPeriod, currentDayIndex)); // 현재 교시와 요일 셀 강조
        }

        lectureTable.repaint(); // 테이블 다시 그리기
    }

    class CustomRenderer extends DefaultTableCellRenderer {
        private Color backgroundColor; // 배경색
        private int highlightedRow = -1; // 강조할 행
        private int highlightedColumn = -1; // 강조할 열

        public CustomRenderer(Color backgroundColor) {
            this.backgroundColor = backgroundColor;
        }

        public CustomRenderer(Color backgroundColor, int highlightedRow) {
            this.backgroundColor = backgroundColor;
            this.highlightedRow = highlightedRow;
        }

        public CustomRenderer(Color backgroundColor, int highlightedRow, int highlightedColumn) {
            this.backgroundColor = backgroundColor;
            this.highlightedRow = highlightedRow;
            this.highlightedColumn = highlightedColumn;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
                                                       int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ((highlightedRow == -1 || row == highlightedRow)
                    && (highlightedColumn == -1 || column == highlightedColumn)) {
                c.setBackground(backgroundColor); // 강조된 행과 열의 셀 배경색 설정
            } else if (column == highlightedColumn) {
                c.setBackground(Color.PINK); // 강조된 열의 셀 배경색 설정
            } else {
                c.setBackground(Color.WHITE); // 기본 셀 배경색 설정
            }
            return c;
        }
    }

    class ProfessorInfo {
        private String name; // 교수 이름

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    class Lecture {
        private String lectureName; // 강의 이름
        private String lectureTime1; // 강의 시간1
        private String lectureTime2; // 강의 시간2

        public String getLectureName() {
            return lectureName;
        }

        public void setLectureName(String lectureName) {
            this.lectureName = lectureName;
        }

        public String getLectureTime1() {
            return lectureTime1;
        }

        public void setLectureTime1(String lectureTime1) {
            this.lectureTime1 = lectureTime1;
        }

        public String getLectureTime2() {
            return lectureTime2;
        }

        public void setLectureTime2(String lectureTime2) {
            this.lectureTime2 = lectureTime2;
        }
    }
}
