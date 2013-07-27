package scenes;

import java.util.ArrayList;
import java.util.List;

import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;

public class TextEvent {
	public Color color;
	public String text;
	public Shape textBox;
	public List<String> displayText;
	
	public TextEvent(String str) {
		color = new Color(200, 200, 200);
		text = str;
		textBox = new Rectangle(312, 284, 400, 200);
	}
	
	public void render (Graphics g) {
		//Wrap the string first
		int textPad = 10;
		
		displayText = wrap(text, g.getFont(), (int)textBox.getWidth()-2*textPad);

		
		//Draw transparent black overlay
		g.setColor(new Color(0, 0, 0, 200));
		g.fill(new Rectangle(0, 0, 1024, 768));
		//Draw Text Box
		g.setColor(color);
		g.draw(textBox);
		//Draw Text
		int lineHeight = g.getFont().getLineHeight();
		for (int i = 0; i < displayText.size(); i++) {
			g.drawString(displayText.get(i), textBox.getMinX()+textPad, textBox.getMinY()+textPad+lineHeight*i);
		}
	}
}
