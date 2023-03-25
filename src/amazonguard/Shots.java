/*
 * Created on 16/06/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package amazonguard;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/**
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class Shots extends Sprite {
	/**
	 * @param arg0
	 */
	protected static final short HEIGHT = 13;
    protected static final short WIDTH  = 4;
    protected static final short MHEIGHT = HEIGHT / 2;
    protected static final short MWIDTH = WIDTH / 2;
    private static final short SPEED = -4;
    private static final short VX = 2;
    
    protected final short POWER;
    private final int VY;

	public Shots(Image img, int posx, int posy, short frame) {
		super(img, WIDTH, HEIGHT);	// cria o sprite...
		this.defineReferencePixel(MWIDTH, MHEIGHT); // coloca o pixel de referência no centro...
		this.setPosition((posx - MWIDTH), posy); // define a posição inicial do sprite...
		
		this.setFrame(frame);			// frame a ser mostrado de acordo com o tipo...
		this.POWER = (short)(1 + frame);	// tipo de tiro...
		VY = SPEED;	// deslocamento em Y de acordo com o sentido indicado....
	}
	
	/* Realiza a movimentação do sprite na tela de acordo com sua velocidade
	 * e sentido de deslocamento.
	 * Se o sprite sair do limite inferior ou superior da tela retorna falso,
	 * caso contrário o metódo retornar verdeiro indicando movimentação realizada. 
	*/
	protected final boolean tick(int limiteY){
		this.move(0, VY);		// movimenta o sprite em y....
		
		if ((this.getY() + this.getHeight()) < 0)
			return false;
		if (this.getY() > limiteY)
			return false;
			
		return true;
	}
}
