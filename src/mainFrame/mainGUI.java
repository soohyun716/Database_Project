package mainFrame;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import administrator.Administrator;
import professor.professor_choiceGUI;
import student.student_choiceGUI;

public class mainGUI extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	mainGUI frame = new mainGUI();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public mainGUI() {
    	setTitle("GONG-GANG");
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

        // 교수 버튼 이미지
        ImageIcon professorImage_temp = new ImageIcon(mainGUI.class.getResource("/images/professorImage.png"));
        Image prof_img = professorImage_temp.getImage();
        Image prof_Changing = prof_img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon professorImage = new ImageIcon(prof_Changing);
        // 교수 버튼 생성
        JButton professorButton = new JButton("Professor", professorImage);
        professorButton.setBackground(new Color(255, 255, 255));
        professorButton.setFont(new Font("Arial Black", Font.PLAIN, 20));
        professorButton.setVerticalAlignment(SwingConstants.CENTER);
        professorButton.setVerticalTextPosition(SwingConstants.BOTTOM);
        buttonPanel.add(professorButton);

        professorButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                professor_choiceGUI choiceFrame = new professor_choiceGUI(); // Assuming choiceGUI is another frame class
                choiceFrame.setVisible(true); // Open the choiceGUI frame
            }
        });

        // 학생 버튼 이미지
        ImageIcon studentImage_temp = new ImageIcon(mainGUI.class.getResource("/images/studentImage.png"));
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

        // Add ActionListener to studentButton
        studentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                student_choiceGUI choiceFrame = new student_choiceGUI(); // Assuming choiceGUI is another frame class
                choiceFrame.setVisible(true); // Open the choiceGUI frame
            }
        });

        // 관리자 버튼을 붙이는 panel
        JPanel administratorPanel = new JPanel();
        administratorPanel.setBackground(new Color(255, 255, 255));
        administratorPanel.setPreferredSize(new Dimension(100, 35)); // Set preferred size for the South panel
        administratorPanel.setLayout(new FlowLayout(FlowLayout.RIGHT)); // Align components to the right
        contentPane.add(administratorPanel, BorderLayout.SOUTH);
        //관리자 버튼
        JButton adminButton = new JButton("Administrator");
        adminButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        adminButton.setBackground(new Color(255, 255, 255));
        adminButton.setPreferredSize(new Dimension(115, 26));
        administratorPanel.add(adminButton);

        adminButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                Administrator adminFrame = new Administrator(); // Assuming choiceGUI is another frame class
            }
        });
    }
}

