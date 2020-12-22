package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;

import GUI.Menu.MyMouseListener;

class Infector_list extends JFrame{
	
	Statement stmt;
	
	private String value[]= {"연번","확진일","지역","접촉력","상태","노출여부"};
	
	public Infector_list(Statement stmt) {
		
		this.stmt=stmt;
		
		this.setResizable(false);
		this.setSize(600, 500);
		this.setVisible(true);
		
		DefaultTableModel mod = new DefaultTableModel(null,value) {
			private static final long serialVersionUID=1L;
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};
		
		JTable scrTable = new JTable(mod);
		
		JScrollPane Scr = new JScrollPane(scrTable);
		
		Scr.setPreferredSize(new Dimension(400,400));
		
		scrTable.getColumn("연번").setPreferredWidth(20);
		scrTable.getColumn("확진일").setPreferredWidth(20);
		scrTable.getColumn("지역").setPreferredWidth(20);
		scrTable.getColumn("접촉력").setPreferredWidth(20);
		scrTable.getColumn("상태").setPreferredWidth(20);
		scrTable.getColumn("노출여부").setPreferredWidth(20);	
		
		
		this.add(Scr, BorderLayout.CENTER);
		
		String sql="SELECT * FROM seoul.person";
		
		try {
			mod.setNumRows(0);
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				mod.addRow(new Object[] {rs.getString("ID"),rs.getString("DATE"),rs.getString("REGION"),
						rs.getString("CONTACT"),rs.getString("STATUS"),rs.getString("EXPOSURE")});
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
}


class Infector extends JFrame{

	private JTextField ID_TextField;
	private JTextField DATE_TextField;
	private JTextField REGION_TextField;
	private JTextField CONTACT_TextField;
	private JTextField STATUS_TextField;
	private JTextField EXPOSURE_TextField;
	private JTextField CSVNAME_TextField;
	private JButton Load;
	private JButton Modify;
	private JButton Write;
	private JButton Create;
	private JButton Delete;
	private JButton Upload;
	private JButton List_data;
	
	private Statement stmt;
	
	public Infector(Statement stmt) {
		this.stmt=stmt;
		
		this.setResizable(false);
		this.setSize(700, 650);
		this.setVisible(true);
		
		this.setLayout(new BorderLayout());
		
		JPanel displayPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		
		ID_TextField=new JTextField(10);
		DATE_TextField=new JTextField(10);
		REGION_TextField=new JTextField(20);
		CONTACT_TextField=new JTextField(10);
		STATUS_TextField=new JTextField(10);
		EXPOSURE_TextField=new JTextField(10);
		CSVNAME_TextField=new JTextField(10);
		
		Load=new CButton();
		Modify=new CButton();
		Write=new CButton();
		Create=new CButton();
		Delete=new CButton();
		Upload=new CButton();
		List_data=new CButton();
		
		displayPanel.setLayout(new GridLayout(6,3));
		
		displayPanel.add(new JLabel("연번 "));
		displayPanel.add(ID_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("확진일 "));
		displayPanel.add(DATE_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("지역 "));
		displayPanel.add(REGION_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("접촉력 "));
		displayPanel.add(CONTACT_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("상태 "));
		displayPanel.add(STATUS_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("노출여부 "));
		displayPanel.add(EXPOSURE_TextField);
		displayPanel.add(new JLabel(""));
		
		this.add(displayPanel,BorderLayout.CENTER);
		
		MyMouseListener mouse=new MyMouseListener();
		controlPanel.setLayout(new GridLayout(9,2,5,5));
		
		List_data.setText("데이터목록");
		
		List_data.addActionListener((evt)->{
			Load_data(evt);
		});
		
		List_data.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		List_data.addMouseListener(mouse);
		
		controlPanel.add(List_data);
		
		Load.setText("조회");
		
		Load.addActionListener((evt)->{
			Read_CSV(evt);	
		});
		
		Load.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Load.addMouseListener(mouse);
		
		controlPanel.add(Load);
		
		Modify.setText("수정");
		
		Modify.addActionListener((evt)->{
			Modify_CSV(evt);
		});
		
		Modify.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Modify.addMouseListener(mouse);
		
		controlPanel.add(Modify);
		
		Create.setText("생성");
		
		Create.addActionListener((evt)->{
			Create_CSV(evt);
		});
		
		Create.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Create.addMouseListener(mouse);
		
		controlPanel.add(Create);
		
		Delete.setText("제거");
		Delete.addActionListener((evt)->{
			Delete_CSV(evt);
		});
		
		Delete.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Delete.addMouseListener(mouse);
		
		controlPanel.add(Delete);
		
		controlPanel.add(new JLabel("CSV 파일명 입력"));
		controlPanel.add(CSVNAME_TextField);
		
		Write.setText("출력");
		
		Write.addActionListener((evt)->{
			Write_CSV(evt);
		});
		
		Write.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Write.addMouseListener(mouse);
		
		controlPanel.add(Write);
		
		this.add(controlPanel,BorderLayout.EAST);
		
		Upload.setText("CSV 불러오기");
		Upload.addActionListener((evt)->{
				Upload_CSV(evt);

		});
		
		Upload.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Upload.addMouseListener(mouse);
		
		controlPanel.add(Upload);
		
		this.add(controlPanel,BorderLayout.EAST);
		
		controlPanel.setBackground(new Color(178, 190, 195));
		displayPanel.setBackground(new Color(178, 190, 195));
		
		
		
	}
	
	public void Load_data(ActionEvent e) {
		new Infector_list(stmt);
	}

	
	public void Read_CSV(ActionEvent e) {
		
		
		try {
			
			String sql="SELECT * FROM seoul.person where ID ='"+ID_TextField.getText()+"'";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				
				ID_TextField.setText(rs.getString("ID"));
				DATE_TextField.setText(rs.getString("DATE"));
				REGION_TextField.setText(rs.getString("REGION"));
				CONTACT_TextField.setText(rs.getString("CONTACT"));
				STATUS_TextField.setText(rs.getString("STATUS"));
				EXPOSURE_TextField.setText(rs.getString("EXPOSURE"));
				
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void Modify_CSV(ActionEvent e) {
		
		try {
			
			String sql="UPDATE seoul.person SET DATE='"+
			DATE_TextField.getText()+"',REGION='"+REGION_TextField.getText()+
			"',CONTACT='"+CONTACT_TextField.getText()+"', STATUS='"+STATUS_TextField.getText()+
			"',EXPOSURE='"+EXPOSURE_TextField.getText()+"' "
					+"WHERE ID='"+ID_TextField.getText()+"'";
			
			stmt.executeUpdate(sql);
			
			
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
		
	}
	
	public void Create_CSV(ActionEvent e) {
		
		try {
			String sql="INSERT INTO seoul.person (ID,DATE,REGION,CONTACT,STATUS,EXPOSURE) VALUES ('"+
					ID_TextField.getText()+"','"+DATE_TextField.getText()+"','"+
					REGION_TextField.getText()+"','"+
					CONTACT_TextField.getText()+"','"+
					STATUS_TextField.getText()+"','"+
					EXPOSURE_TextField.getText()+"')";
			
			stmt.executeUpdate(sql);
			
			
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
		
	}
	
	public void Delete_CSV(ActionEvent e) {
		
		try {
			String sql="DELETE FROM seoul.person WHERE ID='"+ID_TextField.getText()+"'";
		
			stmt.executeUpdate(sql);
		}
		catch(Exception e1){
			System.out.println(e1.getMessage());
		}
	}
	
	public void Write_CSV(ActionEvent e) {
		
		
		try{
			String sql="SELECT * FROM seoul.person";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BufferedWriter br=new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\hyeon\\2학년 2학기\\고급객체지향프로그래밍\\"+CSVNAME_TextField.getText()+".csv"));
			
			br.write("ID,DATE,REGION,CONTACT,STATUS,EXPOSURE");
			
			while(rs.next()) {
				String id=rs.getString("ID");
				String date=rs.getString("DATE");
				String region=rs.getString("REGION");
				String contact=rs.getString("CONTACT");
				String status=rs.getString("STATUS");
				String exposure=rs.getString("EXPOSURE");
				
				if(status==null) {
					status="";
				}
				
				String line = String.format("%s,%s,%s,%s,%s,%s",id,date,region,contact,
						status,exposure);
				
				br.newLine();
				br.write(line);
				
				
			}
			
			br.close();
			
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		
		
		
	}
	
	
	public void Upload_CSV(ActionEvent e){
		JFileChooser Chooser;
		Chooser=new JFileChooser();
		String[][] indata=new String[10000][6];
		
		FileNameExtensionFilter filter=new FileNameExtensionFilter(
				"CSV 파일 ","CSV");
		Chooser.setFileFilter(filter);
		
		int ret=Chooser.showOpenDialog(null);
		if(ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.");
			
			return;
		}
		
		String filePath=Chooser.getSelectedFile().getPath();
		
		try {
			String sql="DELETE FROM seoul.person";
			stmt.executeUpdate(sql);
		}		
		catch(Exception e1){
			System.out.println(e1.getMessage());
		}
		
		BufferedReader br=null;
		try {
			br=Files.newBufferedReader(Paths.get(filePath));
			
			String line="";
			int row=0;
			
			while((line=br.readLine())!=null){
				
				String[] token=line.split(",",-1);
				
				for(int i=0;i<6;i++) {
					indata[row][i]=token[i];
				}
				
				
				row++;
			} 
			
			for (int i=0;i<6;i++) {
				System.out.println(indata[1][i]);
			}
			
			for(int i=0;i<row;i++) {
				
				if (indata[i][0].equals("ID")) {
				}
				else {
					String sql1="INSERT INTO seoul.person (ID,DATE,REGION,CONTACT,STATUS,EXPOSURE) VALUES ('"+
							indata[i][0]+"','"+indata[i][1]+"','"+
							indata[i][2]+"','"+
							indata[i][3]+"','"+
							indata[i][4]+"','"+
							indata[i][5]+"')";
			
					stmt.executeUpdate(sql1);
				}
			}
			
			br.close();
		}catch(FileNotFoundException e1) {
			e1.printStackTrace();
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		catch(Exception e3){
			System.out.println(e3.getMessage());
		}
	}

	class MyMouseListener implements MouseListener{

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==List_data) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Modify) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Write) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Load) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Create) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Delete) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Upload) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==List_data) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Modify) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Write) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Load) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Create) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Delete) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Upload) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
}

