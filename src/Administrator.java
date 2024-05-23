package 공강;

//Import required packages
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.DefaultTableModel;
import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Vector;

/*
 * 데이터베이스 수정 및 관리 위한 코드 
 * 
 * [투플 값 관리] 추가/수정/삭제 선택 
 * -> 5가지 테이블 중 관리할 테이블 선택
 * 추가 -> 데이터 입력받기 
 * 수정 -> 변경할 데이터 선택 
 * 삭제 -> 삭제할 데이터 선택
 * 
 */

public class DB2024 { // 메인 실행 함수
	public static void main(String[] args) {
		new 관리자();
		// new addCommonComponents();
	}
}

class 관리자 extends JFrame implements ActionListener {
	public JLabel result = new JLabel();
	JFrame frame = new JFrame();
	DefaultTableModel tableModel = null;

	JTable table = new JTable(tableModel);
	JScrollPane scrollPane = new JScrollPane(table);
	public JTextField inputField = new JTextField(30); // 사용자 입력을 받기 위한 텍스트 필드 추가
	Container ct = getContentPane(); // 컨테이너 객체 생성

	// JDBC driver name and database URL
	static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
	static final String DB_URL = "jdbc:mysql://localhost/DB_2024";
	// Database credentials
	// MySQL 계정과 암호 입력
	String pass;
	static final String USER = "root";
	static final String PASS = "rootroot";
	private String tableName;


	String command1 = null;
	String command2 = null;
	
	TextField idField, passField;
    Button submitButton;

	public 관리자() {
        password();
	}

