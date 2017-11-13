package research.weblog;
import java.awt.*;

public class WindowTools
{		
	public static Point GetLocationCenter(int uw,int uh)
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension d = kit.getScreenSize();
		int screenWidth = d.width;
		int screenHeight = d.height;
		int inputscreenWidth = uw;
		int inputscreenHeight = uh;
		int l = (screenWidth - inputscreenWidth) / 2;
		int t = (screenHeight - inputscreenHeight) / 2;
		Point p = new Point();
		p.x = l;
		p.y = t;
		return p;
	}
	
	public static Dimension GetFullDimension()
	{
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension d = kit.getScreenSize();
		
		return d;
	}
	
}