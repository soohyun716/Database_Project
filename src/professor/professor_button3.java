package professor;

import java.awt.BorderLayout; // BorderLayout 관련 패키지 가져오기
import java.awt.Color; // 색상 관련 패키지 가져오기
import java.awt.Dimension; // 크기 관련 패키지 가져오기
import java.awt.FlowLayout; // FlowLayout 관련 패키지 가져오기
import java.awt.Font; // 폰트 관련 패키지 가져오기
import java.awt.GridLayout; // GridLayout 관련 패키지 가져오기
import java.awt.event.ActionEvent; // 액션 이벤트 관련 패키지 가져오기
import java.awt.event.ActionListener; // 액션 리스너 관련 패키지 가져오기
import java.sql.Connection; // 데이터베이스 연결 관련 패키지 가져오기
import java.sql.DriverManager; // 드라이버 관리자 관련 패키지 가져오기
import java.sql.PreparedStatement; // 준비된 명령문 관련 패키지 가져오기
import java.sql.ResultSet; // 결과 집합 관련 패키지 가져오기
import java.sql.SQLException; // SQL 예외 관련 패키지 가져오기
import java.util.ArrayList; // ArrayList 관련 패키지 가져오기
import java.util.HashMap; // HashMap 관련 패키지 가져오기
import java.util.List; // List 관련 패키지 가져오기
import java.util.Map; // Map 관련 패키지 가져오기

import javax.swing.BorderFactory; // BorderFactory 관련 패키지 가져오기
import javax.swing.JButton; // JButton 관련 패키지 가져오기
import javax.swing.JCheckBox; // JCheckBox 관련 패키지 가져오기
import javax.swing.JFrame; // JFrame 관련 패키지 가져오기
import javax.swing.JLabel; // JLabel 관련 패키지 가져오기
import javax.swing.JOptionPane; // JOptionPane 관련 패키지 가져오기
import javax.swing.JPanel; // JPanel 관련 패키지 가져오기
import javax.swing.JScrollPane; // JScrollPane 관련 패키지 가져오기
import javax.swing.JTextField; // JTextField 관련 패키지 가져오기
import javax.swing.SwingConstants; // SwingConstants 관련 패키지 가져오기
import javax.swing.border.EmptyBorder; // EmptyBorder 관련 패키지 가져오기

import mainFrame.mainGUI; // mainGUI 클래스 가져오기
import javax.swing.JTextArea; // JTextArea 관련 패키지 가져오기

public class professor_button3 extends JFrame {

    private JPanel contentPane; // 메인 콘텐츠 패널
    private JTextField roomField; // 강의실 위치 입력 필드
    private JTextArea professorInfoArea; // 교수 정보 출력 영역
    private JCheckBox[] timeCheckBoxes; // 강의 시간 체크박스 배열
    private Connection connection; // 데이터베이스 연결 객체

    Map<String, Boolean> timeDictionary = new HashMap<>(); // 시간 선택 상태 저장 맵

