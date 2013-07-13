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
	
	/* Jacked from http://slick.javaunlimited.net/viewtopic.php?t=3778 */
	
	//Wraps the given string into a list of split lines based on the width
    private List<String> wrap(String text, Font font, int width) {
        //A less accurate but more efficient wrap would be to specify the max 
        //number of columns (e.g. using the width of the 'M' character or something). 
        //The below method will look nicer in the end, though.
        
        List<String> list = new ArrayList<String>();
        String str = text;
        String line = "";
        
        //we will go through adding characters, once we hit the max width
        //we will either split the line at the last space OR split the line
        //at the given char if no last space exists
        
        //while we still have text to check
        int i = 0;
        int lastSpace = -1;
        while (i<str.length()) {
            char c = str.charAt(i);
            if (Character.isWhitespace(c))
                lastSpace = i;
            
            //time to wrap 
            if (c=='\n' || font.getWidth(line + c) > width) {
                //if we've hit a space recently, use that
                int split = lastSpace!=-1 ? lastSpace : i;
                int splitTrimmed = split;
                
                //if we are splitting by space, trim it off for the start of the
                //next line
                if (lastSpace!=-1 && split<str.length()-1) {
                   splitTrimmed++;
                }
                
                line = str.substring(0, split);
                str = str.substring(splitTrimmed);
                
                //add the line and reset our values
                list.add(line);
                line = "";
                i = 0;
                lastSpace = -1;
            } 
            //not time to wrap, just keep moving along
            else {
                line += c;
                i++;
            }
        }
        if (str.length()!=0)
            list.add(str);
        return list;
    }
}
