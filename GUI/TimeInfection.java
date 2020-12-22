package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TimeInfection extends JPanel {
	Statement stmt;
	JButton btn;
	JComboBox<String> monthBox;
	JComboBox<String> weekBox;
	JLabel txt1;
	JLabel txt2;
	String month[] = {"1","2","3","4","5","6","7","8","9","10","11","12"};
	String week[] = {"1","2","3","4","5"};
	
	//일주일 날
	int number[] = {0,1,2,3,4,5,6};
	
	int week1[] = {1,2,3,4,5,6,7};
	int week2[] = {8,9,10,11,12,13,14};
	int week3[] = {15,16,17,18,19,20,21};
	int week4[] = {22,23,24,25,26,27,28};
	int week5[] = {29,30,31};
	
	
	public TimeInfection(Statement stmt) {
		
		
		this.stmt=stmt;	
		
		this.setLayout(new BorderLayout());
		
		JPanel j = new JPanel();
		drawGraph g = new drawGraph();
		
		txt1 = new JLabel("월");
		txt2 = new JLabel("주차");
		
		btn = new JButton("확인");
		monthBox = new JComboBox<String>(month);
		weekBox = new JComboBox<String>(week);
		
		j.setBackground(new Color(178, 190, 195));
		setBackground(new Color(178, 190, 195));
		
		j.add(monthBox);
		j.add(txt1);
		j.add(weekBox);
		j.add(txt2);
		j.add(btn);
		
		this.add(j,BorderLayout.NORTH);
		this.add(g,BorderLayout.CENTER);
		
		
		
		btn.addActionListener(new DrawActionListener(g));
		
	}
	
	void setDay(int[] number) {
		for(int i = 0; i < number.length; i++) {
			this.number[i]=number[i];
		}
	}
	
	
	public class drawGraph extends JPanel {
		public void paint(Graphics g) {
			String str = weekBox.getSelectedItem().toString();
			
			 g.clearRect(0,0,getWidth(),getHeight());
			 
			 //그래프 그리기
		        g.drawLine(90,250,750,250);
		        g.drawString("0", 75, 255);
		        
		        //그래프 배경 그리기 
		        for(int cnt = 1 ;cnt<11;cnt++){
		        	g.setColor(Color.black);
		            g.drawString(cnt*10 +"",65,255-20 * cnt);
		            g.drawLine(90, 250-20*cnt, 750, 250-20*cnt);
		        }
		        g.drawLine(90,20,90,250);
		        g.setColor(Color.BLUE);
		        
		     //날짜 입력
		        writeDate(g,str);
		        for(int i = 1; i<8; i++) {
		        //그래프그리기 x축, y축, 굵기, 높
		        	g.fillRect(i*100+7, 250-number[i-1]*2, 25, number[i-1]*2);
		        }
		        
		        resetDat();
		        
		}
		
		public void writeDate(Graphics g, String s) {
			int t=Integer.parseInt(s);
			if(t==1) {
				for(int i = 0; i<7; i++) {
	        	g.drawString( week1[i] + "일",(i+1)*100+10, 270);
				}
			}
			if(t==2) {
				for(int i = 0; i<7; i++) {
	        	g.drawString( week2[i] + "일",(i+1)*100+10, 270);
				}
			}
			if(t==3) {
				for(int i = 0; i<7; i++) {
	        	g.drawString( week3[i] + "일",(i+1)*100+10, 270);
				}
			}
			if(t==4) {
				for(int i = 0; i<7; i++) {
	        	g.drawString( week4[i] + "일",(i+1)*100+10, 270);
				}
			}
			if(t==5) {
				for(int i = 0; i<3; i++) {
	        	g.drawString( week5[i] + "일",(i+1)*100+10, 270);
				}
			}
			
		}
	}
	
	public void resetDat() {
		for(int i = 0; i < number.length; i++) {
			number[i]=0;
		}
	}
	
	
	public class DrawActionListener implements ActionListener{
		drawGraph dG;
		DrawActionListener(drawGraph dG){
			this.dG = dG;
		}
		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			String monthStr = monthBox.getSelectedItem().toString();
			String weekStr = weekBox.getSelectedItem().toString();
			String qu;
			//1월 and 10,11,12월구별하기 위해서 
			if(monthStr.contentEquals("1")) {
				qu = "SELECT * FROM seoul.person WHERE DATE LIKE '1.%'";
			}else {
				qu = "SELECT * FROM seoul.person WHERE DATE LIKE " + "'" + monthStr + "%'";
			}
				
				ResultSet rs;
				try {
					rs = stmt.executeQuery(qu);
					while(rs.next()) {
						//1주차 데이		
							String check = rs.getString("DATE").substring(2);
							String modi = check.replace(".", "");
							int t= Integer.parseInt(weekStr);
							setData(t, modi);
					
		    		}
				}catch(Exception e1) {
					
				}
			dG.repaint();
		}
		
		
		
		
		public void setData(int week, String s) {
			int d = Integer.parseInt(s);
			if(week==1) {
				for(int i=0; i<7; i++) {
					if(d==week1[i]) {
						number[i]++;
					}
				}	
			}
			if(week==2) {
				for(int i=0; i<7; i++) {
					if(d==week2[i]) {
						number[i]++;
					}
				}	
			}
			if(week==3) {
				for(int i=0; i<7; i++) {
					if(d==week3[i]) {
						number[i]++;
					}
				}	
			}
			if(week==4) {
				for(int i=0; i<7; i++) {
					if(d==week4[i]) {
						number[i]++;
					}
				}	
			}
			if(week==5) {
				for(int i=0; i<7; i++) {
					if(d==week5[i]) {
						number[i]++;
					}
				}	
			}
			
		}
		
	}

}




