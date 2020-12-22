package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class DistrictInfection extends JPanel {	
	
	Statement stmt;
	//콤보박스에 들어갈 구를 배열에 저장
	String seoul[] = {"강남구", "강동구", "강북구", "강서구", "관악구", "광진구", "구로구", "금천구", "노원구", "도봉구", "동대문구", 
			"동작구", "마포구", "서대문구", "서초구", "성동구", "성북구", "송파구", "양천구", "영등포구", "용산구", "은평구", "중랑구",
			"종로구", "중구", "중랑구"};
	
	//콤보박스 생성
	private JComboBox<String> disCombo;	
	//콤보박스 패널
	private JPanel disMenu;	
	//차트 패널
	private JScrollPane disChart;
	
	private DrawingGraph drawingGraph;
		
	public DistrictInfection(Statement stmt) {
		this.stmt = stmt;
		this.setLayout(new BorderLayout());
		
		drawingGraph = new DrawingGraph();	
		
		disCombo = new JComboBox<String>(seoul);
	    
		//버튼 패널 생성
		disMenu = new JPanel();	

		disMenu.add(disCombo, BorderLayout.CENTER);	
		disMenu.setBackground(new Color(178, 190, 195));
		
		disChart = new JScrollPane(drawingGraph);	
				
		this.add(disMenu, BorderLayout.NORTH);
		this.add(disChart, BorderLayout.CENTER);
		this.setBackground(new Color(178, 190, 195));
	    	    	    	    		
	//콤보박스 클릭 시 반응하는 리스너
	//클릭 시 데이터베이스에서 총 확진자, 격리자, 완치자, 사망자 값의 통계를 내고 drawingGraph함수로 값을 넘겨줌
		disCombo.addActionListener(new ActionListener() {			    	
			@Override
			public void actionPerformed(ActionEvent e) {
				String click = (String) disCombo.getSelectedItem();
				int total = 1;
  	  			int patient = 1;
  	  			int care = 1;
  	  			int dead = 1;
  	  			for(int i = 0; i < seoul.length; i++) {
  	  				if(seoul[i].equals(click)) {
  	  					String sql = "SELECT * FROM seoul.person WHERE REGION = '" + seoul[i] +"'";
  	  					ResultSet rs;
  	  					try {
  	  						rs = stmt.executeQuery(sql);
  	  						while(rs.next()) {
  	  							if(rs.getString("STATUS").equals(""))
  	  								patient++;
  	  							if(rs.getString("STATUS").equals("퇴원"))
  	  								care++;
  	  							if(rs.getString("STATUS").equals("사망"))
  	  								dead++;
  	  							total++;
  	  						}
  	  					} catch (SQLException e1) {
  	  						// TODO Auto-generated catch block
  	  						e1.printStackTrace();
  	  					}
	  	  		
  	  				}
  	  			}
  	  			drawingGraph.setConfirm(total, patient, care, dead);
  	  			drawingGraph.repaint();
			}
		});	
	}
	
    //그래프를 그리는 함수
    private class DrawingGraph extends JComponent {
    	
      //확진자, 격리자, 완치자, 사망자 변수
    	int confirm, patients, cure, dead;
    
    	public void paint(Graphics g){
    		 g.clearRect(0,0,getWidth(),getHeight());	  
    		 g.drawLine(180,250,600,250);	        
    		 for(int cnt = 1 ; cnt<11; cnt++) {
    	            g.drawString(cnt * 50 + "", 155, 255-20 * cnt);
    	            g.drawLine(180, 250-20 * cnt, 600, 250-20 * cnt);
    	        }
    		 g.drawLine(180,20,180,250);       
    		 g.drawString("총 확진자",225,280);
    	     g.drawString("격리",340,280);
    	     g.drawString("완치",440,280);
    	     g.drawString("사망",540,280);
    	     g.setColor(Color.RED);
    	        	      
    	     if (confirm>0) {
    	            g.fillRect(240, 250-(confirm*2/5), 20, confirm*2/5);
    	        }
    	        if(patients>0) {
    	            g.fillRect(340, 250-patients*2/5, 20, patients*2/5);
    	        }
    	        if(cure>0) {
    	            g.fillRect(440,250-cure*2/5, 20, cure*2/5);
    	        }
    	        if(dead>0) {
    	            g.fillRect(540,250-dead*2/5, 20, dead*2/5);
    	        }   	      
    	}

    	void setConfirm(int confirm, int patients, int cure, int dead) {
    	     this.confirm = confirm;
    	     this.patients=patients;
    	     this.cure=cure;
    	     this.dead=dead;
        }   	    
    }    
    
    
    
}