package GUI;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;


import javax.swing.JButton;

public class RButton extends JButton {
	public RButton() { super(); decorate(); } 
	public RButton(String text) { super(text); decorate(); } 
	protected void decorate() { setBorderPainted(false); setOpaque(false); }

	@Override
	protected void paintComponent(Graphics g) {
		int width = getWidth(); 
		int height = getHeight(); 
		Graphics2D graphics = (Graphics2D) g; 
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON); 
		graphics.setColor(getBackground());
		graphics.fillRoundRect(0, 0, width, height, 50, 50); 
				
		FontMetrics fontMetrics = graphics.getFontMetrics(); 
		Rectangle stringBounds;
		graphics.setColor(getForeground());
		graphics.setFont(getFont()); 
		int textX ;
		int textY;
		int i=0;
		if(getText().length() > 4) {
			for (String line : getText().split("\n")) {
				stringBounds = fontMetrics.getStringBounds(line, graphics).getBounds();
				textX = (width - stringBounds.width) / 2; 
				textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
				graphics.drawString(line, textX, textY-8+i);
				i=20;
		       }
		} else {
			for (String line : getText().split("\n")) {
				stringBounds = fontMetrics.getStringBounds(line, graphics).getBounds();
				textX = (width - stringBounds.width) / 2;
				textY = (height - stringBounds.height) / 2 + fontMetrics.getAscent(); 
				graphics.drawString(line, textX, textY);
				}
		}
		graphics.dispose(); 
		super.paintComponent(g);
	}
	


}
