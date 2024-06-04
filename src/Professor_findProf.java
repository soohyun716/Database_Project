import java.awt.*; // AWT 패키지 가져오기
import java.awt.event.*; // 이벤트 관련 패키지 가져오기
import javax.swing.*; // 스윙 패키지 가져오기
import javax.swing.border.EmptyBorder; // 빈 테두리 관련 패키지 가져오기
import java.sql.*; // SQL 패키지 가져오기
import java.util.ArrayList; // ArrayList 클래스 가져오기
import java.util.List; // 리스트 인터페이스 가져오기

public class Professor_findProf extends JFrame {

    private JPanel contentPane; // 메인 콘텐츠 패널
    private JTextField roomField; // 강의실 입력 필드
    private JCheckBox[] timeCheckBoxes; // 시간 체크박스 배열
    private JTextArea professorInfoArea; // 교수 정보 표시 영역
    private Connection connection; // 데이터베이스 연결 객체

    /**
     * 애플리케이션 실행 메서드
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() { // 이벤트 큐에서 실행
            public void run() {
                try {
                    Professor_findProf frame = new Professor_findProf(); // 프레임 객체 생성
                    frame.setVisible(true); // 프레임 보이기
                } catch (Exception e) {
                    e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
                }
            }
        });
    }

    /**
     * 프레임 생성자
     */
    public Professor_findProf() {
        // 데이터베이스 연결 설정
        connectToDatabase();

        setBackground(new Color(255, 255, 255)); // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        setBounds(100, 100, 800, 600); // 프레임 크기 설정
        setLocation(50, 50); // 프레임 위치 설정
        contentPane = new JPanel(); // 메인 콘텐츠 패널 생성
        contentPane.setForeground(new Color(255, 255, 255)); // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 10)); // 레이아웃 설정
        setContentPane(contentPane); // 프레임에 콘텐츠 패널 추가

        // 헤더 패널 생성
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        headerPanel.setLayout(new BorderLayout()); // 레이아웃 설정

        // 제목 설정
        JLabel titleLabel = new JLabel("강의실 및 시간으로 교수님 정보 찾기");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 24)); // 폰트 설정
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        headerPanel.add(titleLabel, BorderLayout.NORTH); // 헤더 패널에 제목 추가

        contentPane.add(headerPanel, BorderLayout.NORTH); // 메인 패널에 헤더 패널 추가

        // 검색 패널 생성
        JPanel searchPanel = new JPanel();
        searchPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        searchPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20)); // 플로우 레이아웃 설정

        JLabel roomLabel = new JLabel("강의실 위치:"); // 강의실 위치 라벨
        roomLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        searchPanel.add(roomLabel); // 검색 패널에 추가

        roomField = new JTextField(); // 강의실 입력 필드 생성
        roomField.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        roomField.setColumns(10); // 칼럼 수 설정
        searchPanel.add(roomField); // 검색 패널에 추가

        JLabel timeLabel = new JLabel("강의 시간:"); // 강의 시간 라벨
        timeLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        searchPanel.add(timeLabel); // 검색 패널에 추가

        JPanel timePanel = new JPanel(); // 시간 패널 생성
        timePanel.setLayout(new GridLayout(0, 5)); // 그리드 레이아웃 설정
        String[] times = {
                "월1", "월2", "월3", "월4", "월5", "월6", "월7", "월8", "월9",
                "화1", "화2", "화3", "화4", "화5", "화6", "화7", "화8", "화9",
                "수1", "수2", "수3", "수4", "수5", "수6", "수7", "수8", "수9",
                "목1", "목2", "목3", "목4", "목5", "목6", "목7", "목8", "목9",
                "금1", "금2", "금3", "금4", "금5", "금6", "금7", "금8", "금9"
        }; // 시간 배열 설정
        timeCheckBoxes = new JCheckBox[times.length]; // 시간 체크박스 배열 생성
        for (int i = 0; i < times.length; i++) {
            timeCheckBoxes[i] = new JCheckBox(times[i]); // 체크박스 생성
            timePanel.add(timeCheckBoxes[i]); // 시간 패널에 체크박스 추가
        }
        searchPanel.add(timePanel); // 검색 패널에 시간 패널 추가

        JButton searchButton = new JButton("검색"); // 검색 버튼 생성
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        searchButton.addActionListener(new ActionListener() { // 검색 버튼 클릭 이벤트 리스너 추가
            public void actionPerformed(ActionEvent e) {
                searchProfessorByRoomAndTime(); // 강의실 및 시간으로 교수 검색 메서드 호출
            }
        });
        searchPanel.add(searchButton); // 검색 패널에 검색 버튼 추가

        contentPane.add(searchPanel, BorderLayout.CENTER); // 메인 패널에 검색 패널 추가

        // 교수 정보 표시 영역
        professorInfoArea = new JTextArea(); // 교수 정보 영역 생성
        professorInfoArea.setFont(new Font("맑은 고딕", Font.PLAIN, 20)); // 폰트 설정
        professorInfoArea.setEditable(false); // 편집 불가 설정
        JScrollPane infoScrollPane = new JScrollPane(professorInfoArea); // 스크롤 패널 생성
        infoScrollPane.setPreferredSize(new Dimension(800, 200)); // 선호 크기 설정
        contentPane.add(infoScrollPane, BorderLayout.SOUTH); // 메인 패널에 스크롤 패널 추가
    }

    // 데이터베이스 연결 설정 메서드
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 로드
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "root", "root"); // 데이터베이스
                                                                                                                  // 연결
            System.out.println("Database connected successfully."); // 연결 성공 메시지 출력
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    // 강의실 및 시간으로 교수 검색 메서드
    private void searchProfessorByRoomAndTime() {
        String room = roomField.getText().trim(); // 강의실 위치 입력값 가져오기
        List<String> selectedTimes = new ArrayList<>(); // 선택된 시간 리스트 생성
        for (JCheckBox checkBox : timeCheckBoxes) { // 체크박스 순회
            if (checkBox.isSelected()) { // 선택된 체크박스일 경우
                selectedTimes.add(checkBox.getText()); // 선택된 시간 리스트에 추가
            }
        }

        if (room.isEmpty() || selectedTimes.isEmpty()) { // 강의실 위치나 시간이 입력되지 않은 경우
            JOptionPane.showMessageDialog(this, "강의실 위치와 강의 시간을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE); // 경고 메시지
            return; // 메서드 종료
        }

        try {
            List<ProfessorInfo> professors = getProfessorsByRoomAndTime(room, selectedTimes); // 강의실과 시간으로 교수 정보 가져오기
            if (!professors.isEmpty()) { // 교수 정보가 있는 경우
                updateProfessorInfoArea(professors); // 교수 정보 영역 업데이트
            } else {
                JOptionPane.showMessageDialog(this, "해당 시간에 사용 중인 교수님의 정보를 찾을 수 없습니다.", "경고",
                        JOptionPane.WARNING_MESSAGE); // 교수 정보 없을 시 경고 메시지
            }
        } catch (SQLException e) {
            e.printStackTrace(); // SQL 예외 발생 시 스택 트레이스 출력
        }
    }

    // 강의실 및 시간으로 교수 정보 가져오기 메서드
    private List<ProfessorInfo> getProfessorsByRoomAndTime(String room, List<String> times) throws SQLException {
        List<ProfessorInfo> professors = new ArrayList<>(); // 교수 정보 리스트 생성

        // SQL 쿼리 문자열 생성
        StringBuilder queryBuilder = new StringBuilder("SELECT DISTINCT p.Name, p.Lab_Location, p.Phone, p.Email "
                + "FROM DB2024_Professor p "
                + "JOIN DB2024_Lecture l ON p.Professor_Num = l.Professor_Num "
                + "WHERE l.Room_Number = ? AND (");

        for (int i = 0; i < times.size(); i++) { // 선택된 시간 조건 추가
            queryBuilder.append("l.Lecture_Time1 = ? OR l.Lecture_Time2 = ?");
            if (i < times.size() - 1) {
                queryBuilder.append(" OR ");
            }
        }
        queryBuilder.append(")");

        PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString()); // 쿼리 준비
        stmt.setString(1, room); // 강의실 위치 설정
        int index = 2;
        for (String time : times) { // 선택된 시간 설정
            stmt.setString(index++, time);
            stmt.setString(index++, time);
        }

        ResultSet rs = stmt.executeQuery(); // 쿼리 실행 및 결과 집합 저장
        while (rs.next()) { // 결과 집합 순회
            ProfessorInfo professor = new ProfessorInfo(); // 교수 정보 객체 생성
            professor.setName(rs.getString("Name")); // 교수 이름 설정
            professor.setLabLocation(rs.getString("Lab_Location")); // 연구실 위치 설정
            professor.setPhone(rs.getString("Phone")); // 연락처 설정
            professor.setEmail(rs.getString("Email")); // 이메일 설정
            professors.add(professor); // 교수 정보 리스트에 추가
        }
        return professors; // 교수 정보 리스트 반환
    }

    // 교수 정보 영역 업데이트 메서드
    private void updateProfessorInfoArea(List<ProfessorInfo> professors) {
        StringBuilder info = new StringBuilder(); // 정보 문자열 생성
        for (ProfessorInfo professor : professors) { // 교수 정보 순회
            info.append("교수님 이름: ").append(professor.getName()).append("\n"); // 교수 이름 추가
            if (professor.getLabLocation() != null) {
                info.append("연구실: ").append(professor.getLabLocation()).append("\n"); // 연구실 위치 추가
            } else {
                info.append("연구실: 정보 없음\n"); // 연구실 정보 없음 표시
            }
            if (professor.getPhone() != null) {
                info.append("연락처: ").append(professor.getPhone()).append("\n"); // 연락처 추가
            } else {
                info.append("연락처: 정보 없음\n"); // 연락처 정보 없음 표시
            }
            if (professor.getEmail() != null) {
                info.append("이메일: ").append(professor.getEmail()).append("\n"); // 이메일 추가
            } else {
                info.append("이메일: 정보 없음\n"); // 이메일 정보 없음 표시
            }
            info.append("\n"); // 줄 바꿈 추가
        }
        professorInfoArea.setText(info.toString()); // 교수 정보 영역에 정보 설정
    }

    // 교수 정보 클래스
    class ProfessorInfo {
        private String name; // 교수 이름
        private String labLocation; // 연구실 위치
        private String phone; // 연락처
        private String email; // 이메일

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