	public void password() {

		 ct.setLayout(new FlowLayout(FlowLayout.CENTER,0,100)); // 배치관리자 설정
		// ct.setLayout(new GridLayout(3,1));
		// ct.setLayout(new BorderLayout());


		Panel p1 = new Panel();
		Label idLabel = new Label("ID :");
		p1.add(idLabel);
		idField = new TextField(20);
		p1.add(idField);

		Panel p2 = new Panel();
		Label passLabel = new Label("Password:");
		p2.add(passLabel);

		passField = new TextField(20);
		passField.setEchoChar('*'); // 비밀번호 입력 시 '*'로 표시
		p2.add(passField);

		submitButton = new Button("ENTER");
		submitButton.addActionListener(this);

		submitButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (e.getSource() == submitButton) {
					String id = idField.getText();
					String password = passField.getText();

					System.out.println("ID: " + id);
					System.out.println("Password: " + password);

					if (id.equals(USER) && password.equals(PASS)) {
						menu1(); // pass
					}
				}
			}
		});

		Panel p3 = new Panel();
		p3.add(submitButton);

		ct.add(p1);
		ct.add(p2);
		ct.add(p3);

		setTitle("ID Password Input");
		setSize(1000, 400);
		setVisible(true);
	}
	  

	public void menu1() {

		ct.removeAll();
		repaint();

		ct.setLayout(new FlowLayout()); // 배치관리자 설정

		JButton jb_insert = new JButton("투플추가");
		jb_insert.addActionListener(this);
		ct.add(jb_insert);

		JButton jb_modify = new JButton("투플수정");
		jb_modify.addActionListener(this);
		ct.add(jb_modify);

		JButton jb_delete = new JButton("투플삭제");
		jb_delete.addActionListener(this);
		ct.add(jb_delete);

		setTitle("관리자");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // 프레임을 닫을 때 프로그램이 종료
		setSize(1000, 400);
		setVisible(true);
	}

	public void addCommonComponents() {
		ct.removeAll();
		repaint();

		// 공통으로 사용할 컴포넌트를 추가하는 메서드
		ct.setLayout(new FlowLayout());

		JButton jb1 = new JButton("Space_Info");
		jb1.addActionListener(this);
		ct.add(jb1);

		JButton jb2 = new JButton("Classroom");
		jb2.addActionListener(this);
		ct.add(jb2);

		JButton jb3 = new JButton("Classroom_External");
		jb3.addActionListener(this);
		ct.add(jb3);

		JButton jb4 = new JButton("Professor");
		jb4.addActionListener(this);
		ct.add(jb4);

		JButton jb5 = new JButton("Lecture");
		jb5.addActionListener(this);
		ct.add(jb5);

		JButton backButton = new JButton("뒤로가기");
		backButton.addActionListener(this);
		ct.add(backButton);

		setVisible(true);

	}

	private void showInputField() {
		ct.removeAll();

		run_prog(command1, tableName);

		JButton backButton = new JButton("뒤로가기");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				addCommonComponents();
			}
		});
		ct.add(backButton);

		revalidate();
		repaint();
	}

	// ActionEvent 클래스와 ActionListener 리스너 인터페이스를 사용해서 버튼 누르는 이벤트를 처리
	@Override
	public void actionPerformed(ActionEvent e) {

		String command = e.getActionCommand();

		if (command.equals("투플추가")) {
			command1 = command;
			addCommonComponents();
		} else if (command.equals("투플수정")) {
			command1 = command;
			addCommonComponents();
		} else if (command.equals("투플삭제")) {
			command1 = command;
			addCommonComponents();
		} else {
			command2 = command;

			if ("뒤로가기".equals(command2)) {
				menu1();
			} else if ("Space_Info".equals(command2) || "Classroom".equals(command2)
					|| "Classroom_External".equals(command2) || "Professor".equals(command2)
					|| "Lecture".equals(command2)) {
				tableName = getTableName(command2);

				// 이전 화면을 저장
				결과보기(tableName);

				// 입력 필드를 보여주는 창 열기
				showInputField();
			}
		}
	}

	// ActionEvent 클래스와 ActionListener 리스너 인터페이스를 사용해서 버튼 누르는 이벤트를 처리

	public void run_prog(String command1, String tableName) {
		if (command1.equals("투플추가")) {
			// 투플 추가 로직 실행
			insertTuple(tableName);
		} else if (command1.equals("투플수정")) {
			// 투플 수정 로직 실행
			updateTuple(tableName);
		} else if (command1.equals("투플삭제")) {
			// 투플 삭제 로직 실행
			deleteTuple(tableName);
		}
	}

	private void insertTuple(String tableName) {
		// 기존 컴포넌트 초기화
		ct.removeAll();

		// 결과 텍스트를 선언 및 초기화
		result.setText("INSERT INTO " + tableName + " VALUES ( _____ );  ==> 추가할 _____를 입력하세요 :  ");

		// 결과 텍스트와 입력 필드를 컨테이너에 추가
		ct.add(result);
		ct.add(inputField);

		JButton confirmButton = new JButton("확인");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
						Statement stmt = conn.createStatement()) {

					String tupleInfo = inputField.getText();
					inputField.setText(""); // 입력 필드 초기화
					String query = "INSERT INTO " + tableName + " VALUES (" + tupleInfo + ")";
					System.out.println(query); // 디버깅을 위해 출력

					// 쿼리 실행
					stmt.executeUpdate(query);

					// 결과를 다시 보여주기 위해 결과보기 메서드 호출
					결과보기(tableName);

				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});

		ct.add(confirmButton);

		// 컨테이너 변경사항 적용 및 화면 다시 그리기
		ct.revalidate();
		ct.repaint();
	}

	private void updateTuple(String tableName) {
		// 기존 컴포넌트 초기화
		ct.removeAll();

		// 결과 텍스트를 선언 및 초기화
		result.setText("UPDATE " + tableName + " SET _____ WHERE _____; -> 수정할 _____를 입력하세요 : ");

		// 결과 텍스트와 입력 필드를 컨테이너에 추가
		ct.add(result);
		ct.add(inputField);

		JButton confirmButton = new JButton("확인");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
						Statement stmt = conn.createStatement()) {

					String tupleInfo = inputField.getText();
					inputField.setText(""); // 입력 필드 초기화
					String query = "UPDATE " + tableName + " SET " + tupleInfo;
					System.out.println(query); // 디버깅을 위해 출력

					// 쿼리 실행
					stmt.executeUpdate(query);

					// 결과를 다시 보여주기 위해 결과보기 메서드 호출
					결과보기(tableName);

				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});

		ct.add(confirmButton);

		// 컨테이너 변경사항 적용 및 화면 다시 그리기
		ct.revalidate();
		ct.repaint();
	}

	private void deleteTuple(String tableName) {
		// 기존 컴포넌트 초기화
		ct.removeAll();

		// 결과 텍스트를 선언 및 초기화
		result.setText("DELETE FROM " + tableName + " WHERE _____; ==> 삭제할 _____를 입력하세요 : ");

		// 결과 텍스트와 입력 필드를 컨테이너에 추가
		ct.add(result);
		ct.add(inputField);

		JButton confirmButton = new JButton("확인");
		confirmButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
						Statement stmt = conn.createStatement()) {

					String tupleInfo = inputField.getText();
					inputField.setText(""); // 입력 필드 초기화
					String query = "DELETE FROM " + tableName + " WHERE " + tupleInfo;
					System.out.println(query); // 디버깅을 위해 출력

					// 쿼리 실행
					stmt.executeUpdate(query);

					// 결과를 다시 보여주기 위해 결과보기 메서드 호출
					결과보기(tableName);

				} catch (SQLException se) {
					se.printStackTrace();
				}
			}
		});

		ct.add(confirmButton);

		// 컨테이너 변경사항 적용 및 화면 다시 그리기
		ct.revalidate();
		ct.repaint();
	}

	private String getTableName(String command2) {
		// 버튼에 따른 테이블 이름을 반환하는 메서드
		switch (command2) {
		case "Space_Info":
			return "DB2024_Space_Info";
		case "Classroom":
			return "DB2024_Classroom";
		case "Classroom_External":
			return "DB2024_Classroom_External";
		case "Professor":
			return "DB2024_Professor";
		case "Lecture":
			return "DB2024_Lecture";
		default:
			return null;
		}
	}

	public void 결과보기(String tableName) {
		String query2 = "SELECT * FROM " + tableName;
		System.out.println(query2); // 디버깅을 위해 출력

		DefaultTableModel tableModel = new DefaultTableModel();

		try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
				Statement stmt1 = conn.createStatement();
				ResultSet rs = stmt1.executeQuery(query2)) {

			// Get column names from ResultSet metadata
			ResultSetMetaData metaData = rs.getMetaData();
			int columnCount = metaData.getColumnCount();
			Vector<String> columnNames = new Vector<>();

			for (int column = 1; column <= columnCount; column++) {
				columnNames.add(metaData.getColumnName(column));
			}

			// Add column names to table model
			tableModel.setColumnIdentifiers(columnNames);

			// Get row data from ResultSet
			while (rs.next()) {
				Vector<Object> rowData = new Vector<>();
				for (int column = 1; column <= columnCount; column++) {
					rowData.add(rs.getObject(column));
				}
				tableModel.addRow(rowData);
			}

		} catch (SQLException se) {
			se.printStackTrace();
		}

		// Create JTable with the table model
		JTable table = new JTable(tableModel);
		JScrollPane scrollPane = new JScrollPane(table);

		// Create JFrame and add JScrollPane to it
		JFrame frame = new JFrame("Database Table");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // 프레임을 닫을 때 현재 창만 종료
		frame.setSize(800, 600);
		frame.add(scrollPane, BorderLayout.CENTER);
		frame.setVisible(true);
	}

} // end main
