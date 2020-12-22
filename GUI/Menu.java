package GUI;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Menu extends JPanel {
	
	JButton Btn[]=new RButton[6];
	
	Color[] color = {new Color(252, 92, 101), new Color(253, 150, 68), new Color(254, 211, 48), new Color(38, 222, 129), new Color(43, 203, 186), new Color(69, 170, 242)};
	
	public Menu(ActionListener listener) {
		//지역구 별 패널 레이아웃 설정
		String[] menu = {"날짜별\n확진자", "지역구별\n확진자", "지역구별\n선별진료소",
				"운영시간대별\n선별진료소", "CSV", "종료하기"};
		MyMouseListener mouse=new MyMouseListener();
		
		for(int i=0;i<menu.length;i++) {
			Btn[i] = new RButton(menu[i]);
			Btn[i].addActionListener(listener);
			Btn[i].addMouseListener(mouse);
			Btn[i].setFont(new Font("맑은 고딕", Font.BOLD, 20));
			Btn[i].setBackground(color[i]);
			Btn[i].setPreferredSize(new Dimension(130, 100));
			this.add(Btn[i]);	
		}

		this.setBackground(new Color(52, 73, 94));
	}
	
	class MyMouseListener implements MouseListener{
		public void mouseClicked(MouseEvent e) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==Btn[0]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(new Color(152,3,11));
			}
			else if(e.getSource()==Btn[1]){
				RButton b=(RButton)e.getSource();
				b.setBackground(new Color(183,84,2));
			}
			else if(e.getSource()==Btn[2]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(new Color(186,149,1));
			}
			else if(e.getSource()==Btn[3]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(new Color(17,115,66));
			}
			else if(e.getSource()==Btn[4]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(new Color(24,114,105));
			}
			else if(e.getSource()==Btn[5]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(new Color(10,81,133));
			}
		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub
			if(e.getSource()==Btn[0]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(color[0]);
			}
			else if(e.getSource()==Btn[1]){
				RButton b=(RButton)e.getSource();
				b.setBackground(color[1]);
			}
			else if(e.getSource()==Btn[2]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(color[2]);
			}
			else if(e.getSource()==Btn[3]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(color[3]);
			}
			else if(e.getSource()==Btn[4]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(color[4]);
			}
			else if(e.getSource()==Btn[5]) {
				RButton b=(RButton)e.getSource();
				b.setBackground(color[5]);
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
