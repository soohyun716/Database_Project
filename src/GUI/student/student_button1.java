package GUI.student;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import GUI.mainFrame.mainGUI;
import javax.swing.JCheckBox;
import java.awt.FlowLayout;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import java.awt.Component;

public class student_button1 extends JFrame{
	
	private JPanel contentPane;
	
	String usage;
    String seats;
    boolean content;
    boolean project;
    boolean eat;
    boolean computer;
    Map<String, Boolean> timeDictionary=new HashMap<>();
    private JTextArea infoArea;
	
	public student_button1() {
		
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
        
        JLabel userLabel = new JLabel("- \uC6D0\uD558\uB294 \uAC15\uC758\uC2E4 \uCC3E\uAE30 -");
        userLabel.setHorizontalAlignment(SwingConstants.CENTER);
        userLabel.setFont(new Font("�������", Font.BOLD, 22));
        logoPanel.add(userLabel);
        
        //�ɼǵ� ���̴� mainPanel
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(mainPanel, BorderLayout.CENTER);
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        
        //combobox�� checkbox ���̴� subMainPanel
        JPanel subMainPanel = new JPanel();
        subMainPanel.setBackground(new Color(255, 255, 255));
        mainPanel.add(subMainPanel);
        subMainPanel.setLayout(new GridLayout(2, 0, 10, 20));
        subMainPanel.setPreferredSize(new Dimension(400, 170));
        
        
        //checkBox Panel
        JPanel checkBoxPanel = new JPanel();
        checkBoxPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(checkBoxPanel);
        
        JCheckBox contentCheckBox = new JCheckBox("�ܼ�Ʈ");
        contentCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        contentCheckBox.setFont(new Font("�������", Font.BOLD, 13));
        contentCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox beamProjectCheckBox = new JCheckBox("��������Ʈ");
        beamProjectCheckBox.setFont(new Font("�������", Font.BOLD, 13));
        beamProjectCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox eatCheckBox = new JCheckBox("�Ļ�");
        eatCheckBox.setHorizontalAlignment(SwingConstants.LEFT);
        eatCheckBox.setFont(new Font("�������", Font.BOLD, 13));
        eatCheckBox.setBackground(new Color(255, 255, 255));
        JCheckBox computerCheckBox=new JCheckBox("��ǻ��");
        computerCheckBox.setFont(new Font("�������", Font.BOLD, 13));
        computerCheckBox.setBackground(new Color(255, 255, 255));
        checkBoxPanel.setLayout(new GridLayout(2, 2, 0, 2));
        checkBoxPanel.add(contentCheckBox);
        checkBoxPanel.add(beamProjectCheckBox);
        checkBoxPanel.add(eatCheckBox);
        checkBoxPanel.add(computerCheckBox);
        
        //dropdown Panel
        JPanel dropdownsPanel = new JPanel();
        dropdownsPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        dropdownsPanel.setBackground(new Color(255, 255, 255));
        subMainPanel.add(dropdownsPanel);
        dropdownsPanel.setSize(new Dimension(800,200));
        dropdownsPanel.setLayout(new GridLayout(2, 2, 100, 10));
        
        
        JLabel usageLabel = new JLabel("\uACF5\uAC04 \uC720\uD615 : ");
        usageLabel.setHorizontalAlignment(SwingConstants.LEFT);
        usageLabel.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(usageLabel);
        
        JComboBox usageComboBox = new JComboBox(new String[]{"����", "����", "���� ��"});
        usageComboBox.setBackground(new Color(255, 255, 255));
        usageComboBox.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(usageComboBox);
        usageComboBox.setPreferredSize(new Dimension(200, usageComboBox.getPreferredSize().height));
        
        JLabel seatsLabel = new JLabel("\uC88C\uC11D \uC218    : ");
        seatsLabel.setHorizontalAlignment(SwingConstants.LEFT);
        seatsLabel.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(seatsLabel);
        
        JComboBox seatsComboBox = new JComboBox(new String[]{"����", "1-10", "11-20", "21-30", "31-40", "41-50", "51-60", "61-70", "71-80", "81-90"});
        seatsComboBox.setBackground(new Color(255, 255, 255));
        seatsComboBox.setFont(new Font("�������", Font.BOLD, 14));
        dropdownsPanel.add(seatsComboBox);
        

        // Time table
        JPanel timePanel = new JPanel();
        timePanel.setLayout(new GridLayout(8, 5));
        timePanel.setBorder(BorderFactory.createTitledBorder("���ϴ� ���� ����"));
        timePanel.setBackground(new Color(255,255,255));

        String[] days = {"Mon", "Tue", "Wed", "Thu", "Fri"};
        String[] times = {"1", "2", "3", "4", "5", "6", "7","8"};

        for (String time : times) {
            for (String day : days) {
                String name=day+time;
                timeDictionary.put(name,false);
                JCheckBox checkBox = new JCheckBox(day + " " + time);
                checkBox.setBackground(new Color(255,255,255));
                checkBox.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        timeDictionary.put(name, true);

                    }
                });
                checkBox.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                timePanel.add(checkBox);

            }
        }
        JScrollPane scrollPane = new JScrollPane(timePanel);
        mainPanel.add(scrollPane);
        scrollPane.setBackground(new Color(255,255,255));
        
        
        //resultButton & homeButton
        JPanel ButtonPanel = new JPanel();
        ButtonPanel.setBackground(new Color(255, 255, 255));
        contentPane.add(ButtonPanel, BorderLayout.SOUTH);
        ButtonPanel.setLayout(new BorderLayout(0, 0));
        
        JPanel resultPanel = new JPanel(new FlowLayout());
        resultPanel.setBackground(new Color(255, 255, 255));
        ButtonPanel.add(resultPanel, BorderLayout.CENTER);
        JButton resultButton = new JButton("\uAC80\uC0C9");
        resultButton.setFont(new Font("�������", Font.BOLD, 16));
        resultButton.setBackground(new Color(255, 255, 255));
        resultPanel.add(resultButton);
        
        JPanel emptyPanel = new JPanel();
        emptyPanel.setBackground(new Color(255,255,255));
        ButtonPanel.add(emptyPanel, BorderLayout.WEST);
        emptyPanel.setPreferredSize(new Dimension(90,0));
       
        resultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                usage=(String)usageComboBox.getSelectedItem();
                seats=(String)seatsComboBox.getSelectedItem();

                content= contentCheckBox.isSelected();
                project= beamProjectCheckBox.isSelected();
                eat= eatCheckBox.isSelected();
                computer= computerCheckBox.isSelected();
                infoArea=new JTextArea(1100,600);
                if(usage.equals("����")) searchClassroomInfo(seats, content, project, eat, computer);

           
                //else searchClassroomExternalInfo(content, project, eat, computer);
                // �� â�� ���� ����
                JFrame newFrame = new JFrame("�˻��� ����");
                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                newFrame.setBackground(new Color(255, 255, 255));
                newFrame.setBounds(100, 100, 1100, 600);
                newFrame.setLocation(50, 50);
           
                newFrame.getContentPane().setForeground(new Color(255, 255, 255));
                newFrame.getContentPane().setLayout(new BorderLayout(10,10));
                
                
                infoArea.setEditable(false);
                newFrame.getContentPane().add(infoArea, BorderLayout.CENTER);
                newFrame.setVisible(true);
                
                
                //newFrame.add(contentPane);
                
                
                // �ΰ� ���̴� Panel
                JPanel logoPanel = new JPanel();
                logoPanel.setBackground(new Color(255, 255, 255));
                logoPanel.setPreferredSize(new Dimension(1100, 103)); // Set preferred size for the North panel
                newFrame.getContentPane().add(logoPanel, BorderLayout.NORTH);
                logoPanel.setLayout(new GridLayout(2, 0, 0, 0));
                newFrame.getContentPane().setBackground(new Color(255,255,255));
                
                // GONG-GANG �ΰ� label ����
                JLabel logo = new JLabel("Gong-Gang");
                logo.setBackground(new Color(255, 255, 255));
                logo.setHorizontalAlignment(SwingConstants.CENTER);
                logo.setFont(new Font("Arial Black", Font.BOLD, 40));
                logoPanel.add(logo);
                
                JLabel userLabel = new JLabel("- ��� -");
                userLabel.setHorizontalAlignment(SwingConstants.CENTER);
                userLabel.setFont(new Font("�������", Font.BOLD, 22));
                logoPanel.add(userLabel);

            }

        });
         
        //homebuttom 
        JPanel homePanel = new JPanel();
        homePanel.setBackground(new Color(255, 255, 255));
        JButton homeButton = new JButton("HOME");
        ButtonPanel.add(homePanel, BorderLayout.EAST);
        homePanel.setLayout(new BorderLayout(0, 0));
        homeButton.setBackground(new Color(255, 255, 255));
        homeButton.setAlignmentX(Component.RIGHT_ALIGNMENT);
        homeButton.setFont(new Font("������� ExtraBold", Font.BOLD, 12));
        homePanel.add(homeButton, BorderLayout.SOUTH);
       
        
        
        // Add ActionListener to homeButton
        homeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close current frame
                mainGUI mainFrame = new mainGUI();
                mainFrame.setVisible(true); // Open the mainGUI frame
            }
        });
        
        
      
        
	}
	
	
	private void searchClassroomInfo(String seats, boolean content, boolean project, boolean eat, boolean computer) {

    	//JDBC driver name and database URL
        final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
        final String url = "jdbc:mysql://localhost/DB2024Team05";
        //Database credentials

        // MySQL ������ ��ȣ �Է�
        final String user = "root";
        final String password = "4542";
        String message = "�˻��� ������ ��ȣ: \n";


        String query;
        if (eat) {infoArea.setText("���ǿ����� ����� �Ұ����մϴ�. �缱�� ���ּ���"); return;};
        if (computer)
            if (project) {
                query = "SELECT * FROM DB2024_Classroom WHERE Projector='�� ����' AND Practicable='�ǽ�����'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    // ��� ������ ��ȸ�ϸ� ��� �� ó��
                    while (rs.next()) {
                       // for boolean empty=rs.getBoolean()
                        String Room_number = rs.getString("Room_number");



                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" ����\n";
                            };
                        }

                    }

                    
                    // ��� ���ڿ��� �ؽ�Ʈ ������ ����
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("���ϴ� ������ �����ϴ�. ������ �缱���ϼ���.");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("�����͸� �ҷ����� �������� ������ �ֽ��ϴ�. �ٽ� Ȯ���ϼ���");
                }
                return;


            } else {
                query = "SELECT * FROM DB2024_Classroom WHERE Practicable='�ǽ�����'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" ����\n";
                            };
                        }
                    }


                    
                    // ��� ���ڿ��� �ؽ�Ʈ ������ ����
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("���ϴ� ������ �����ϴ�. ������ �缱���ϼ���.");
                    }


                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("�����͸� �ҷ����� �������� ������ �ֽ��ϴ�. �ٽ� Ȯ���ϼ���");
                }return;
            }
        else {
            if(project){
                query = "SELECT * FROM DB2024_Classroom WHERE Projector='�� ����'";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" ����\n";
                            };
                        }
                    }

                    
                    // ��� ���ڿ��� �ؽ�Ʈ ������ ����
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("���ϴ� ������ �����ϴ�. ������ �缱���ϼ���.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("�����͸� �ҷ����� �������� ������ �ֽ��ϴ�. �ٽ� Ȯ���ϼ���");
                }return;
            }else {
                query = "SELECT * FROM DB2024_Classroom";
                try (Connection conn = DriverManager.getConnection(url, user, password);
                     PreparedStatement stmt = conn.prepareStatement(query)) {

                    ResultSet rs = stmt.executeQuery();
                    while (rs.next()) {
                        String Room_number = rs.getString("Room_number");

                        for (Map.Entry<String, Boolean> entry : timeDictionary.entrySet()) {
                            String key = entry.getKey();
                            Boolean value = entry.getValue();
                            Boolean seatAvailable=isNumberInRange(seats, rs.getInt("SeatCount"));
                            if(value&&seatAvailable) {
                                if(rs.getBoolean(key)) message += Room_number + " " + key+" ����\n";
                            };
                        }
                    }

                    
                    // ��� ���ڿ��� �ؽ�Ʈ ������ ����
                    if (!message.isEmpty()) {
                        infoArea.setText(message);
                    } else {
                        infoArea.setText("���ϴ� ������ �����ϴ�. ������ �缱���ϼ���.");
                    }

                } catch (SQLException e) {
                    e.printStackTrace();
                    infoArea.setText("�����͸� �ҷ����� �������� ������ �ֽ��ϴ�. �ٽ� Ȯ���ϼ���");
                }return;
            }


        }

    }
    //private void searchClassroomExternalInfo(boolean content, boolean project, boolean eat, boolean computer){
    // }
    public static boolean isNumberInRange(String range, int number) {
        String[] parts = range.split("-");



            int start = Integer.parseInt(parts[0].trim());
            int end = Integer.parseInt(parts[1].trim());

            return number >= start && number <= end;

    }

	
}