class Clinic_list extends JFrame{
	
	Statement stmt;
	
	private String value[]= {"이름", "소속 구", "위치", "평일 운영시간", "토요일 운영시간", "일/공휴일 운영시간", "대표 전화번호"};
	
	public Clinic_list(Statement stmt) {
		
		this.stmt=stmt;
		
		this.setResizable(false);
		this.setSize(600, 500);
		this.setVisible(true);
		
		DefaultTableModel mod = new DefaultTableModel(null,value) {
			private static final long serialVersionUID=1L;
			public boolean isCellEditable(int rowIndex,int mColIndex) {
				return false;
			}
		};
		
		JTable scrTable = new JTable(mod);
		
		JScrollPane Scr = new JScrollPane(scrTable);
		
		Scr.setPreferredSize(new Dimension(400,400));
		
		scrTable.getColumn("이름").setPreferredWidth(45);
		scrTable.getColumn("소속 구").setPreferredWidth(5);
		scrTable.getColumn("위치").setPreferredWidth(100);
		scrTable.getColumn("평일 운영시간").setPreferredWidth(35);
		scrTable.getColumn("토요일 운영시간").setPreferredWidth(35);
		scrTable.getColumn("일/공휴일 운영시간").setPreferredWidth(35);
		scrTable.getColumn("대표 전화번호").setPreferredWidth(5);
		
		this.add(Scr, BorderLayout.CENTER);
		
		String sql="SELECT * FROM seoul.clinic";
		
		try {
			mod.setNumRows(0);
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()) {
				mod.addRow(new Object[]{rs.getString("name"), rs.getString("district")
						, rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
				rs.getString("holi_time"), rs.getString("number") });
			}
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	
	
}

class Clinic extends JFrame{
	
