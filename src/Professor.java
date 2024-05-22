import java.awt.*;  // AWT 라이브러리 임포트
import java.awt.event.*;  // AWT 이벤트 라이브러리 임포트
import javax.swing.*;  // Swing 라이브러리 임포트
import javax.swing.border.EmptyBorder;  // 빈 테두리 임포트
import javax.swing.table.DefaultTableCellRenderer;  // 테이블 셀 렌더러 임포트
import java.sql.*;  // SQL 라이브러리 임포트
import java.util.ArrayList;  // ArrayList 임포트
import java.util.Calendar;  // Calendar 임포트
import java.util.List;  // List 임포트

public class Professor extends JFrame {

    private JPanel contentPane;  // 메인 패널
    private JTextField searchField;  // 검색 필드
    private JTable lectureTable;  // 강의 테이블
    private JTextArea professorInfoArea;  // 교수 정보 영역
    private Connection connection;  // 데이터베이스 연결 객체

    /**
     * 애플리케이션 실행.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Professor frame = new Professor();  // 프레임 생성
                    frame.setVisible(true);  // 프레임 보이기
                } catch (Exception e) {
                    e.printStackTrace();  // 예외 발생 시 스택 트레이스 출력
                }
            }
        });
    }

    /**
     * 프레임 생성자.
     */
    public Professor() {
        // 데이터베이스 연결 설정
        connectToDatabase();

        setBackground(new Color(255, 255, 255));  // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);  // 종료 시 동작 설정
        setBounds(100, 100, 1100, 800);  // 프레임 크기 설정
        setLocation(50, 50);  // 프레임 위치 설정
        contentPane = new JPanel();  // 메인 패널 생성
        contentPane.setForeground(new Color(255, 255, 255));  // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255));  // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));  // 빈 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 10));  // 레이아웃 설정
        setContentPane(contentPane);  // 프레임에 메인 패널 설정

        // 헤더 패널 생성 및 설정
        JPanel headerPanel = new JPanel();  // 헤더 패널 생성
        headerPanel.setBackground(new Color(255, 255, 255));  // 배경색 설정
        headerPanel.setLayout(new BorderLayout());  // 레이아웃 설정

        // 로고 패널 생성 및 설정
        JPanel logoPanel = new JPanel();  // 로고 패널 생성
        logoPanel.setBackground(new Color(255, 255, 255));  // 배경색 설정
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));  // 그리드 레이아웃 설정
        logoPanel.setPreferredSize(new Dimension(100, 100));  // 크기 설정

        JLabel logo1 = new JLabel("공간이 필요하다면?");  // 첫 번째 라벨 생성
        logo1.setFont(new Font("맑은 고딕", Font.PLAIN, 20));  // 폰트 설정
        logo1.setHorizontalAlignment(SwingConstants.CENTER);  // 가운데 정렬
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);  // 아래 정렬
        logoPanel.add(logo1);  // 로고 패널에 라벨 추가

        JLabel logo2 = new JLabel("Gong-Gang");  // 두 번째 라벨 생성
        logo2.setHorizontalAlignment(SwingConstants.CENTER);  // 가운데 정렬
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));  // 폰트 설정
        logoPanel.add(logo2);  // 로고 패널에 라벨 추가

        headerPanel.add(logoPanel, BorderLayout.NORTH);  // 헤더 패널에 로고 패널 추가

        // 검색 패널 생성 및 설정
        JPanel searchPanel = new JPanel();  // 검색 패널 생성
        searchPanel.setBackground(new Color(255, 255, 255));  // 배경색 설정
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));  // 레이아웃 설정

        JLabel searchLabel = new JLabel("교수님 이름:");  // 검색 라벨 생성
        searchLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20));  // 폰트 설정
        searchPanel.add(searchLabel);  // 검색 패널에 라벨 추가

        searchField = new JTextField();  // 검색 필드 생성
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 15));  // 폰트 설정
        searchField.setColumns(20);  // 열 수 설정
        searchPanel.add(searchField);  // 검색 패널에 검색 필드 추가

        JButton searchButton = new JButton("검색");  // 검색 버튼 생성
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20));  // 폰트 설정
        searchButton.addActionListener(new ActionListener() {  // 버튼 클릭 이벤트 설정
            public void actionPerformed(ActionEvent e) {
                searchProfessor();  // 교수 검색 메소드 호출
            }
        });
        searchPanel.add(searchButton);  // 검색 패널에 버튼 추가

        headerPanel.add(searchPanel, BorderLayout.SOUTH);  // 헤더 패널에 검색 패널 추가

        contentPane.add(headerPanel, BorderLayout.NORTH);  // 메인 패널에 헤더 패널 추가


        // 결과 영역 (강의 테이블)
        String[] columnNames = {"", "월", "화", "수", "목", "금"};  // 테이블 컬럼 이름 설정
        String[][] data = new String[9][6];  // 테이블 데이터 배열 생성, 9교시 기준
        for (int i = 0; i < 9; i++) {
            data[i][0] = String.valueOf(i + 1);  // 교시 번호 설정
        }
        lectureTable = new JTable(data, columnNames);  // 테이블 생성
        lectureTable.setFont(new Font("맑은 고딕", Font.PLAIN, 15));  // 폰트 설정
        JScrollPane scrollPane = new JScrollPane(lectureTable);  // 스크롤 패널 생성
        contentPane.add(scrollPane, BorderLayout.CENTER);  // 메인 패널에 추가

        // 교수 정보 영역
        professorInfoArea = new JTextArea();  // 텍스트 영역 생성
        professorInfoArea.setFont(new Font("맑은 고딕", Font.PLAIN, 20));  // 폰트 설정
        professorInfoArea.setEditable(false);  // 편집 불가능 설정
        JScrollPane infoScrollPane = new JScrollPane(professorInfoArea);  // 스크롤 패널 생성
        infoScrollPane.setPreferredSize(new Dimension(800, 150));  // 크기 설정
        contentPane.add(infoScrollPane, BorderLayout.SOUTH);  // 메인 패널에 추가
    }

    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");  // MySQL 드라이버 로드
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "root", "root");  // 데이터베이스 연결
            System.out.println("Database connected successfully.");  // 연결 성공 메시지 출력
        } catch (Exception e) {
            e.printStackTrace();  // 예외 발생 시 스택 트레이스 출력
        }
    }

    private void searchProfessor() {
        String professorName = searchField.getText().trim();  // 검색어 가져오기
        if (professorName.isEmpty()) {
            JOptionPane.showMessageDialog(this, "교수님 이름을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE);  // 검색어 없을 시 경고 메시지
            return;
        }

        try {
            ProfessorInfo professorInfo = getProfessorInfo(professorName);  // 교수 정보 가져오기
            if (professorInfo != null) {
                updateLectureTable(professorInfo);  // 테이블 업데이트
                updateProfessorInfoArea(professorInfo);  // 교수 정보 영역 업데이트
            } else {
                JOptionPane.showMessageDialog(this, "해당 교수님의 정보를 찾을 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE);  // 교수 정보 없을 시 경고 메시지
            }
        } catch (SQLException e) {
            e.printStackTrace();  // SQL 예외 발생 시 스택 트레이스 출력
        }
    }

    private ProfessorInfo getProfessorInfo(String professorName) throws SQLException {
        ProfessorInfo professorInfo = null;

        String professorQuery = "SELECT Name, Lab_Location, Phone, Email FROM DB2024_Professor WHERE Name = ?";  // 교수 정보 쿼리
        PreparedStatement professorStmt = connection.prepareStatement(professorQuery);  // 쿼리 준비
        professorStmt.setString(1, professorName);  // 검색어 설정
        ResultSet professorRs = professorStmt.executeQuery();  // 쿼리 실행

        if (professorRs.next()) {
            professorInfo = new ProfessorInfo();
            professorInfo.setName(professorRs.getString("Name"));  // 이름 설정
            professorInfo.setLabLocation(professorRs.getString("Lab_Location"));  // 연구실 설정
            professorInfo.setPhone(professorRs.getString("Phone"));  // 전화번호 설정
            professorInfo.setEmail(professorRs.getString("Email"));  // 이메일 설정

            String lectureQuery = "SELECT Lecture_Name, Lecture_Time1, Lecture_Time2 FROM DB2024_Lecture WHERE Professor_Name = ?";  // 강의 정보 쿼리
            PreparedStatement lectureStmt = connection.prepareStatement(lectureQuery);  // 쿼리 준비
            lectureStmt.setString(1, professorName);  // 검색어 설정
            ResultSet lectureRs = lectureStmt.executeQuery();  // 쿼리 실행

            List<Lecture> lectures = new ArrayList<>();
            while (lectureRs.next()) {
                Lecture lecture = new Lecture();
                lecture.setLectureName(lectureRs.getString("Lecture_Name"));  // 강의 이름 설정
                lecture.setLectureTime1(lectureRs.getString("Lecture_Time1"));  // 강의 시간1 설정
                lecture.setLectureTime2(lectureRs.getString("Lecture_Time2"));  // 강의 시간2 설정
                lectures.add(lecture);  // 강의 리스트에 추가
            }
            professorInfo.setLectures(lectures);  // 교수 정보에 강의 리스트 설정
        }

        return professorInfo;  // 교수 정보 반환
    }

    private void updateLectureTable(ProfessorInfo professorInfo) {
        // 테이블 초기화
        for (int i = 0; i < 9; i++) {
            for (int j = 1; j < 6; j++) {
                lectureTable.setValueAt("", i, j);  // 테이블 셀 초기화
            }
        }

        // 테이블에 강의 정보 업데이트
        for (Lecture lecture : professorInfo.getLectures()) {
            int dayIndex = getDayIndex(lecture.getLectureTime1());
            if (dayIndex != -1) {
                int period = Integer.parseInt(lecture.getLectureTime1().substring(1)) - 1;
                lectureTable.setValueAt(lecture.getLectureName(), period, dayIndex);
            }

            if (lecture.getLectureTime2() != null) {
                dayIndex = getDayIndex(lecture.getLectureTime2());
                if (dayIndex != -1) {
                    int period = Integer.parseInt(lecture.getLectureTime2().substring(1)) - 1;
                    lectureTable.setValueAt(lecture.getLectureName(), period, dayIndex);
                }
            }
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
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int hourOfDay = calendar.get(Calendar.HOUR_OF_DAY);

        int currentDayIndex = -1;
        switch (dayOfWeek) {
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

        int currentPeriod = -1;
        if (hourOfDay >= 9 && hourOfDay < 18) {
            currentPeriod = (hourOfDay - 9) / 2;  // 9시부터 2시간 단위 교시 계산
        }

        // 새로운 렌더러 적용
        for (int i = 0; i < lectureTable.getRowCount(); i++) {
            for (int j = 0; j < lectureTable.getColumnCount(); j++) {
                lectureTable.getColumnModel().getColumn(j).setCellRenderer(new DefaultTableCellRenderer());
            }
        }

        if (currentDayIndex != -1) {
            lectureTable.getColumnModel().getColumn(currentDayIndex).setCellRenderer(new CustomRenderer(Color.PINK, -1));
        }
        if (currentPeriod != -1 && currentDayIndex != -1) {
            lectureTable.getColumnModel().getColumn(currentDayIndex).setCellRenderer(new CustomRenderer(Color.PINK, currentPeriod));
            lectureTable.getColumnModel().getColumn(currentDayIndex).setCellRenderer(new CustomRenderer(Color.RED, currentPeriod, currentDayIndex));
        }

        lectureTable.repaint();
    }

    private void updateProfessorInfoArea(ProfessorInfo professorInfo) {
        StringBuilder info = new StringBuilder();
        info.append("교수님 이름: ").append(professorInfo.getName()).append("\n");

        if (professorInfo.getLabLocation() != null) {
            info.append("연구실: ").append(professorInfo.getLabLocation()).append("\n");
        } else {
            info.append("연구실: 정보 없음\n");
        }

        if (professorInfo.getPhone() != null) {
            info.append("연락처: ").append(professorInfo.getPhone()).append("\n");
        } else {
            info.append("연락처: 정보 없음\n");
        }

        if (professorInfo.getEmail() != null) {
            info.append("이메일: ").append(professorInfo.getEmail()).append("\n");
        } else {
            info.append("이메일: 정보 없음\n");
        }

        professorInfoArea.setText(info.toString());
    }

    class CustomRenderer extends DefaultTableCellRenderer {
        private Color backgroundColor;  // 배경색
        private int highlightedRow = -1;  // 강조할 행
        private int highlightedColumn = -1;  // 강조할 열

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
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if ((highlightedRow == -1 || row == highlightedRow) && (highlightedColumn == -1 || column == highlightedColumn)) {
                c.setBackground(backgroundColor);  // 강조된 행과 열의 셀 배경색 설정
            } else if (column == highlightedColumn) {
                c.setBackground(Color.PINK);  // 강조된 열의 셀 배경색 설정
            } else {
                c.setBackground(Color.WHITE);  // 기본 셀 배경색 설정
            }
            return c;
        }
    }

    class ProfessorInfo {
        private String name;  // 교수 이름
        private String labLocation;  // 연구실 위치
        private String phone;  // 전화번호
        private String email;  // 이메일
        private List<Lecture> lectures;  // 강의 리스트

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

        public List<Lecture> getLectures() {
            return lectures;
        }

        public void setLectures(List<Lecture> lectures) {
            this.lectures = lectures;
        }
    }

    class Lecture {
        private String lectureName;  // 강의 이름
        private String lectureTime1;  // 강의 시간1
        private String lectureTime2;  // 강의 시간2

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
