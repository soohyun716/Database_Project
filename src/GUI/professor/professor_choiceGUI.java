package GUI.professor;

import GUI.mainFrame.mainGUI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class professor_choiceGUI extends JFrame{

    private JPanel contentPane;

    public professor_choiceGUI() {
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

        // �ΰ� ���̴� Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
        logoPanel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);

        // ������ �ʿ��ϴٸ�? �ΰ� label ����
        JLabel logo1 = new JLabel("\uACF5\uAC04\uC774 \uD544\uC694\uD558\uB2E4\uBA74?");
        logo1.setFont(new Font("������� ExtraBold", Font.PLAIN, 20));
        logo1.setHorizontalAlignment(SwingConstants.CENTER);
        logo1.setVerticalAlignment(SwingConstants.BOTTOM);
        logoPanel.add(logo1);

        // GONG-GANG �ΰ� label ����
        JLabel logo2 = new JLabel("Gong-Gang");
        logo2.setHorizontalAlignment(SwingConstants.CENTER);
        logo2.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo2);

        // ����, �л� ��ư ���̴� panel
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 60));
        buttonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(buttonPanel, BorderLayout.CENTER);

        // �л� ��ư �̹���
        ImageIcon professorImage_temp = new ImageIcon(professor_choiceGUI.class.getResource("/images/professorImage.png"));
        Image prof_img = professorImage_temp.getImage();
        Image prof_Changing = prof_img.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
        ImageIcon professorImage = new ImageIcon(prof_Changing);

        // �л� ��ư ����
        JLabel studentLabel = new JLabel("Professor", professorImage, SwingConstants.CENTER);
        studentLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        studentLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        studentLabel.setFont(new Font("Arial Black", Font.PLAIN, 20));
        studentLabel.setBackground(new Color(255, 255, 255));
        buttonPanel.add(studentLabel);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        buttonPanel.add(panel);
        panel.setLayout(new GridLayout(3, 0, 20, 20));

        JButton button1 = new JButton("원하는 강의실 찾기");
        button1.setBackground(new Color(255, 255, 255));
        button1.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        panel.add(button1);

        // Add ActionListener to button1
        button1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                new Professor_findClassroom();
            }
        });

        JButton button2 = new JButton("나의 강의실 찾기");
        button2.setBackground(new Color(255, 255, 255));
        button2.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        panel.add(button2);

        // Add ActionListener to button2
        button2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Professor_findLecture frame = new Professor_findLecture();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        JButton button3 = new JButton("강의실 관련 교수님 찾기");
        button3.setBackground(new Color(255, 255, 255));
        button3.setFont(new Font("������� ExtraBold", Font.PLAIN, 22));
        panel.add(button3);


        // Add ActionListener to button3
        button3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                EventQueue.invokeLater(new Runnable() {
                    public void run() {
                        try {
                            Professor_findProf frame = new Professor_findProf();
                            frame.setVisible(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });


        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 12));
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
