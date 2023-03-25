package amazonguard;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/*
 * Created on 09/07/2004
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
public final class Enemy extends Sprite {
    private static final short VY = 1;
    private static final short VX = 1;
	protected static final short HEIGHT = 18;
    protected static final short WIDTH  = 18;
    private static final short MHEIGHT = HEIGHT / 2;
    private static final short MWIDTH = WIDTH / 2;
    
	protected static final short SHOT_HEIGHT = 13;
    protected static final short SHOT_WIDTH  = 4;
    private static final short SHOT_MHEIGHT = SHOT_HEIGHT / 2;
    private static final short SHOT_MWIDTH = SHOT_WIDTH / 2;

    private static final short BOSS_WEAPON = 3;
    private static final short SHIFTY = 7;
    private static final short SIZEY = 6;
    private static final short EXPLODE_FRAMES = 7; // nro de frames - 1;
    private static final short ENEMY = 0;
    private static final short BULLET = 1;
    private short explosion;
    private short type;
    private short alvox;
    protected static short vy;
    protected boolean live;
    
	/**
	 * @param arg0
	 */
	public Enemy(Image img, int desafio) {
		super(img, WIDTH, HEIGHT);
		this.defineReferencePixel(MWIDTH, MHEIGHT);
		this.setTransform(Sprite.TRANS_ROT180);
		this.defineCollisionRectangle(0, SHIFTY, WIDTH, SIZEY);
		vy = (short) (VY + desafio);
		type = ENEMY;
		explosion = 0;
		live = true;
	}

	public Enemy(Image img, int desafio, int px, int py, int ax) {
		super(img, SHOT_WIDTH, SHOT_HEIGHT);
		this.defineReferencePixel(SHOT_MWIDTH, SHOT_MHEIGHT);
		this.setPosition(px, py);
		this.setFrame(BOSS_WEAPON);
		vy = (short) (VY + desafio);
		type = BULLET;
		alvox = (short)ax;
		live = true;
	}
	
	protected void tick(){
		this.move(0, vy);
		if (type != ENEMY)
			if (alvox > this.getX())
				this.move(VX, 0);
			else
				if (alvox < this.getX())
					this.move(-VX, 0);

	}
	
	protected void setDead(Image img, int w, int h){
		this.setImage(img, w, h);
		setExplosion();
		live = false;
	}
	
	protected boolean setExplosion(){
		if (type != ENEMY) return false;
		if (explosion == EXPLODE_FRAMES) return false;
		this.setFrame(explosion);
		explosion++;
		return true;
	}
} 