	private JTextField NAME_TextField;
	private JTextField DISTRICT_TextField;
	private JTextField LOCATION_TextField;
	private JTextField WEEK_TIME_TextField;
	private JTextField SAT_TIME_TextField;
	private JTextField HOLI_TIME_TextField;
	private JTextField NUMBER_TextField;
	private JTextField CITY_TextField;
	private JTextField CSVNAME_TextField;
	private JButton Load;
	private JButton Modify;
	private JButton Write;
	private JButton Create;
	private JButton Delete;
	private JButton Upload;
	private JButton List_data;
	
	private Statement stmt;
	
	public Clinic(Statement stmt) {
		this.stmt=stmt;
		
		this.setResizable(false);
		this.setSize(700, 650);
		this.setVisible(true);
		
			
		
		
		this.setLayout(new BorderLayout());
		
		JPanel displayPanel = new JPanel();
		JPanel controlPanel = new JPanel();
		JPanel textPanel = new JPanel();
		
		NAME_TextField=new JTextField(10);
		DISTRICT_TextField=new JTextField(20);
		LOCATION_TextField=new JTextField(25);
		WEEK_TIME_TextField=new JTextField(10);
		SAT_TIME_TextField=new JTextField(10);
		HOLI_TIME_TextField=new JTextField(10);
		NUMBER_TextField=new JTextField(10);
		CITY_TextField=new JTextField(10);
		CSVNAME_TextField=new JTextField(10);
		
		Load=new CButton();
		Modify=new CButton();
		Write=new CButton();
		Create=new CButton();
		Delete=new CButton();
		Upload=new CButton();
		List_data=new CButton();
		
		
		displayPanel.setLayout(new GridLayout(7,3));
		
		displayPanel.add(new JLabel("진료소명"));
		displayPanel.add(NAME_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("지역구"));
		displayPanel.add(DISTRICT_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("위치"));
		displayPanel.add(LOCATION_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("평일 시간"));
		displayPanel.add(WEEK_TIME_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("토요일 시간"));
		displayPanel.add(SAT_TIME_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("공휴일 시간 "));
		displayPanel.add(HOLI_TIME_TextField);
		displayPanel.add(new JLabel(""));
		
		displayPanel.add(new JLabel("도시"));
		displayPanel.add(CITY_TextField);
		displayPanel.add(new JLabel(""));
		
		this.add(displayPanel,BorderLayout.CENTER);
		
		MyMouseListener mouse=new MyMouseListener();
		controlPanel.setLayout(new GridLayout(9,2,5,5));
		
		List_data.setText("데이터목록");
		
		List_data.addActionListener((evt)->{
			Load_data(evt);
		});
		
		List_data.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		List_data.addMouseListener(mouse);
		
		controlPanel.add(List_data);
		
		Load.setText("조회");
		
		Load.addActionListener((evt)->{
			Read_CSV(evt);	
		});
		
		Load.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Load.addMouseListener(mouse);
		
		controlPanel.add(Load);
		
		Modify.setText("수정");
		
		Modify.addActionListener((evt)->{
			Modify_CSV(evt);
		});
		
		Modify.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Modify.addMouseListener(mouse);
		
		controlPanel.add(Modify);
		
		Create.setText("생성");
		
		Create.addActionListener((evt)->{
			Create_CSV(evt);
		});
		
		Create.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Create.addMouseListener(mouse);
		
		controlPanel.add(Create);
		
		Delete.setText("제거");
		Delete.addActionListener((evt)->{
			Delete_CSV(evt);
		});
		
		Delete.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Delete.addMouseListener(mouse);
		
		controlPanel.add(Delete);
		
		controlPanel.add(new JLabel("CSV 파일명 입력"));
		controlPanel.add(CSVNAME_TextField);
		
		Write.setText("출력");
		
		Write.addActionListener((evt)->{
			Write_CSV(evt);
		});
		
		Write.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Write.addMouseListener(mouse);
		
		controlPanel.add(Write);
		
		this.add(controlPanel,BorderLayout.EAST);
		
		Upload.setText("CSV 불러오기");
		Upload.addActionListener((evt)->{
				Upload_CSV(evt);

		});
		
		Upload.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		
		Upload.addMouseListener(mouse);
		
		
		controlPanel.add(Upload);
		controlPanel.setBackground(new Color(178, 190, 195));
		displayPanel.setBackground(new Color(178, 190, 195));
		
		
		
	}
	

	
	

	
	public void Load_data(ActionEvent e) {
		new Clinic_list(stmt);
	}




