package amazonguard;
import javax.microedition.lcdui.Canvas;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;

/*
 * Created on 17/05/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
/**
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class SplashScreen extends Canvas implements Runnable {
	private final AmazonGuardMIDlet agm;
	private Image buffer;
	private volatile boolean dismissed = false;
	private final Graphics graphics;
	private final int height;
	private final int mheight;
	private final int mwidth;
	private Image splashImage;
	
	private final int width;
	
	public SplashScreen(AmazonGuardMIDlet agm){
		this.agm = agm;
		setFullScreenMode(true);
		width  = getWidth();
		height = getHeight();
		mwidth = width / 2;
		mheight = height / 2;
		splashImage = agm.createImage("res/splash.png");
		buffer = Image.createImage(width, height);
		graphics = buffer.getGraphics();
		new Thread(this).start();
    }

	private final synchronized void dismiss(){
		if (!dismissed){
			dismissed = true;
			agm.splashScreenDone();
		}
	}	

	public final void keyPressed(int keyCode){
		dismiss();
	}

	public final void paint(Graphics g){
		String str = new String(agm.dictionary.getString(Dictionary.LABEL_ERROR_LOAD));
		
        Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_SMALL);
        graphics.setFont(font);
		
		graphics.setColor(0x00FFFFFF);  // white
		graphics.fillRect(0, 0, width, height);

		graphics.setColor(0x0000FF00);  // green
		graphics.drawRect(0, 0, width-1, height-1);  // green border one pixel from edge

		if (splashImage != null){
			graphics.drawImage(splashImage, mwidth, mheight, Graphics.VCENTER | Graphics.HCENTER);
			splashImage = null;
		}
		else{
			graphics.drawString(str, mwidth, mheight, Graphics.BASELINE | Graphics.HCENTER);
		}
		if (buffer != null){
			g.drawImage(buffer, mwidth, mheight, Graphics.VCENTER | Graphics.HCENTER);
			buffer = null;
		}
		System.gc();
		agm.splashScreenPainted();
	}
    
	public final void run(){
		synchronized(this){
			try{
				wait(5000L);   // 5 seconds
			}
			catch (InterruptedException e){
				// can't happen in MIDP: no Thread.interrupt method
			}
		}
		dismiss();
	}
}
