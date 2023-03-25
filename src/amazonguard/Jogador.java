package amazonguard;

import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.Sprite;

/*
 * Created on 31/05/2004
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
public final class Jogador extends Sprite {
	/**
	 * @param arg0
	 */
	
	private final GameManager gm;
    private static final short NUM_FRAMES = 3;
    private static final short VX = 5; // move two pixel per time in X
    private static final short VY = 2; // move one pixel per time in Y
    
	private boolean firing;
	private boolean visible;
	protected boolean explosion;
	private short explosion_frame;
	private short immunity;
	private final short IMMUNITY_TICKS = 20;
    protected short power;
    protected short life;
    
    protected static final short WIDTH = 20;
    protected static final short HEIGHT = 28;
    private static final short MWIDTH = WIDTH / 2;
    private static final short MHEIGHT = HEIGHT / 2;

	public Jogador(Image img, GameManager gm) {
		super(img, WIDTH, HEIGHT);
		this.gm = gm;
		init();
		// TODO Auto-generated constructor stub
	}
	
	protected final void init(){
		this.setPosition((gm.getWidthGame() / 2) - MWIDTH, gm.getHeightGame() - HEIGHT);
		this.defineReferencePixel(MWIDTH, MHEIGHT);
		this.setFrame(0);
		
		this.life = 3;
		this.power = 0;
		this.firing = false;
		this.explosion = false;
		this.explosion_frame = 0;
	}
	
	/* definir as ações do player de acordo com a entrada
	** do usuário e a lógica do jogo.
	*/
	protected final void tick(int keyStates){
		final int left 	= (keyStates & GameManager.LEFT_PRESSED);
		final int right = (keyStates & GameManager.RIGHT_PRESSED);
		final int up 	= (keyStates & GameManager.UP_PRESSED);
		final int down 	= (keyStates & GameManager.DOWN_PRESSED);
		final int fire	= (keyStates & GameManager.FIRE_PRESSED);
	
		// verifica se o player não está em processo de explosão...
		if (explosion){
			if (explosion_frame >= 0){
				gm.explosionSprite.setFrame((GameManager.EXPLODE_FRAMES - explosion_frame));
				explosion_frame--;		
			}
			else{
				if (immunity > 0 && life > 0){
					if (immunity == IMMUNITY_TICKS)
						gm.finishExplosionPlayer();
					if (visible)
						visible = false;
					else
						visible = true;
					this.setVisible(visible);
					immunity--;
				}
				else{
					explosion = false;
					this.setVisible(true);
				}
			}
			if (explosion_frame > 0)
				return;
		}
		/* controla os movimentos na horizontal...
		 * this.setFrame(?) desabilitado pq não tenho
		 * imagens na diagonal para o player....
		 */
		if ((left != 0) && (right == 0)){ //movimento pra esquerda...
			//this.setFrame(1);
			if (this.getX() > GameManager.xViewWindow) 
				this.move(-VX, 0);
		}
		else 
			if ((right != 0) && (left == 0)){ //movimento pra direita...
				//this.setFrame(2);
				if (this.getX() < (gm.getLimitX(WIDTH + 1)))
					this.move(VX, 0);
			}
			//else
			//	this.setFrame(0);

		// controla os movimentos na vertical...
		if ((up != 0) && (down == 0)){ // movimento pra cima
			if (this.getY() > GameManager.yViewWindow)
				this.move(0, -VY);
		}
		else 
			if ((down != 0) && (up == 0)) //movimento pra baixo...
				if (this.getY() < (gm.getLimitY(HEIGHT + 1)))
					this.move(0, VY);
		
		// controla os disparos do jogador...
		// implement a toggle, so fire only happens once per click
		// (will therefore not register very rapid multiple-clicks)
		//if ((keyStates & GameManager.FIRE_PRESSED) != 0){
		if (fire != 0){
			if (!firing){
				firing = true;
				gm.createShotPlayer(this.getX() + MWIDTH, this.getY(), power);
			}
			else
				firing = false;
		}
		else
			firing = false;
	}
	
	protected final void beginExplosion(){
		gm.explosionSprite.setPosition(this.getX(), this.getY());
		gm.explosionSprite.setVisible(true);
	
		visible = false;
		this.setVisible(visible);
		immunity = IMMUNITY_TICKS;
		explosion = true;
		explosion_frame = GameManager.EXPLODE_FRAMES;
		if (life > 0) life--;
		if (power > 0)power--;
	}
}