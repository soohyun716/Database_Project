package professor;

import java.awt.BorderLayout; // 레이아웃 관련 패키지 가져오기
import java.awt.Color; // 색상 관련 패키지 가져오기
import java.awt.Component; // 컴포넌트 관련 패키지 가져오기
import java.awt.Dimension; // 크기 관련 패키지 가져오기
import java.awt.Font; // 폰트 관련 패키지 가져오기
import java.awt.GridLayout; // 그리드 레이아웃 관련 패키지 가져오기
import java.awt.event.ActionEvent; // 이벤트 관련 패키지 가져오기
import java.awt.event.ActionListener; // 이벤트 리스너 관련 패키지 가져오기
import java.sql.Connection; // 데이터베이스 연결 관련 패키지 가져오기
import java.sql.DriverManager; // 드라이버 관리자 관련 패키지 가져오기
import java.sql.PreparedStatement; // 준비된 명령문 관련 패키지 가져오기
import java.sql.ResultSet; // 결과 집합 관련 패키지 가져오기
import java.sql.SQLException; // SQL 예외 관련 패키지 가져오기
import java.util.HashMap; // 해시맵 클래스 가져오기
import java.util.Map; // 맵 인터페이스 가져오기

import javax.swing.JFrame; // JFrame 클래스 가져오기
import javax.swing.JLabel; // JLabel 클래스 가져오기
import javax.swing.JPanel; // JPanel 클래스 가져오기
import javax.swing.JScrollPane; // JScrollPane 클래스 가져오기
import javax.swing.JTextArea; // JTextArea 클래스 가져오기
import javax.swing.SwingConstants; // 스윙 상수 가져오기
import javax.swing.border.EmptyBorder; // 빈 테두리 관련 패키지 가져오기

import mainFrame.mainGUI; // mainGUI 클래스 가져오기

import javax.swing.JComboBox; // JComboBox 클래스 가져오기
import java.awt.FlowLayout; // 플로우 레이아웃 관련 패키지 가져오기

import javax.swing.BorderFactory; // 테두리 팩토리 관련 패키지 가져오기
import javax.swing.JButton; // JButton 클래스 가져오기
import javax.swing.JCheckBox; // JCheckBox 클래스 가져오기

public class professor_button1 extends JFrame {

    private JPanel contentPane; // 메인 콘텐츠 패널

    String usage; // 공간 유형
    String seats; // 좌석 수
    String cameraType; // 카메라 유형
    boolean outlet; // 콘센트 유무
    boolean projector; // 빔프로젝트 유무
    boolean reservation; // 예약 필요 여부
    boolean recording; // 녹화 가능 여부
    boolean practicable; // 실습 가능 여부
    Map<String, Boolean> timeDictionary = new HashMap<>(); // 시간 선택 상태 저장할 맵
    private JTextArea infoArea; // 정보 표시 영역