	public void Read_CSV(ActionEvent e) {
		
		
		try {
			
			String sql="SELECT * FROM seoul.clinic where name ='"+NAME_TextField.getText()+"'";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			while(rs.next()){
				
				NAME_TextField.setText(rs.getString("name"));
				DISTRICT_TextField.setText(rs.getString("district"));
				LOCATION_TextField.setText(rs.getString("location"));
				WEEK_TIME_TextField.setText(rs.getString("week_time"));
				SAT_TIME_TextField.setText(rs.getString("sat_time"));
				HOLI_TIME_TextField.setText(rs.getString("holi_time"));
				NUMBER_TextField.setText(rs.getString("number"));
				CITY_TextField.setText(rs.getString("city"));
			}
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}
	}
	
	public void Modify_CSV(ActionEvent e) {
		
		try {
			
			String sql="UPDATE seoul.clinic SET district='"+
			DISTRICT_TextField.getText()+"',location='"+LOCATION_TextField.getText()+
			"',week_time='"+WEEK_TIME_TextField.getText()+"', sat_time='"+SAT_TIME_TextField.getText()+
			"',holi_time='"+HOLI_TIME_TextField.getText()+"',number='"+NUMBER_TextField.getText()+"',city='"+CITY_TextField+"' "
					+"WHERE ID='"+NAME_TextField.getText()+"'";
			
			stmt.executeUpdate(sql);
			
			
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
		
	}
	
	public void Create_CSV(ActionEvent e) {
		
		try {
			String sql="INSERT INTO seoul.clinic (name,district,location,week_time,sat_time,holi_time,number,city) VALUES ('"+
					NAME_TextField.getText()+"','"+DISTRICT_TextField.getText()+"','"+
					LOCATION_TextField.getText()+"','"+
					WEEK_TIME_TextField.getText()+"','"+
					SAT_TIME_TextField.getText()+"','"+
					HOLI_TIME_TextField.getText()+"','"+
					NUMBER_TextField.getText()+"','"+
					CITY_TextField.getText()+"')";
			
			stmt.executeUpdate(sql);
			
			
		}catch(Exception e1) {
			System.out.println(e1.getMessage());
		}
		
	}
	
	public void Delete_CSV(ActionEvent e) {
		
		try {
			String sql="DELETE FROM seoul.clinic WHERE name='"+NAME_TextField.getText()+"'";
		
			stmt.executeUpdate(sql);
		}
		catch(Exception e1){
			System.out.println(e1.getMessage());
		}
	}
	
	public void Write_CSV(ActionEvent e) {
		
		
		try{
			String sql="SELECT * FROM seoul.clinic";
			
			ResultSet rs=stmt.executeQuery(sql);
			
			BufferedWriter br=new BufferedWriter(new FileWriter("C:\\Users\\pc\\Desktop\\hyeon\\2학년 2학기\\고급객체지향프로그래밍\\"+CSVNAME_TextField.getText()+".csv"));
			
			br.write("name,district,location,week_time,sat_time,holi_time,number,city");
			
			while(rs.next()) {
				String name=rs.getString("name");
				String district=rs.getString("district");
				String location=rs.getString("location");
				String week_time=rs.getString("week_time");
				String sat_time=rs.getString("sat_time");
				String holi_time=rs.getString("holi_time");
				String number=rs.getString("number");
				String city=rs.getString("city");
			
				
				String line = String.format("%s,%s,%s,%s,%s,%s,%s,%s",name,district,location,week_time,
						sat_time,holi_time,number,city);
				
				br.newLine();
				br.write(line);
				
				
			}
			
			br.close();
			
			
		}catch(SQLException e1) {
			e1.printStackTrace();
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		
		
		
	}
	
	public void Upload_CSV(ActionEvent e){
		JFileChooser Chooser;
		Chooser=new JFileChooser();
		String[][] indata=new String[10000][8];
		
		FileNameExtensionFilter filter=new FileNameExtensionFilter(
				"CSV 파일 ","CSV");
		Chooser.setFileFilter(filter);
		
		int ret=Chooser.showOpenDialog(null);
		if(ret != JFileChooser.APPROVE_OPTION) {
			JOptionPane.showMessageDialog(null, "파일을 선택하지 않았습니다.");
			
			return;
		}
		
		String filePath=Chooser.getSelectedFile().getPath();
		
		try {
			String sql="DELETE FROM seoul.clinic";
			stmt.executeUpdate(sql);
		}		
		catch(Exception e1){
			System.out.println(e1.getMessage());
		}
		
		BufferedReader br=null;
		try {
			br=Files.newBufferedReader(Paths.get(filePath));
			
			String line="";
			int row=0;
			
			while((line=br.readLine())!=null){
				
				String[] token=line.split(",",-1);
				
				for(int i=0;i<8;i++) {
					indata[row][i]=token[i];
				}
				
				
				row++;
			} 
			
			if(indata.equals("name")) {
				
			}
			else {
			for(int i=1;i<row;i++) {
					String sql1="INSERT INTO seoul.Clinic (name,district,location,week_time,sat_time,holi_time,number,city) VALUES ('"+
							indata[i][0]+"','"+indata[i][1]+"','"+
							indata[i][2]+"','"+
							indata[i][3]+"','"+
							indata[i][4]+"','"+
							indata[i][5]+"','"+
							indata[i][6]+"','"+
							indata[i][7]+"')";
			
					stmt.executeUpdate(sql1);
				}
			}
			
			br.close();
		}catch(FileNotFoundException e1) {
			e1.printStackTrace();
		}catch(IOException e2) {
			e2.printStackTrace();
		}
		catch(Exception e3){
			System.out.println(e3.getMessage());
		}
	}

	class MyMouseListener implements MouseListener{

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==List_data) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Modify) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Write) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Load) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Create) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Delete) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
			else if(e.getSource()==Upload) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(210, 210, 210));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==List_data) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Modify) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Write) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Load) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Create) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Delete) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
			else if(e.getSource()==Upload) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(238, 238, 238));
			}
		}

		@Override
		public void mouseClicked(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

	}
	
	
}

