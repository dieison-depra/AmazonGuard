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
public final class Boss extends Sprite {
	/**
	 * @param arg0
	 */
	private final short GAME_WIDTH;
	private final short GAME_HEIGHT;
	private final int GAME_MWIDTH;
	private final int GAME_MHEIGHT;

	protected static final short SHIFTY = 26; // inicio em Y do retangulo para detecção de colisão.
	private static final short SIZEY  = 7; // tamanho em Y do retangulo para detecção de colisão.
	private static final int LIFE = 100;
    private static final short LEFT_RIGHT = 1;
    private static final short RIGHT_LEFT = -1;
    private static final short UP_DOWN = 1;
    private static final short DOWN_UP = -1;
    
	protected static final short HEIGHT = 39;
    protected static final short WIDTH  = 40;
    protected static final short MHEIGHT = HEIGHT / 2;
    protected static final short MWIDTH = WIDTH / 2;
    
    private short shiftX;
    private short shiftY;
	protected static final short WEAPON = 3;	//arma do chefão...
	protected int life;
	protected int full_life;

	public Boss(Image img, GameManager gm) {
		super(img, WIDTH, HEIGHT);
		GAME_HEIGHT = (short)gm.getHeightGame();
		GAME_WIDTH = (short)gm.getWidthGame();
		GAME_MHEIGHT = GAME_HEIGHT / 2;
		GAME_MWIDTH = GAME_WIDTH / 2;
	}
	
	// inicialização do sprite e das váriáveis de controle do chefão..
	protected final void init(short level){
		this.setPosition((GAME_MWIDTH - MWIDTH), 0);
		this.defineReferencePixel(MWIDTH, MHEIGHT);
		this.defineCollisionRectangle(0, SHIFTY, WIDTH, SIZEY);
		this.setFrame(0);
		full_life = life = LIFE * (level + 1);
		shiftX = LEFT_RIGHT;
		shiftY = UP_DOWN;
	}
	
	protected final int getLife(){
		return ((life * 100) / full_life);
	}
	
	// Controla a movimentanção do chefão...
	protected final void tick(){
		this.move(shiftX, shiftY); // deslocamento em X e Y
		
		// testa limite da direita...
		if ((this.getX() + WIDTH) > GAME_WIDTH){
			shiftX = RIGHT_LEFT;
			this.move(shiftX, 0);
		}
		else{
			// testa limite da esquerda....
			if (this.getX() < 0){
				shiftX = LEFT_RIGHT;
				this.move(shiftX, 0);
			}
		}
		
		// testa limite superior...
		if (this.getY() < 0){
			shiftY = UP_DOWN;
			this.move(0, shiftY);
		}
		else{
			// testa limite inferior....
			if ((this.getY() + HEIGHT) > GAME_MHEIGHT){
				shiftY = DOWN_UP;
				this.move(0, shiftY);
			}
		}
	}
}