    public professor_button3() {
        setTitle("GONG-GANG"); // 프레임 제목 설정
        setBackground(new Color(255, 255, 255)); // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        setBounds(100, 100, 1100, 600); // 프레임 크기 설정
        setLocation(50, 50); // 프레임 위치 설정
        contentPane = new JPanel(); // 메인 콘텐츠 패널 생성
        contentPane.setForeground(new Color(255, 255, 255)); // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 0)); // 레이아웃 설정
        setContentPane(contentPane); // 프레임에 콘텐츠 패널 추가

        // 로고 패널 생성
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // 선호 크기 설정
        contentPane.add(logoPanel, BorderLayout.NORTH); // 콘텐츠 패널에 로고 패널 추가
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정

        // GONG-GANG 로고 라벨 생성
        JLabel logo = new JLabel("Gong-Gang");
        logo.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logo.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
        logoPanel.add(logo); // 로고 패널에 로고 라벨 추가

        JLabel userLabel = new JLabel("- 교수님 찾기 -");
        userLabel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        userLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        userLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 22)); // 폰트 설정
        logoPanel.add(userLabel); // 로고 패널에 부제 라벨 추가

        // 메인 패널 생성
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        mainPanel.setPreferredSize(new Dimension(700, 400)); // 선호 크기 설정
        contentPane.add(mainPanel, BorderLayout.CENTER); // 콘텐츠 패널에 메인 패널 추가
        mainPanel.setLayout(new BorderLayout(10, 10)); // 레이아웃 설정

        // 입력 패널 생성
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5)); // 플로우 레이아웃 설정

        mainPanel.add(inputPanel, BorderLayout.NORTH); // 메인 패널에 입력 패널 추가

        // 시간 선택 패널 생성
        JPanel timePanel = new JPanel();
        timePanel.setBorder(BorderFactory.createTitledBorder("강의 시간 선택")); // 테두리 설정
        timePanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        timePanel.setLayout(new GridLayout(9, 5, 10, 8)); // 그리드 레이아웃 설정
        timePanel.setPreferredSize(new Dimension(470, 200)); // 선호 크기 설정

        String[] times = { "Mon1", "Tue1", "Wed1", "Thu1", "Fri1", "Mon2", "Tue2", "Wed2", "Thu2", "Fri2", "Mon3", "Tue3", "Wed3", "Thu3", "Fri3", "Mon4", "Tue4", "Wed4", "Thu4", "Fri4", "Mon5", "Tue5", "Wed5", "Thu5", "Fri5", "Mon6", "Tue6", "Wed6", "Thu6", "Fri6", "Mon7", "Tue7", "Wed7", "Thu7", "Fri7", "Mon8", "Tue8", "Wed8", "Thu8", "Fri8", "Mon9", "Tue9", "Wed9", "Thu9", "Fri9" };

        timeCheckBoxes = new JCheckBox[times.length];

        for (int i = 0; i < times.length; i++) {
            timeCheckBoxes[i] = new JCheckBox(times[i]); // 체크박스 생성
            timeCheckBoxes[i].setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12)); // 폰트 설정
            timeCheckBoxes[i].setPreferredSize(new Dimension(50, 20)); // 크기 설정
            timeCheckBoxes[i].setBackground(new Color(255, 255, 255)); // 배경색 설정
            timePanel.add(timeCheckBoxes[i]); // 시간 패널에 체크박스 추가
        }
        inputPanel.add(timePanel); // 입력 패널에 시간 패널 추가

        // 강의실 위치 패널 생성
        JPanel roomPanel = new JPanel();
        roomPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        roomPanel.setPreferredSize(new Dimension(350, 100)); // 선호 크기 설정
        inputPanel.add(roomPanel); // 입력 패널에 강의실 위치 패널 추가

        JLabel roomLabel = new JLabel("강의실 위치    :");
        roomLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        roomLabel.setPreferredSize(new Dimension(190, 40)); // 크기 설정
        roomLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20)); // 폰트 설정
        roomField = new JTextField(10); // 강의실 위치 입력 필드 생성
        JButton searchButton = new JButton("Search"); // 검색 버튼 생성
        searchButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12)); // 폰트 설정
        searchButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        searchButton.addActionListener(new ActionListener() { // 검색 버튼 클릭 이벤트 리스너 추가
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProfessorByRoomAndTime(); // 강의실과 시간으로 교수 검색 메서드 호출
            }
        });
        roomPanel.add(roomLabel); // 강의실 위치 패널에 라벨 추가
        roomPanel.add(roomField); // 강의실 위치 패널에 입력 필드 추가
        roomPanel.add(searchButton); // 강의실 위치 패널에 검색 버튼 추가

        // 교수 정보 출력 영역 생성
        professorInfoArea = new JTextArea(5, 30); // 텍스트 영역 생성
        professorInfoArea.setTabSize(5); // 탭 크기 설정
        professorInfoArea.setEditable(false); // 편집 불가 설정
        JScrollPane scrollPane = new JScrollPane(professorInfoArea); // 스크롤 패널 생성
        mainPanel.add(scrollPane, BorderLayout.CENTER); // 메인 패널에 스크롤 패널 추가

        // 홈 버튼 패널 생성
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH); // 콘텐츠 패널에 홈 버튼 패널 추가

        JButton homeButton = new JButton("HOME"); // 홈 버튼 생성
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12)); // 폰트 설정
        homeButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        homeButtonPanel.add(homeButton); // 홈 버튼 패널에 홈 버튼 추가
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 레이아웃 설정

        // 메인 패널 위치 조정을 위한 빈 패널 생성
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(50, 100)); // 선호 크기 설정
        westPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(westPanel, BorderLayout.WEST); // 콘텐츠 패널에 빈 패널 추가

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(50, 100)); // 선호 크기 설정
        eastPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(eastPanel, BorderLayout.EAST); // 콘텐츠 패널에 빈 패널 추가

        // 홈 버튼에 액션 리스너 추가
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                mainGUI mainFrame = new mainGUI(); // mainGUI 객체 생성
                mainFrame.setVisible(true); // mainGUI 프레임 보이기
            }
        });

        connectToDatabase(); // 데이터베이스 연결 설정 메서드 호출
    }

    // 데이터베이스 연결 설정 메서드
    private void connectToDatabase() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // MySQL 드라이버 로드
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "DB2024Team05", "DB2024Team05"); // 데이터베이스 연결
            System.out.println("Database connected successfully."); // 연결 성공 메시지 출력
        } catch (Exception e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    // 강의실과 시간으로 교수 검색 메서드
    private void searchProfessorByRoomAndTime() {
        String room = roomField.getText().trim(); // 강의실 위치 입력 값 가져오기
        List<String> selectedTimes = new ArrayList<>(); // 선택된 시간 리스트 생성
        for (JCheckBox checkBox : timeCheckBoxes) {
            if (checkBox.isSelected()) { // 체크박스가 선택된 경우
                selectedTimes.add(checkBox.getText()); // 선택된 시간 리스트에 추가
            }
        }

        if (room.isEmpty() || selectedTimes.isEmpty()) { // 강의실 위치나 시간이 선택되지 않은 경우
            JOptionPane.showMessageDialog(this, "강의실 위치와 강의 시간을 입력하세요.", "경고", JOptionPane.WARNING_MESSAGE); // 경고 메시지 출력
            return;
        }

        try {
            List<ProfessorInfo> professors = getProfessorsByRoomAndTime(room, selectedTimes); // 강의실과 시간으로 교수 정보 가져오기
            if (!professors.isEmpty()) { // 교수 정보가 있는 경우
                updateProfessorInfoArea(professors); // 교수 정보 출력 영역 업데이트
            } else {
                JOptionPane.showMessageDialog(this, "해당 시간에 사용 중인 교수님의 정보를 찾을 수 없습니다.", "경고", JOptionPane.WARNING_MESSAGE); // 경고 메시지 출력
            }
        } catch (SQLException e) {
            e.printStackTrace(); // 예외 발생 시 스택 트레이스 출력
        }
    }

    // 강의실과 시간으로 교수 정보 가져오기 메서드
    private List<ProfessorInfo> getProfessorsByRoomAndTime(String room, List<String> times) throws SQLException {
        List<ProfessorInfo> professors = new ArrayList<>(); // 교수 정보 리스트 생성

        // SQL 쿼리 생성
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

        PreparedStatement stmt = connection.prepareStatement(queryBuilder.toString()); // 준비된 명령문 생성
        stmt.setString(1, room); // 강의실 위치 설정
        int index = 2; // 시간 설정 인덱스 초기값
        for (String time : times) {
            stmt.setString(index++, time); // 강의 시간1 설정
            stmt.setString(index++, time); // 강의 시간2 설정
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

    // 교수 정보 출력 영역 업데이트 메서드
    private void updateProfessorInfoArea(List<ProfessorInfo> professors) {
        StringBuilder info = new StringBuilder(); // 교수 정보 문자열 생성
        for (ProfessorInfo professor : professors) {
            info.append("교수님 이름: ").append(professor.getName()).append("\n"); // 교수 이름 추가
            if (professor.getLabLocation() != null) {
                info.append("연구실: ").append(professor.getLabLocation()).append("\n"); // 연구실 위치 추가
            } else {
                info.append("연구실: 정보 없음\n"); // 연구실 정보 없음 메시지 추가
            }
            if (professor.getPhone() != null) {
                info.append("연락처: ").append(professor.getPhone()).append("\n"); // 연락처 추가
            } else {
                info.append("연락처: 정보 없음\n"); // 연락처 정보 없음 메시지 추가
            }
            if (professor.getEmail() != null) {
                info.append("이메일: ").append(professor.getEmail()).append("\n"); // 이메일 추가
            } else {
                info.append("이메일: 정보 없음\n"); // 이메일 정보 없음 메시지 추가
            }
            info.append("\n"); // 줄 바꿈 추가
        }
        professorInfoArea.setText(info.toString()); // 교수 정보 출력 영역에 텍스트 설정
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
