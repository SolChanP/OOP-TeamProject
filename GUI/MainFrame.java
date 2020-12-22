package GUI;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


public class MainFrame extends JFrame {
	private JPanel card;//메뉴별 패널 공간, CardLayout
	private CardLayout cardLayout;
	//메인 프레임 생성자
	
	public int csv_on=0;
	public int dc_on=0;
	public int di_on=0;
	public int tc_on=0;
	public int ti_on=0;
	
	
	JPanel back=new JPanel() {
		ImageIcon icon=new ImageIcon("/Users/sol/Desktop/무제 폴더/COVID19/mainimg.png");
		Image img=icon.getImage();
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(img, 0,0, this);
		}
	};
	
	public MainFrame(Statement stmt) {
		// 타이틀 설정
		super("메인 화면");
		// menu 액션 클릭 리스너
		MyListener listener = new MyListener();
		
		//카드레이아웃 생성 및 메뉴별 패널 세팅
		cardLayout = new CardLayout(10,10);
		card = new JPanel();
		card.setLayout(cardLayout);
		//card.setSize(new Dimension(800, 900));
		card.setPreferredSize(new Dimension(820,420));
		//메뉴별 패널 등록
		//인트로 패널 객체 등록!, (패널, CardLayout 구분 문구) 
		card.add(back,"back");
		// 날짜별 확진자 패널 객체 등록! (패널, CardLayout 구분 문구) 
		card.add(new TimeInfection(stmt),"TimeInfection");
		// 지역구별 확진자 패널 객체 등록! (패널, CardLayout 구분 문구) 
		card.add(new DistrictInfection(stmt),"disInfection");
		// 지역구별 선별진료소 패널 객체 등록! (패널, CardLayout 구분 문구) 
		card.add(new DistrictClinic(stmt), "disClinic"); 
		// 운영시간대별 선별진료소 패널 객체 등록! (패널, CardLayout 구분 문구) 
		card.add(new TimeClinic(stmt),"TimeClinic");
		// CSV관리 패널 객체 등록! (패널, CardLayout 구분 문구) 
		card.add(new Csv(stmt),"CSV");
		
		card.setBackground(new Color (99, 110, 114));
		
		// 메인 프레임 세팅
		// 메인 프레임 레이아웃 설정
		getContentPane().setLayout(new FlowLayout());
		//메인 메뉴 객체 등록
		Menu menu = new Menu(listener);
		getContentPane().add(menu);
		//메뉴별 패널 CardLayout 등록
		getContentPane().add(card);

		// 프레임 기본 세팅, 프레임 사이즈 조절 금지!
		this.getContentPane().setBackground(new Color(52, 73, 94));
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(900, 600);
		this.setVisible(true);
	}

	// menu 클릭 이벤트 처리
	class MyListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (e.getActionCommand()) {
			case "날짜별\n확진자": // 날짜별 확진자 메뉴 클릭시
				if(ti_on==0) {
					ti_on=1;
					tc_on=0;
					dc_on=0;
					di_on=0;
					csv_on=0;
					cardLayout.show(card, "TimeInfection");
					break;
				}
				else {
					ti_on=0;
					cardLayout.show(card,"back");
					break;
				}
			case "지역구별\n확진자":// 지역구별 확진자 메뉴 클릭시
				if(di_on==0) {
					ti_on=0;
					tc_on=0;
					dc_on=0;
					di_on=1;
					csv_on=0;
					cardLayout.show(card, "disInfection");
					break;
				}
				else {
					di_on=0;
					cardLayout.show(card, "back");
					break;
				}
			case "지역구별\n선별진료소":// 지역구별 선별진료소 메뉴 클릭시
				if(dc_on==0) {
					ti_on=0;
					tc_on=0;
					dc_on=1;
					di_on=0;
					csv_on=0;
					cardLayout.show(card, "disClinic");
					break;
				}
				else {
					dc_on=0;
					cardLayout.show(card,"back");
					break;
				}
			case "운영시간대별\n선별진료소":// 운영시간대별 선별진료소 메뉴 클릭시
				if(tc_on==0) {
					ti_on=0;
					tc_on=1;
					dc_on=0;
					di_on=0;
					csv_on=0;
					cardLayout.show(card, "TimeClinic");
					break;
				}
				else {
					tc_on=0;
					cardLayout.show(card, "back");
					break;
				}
			case "CSV":// CSV메뉴 클릭시
				if(csv_on==0) {
					ti_on=0;
					tc_on=0;
					dc_on=0;
					di_on=0;
					csv_on=1;
					cardLayout.show(card, "CSV");
					break;
				}
				else {
					csv_on=0;
					cardLayout.show(card, "back");
					break;
				}
			default:
				System.exit(0);
			}
		}
	}

}