    public professor_button1() {
        setTitle("GONG-GANG"); // 프레임 제목 설정
        setBackground(new Color(255, 255, 255)); // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        setBounds(100, 100, 1100, 600); // 프레임 크기 설정
        setLocation(50, 50); // 프레임 위치 설정
        contentPane = new JPanel(); // 메인 콘텐츠 패널 생성
        contentPane.setForeground(new Color(255, 255, 255)); // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 10)); // 레이아웃 설정
        setContentPane(contentPane); // 프레임에 콘텐츠 패널 추가

        // 로고 붙이는 Panel
        JPanel logoPanel = new JPanel(); // 로고 패널 생성
        logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // 패널 선호 크기 설정
        contentPane.add(logoPanel, BorderLayout.NORTH); // 콘텐츠 패널에 로고 패널 추가
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정

        // GONG-GANG 로고 label 생성
        JLabel logo = new JLabel("Gong-Gang"); // 로고 라벨 생성
        logo.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logo.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
        logoPanel.add(logo); // 로고 패널에 로고 라벨 추가

        JLabel userLabel = new JLabel("- \uC6D0\uD558\uB294 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -"); // 부제 라벨 생성
        userLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22)); // 폰트 설정
        logoPanel.add(userLabel); // 로고 패널에 부제 라벨 추가

        JPanel mainPanel = new JPanel(); // 메인 패널 생성
        mainPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(mainPanel, BorderLayout.CENTER); // 콘텐츠 패널에 메인 패널 추가

        // 옵션 패널 생성
        JPanel optionPanel = new JPanel(); // 옵션 패널 생성
        optionPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        mainPanel.add(optionPanel); // 메인 패널에 옵션 패널 추가
        optionPanel.setPreferredSize(new Dimension(500, 170)); // 옵션 패널 선호 크기 설정

        // 콤보박스 패널 생성
        JPanel dropdownsPanel = new JPanel(); // 콤보박스 패널 생성
        optionPanel.add(dropdownsPanel); // 옵션 패널에 콤보박스 패널 추가
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT); // 정렬 설정
        dropdownsPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        dropdownsPanel.setSize(new Dimension(600, 200)); // 크기 설정
        dropdownsPanel.setLayout(new GridLayout(3, 2, 40, 10)); // 그리드 레이아웃 설정

        // 공간 유형 라벨 생성
        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615   : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT); // 수평 정렬 설정
        usageLabel.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(usageLabel); // 콤보박스 패널에 공간 유형 라벨 추가

        // 공간 유형 콤보박스 생성
        JComboBox usageComboBox = new JComboBox(new String[] { "선택", "교실", "교실 외" });
        usageComboBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
        usageComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(usageComboBox); // 콤보박스 패널에 공간 유형 콤보박스 추가
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height)); // 크기 설정

        // 좌석 수 라벨 생성
        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218       : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT); // 수평 정렬 설정
        seatsLabel.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(seatsLabel); // 콤보박스 패널에 좌석 수 라벨 추가

        // 좌석 수 콤보박스 생성
        JComboBox seatsComboBox = new JComboBox(
                new String[] { "선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90" });
        seatsComboBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
        seatsComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(seatsComboBox); // 콤보박스 패널에 좌석 수 콤보박스 추가

        // 카메라 유형 라벨 생성
        JLabel cameraLabel = new JLabel("\uCE74\uBA54\uB77C \uC720\uD615 :");
        dropdownsPanel.add(cameraLabel); // 콤보박스 패널에 카메라 유형 라벨 추가
        cameraLabel.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정

        // 카메라 유형 콤보박스 생성
        JComboBox cameraComboBox = new JComboBox(new String[] { "선택", "고정식 카메라", "추적식 카메라" });
        cameraComboBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
        cameraComboBox.setFont(new Font("나눔고딕", Font.BOLD, 14)); // 폰트 설정
        dropdownsPanel.add(cameraComboBox); // 콤보박스 패널에 카메라 유형 콤보박스 추가

        // 체크박스 패널 생성
        JPanel checkBoxPanel = new JPanel(); // 체크박스 패널 생성
        checkBoxPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10)); // 플로우 레이아웃 설정
        checkBoxPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        optionPanel.add(checkBoxPanel); // 옵션 패널에 체크박스 패널 추가

        // 체크박스 생성 및 추가
        JCheckBox CheckBox1 = new JCheckBox("\uCF58\uC13C\uD2B8");
        CheckBox1.setBackground(new Color(255, 255, 255)); // 배경색 설정
        checkBoxPanel.add(CheckBox1); // 체크박스 패널에 체크박스 추가
        CheckBox1.setFont(new Font("나눔고딕", Font.BOLD, 13)); // 폰트 설정

        JCheckBox CheckBox2 = new JCheckBox("\uBE54\uD504\uB85C\uC81D\uD2B8");
        checkBoxPanel.add(CheckBox2); // 체크박스 패널에 체크박스 추가
        CheckBox2.setBackground(new Color(255, 255, 255)); // 배경색 설정
        CheckBox2.setFont(new Font("나눔고딕", Font.BOLD, 13)); // 폰트 설정

        JCheckBox CheckBox3 = new JCheckBox("\uC608\uC57D \uD544\uC694");
        checkBoxPanel.add(CheckBox3); // 체크박스 패널에 체크박스 추가
        CheckBox3.setBackground(new Color(255, 255, 255)); // 배경색 설정
        CheckBox3.setFont(new Font("나눔고딕", Font.BOLD, 13)); // 폰트 설정

        JCheckBox CheckBox4 = new JCheckBox("\uB179\uD654 \uAC00\uB2A5");
        checkBoxPanel.add(CheckBox4); // 체크박스 패널에 체크박스 추가
        CheckBox4.setBackground(new Color(255, 255, 255)); // 배경색 설정
        CheckBox4.setFont(new Font("나눔고딕", Font.BOLD, 13)); // 폰트 설정

        JCheckBox CheckBox5 = new JCheckBox("\uC2E4\uC2B5 \uAC00\uB2A5");
        checkBoxPanel.add(CheckBox5); // 체크박스 패널에 체크박스 추가
        CheckBox5.setBackground(new Color(255, 255, 255)); // 배경색 설정
        CheckBox5.setFont(new Font("나눔고딕", Font.BOLD, 13)); // 폰트 설정

        // 시간표 패널 생성
        JPanel timePanel = new JPanel(); // 시간표 패널 생성
        timePanel.setLayout(new GridLayout(8, 5)); // 그리드 레이아웃 설정
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 교시 선택")); // 테두리 설정
        timePanel.setBackground(new Color(255, 255, 255)); // 배경색 설정

        // 시간표 체크박스 생성 및 추가
        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri" };
        String[] times = { "1", "2", "3", "4", "5", "6", "7", "8" };

        for (String time : times) {
            for (String day : days) {
                String name = day + time;
                timeDictionary.put(name, false); // 초기값 설정
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.setBackground(new Color(255, 255, 255)); // 배경색 설정
                checkBox.addActionListener(new ActionListener() { // 체크박스 클릭 이벤트 리스너 추가
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeDictionary.put(name, true); // 체크박스 상태 저장
                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 테두리 설정
                timePanel.add(checkBox); // 시간표 패널에 체크박스 추가
            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel); // 스크롤 패널 생성
        mainPanel.add(scrollPane); // 메인 패널에 스크롤 패널 추가
        scrollPane.setBackground(new Color(255, 255, 255)); // 배경색 설정

        // 결과 버튼 및 홈 버튼 패널 생성
        JPanel ButtonPanel = new JPanel(); // 버튼 패널 생성
        ButtonPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(ButtonPanel, BorderLayout.SOUTH); // 콘텐츠 패널에 버튼 패널 추가
        ButtonPanel.setLayout(new BorderLayout(0, 0)); // 레이아웃 설정

        JPanel resultPanel = new JPanel(new FlowLayout()); // 결과 패널 생성
        resultPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        ButtonPanel.add(resultPanel, BorderLayout.CENTER); // 버튼 패널에 결과 패널 추가
        JButton resultButton = new JButton("\uAC80\uC0C9"); // 검색 버튼 생성
        resultButton.setFont(new Font("나눔고딕", Font.BOLD, 16)); // 폰트 설정
        resultButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        resultPanel.add(resultButton); // 결과 패널에 검색 버튼 추가

        resultButton.addActionListener(new ActionListener() { // 검색 버튼 클릭 이벤트 리스너 추가
            @Override
            public void actionPerformed(ActionEvent e) {
                usage = (String) usageComboBox.getSelectedItem(); // 선택된 공간 유형 저장
                seats = (String) seatsComboBox.getSelectedItem(); // 선택된 좌석 수 저장
                cameraType = (String) cameraComboBox.getSelectedItem(); // 선택된 카메라 유형 저장

                outlet = CheckBox1.isSelected(); // 콘센트 체크박스 상태 저장
                projector = CheckBox2.isSelected(); // 빔프로젝트 체크박스 상태 저장
                reservation = CheckBox3.isSelected(); // 예약 필요 체크박스 상태 저장
                recording = CheckBox4.isSelected(); // 녹화 가능 체크박스 상태 저장
                practicable = CheckBox5.isSelected(); // 실습 가능 체크박스 상태 저장
                infoArea = new JTextArea(80, 40); // 정보 표시 영역 생성

                searchClassroomInfo(); // 교실 정보 검색

                // 새로운 창을 열어 결과를 보여줌
                JFrame newFrame = new JFrame("검색된 정보"); // 새 프레임 생성
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 설정
                newFrame.setBackground(new Color(255, 255, 255)); // 배경색 설정
                newFrame.setBounds(100, 100, 1100, 600); // 새 프레임 크기 설정
                newFrame.setLocation(50, 50); // 새 프레임 위치 설정

                newFrame.getContentPane().setForeground(new Color(255, 255, 255)); // 전경색 설정
                newFrame.getContentPane().setLayout(new BorderLayout(10, 10)); // 레이아웃 설정

                infoArea.setEditable(false); // 정보 표시 영역 수정 불가 설정
                newFrame.getContentPane().add(infoArea); // 새 프레임에 정보 표시 영역 추가
                newFrame.setVisible(true); // 새 프레임 보이기 설정

                // 로고 붙이는 Panel
                JPanel logoPanel = new JPanel(); // 로고 패널 생성
                logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
                logoPanel.setPreferredSize(new Dimension(1100, 103)); // 패널 선호 크기 설정
                newFrame.getContentPane().add(logoPanel, BorderLayout.NORTH); // 새 프레임에 로고 패널 추가
                logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정
                newFrame.getContentPane().setBackground(new Color(255, 255, 255)); // 배경색 설정

                // GONG-GANG 로고 label 생성
                JLabel logo = new JLabel("Gong-Gang"); // 로고 라벨 생성
                logo.setBackground(new Color(255, 255, 255)); // 배경색 설정
                logo.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
                logo.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
                logoPanel.add(logo); // 로고 패널에 로고 라벨 추가

                JLabel userLabel = new JLabel("- 결과 -"); // 부제 라벨 생성
                userLabel.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
                userLabel.setFont(new Font("나눔고딕", Font.BOLD, 22)); // 폰트 설정
                logoPanel.add(userLabel); // 로고 패널에 부제 라벨 추가
            }
        });

        // 홈 버튼 생성
        JPanel homePanel = new JPanel(); // 홈 패널 생성
        homePanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        JButton homeButton = new JButton("HOME"); // 홈 버튼 생성
        ButtonPanel.add(homePanel, BorderLayout.EAST); // 버튼 패널에 홈 패널 추가
        homePanel.setLayout(new BorderLayout(0, 0)); // 레이아웃 설정
        homeButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        homeButton.setAlignmentX(Component.RIGHT_ALIGNMENT); // 정렬 설정
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 12)); // 폰트 설정
        homePanel.add(homeButton, BorderLayout.SOUTH); // 홈 패널에 홈 버튼 추가

        // 홈 버튼에 ActionListener 추가
        homeButton.addActionListener(new ActionListener() { // 홈 버튼 클릭 이벤트 리스너 추가
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                mainGUI mainFrame = new mainGUI(); // mainGUI 객체 생성
                mainFrame.setVisible(true); // mainGUI 프레임 보이기
            }
        });

        // 디자인상 필요한 빈 패널
        JPanel emptyPanel = new JPanel(); // 빈 패널 생성
        emptyPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        ButtonPanel.add(emptyPanel, BorderLayout.WEST); // 버튼 패널에 빈 패널 추가
        emptyPanel.setPreferredSize(new Dimension(90, 0)); // 빈 패널 선호 크기 설정

    }

    // 교실 정보 검색 메서드
    private void searchClassroomInfo() {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // JDBC 드라이버 설정
        final String DB_URL = "jdbc:mysql://localhost:3306/DB2024Team05"; // DB URL 설정
        final String USER = "DB2024Team05"; // DB 사용자명 설정
        final String PASS = "DB2024Team05"; // DB 비밀번호 설정
        String message = "검색된 교실의 번호:\n"; // 결과 메시지 초기값 설정

        // SQL 쿼리 문자열 생성
        StringBuilder query = new StringBuilder("SELECT * FROM ClassroomView WHERE 1=1");

        // 좌석 수 조건 추가
        String[] seatRange = seats.split("-");
        if (!seats.equals("선택")) {
            query.append(" AND SeatCount BETWEEN ").append(Integer.parseInt(seatRange[0])).append(" AND ")
                    .append(Integer.parseInt(seatRange[1]));
        }
        // 카메라 유형 조건 추가
        if (!cameraType.equals("선택")) {
            query.append(" AND CameraType = '").append(cameraType).append("'");
        }
        // 콘센트 조건 추가
        if (outlet)
            query.append(" AND OutletCount > 0");
        // 빔프로젝트 조건 추가
        if (projector)
            query.append(" AND Projector = '빔 있음'");
        // 예약 필요 조건 추가
        if (reservation)
            query.append(" AND ReservationRequired = '예약 필요'");
        // 녹화 가능 조건 추가
        if (recording)
            query.append(" AND RecordingAvailable = '가능'");
        // 실습 가능 조건 추가
        if (practicable)
            query.append(" AND Practicable = '실습 가능'");

        // 데이터베이스 연결 및 쿼리 실행
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
                PreparedStatement stmt = conn.prepareStatement(query.toString())) { // 커넥션과 준비된 명령문 생성

            ResultSet rs = stmt.executeQuery(); // 쿼리 실행 및 결과 저장

            while (rs.next()) { // 결과 집합 순회
                String roomNumber = rs.getString("Room_Number"); // 교실 번호 가져오기
                String Room_name = rs.getString("Room_Name"); // 교실 이름 가져오기
                String Location = rs.getString("Location"); // 위치 가져오기
                String availableTimes = getAvailableTimes(rs); // 가능한 시간 가져오기
                if (!availableTimes.isEmpty()) { // 가능한 시간이 있으면
                    message += roomNumber + " " + Room_name + " " + Location + " 가능 시간: " + availableTimes + "\n"; // 메시지에
                                                                                                                   // 추가
                }
            }

            if (!message.equals("검색된 교실의 번호:\n")) { // 검색 결과가 있으면
                infoArea.setText(message); // 메시지를 정보 표시 영역에 설정
            } else {
                infoArea.setText("원하는 교실이 없습니다. 조건을 재선택하세요."); // 검색 결과가 없으면
            }

        } catch (SQLException e) { // 예외 처리
            e.printStackTrace(); // 스택 트레이스 출력
            infoArea.setText("데이터를 불러오는 과정에서 오류가 있습니다. 다시 확인하세요."); // 오류 메시지 설정
        }
    }

    // 가능한 시간 가져오는 메서드
    private String getAvailableTimes(ResultSet rs) throws SQLException {
        StringBuilder availableTimes = new StringBuilder(); // 가능한 시간 문자열 생성
        for (String time : timeDictionary.keySet()) { // 시간 딕셔너리 순회
            if (timeDictionary.get(time) && rs.getBoolean(time)) { // 시간 선택 상태 확인
                availableTimes.append(time).append(" "); // 가능한 시간 추가
            }
        }
        return availableTimes.toString().trim(); // 가능한 시간 문자열 반환
    }
}
