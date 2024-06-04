import javax.swing.*; // GUI 구성 요소 가져오기
import java.awt.*; // 그래픽 구성 요소 가져오기
import java.awt.event.ActionEvent; // 이벤트 처리 관련 클래스 가져오기
import java.awt.event.ActionListener; // 이벤트 리스너 인터페이스 가져오기
import java.sql.*; // SQL 관련 클래스 가져오기
import java.util.HashMap; // 해시맵 클래스 가져오기
import java.util.Map; // 맵 인터페이스 가져오기

public class Professor_findClassroom {
    String usage; // 사용 유형
    String seats; // 좌석 수
    String cameraType; // 카메라 유형
    boolean outlet; // 콘센트 유무
    boolean projector; // 빔프로젝트 유무
    boolean reservation; // 예약 필요 여부
    boolean recording; // 녹화 가능 여부
    boolean practicable; // 실습 가능 여부
    Map<String, Boolean> timeDictionary = new HashMap<>(); // 시간 선택 상태 저장할 맵
    private JTextArea infoArea; // 정보 표시 영역

    public Professor_findClassroom() {
        // 프레임 생성
        JFrame frame = new JFrame("Classroom Search");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        frame.setSize(1200, 700); // 프레임 크기 설정
        frame.setLayout(new BorderLayout()); // 레이아웃 설정

        // 제목 설정
        JLabel title = new JLabel("Classroom Search", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24)); // 폰트 설정
        frame.add(title, BorderLayout.NORTH); // 프레임에 제목 추가

