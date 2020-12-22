package GUI;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import GUI.DistrictClinic.MyMouseListener;


public class TimeClinic extends JPanel{
   Statement stmt;
   //시간 데이터 생성
   private String[] district = {"00:00", "01:00", "02:00", "03:00", "04:00", "05:00", "06:00", 
         "07:00", "08:00", "09:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00",
         "16:00", "17:00", "18:00", "19:00", "20:00", "21:00", "22:00", "23:00", "24:00"};
   //시간 버튼 패널
   private JPanel disMenu;
   //시간 버튼
   private JButton disBtn[]=new CButton[25];
   //결과 스크롤 패널
   private JScrollPane disScr;
   private JTable scrTable;
   //버튼 리스너
   private MyListener listener;
   //스크롤 패널 데이터
   private String value[] = {"이름", "소속 구", "위치", "평일 운영시간", "토요일 운영시간", "일/공휴일 운영시간", "대표 전화번호"};
   private DefaultTableModel mod;
   
   String btNames[] = {"평일", "토요일", "일/공휴일"};
   
   JRadioButton rb1, rb2, rb3;
   ButtonGroup rbg;
   
   private JPanel p;
   
   public TimeClinic(Statement stmt) {
      //평일, 토요일, 공휴일 라디오 버튼 설정
      rb1 = new JRadioButton("평일");
      rb2 = new JRadioButton("토요일");
      rb3 = new JRadioButton("일/공휴일");
      
      ButtonGroup group=new ButtonGroup();
      group.add(rb1);
      group.add(rb2);
      group.add(rb3);
      
      rbg = new ButtonGroup();
      
      //rbg.add(rb1);
      //rbg.add(rb2);
      //rbg.add(rb3);
      
      p = new JPanel();
      p.setLayout(new FlowLayout());
      
      p.add(rb1);
      p.add(rb2);
      p.add(rb3);
  
      
      //DB쿼리 날리기 위한 Statement
      this.stmt = stmt;
   
      //시간 별 패널 레이아웃 설정
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
		
      this.add(disMenu, BorderLayout.CENTER);   
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
      disScr.setPreferredSize(new Dimension(200,250));
         // 테이블 Colum width 크기 조절
      scrTable.getColumn("이름").setPreferredWidth(45);
      scrTable.getColumn("소속 구").setPreferredWidth(5);
      scrTable.getColumn("위치").setPreferredWidth(100);
      scrTable.getColumn("평일 운영시간").setPreferredWidth(35);
      scrTable.getColumn("토요일 운영시간").setPreferredWidth(35);
      scrTable.getColumn("일/공휴일 운영시간").setPreferredWidth(35);
      scrTable.getColumn("대표 전화번호").setPreferredWidth(5);
      this.add(disScr, BorderLayout.SOUTH);
      
      //라디오버튼 패널을 추가
      this.add(p, BorderLayout.NORTH);
      
      setBackground(new Color(178, 190, 195));
      p.setBackground(new Color(178, 190, 195));
      disMenu.setBackground(new Color(178, 190, 195));
      
      
   }
   
   //시간 버튼별 리스너
   private class MyListener implements ActionListener{

      @Override
      public void actionPerformed(ActionEvent e) {
         String clicked = e.getActionCommand();
         for(int i = 0; i < 25; i++) {
            if(district[i].equals(clicked)) {
               //시간 클릭 시 해당 지역구의 데이터 요청 후 셋업
               String sql = "SELECT * FROM seoul.clinic";
              // System.out.println(sql);
               try {
                  DefaultTableModel m =
                            (DefaultTableModel)scrTable.getModel();
                  m.setNumRows(0);
                  ResultSet rs = stmt.executeQuery(sql);
                  while(rs.next()) {
                     if(rb1.isSelected()) {
//                        System.out.println("rb1");
                        //"미운영" String 데이터 오류 방지 try-catch문 사용
                        //라디오버튼을 통해 평일,토요일,공휴일 별 출력을 다르게 시행
                        try {
                        	
                        	if(rs.getString("week_time").equals("미운영")||rs.getString("week_time").equals("")) {
                        		
                        	}
                        	else{
                           // 데이터 시작시간
                        		int dbSt = Integer.parseInt(rs.getString("week_time").charAt(0) +"" +rs.getString("week_time").charAt(1));
                           	// 클릭 시간               
                        		int inputData = Integer.parseInt((clicked.charAt(0) + "" + clicked.charAt(1)));
                           	//데이터 종료시간
                        		int dbEnd = Integer.parseInt(rs.getString("week_time").charAt(8) +"" +rs.getString("week_time").charAt(9));
                           
                        		if((dbSt < inputData && dbEnd > inputData)||(dbSt==inputData)||(dbEnd==inputData)) {
                        			m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
                        					, rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
                                    rs.getString("holi_time"), rs.getString("number") });
                        		}
                        		else {
                        			continue;
                           		}
                        	}
                        
                        }catch(Exception k){
                           m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
                                 , rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
                           rs.getString("holi_time"), rs.getString("number") });
                        }
                     }else if(rb2.isSelected()) {
//                        System.out.println("rb2");
                        try {
                        	if(rs.getString("sat_time").equals("미운영")||rs.getString("sat_time").equals("")) {
                        		
                        	}
                        	else {
                           // 데이터 시작시간
                        		int dbSt = Integer.parseInt(rs.getString("sat_time").charAt(0) +"" +rs.getString("sat_time").charAt(1));
                           // 클릭 시간               
                        		int inputData = Integer.parseInt((clicked.charAt(0) + "" + clicked.charAt(1)));
                           //데이터 종료시간
                        		int dbEnd = Integer.parseInt(rs.getString("sat_time").charAt(8) +"" +rs.getString("sat_time").charAt(9));
                           
                        		if((dbSt < inputData && dbEnd > inputData)||(dbSt==inputData)||(dbEnd==inputData)) {
                        			m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
                            		  , rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
                                    rs.getString("holi_time"), rs.getString("number") });
                           	}
                           	else {
                        	   continue;
                           		}
                        	}
                        }catch(Exception k){
                           m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
                                 , rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
                           rs.getString("holi_time"), rs.getString("number") });
                        }
                     }else if(rb3.isSelected()) {
//                        System.out.println("rb3");
                        try {
                        	if(rs.getString("holi_time").equals("미운영")||rs.getString("holi_time").equals("")) {
                        		
                        	}
                        	else {
                           // 데이터 시작시간
                        		int dbSt = Integer.parseInt(rs.getString("holi_time").charAt(0) +"" +rs.getString("holi_time").charAt(1));
                           // 클릭 시간               
                        		int inputData = Integer.parseInt((clicked.charAt(0) + "" + clicked.charAt(1)));
                           //데이터 종료시간
                        		int dbEnd = Integer.parseInt(rs.getString("holi_time").charAt(8) +"" +rs.getString("holi_time").charAt(9));
                           
                        		if((dbSt < inputData && dbEnd > inputData)||(dbSt==inputData)||(dbEnd==inputData)) {
                        			m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
                        					, rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
                        					rs.getString("holi_time"), rs.getString("number") });
                        		}
                        		else {
                        			continue;
                        		}
                        	}
                           
                        }catch(Exception k){
                           m.addRow(new Object[]{rs.getString("name"), rs.getString("district")
                                 , rs.getString("location"), rs.getString("week_time"), rs.getString("sat_time"),
                           rs.getString("holi_time"), rs.getString("number") });
                        }
                     }
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