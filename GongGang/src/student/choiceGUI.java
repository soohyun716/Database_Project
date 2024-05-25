package student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import mainFrame.mainGUI;


public class choiceGUI extends JFrame {

    private JPanel contentPane;

    public choiceGUI() {
        setBackground(new Color(255, 255, 255));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1100, 600);
        setLocation(50, 50);
        setTitle("GONG-GANG");
        contentPane = new JPanel();
        contentPane.setForeground(new Color(255, 255, 255));
        contentPane.setBackground(new Color(255, 255, 255));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setLayout(new BorderLayout(10, 10));
        setContentPane(contentPane);

        // 로고 붙이는 Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);

        // 공간이 필요하다면? 로고 label 생성
        JLabel logo1 = new JLabel("\uACF5\uAC04\uC774 \uD544\uC694\uD558\uB2E4\uBA74?");
        logo1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        // GONG-GANG 로고 label 생성
        JLabel logo2 = new JLabel("Gong-Gang");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);

        // 교수, 학생 버튼 붙이는 panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 60));
        buttonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        // 학생 버튼 이미지
        ImageIcon studentImage_temp = new ImageIcon(choiceGUI.class.getResource("/images/studentImage.png"));
        Image stud_img = studentImage_temp.getImage();
        Image stud_Changing = stud_img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon studentImage = new ImageIcon(stud_Changing);

        // 학생 버튼 생성
        JButton studentButton = new JButton("Student", studentImage);
        studentButton.setBackground(new Color(255, 255, 255));
        studentButton.setFont(new Font("Arial Black", Font.PLAIN, 20));
        studentButton.setVerticalAlignment(SwingConstants.CENTER);
        studentButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        buttonPanel.add(studentButton);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        buttonPanel.add(panel);
        panel.setLayout(new GridLayout(3, 0, 20, 20));

        JButton button1 = new JButton("원하는 공간 찾기");
        button1.setBackground(new Color(255, 255, 255));
        button1.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22));
        panel.add(button1);

        // Add ActionListener to button1
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                student_button1 button1Frame = new student_button1();
                button1Frame.setVisible(true); // Open the button1GUI frame
            }
        });

        JButton button2 = new JButton("\uC218\uC5C5 \uAC15\uC758\uC2E4 \uCC3E\uAE30");
        button2.setBackground(new Color(255, 255, 255));
        button2.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22));
        panel.add(button2);

        // Add ActionListener to button2
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                student_button2 button2Frame = new student_button2();
                button2Frame.setVisible(true); // Open the button1GUI frame
            }
        });


        JButton button3 = new JButton("교수님 찾기");
        button3.setBackground(new Color(255, 255, 255));
        button3.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 22));
        panel.add(button3);


        // Add ActionListener to button3
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                student_button3 button3Frame = new student_button3();
                button3Frame.setVisible(true); // Open the button1GUI frame
            }
        });


        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
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
    }
}