public class Csv extends JPanel {
	
	Statement stmt;
	
	private JButton OpenInfector;
	private JButton OpenClinic;
	
	public Csv(Statement stmt) {
		
		this.stmt=stmt;
		
		JPanel MainContainer=new JPanel();
	
		OpenInfector = new CButton("확진자 데이터");
		OpenClinic=new CButton("진료소 데이터");
	
		Listener Listen=new Listener();
		
		OpenInfector.addActionListener(Listen);
		OpenInfector.setPreferredSize(new Dimension(200, 70));
		OpenInfector.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		OpenInfector.setBackground(new Color(255,255,255));
		OpenClinic.addActionListener(Listen);
		OpenClinic.setPreferredSize(new Dimension(200, 70));
		OpenClinic.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		OpenClinic.setBackground(new Color(255,255,255));
		
		MainContainer.add(OpenInfector);
		MainContainer.add(OpenClinic);
		
		MyMouseListener mouse=new MyMouseListener();
		OpenInfector.addMouseListener(mouse);
		OpenClinic.addMouseListener(mouse);
		
		this.setBackground(new Color(178, 190, 195));
		MainContainer.setBackground(new Color(178, 190, 195));
		
		new BorderLayout();
		
		this.add(MainContainer,BorderLayout.CENTER);
		
		
		
	}

	class MyMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==OpenInfector) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(181,198,215));
			}
			else if(e.getSource()==OpenClinic){
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(181,198,215));
			}

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==OpenInfector) {
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(255,255,255));
			}
			else if(e.getSource()==OpenClinic){
				CButton b=(CButton)e.getSource();
				b.setBackground(new Color(255,255,255));
			}


		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub
			
		}
	}
	

	
	class Listener implements ActionListener {
		
		public void actionPerformed(ActionEvent e) {
			switch(e.getActionCommand()) {
			case "확진자 데이터":
				new Infector(stmt);
				break;
			case "진료소 데이터":
				new Clinic(stmt);
				break;
			default :
				System.exit(0);
			}
		}
	}

}


