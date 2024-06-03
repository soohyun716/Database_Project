package GUI.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import GUI.mainFrame.mainGUI;
import javax.swing.JTextArea;

public class student_button3 extends JFrame{

    private JPanel contentPane;
    private JTextField nameField;
    private JTextArea infoArea;

    public student_button3() {
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
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));

        // GONG-GANG 로고 label 생성
        JLabel logo = new JLabel("Gong-Gang");
        logo.setBackground(new Color(255, 255, 255));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo);

        JLabel userLabel = new JLabel("- \uAD50\uC218\uB2D8 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("나눔고딕 ExtraBold", Font.BOLD, 22));
        logoPanel.add(userLabel);

        //inputPanel & Info display area 붙이는 mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255,255,255));
        mainPanel.setPreferredSize(new Dimension(700,400));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new GridLayout(0, 1, 0, 0));

        // Input Panel
        JPanel inputPanel = new JPanel();
        inputPanel.setBackground(new Color(255, 255, 255));
        inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));

        mainPanel.add(inputPanel);
        JLabel nameLabel = new JLabel("교수님 이름을 입력하세요");
        nameLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        nameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nameLabel.setPreferredSize(new Dimension(1000,80));
        nameLabel.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 20));
        nameField = new JTextField(10);
        JButton searchButton = new JButton("Search");
        searchButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        searchButton.setBackground(new Color(255, 255, 255));
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchProfessorInfo(nameField.getText().trim());
            }
        });
        inputPanel.add(nameLabel);
        inputPanel.add(nameField);
        inputPanel.add(searchButton);


        // Info display area
        infoArea = new JTextArea(10, 40);
        infoArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(infoArea);

        mainPanel.add(scrollPane);

        //home button
        JPanel homeButtonPanel = new JPanel();
        homeButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(homeButtonPanel, BorderLayout.SOUTH);

        JButton homeButton = new JButton("HOME");
        homeButton.setFont(new Font("나눔고딕 ExtraBold", Font.PLAIN, 12));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButtonPanel.add(homeButton);
        homeButtonPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));

        //mainPanel 위치 조정하기 위해 넣은 panel 2개
        JPanel westPanel = new JPanel();
        westPanel.setPreferredSize(new Dimension(50,100));
        westPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(westPanel, BorderLayout.WEST);

        JPanel eastPanel = new JPanel();
        eastPanel.setPreferredSize(new Dimension(50,100));
        eastPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(eastPanel, BorderLayout.EAST);


        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });




    }

    private void searchProfessorInfo(String name) {

        //JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        //Database credentials
        // MySQL 계정과 암호 입력
        final String user = "root";
        final String password = "kms1";

        String query = "SELECT Email, Lab_Location, Phone FROM DB2024_Professor WHERE Name = ?";
        try (Connection conn = DriverManager.getConnection(url, user, password);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, name);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String email = rs.getString("Email");
                String Lab_Location = rs.getString("Lab_Location");
                String Phone = rs.getString("Phone");
                infoArea.setText("Email: " + email + "\nLab_Location: " + Lab_Location + "\nPhone Number: " + Phone);
            } else {
                infoArea.setText("No information found.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            infoArea.setText("Error retrieving data.");
        }
    }


}