        // 내용 패널 생성
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS)); // 레이아웃 설정
        frame.add(contentPanel, BorderLayout.CENTER); // 프레임에 내용 패널 추가

        // 드롭다운 메뉴 패널 생성
        JPanel dropdownPanel = new JPanel();
        dropdownPanel.setLayout(new GridLayout(3, 2, 10, 10)); // 그리드 레이아웃 설정
        dropdownPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // 패널 테두리 설정

        // 드롭다운 메뉴 라벨 및 콤보박스 생성
        JLabel usageLabel = new JLabel("공간 유형:");
        JComboBox<String> usageComboBox = new JComboBox<>(new String[] { "선택", "교실", "교실 외" });

        JLabel seatsLabel = new JLabel("좌석 수:");
        JComboBox<String> seatsComboBox = new JComboBox<>(
                new String[] { "선택", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70" });

        JLabel cameraTypeLabel = new JLabel("카메라 유형:");
        JComboBox<String> cameraTypeComboBox = new JComboBox<>(new String[] { "선택", "고정식 카메라", "추적식 카메라" });

        // 드롭다운 메뉴 패널에 라벨 및 콤보박스 추가
        dropdownPanel.add(usageLabel);
        dropdownPanel.add(usageComboBox);
        dropdownPanel.add(seatsLabel);
        dropdownPanel.add(seatsComboBox);
        dropdownPanel.add(cameraTypeLabel);
        dropdownPanel.add(cameraTypeComboBox);
        contentPanel.add(dropdownPanel);

        // 체크박스 패널 생성
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setLayout(new GridLayout(3, 2)); // 그리드 레이아웃 설정

        // 체크박스 생성
        JCheckBox outletCheckBox = new JCheckBox("콘센트");
        JCheckBox projectorCheckBox = new JCheckBox("빔프로젝트");
        JCheckBox reservationCheckBox = new JCheckBox("예약 필요");
        JCheckBox recordingCheckBox = new JCheckBox("녹화 가능");
        JCheckBox practicableCheckBox = new JCheckBox("실습 가능");

        // 체크박스 패널에 체크박스 추가
        checkBoxPanel.add(outletCheckBox);
        checkBoxPanel.add(projectorCheckBox);
        checkBoxPanel.add(reservationCheckBox);
        checkBoxPanel.add(recordingCheckBox);
        checkBoxPanel.add(practicableCheckBox);
        contentPanel.add(checkBoxPanel);

        // 시간표 패널 생성
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(8, 5)); // 그리드 레이아웃 설정
        timePanel.setBorder(BorderFactory.createTitledBorder("원하는 시간 찾기")); // 테두리 설정

        // 시간표 라벨 및 체크박스 생성
        String[] days = { "Mon", "Tue", "Wed", "Thu", "Fri" };
        String[] times = { "1", "2", "3", "4", "5", "6", "7", "8" };

        for (String time : times) {
            for (String day : days) {
                String name = day + time;
                timeDictionary.put(name, false); // 초기값 설정
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeDictionary.put(name, checkBox.isSelected()); // 체크박스 상태 저장
                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // 테두리 설정
                timePanel.add(checkBox); // 시간표 패널에 체크박스 추가
            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel); // 스크롤 패널 생성
        contentPanel.add(scrollPane); // 내용 패널에 스크롤 패널 추가

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout()); // 플로우 레이아웃 설정

        JButton backButton = new JButton("Back");
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage = (String) usageComboBox.getSelectedItem(); // 선택된 사용 유형 저장
                seats = (String) seatsComboBox.getSelectedItem(); // 선택된 좌석 수 저장
                cameraType = (String) cameraTypeComboBox.getSelectedItem(); // 선택된 카메라 유형 저장

                outlet = outletCheckBox.isSelected(); // 콘센트 체크박스 상태 저장
                projector = projectorCheckBox.isSelected(); // 빔프로젝트 체크박스 상태 저장
                reservation = reservationCheckBox.isSelected(); // 예약 필요 체크박스 상태 저장
                recording = recordingCheckBox.isSelected(); // 녹화 가능 체크박스 상태 저장
                practicable = practicableCheckBox.isSelected(); // 실습 가능 체크박스 상태 저장
                infoArea = new JTextArea(80, 40); // 정보 표시 영역 생성

                searchClassroomInfo(); // 교실 정보 검색

                // 새로운 창을 열어 결과를 보여줌
                JFrame newFrame = new JFrame("검색된 정보");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 닫기 버튼 설정
                newFrame.setSize(800, 400); // 새 창 크기 설정
                newFrame.setLayout(new FlowLayout()); // 레이아웃 설정

                infoArea.setEditable(false); // 정보 표시 영역 수정 불가 설정
                newFrame.add(infoArea); // 새 창에 정보 표시 영역 추가
                newFrame.setVisible(true); // 새 창 보이기 설정
            }
        });

        buttonPanel.add(backButton); // 버튼 패널에 백 버튼 추가
        buttonPanel.add(searchButton); // 버튼 패널에 검색 버튼 추가
        frame.add(buttonPanel, BorderLayout.SOUTH); // 프레임에 버튼 패널 추가

        // 프레임 보이기 설정
        frame.setVisible(true);
    }

    // 교실 정보 검색 함수
    private void searchClassroomInfo() {
        final String JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"; // JDBC 드라이버 설정
        final String DB_URL = "jdbc:mysql://localhost:3306/DB2024Team05"; // DB URL 설정
        final String USER = "DB2024Team05"; // DB 사용자명 설정
        final String PASS = "root"; // DB 비밀번호 설정
        String message = "검색된 교실의 번호:\n"; // 결과 메시지 초기값 설정

        // SQL 쿼리 문자열 생성
        StringBuilder query = new StringBuilder("SELECT * FROM DB2024_Classroom WHERE 1=1");

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

        // DB와의 커넥션 생성
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/DB2024Team05", "root", "root");
                PreparedStatement stmt = conn.prepareStatement(query.toString())) { // 커넥션과 준비된 명령문 생성

            ResultSet rs = stmt.executeQuery(); // 쿼리 실행 및 결과 저장

            while (rs.next()) { // 결과 집합 순회
                String roomNumber = rs.getString("Room_Number"); // 교실 번호 가져오기
                String availableTimes = getAvailableTimes(rs); // 가능한 시간 가져오기
                if (!availableTimes.isEmpty()) { // 가능한 시간이 있으면
                    message += roomNumber + " 가능 시간: " + availableTimes + "\n"; // 메시지에 추가
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

    // 가능한 시간 가져오는 함수
    private String getAvailableTimes(ResultSet rs) throws SQLException {
        StringBuilder availableTimes = new StringBuilder(); // 가능한 시간 문자열 생성
        for (String time : timeDictionary.keySet()) { // 시간 딕셔너리 순회
            if (timeDictionary.get(time) && rs.getBoolean(time)) { // 시간 선택 상태 확인
                availableTimes.append(time).append(" "); // 가능한 시간 추가
            }
        }
        return availableTimes.toString().trim(); // 가능한 시간 문자열 반환
    }

    // 메인 함수
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() { // 이벤트 디스패치 스레드에서 실행
            public void run() {
                new Professor_findClassroom(); // 객체 생성
            }
        });
    }
}
