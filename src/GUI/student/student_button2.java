package GUI.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import GUI.mainFrame.mainGUI;

public class student_button2 extends JFrame{
	
	private JPanel contentPane;
	
	private JTextField lectureNumberField;
    private JButton searchButton;
    private JFrame resultFrame;
    private JTextField textField;
    
	public student_button2() {
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
        
        // �ΰ� ���̴� Panel
        JPanel logoPanel = new JPanel();
        logoPanel.setBackground(new Color(255, 255, 255));
        logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
        contentPane.add(logoPanel, BorderLayout.NORTH);
        logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
       
        // GONG-GANG �ΰ� label ����
        JLabel logo = new JLabel("Gong-Gang");
        logo.setBackground(new Color(255, 255, 255));
        logo.setHorizontalAlignment(SwingConstants.CENTER);
        logo.setFont(new Font("Arial Black", Font.BOLD, 40));
        logoPanel.add(logo);
        
        JLabel userLabel = new JLabel("- \uC218\uC5C5 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("�������", Font.BOLD, 22));
        logoPanel.add(userLabel);
        
        
        
        //�Է� �޴� mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 20));
        
        JLabel inputLabel = new JLabel("\uD559\uC218\uBC88\uD638\uB97C \uC785\uB825\uD558\uC138\uC694 (ex 14349-1)");
        inputLabel.setVerticalAlignment(SwingConstants.BOTTOM);
        inputLabel.setPreferredSize(new Dimension(1050,100));
        inputLabel.setHorizontalAlignment(SwingConstants.CENTER);
        inputLabel.setFont(new Font("������� ExtraBold", Font.PLAIN, 21));
        inputLabel.setBounds(200, 400, WIDTH, HEIGHT);
        mainPanel.add(inputLabel);
        
        lectureNumberField = new JTextField(17);
        mainPanel.add(lectureNumberField);
        
        searchButton = new JButton("Search");
        searchButton.setFont(new Font("������� ExtraBold", Font.PLAIN, 12));
        searchButton.setBackground(new Color(255, 255, 255));
        mainPanel.add(searchButton);
        
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String lectureNumber = lectureNumberField.getText();
                if (!lectureNumber.isEmpty()) {
                    showRoomNumber(lectureNumber);
                } else {
                    JOptionPane.showMessageDialog(null, "�ùٸ� �м� ��ȣ�� �Է��ϼ���.");
                }
            }
        });

        
        //home button
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
	
	 private void showRoomNumber(String lectureNumber) {
	        String dbUrl = "jdbc:mysql://localhost/DB2024Team05";
	        String dbUser = "root";
	        String dbPass = "4542";

	        // Assuming lectureNumber is entered as '14349-1'
	        String[] parts = lectureNumber.split("-");
	        if (parts.length != 2) {
	            JOptionPane.showMessageDialog(this, "�Է°��� Ȯ���ϼ���");
	            return;
	        }

	        String query = "SELECT Room_Number FROM DB2024_Lecture WHERE Lecture_Num = ? AND Class_Num = ?";

	        try (Connection conn = DriverManager.getConnection(dbUrl, dbUser, dbPass);
	             PreparedStatement stmt = conn.prepareStatement(query)) {

	            stmt.setString(1, parts[0]); // Lecture_Num
	            stmt.setString(2, parts[1]); // Class_Num
	            try (ResultSet rs = stmt.executeQuery()) {
	                if (rs.next()) {
	                    String roomNumber = rs.getString("Room_Number");
	                    displayResult(roomNumber);
	                } else {
	                    JOptionPane.showMessageDialog(this, "�м���ȣ�� �ٽ� Ȯ���ϼ���");
	                }
	            }
	        } catch (SQLException ex) {
	            ex.printStackTrace();
	            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage());
	        }
	    }

	    private void displayResult(String roomNumber) {
	        // ����� �� â�� ǥ��
	        if (resultFrame != null) {
	            resultFrame.dispose();
	        }
	        resultFrame = new JFrame("Classroom Information");
	        resultFrame.setSize(300, 100);
	        resultFrame.getContentPane().setLayout(new FlowLayout());
	        resultFrame.getContentPane().add(new JLabel("���ǽ� ��ȣ: " + roomNumber));
	        resultFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	        resultFrame.setVisible(true);
	    }

}
