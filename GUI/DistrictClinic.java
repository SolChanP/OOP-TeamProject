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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI.Menu.MyMouseListener;


public class DistrictClinic extends JPanel {
	//DB쿼리 날리기 위한 Statement
	Statement stmt;
	//지역구 데이터 생성
	private String[] district = {"종로구", "중구", "용산구", "성동구", "광진구", "동대문구", "중랑구", "성북구", 
			"강북구", "도봉구", "노원구", "은평구", "서대문구", "마포구", "양천구", "강서구", "구로구",
			"금천구", "영등포구", "동작구", "관악구", "서초구", "강남구", "송파구", "강동구"};
	//지역구 버튼 패널
	private JPanel disMenu;
	//지역구 버튼
	private JButton disBtn[]=new CButton[25];
	//결과 스크롤 패널
	private JScrollPane disScr;
	private JTable scrTable;
	//버튼 리스너
	private MyListener listener;
	//스크롤 패널 데이터
	private String value[] = {"이름", "소속 구", "위치", "평일 운영시간", "토요일 운영시간", "일/공휴일 운영시간", "대표 전화번호"};
	private DefaultTableModel mod;
	
	public DistrictClinic(Statement stmt) {
		//DB쿼리 날리기 위한 Statement
		this.stmt = stmt;
	
		//지역구 별 패널 레이아웃 설정
		this.setLayout(new BorderLayout(5, 10));
		
		//버튼 리스너 생성
		listener = new MyListener();
		//버튼 패널 생성
		disMenu = new JPanel();
		disMenu.setLayout(new GridLayout(3, 10, 5, 5));
		
		MyMouseListener mouse=new MyMouseListener();
		
		for(int i=0;i<district.length;i++) {
			disBtn[i] = new CButton(district[i]);
			disBtn[i].setActionCommand(district[i]);
			disBtn[i].addMouseListener(mouse);
			disBtn[i].setFont(new Font("맑은 고딕",Font.PLAIN , 15));
			disBtn[i].addActionListener(listener);
			disMenu.add(disBtn[i]);	
		}
		
		
		
		//버튼 패널 색
		disMenu.setBackground(new Color(178, 190, 195));
		// 지역구 별 패널   
		setBackground(new Color(178, 190, 195));
		
		
		this.add(disMenu,BorderLayout.NORTH);
		
		
		//스크롤 리스트 패널 생성
			//스크롤 데이터 생성, 수정 불가
		mod = new DefaultTableModel(null, value) {
			private static final long serialVersionUID = 1L;//버전 검사 할 때 필요한 듯
			public boolean isCellEditable(int rowIndex, int mColIndex) {
		               return false;
		           }
		       };
		scrTable = new JTable(mod);
		
		disScr = new JScrollPane(scrTable);
			//스크롤 리스트 크지 지정
		disScr.setPreferredSize(new Dimension(200,200));
			// 테이블 Colum width 크기 조절
		scrTable.getColumn("이름").setPreferredWidth(45);
		scrTable.getColumn("소속 구").setPreferredWidth(5);
		scrTable.getColumn("위치").setPreferredWidth(100);
		scrTable.getColumn("평일 운영시간").setPreferredWidth(35);
		scrTable.getColumn("토요일 운영시간").setPreferredWidth(35);
		scrTable.getColumn("일/공휴일 운영시간").setPreferredWidth(35);
		scrTable.getColumn("대표 전화번호").setPreferredWidth(5);
		this.add(disScr, BorderLayout.CENTER);
	}
	//지역구 버튼별 리스너
	private class MyListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			String clicked = e.getActionCommand();
			for(int i = 0; i < 25; i++) {
				if(district[i].equals(clicked)) {
					//지역구 클릭 시 해당 지역구의 데이터 요청 후 셋업
					String sql = "SELECT * FROM seoul.clinic where district = '" + clicked +"' and city ='" + "서울'";
					try {
						DefaultTableModel m =
				                (DefaultTableModel)scrTable.getModel();
						m.setNumRows(0);
						ResultSet rs = stmt.executeQuery(sql);
						while(rs.next()) {
							m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
									, rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
							rs.getString("holi_time"), rs.getString("number") });
						}
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;	
				}
			}
		}
	}
	
	class MyMouseListener implements MouseListener{
		
		int i=0;
		CButton b;
		CButton c_b;
		CButton c_be;
		
		public void mouseClicked(MouseEvent e) {
			
			c_b=(CButton)e.getSource();
			
			if(i==0) {
				i=1;
				c_be=c_b;
			}
			
			if(c_be!=c_b&&i==1) {
				c_b.setBackground(new Color(178,178,178));
				c_be.setBackground(new Color(238,238,238));
			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			b=(CButton)e.getSource();
			b.setBackground(new Color(178,178,178));
			
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(c_b!=e.getSource()) {
				b=(CButton)e.getSource();
				b.setBackground(new Color(238,238,238));
			}else {
				c_be=c_b;
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
	
}
