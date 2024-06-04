package professor;

import mainFrame.mainGUI;

import javax.swing.*; // 스윙 패키지 가져오기
import javax.swing.border.EmptyBorder; // 빈 테두리 관련 패키지 가져오기
import java.awt.*; // AWT 패키지 가져오기
import java.awt.event.ActionEvent; // 액션 이벤트 관련 패키지 가져오기
import java.awt.event.ActionListener; // 액션 리스너 관련 패키지 가져오기

public class professor_choiceGUI extends JFrame{

    private JPanel contentPane; // 메인 콘텐츠 패널

    public professor_choiceGUI() {
        setBackground(new Color(255, 255, 255)); // 배경색 설정
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 닫기 버튼 설정
        setBounds(100, 100, 1100, 600); // 프레임 크기 설정
        setLocation(50, 50); // 프레임 위치 설정
        setTitle("GONG-GANG"); // 프레임 제목 설정
        contentPane = new JPanel(); // 메인 콘텐츠 패널 생성
        contentPane.setForeground(new Color(255, 255, 255)); // 전경색 설정
        contentPane.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5)); // 테두리 설정
        contentPane.setLayout(new BorderLayout(10, 10)); // 레이아웃 설정
        setContentPane(contentPane); // 프레임에 콘텐츠 패널 추가

        // 로고 패널 생성
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0)); // 그리드 레이아웃 설정
        logoPanel.setPreferredSize(new Dimension(100, 100)); // 선호 크기 설정
        contentPane.add(logoPanel, BorderLayout.NORTH); // 콘텐츠 패널에 로고 패널 추가

        // 로고 라벨1 생성
        JLabel logo1 = new JLabel("공간이 필요하다면?");
        logo1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20)); // 폰트 설정
        logo1.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo1.setVerticalAlignment(SwingConstants.BOTTOM); // 수직 정렬 설정
        logoPanel.add(logo1); // 로고 패널에 로고 라벨1 추가

        // 로고 라벨2 생성
        JLabel logo2 = new JLabel("Gong-Gang");
        logo2.setHorizontalAlignment(SwingConstants.CENTER); // 수평 정렬 설정
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40)); // 폰트 설정
        logoPanel.add(logo2); // 로고 패널에 로고 라벨2 추가

        // 버튼 패널 생성
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 60)); // 플로우 레이아웃 설정
        buttonPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(buttonPanel, BorderLayout.CENTER); // 콘텐츠 패널에 버튼 패널 추가

        // 교수 이미지 아이콘 생성
        ImageIcon professorImage_temp = new ImageIcon(professor_choiceGUI.class.getResource("/images/professorImage.png"));
        Image prof_img = professorImage_temp.getImage(); // 이미지 가져오기
        Image prof_Changing = prof_img.getScaledInstance(250, 250, Image.SCALE_SMOOTH); // 이미지 크기 조정
        ImageIcon professorImage = new ImageIcon(prof_Changing); // 이미지 아이콘 생성

        // 교수 라벨 생성
        JLabel studentLabel = new JLabel("Professor", professorImage, SwingConstants.CENTER);
        studentLabel.setHorizontalTextPosition(SwingConstants.CENTER); // 텍스트 수평 정렬 설정
        studentLabel.setVerticalTextPosition(SwingConstants.BOTTOM); // 텍스트 수직 정렬 설정
        studentLabel.setFont(new Font("Arial Black", Font.PLAIN, 20)); // 폰트 설정
        studentLabel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        buttonPanel.add(studentLabel); // 버튼 패널에 교수 라벨 추가

        // 버튼 패널 내의 패널 생성
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        buttonPanel.add(panel); // 버튼 패널에 내부 패널 추가
        panel.setLayout(new GridLayout(3, 0, 20, 20)); // 그리드 레이아웃 설정

        // 첫 번째 버튼 생성
        JButton button1 = new JButton("원하는 강의실 찾기");
        button1.setBackground(new Color(255, 255, 255)); // 배경색 설정
        button1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22)); // 폰트 설정
        panel.add(button1); // 내부 패널에 버튼 추가

        // 첫 번째 버튼에 ActionListener 추가
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                professor_button1 button1Frame = new professor_button1(); // 새로운 프레임 생성
                button1Frame.setVisible(true); // 새로운 프레임 보이기
            }
        });

        // 두 번째 버튼 생성
        JButton button2 = new JButton("나의 강의실 찾기");
        button2.setBackground(new Color(255, 255, 255)); // 배경색 설정
        button2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22)); // 폰트 설정
        panel.add(button2); // 내부 패널에 버튼 추가

        // 두 번째 버튼에 ActionListener 추가
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                professor_button2 button2Frame = new professor_button2(); // 새로운 프레임 생성
                button2Frame.setVisible(true); // 새로운 프레임 보이기
            }
        });

        // 세 번째 버튼 생성
        JButton button3 = new JButton("강의실 관련 교수님 찾기");
        button3.setBackground(new Color(255, 255, 255)); // 배경색 설정
        button3.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22)); // 폰트 설정
        panel.add(button3); // 내부 패널에 버튼 추가

        // 세 번째 버튼에 ActionListener 추가
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                professor_button3 button3Frame = new professor_button3(); // 새로운 프레임 생성
                button3Frame.setVisible(true); // 새로운 프레임 보이기
            }
        });

        // 홈 버튼 패널 생성
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255)); // 배경색 설정
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH); // 콘텐츠 패널에 홈 버튼 패널 추가

        JButton homeButton = new JButton("HOME"); // 홈 버튼 생성
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12)); // 폰트 설정
        homeButton.setBackground(new Color(255, 255, 255)); // 배경색 설정
        homeButtonPanel.add(homeButton); // 홈 버튼 패널에 홈 버튼 추가
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // 플로우 레이아웃 설정

        // 홈 버튼에 ActionListener 추가
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // 현재 프레임 닫기
                mainGUI mainFrame = new mainGUI(); // mainGUI 객체 생성
                mainFrame.setVisible(true); // mainGUI 프레임 보이기
            }
        });
    }
}
