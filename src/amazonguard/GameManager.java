/*
 * Created on 31/05/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package amazonguard;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;
import java.util.Vector;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Font;
import javax.microedition.lcdui.Graphics;
import javax.microedition.lcdui.Image;
import javax.microedition.lcdui.game.GameCanvas;
import javax.microedition.lcdui.game.LayerManager;
import javax.microedition.lcdui.game.Sprite;
import javax.microedition.lcdui.game.TiledLayer;
import javax.microedition.media.Manager;
import javax.microedition.media.MediaException;
import javax.microedition.media.Player;

/**
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class GameManager extends GameCanvas implements CommandListener, Runnable{
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	
    private static final short MILLIS_PER_TICK = 83;	// fps(20) => 1000 / 50
	//private static final short TIMER = MILLIS_PER_TICK * 10; // timer para chamar o gc... 
    private static final short TILE_WIDTH = 16; 		// largura de cada telha
    private static final short TILE_HEIGHT = 16; 		// altura de cada telha
    private static final short WIDTH_IN_TILES = 14; 	// largura do jogo em telhas...
    private static final short HEIGHT_IN_TILES = 21; 	// altura do jogo em telhas...
    
    // altura e largura do mapa do jogo em pixels
    private static final short GAME_WIDTH = TILE_WIDTH * WIDTH_IN_TILES;  
    private static final short GAME_HEIGHT = TILE_HEIGHT * HEIGHT_IN_TILES;
    private final short WIDTH;			// largura da área de pintura...
    private final short HEIGHT;			// altura da área de pintura...
    private final short GAME_DX; 		// quando a tela for mais larga que o jogo...
    private final short GAME_DY; 		// quando a tela for mais comprida que o jogo...
    private final short STR_DY = 2; 	//deslocamente de texto padão em Y
    private final short STR_DX; 		//deslocamente de texto padão em X
    private final short ROW_PER_SCREEN; //linhas de telhas na tela..
    
    // variáveis para exibir o texto na tela utilizando o método drawChars
    private static final short MAX_LIFE_VALUE = 99;
    private static final short MAX_SCORE_VALUE = 9999;
    private static short old_score; // nro de enemy killed no ultima troca....
    private static short old_life; // nro de enemy killed no ultima troca....
    
    private static final String strLife = "X";
    private final String strScore;
    private static StringBuffer bufScore;
    private static StringBuffer bufLife;
    private static char[] charScore;
    private static char[] charLife;
    private static int sizeScore;
    private static int sizeLife;
    private static final int textTopLeft = Graphics.TOP|Graphics.LEFT; 
    private static final int textTopRight = Graphics.TOP|Graphics.RIGHT;
    
    // mapa da fase 1
	private static final int[][] GAME_MAP =
//			  1  2  3  4  5  6  7  8  9  0  1  2  3  4
		{	{ 5, 5, 5, 5, 5, 5, 5, 5, 5, 4, 8, 7, 5, 5}, //l01
			{ 5, 5, 5, 2, 5, 5, 5, 5, 2, 6, 8, 3, 5, 5}, //l02
			{ 5, 5, 6, 7, 5, 5, 5, 4, 8, 8, 7, 5, 5, 5}, //l03
			{ 5, 4, 8, 3, 5, 5, 5, 4, 8, 8, 3, 5, 5, 5}, //l04
			{ 5, 6, 7, 5, 5, 5, 2, 6, 8, 7, 5, 5, 5, 5}, //l05
			{ 4, 8, 3, 5, 5, 4, 8, 8, 7, 5, 5, 5, 5, 5}, //l06
			{ 6, 7, 5, 5, 5, 5, 4, 8, 3, 5, 5, 5, 5, 5}, //l07
			{ 7, 5, 5, 5, 2, 2, 6, 7, 5, 5, 5, 5, 5, 5}, //l08
			{ 5, 5, 2, 6, 8, 8, 8, 8, 3, 5, 5, 5, 5, 5}, //l09
			{ 5, 4, 8, 8, 8, 7, 1, 1, 5, 5, 5, 5, 5, 5}, //l10
			{ 2, 6, 8, 7, 1, 5, 2, 5, 5, 5, 5, 5, 5, 2}, //l11
			{ 8, 8, 8, 3, 5, 6, 8, 3, 5, 5, 5, 5, 6, 8}, //l12
			{ 8, 7, 1, 5, 6, 8, 8, 7, 5, 5, 2, 6, 8, 8}, //l13	
			{ 7, 5, 5, 5, 1, 1, 1, 2, 2, 6, 8, 8, 8, 7}, //l14	
			{ 5, 5, 5, 5, 5, 5, 6, 8, 8, 8, 7, 1, 1, 6}, //l15	
			{ 5, 5, 5, 5, 2, 6, 8, 8, 7, 1, 5, 4, 8, 8}, //l16	
			{ 5, 5, 5, 4, 8, 8, 8, 7, 5, 5, 5, 6, 8, 7}, //l17	
			{ 5, 5, 2, 6, 8, 7, 1, 5, 5, 5, 6, 8, 8, 3}, //l18	
			{ 2, 6, 8, 8, 8, 3, 5, 5, 5, 5, 1, 1, 1, 5}, //l19	
			{ 8, 8, 8, 7, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5}, //l20
			{ 7, 1, 1, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5}  //l21
		};
     
	protected short challenge;
	private final AmazonGuardMIDlet agm;
    private final TiledLayer background;
    private final Image jogadorImage;
	private final Jogador jogador;
	
	private static boolean explosion;
	private static short explosion_frame;
	
	private final Boss bossSprite;
	private final Image bossImage;

	private Sprite bonusLifeSprite; 	//sprite de vida bonus...
	private Sprite bonusWeaponSprite;	//sprite de arma bonus...
	
	private Shots weaponSprite;
	private final Image weaponsImage;
	private static final short WEAPON_BULLET	= 0;		//arma mais simples
	private static final short WEAPON_MISSILE	= 1;		//arma intermeiária 
	private static final short WEAPON_LASER	= 2;		//arma mais poderosa..
	private static final short DOWN_UP = -1;
	private static final short UP_DOWN = 1;
	
	private Enemy enemySprite;
	private final Image enemyImage;
    private static final short MAX_ENEMY = 3; //numero máximo de enimigos ativos...

	protected final Sprite explosionSprite;
	private final Image explosionImage;
	
	private static final short EXPLODE_HEIGHT = 26;
    private static final short EXPLODE_WIDTH  = 20;
    protected static final short EXPLODE_FRAMES = 7; // nro de frames - 1;
    private static final short EXPLODE_MHEIGHT = EXPLODE_HEIGHT / 2;
    private static final short EXPLODE_MWIDTH  = EXPLODE_WIDTH / 2;
    
    private static final short FACE_WIDTH = 13;
    private static final short FACE_HEIGHT = 18;
	private final Image playerImage;
	private final Sprite playerSprite;
	private final Command backCommand;
	
	private final Graphics graphics;
    private final Vector vshots = new Vector(32);
    private final Vector venemies = new Vector(32);
    
    private final LayerManager layerManager;
    private static final Random random = new Random();
    private final Player shotSoundPlayer;
    
    private static final short GAME_OVER     = 0;
    private static final short GAME_RUNNING  = 1;
    private static final short GAME_BOSS     = 2;
    private static final short GAME_FINISHED = 3;
    private static short game_state;
    
    private static int backgroundScroll;
    private static int row_in_map;
    private static int desafio;
    private static boolean centered;
    private static boolean show_message_final;
    private static boolean boss_active;
    private static boolean life_bonus;
    private static int life_bonus_rotate;
    private static boolean weapon_bonus;
    private static int weapon_bonus_rotate;
    
    private static final short TICK_BACKGROUND_IN_FRAMES = 2;
    private static final short TICK_ENEMY_IN_FRAMES = 1;
    private static final short MIN_ENEMY_BOSS = 130;	//minimo de inimigos mortos antes do chefão este nro é multiplicado pelo grau de dificuldade + 1
    private static final short MAX_ENEMY_LOST = 40;	//maximo de inimigos perdidos para game over...
	private static final short ENEMY_FOR_LIFE = 30;	//nro de inimigos consecutivos para gerar uma vida bonus...
	private static final short ENEMY_FOR_WEAPON = 10;	//nro de inimigos consecutivos para gerar uma nova arma...
	private static short enemy_sequential; 			//nro de inimigos destroidos desde de o ultimo que escapou.... 
    
    private static int last_enemy_create; // nro de pixel desde o ultimo inimigo criado...
    private static short tick_background;
    private static short tick_enemy;
    private static short enemy_killed;
    private static short enemy_lost;
    private static int layer_insert; //posição onde deve ser inseridos os elemetos móveis...
    
    private static final Font font = Font.getFont(Font.FACE_SYSTEM, Font.STYLE_BOLD, Font.SIZE_MEDIUM);    
    private volatile Thread animationThread = null;
    private static boolean useViewWindow = false;
    private final int wViewWindow;
    private final int hViewWindow;
   	protected static final int xViewWindow = 0;
    protected static final int yViewWindow = 0;
    
	public GameManager(AmazonGuardMIDlet agm){
		super(true);
		this.agm = agm;
		setFullScreenMode(true);
		
		//experimental...
		sizeChanged(getWidth(), getHeight());
		showNotify();
		//final do experimento...
		
		graphics = getGraphics();
		graphics.setFont(font);
		WIDTH = (short)getWidth();	
		HEIGHT = (short)getHeight();
		
		// Use a view window, unless the world
        // fits inside the canvas.
		if (GAME_WIDTH > WIDTH){
			useViewWindow = true;
			wViewWindow = WIDTH;
			GAME_DX = 0;
		}
		else{
			GAME_DX = (short)((WIDTH - GAME_WIDTH) / 2);
			wViewWindow = GAME_WIDTH;
		}
		if (GAME_HEIGHT > HEIGHT){
			useViewWindow = true;
			hViewWindow = HEIGHT;
			GAME_DY = 0;
		}
		else{
			GAME_DY = (short)((HEIGHT - GAME_HEIGHT) / 2);
			hViewWindow = GAME_HEIGHT;
		}
		ROW_PER_SCREEN = (short)((hViewWindow / TILE_HEIGHT) + 1);

		//cria o tiledlayer para o plano de fundo (background)....
		background = new TiledLayer(WIDTH_IN_TILES,
                                    HEIGHT_IN_TILES,
                                    this.agm.createImage("res/background.png"),
                                    TILE_WIDTH,
                                    TILE_HEIGHT);

		/*
		 * Cria o sprite que mostra as vidas do player
		 * e cria o sprite para vidas bonus do player.
		 * Define o pixel central do sprite de vidas bonus como 
		 * pixel de referência para o mesmo...
		 */
		playerImage = this.agm.createImage("res/face.png");
		playerSprite = new Sprite(playerImage, FACE_WIDTH, FACE_HEIGHT);
		STR_DX = (short)(playerImage.getWidth() + 2);
		bonusLifeSprite = new Sprite(playerSprite);
		bonusLifeSprite.defineReferencePixel(FACE_WIDTH / 2, FACE_HEIGHT / 2);
	
		//cria a imagem e o sprite de jogador...
		jogadorImage = this.agm.createImage("res/player.png");
        jogador = new Jogador(jogadorImage, this);
		
        //cria a imagem e o sprite do chefão de fase...
		bossImage = this.agm.createImage("res/boss.png");
        bossSprite = new Boss(bossImage, this);
        
        // cria a imagem para criação dos inimigos da fase...
		enemyImage = this.agm.createImage("res/enemy.png");
		
		/*
		 * Cria a imagem para criação dos tiros.
		 * Cria o sprite para a troca de armas.
		 * Define a posição central do sprite como pixels
		 * de referência para o mesmo... 
		 */
		weaponsImage = this.agm.createImage("res/weapons.png");
		bonusWeaponSprite = new Sprite(weaponsImage, Shots.WIDTH, Shots.HEIGHT);
		bonusWeaponSprite.defineReferencePixel(Shots.MWIDTH, Shots.MHEIGHT);
		
		// cria a imagem e o sprite para as explosões...
		explosionImage = this.agm.createImage("res/explosion.png");
        explosionSprite = new Sprite(explosionImage, EXPLODE_WIDTH, EXPLODE_HEIGHT);

		layerManager = new LayerManager();
		if (useViewWindow){
			layerManager.setViewWindow(xViewWindow,
                                       yViewWindow,
                                       wViewWindow,
                                       hViewWindow);
		}
		
		// cria as instância de texto para ser exibido na tela...
		strScore = agm.dictionary.getString(Dictionary.TEXT_GAME_SCORE);
		sizeScore = strScore.length();
		sizeLife = strLife.length();
		
		bufScore = new StringBuffer("0000");
		bufLife  = new StringBuffer("00");
		bufScore.insert(0, strScore);
		bufLife.insert(0, strLife);
		
		charScore = new char[bufScore.length()];
		charLife = new char[bufLife.length()];
		
		bufScore.getChars(0, bufScore.length(), charScore, 0);
		bufLife.getChars(0, bufLife.length(), charLife, 0);
		
        shotSoundPlayer = createSoundPlayer("res/shot.wav", "audio/x-wav");
        backCommand = new Command(agm.dictionary.getString(Dictionary.LABEL_BACK), Command.BACK, 1);
        this.addCommand(backCommand);
		this.setCommandListener(this);
	}
	
    public final void commandAction(Command c, Displayable d){
    	boolean game_active;
    	if (game_state == GAME_OVER || game_state == GAME_FINISHED)
    		game_active = false;
    	else
    		game_active = true;
		if (c == backCommand){
			stop();
			agm.gameManagerMenu(game_active);
		}
	}
    
    /*
     * Responsável pela inicialização do jogo.
     * Limpa os vetores de inimigos que podem conter alguma sujeira de jogos anteriores.
     * Limpa todos os elementos do layermanager que pode conter alguma sujeira.
     * Inicializa todas as váriaveis de controle do jogo.
     * Adiciona o player e o background ao gerenciador de camadas...
     */
	protected final void init(short challenge){
        // reinit level
        int idx=0;
        fillBackgroundMap();
        backgroundScroll = 1 - TILE_HEIGHT;
        background.setPosition(0, backgroundScroll);
        
        // resetando o vetor de tiros disparados...
		for (idx = 0; idx < vshots.size(); idx++){
			weaponSprite = (Shots)(vshots.elementAt(idx));
			idx = removeWeapon(idx);
		}
		
		// resetando o vetor de inimigos ativos... 
		for (idx = 0; idx < venemies.size(); idx++){
			enemySprite = (Enemy)(venemies.elementAt(idx));
			idx = removeEnemy(idx);
		}
		
		// eliminado todos as camadas existentes no controlador de camadas...
		for (idx = 0; idx < layerManager.getSize(); idx++){
			layerManager.remove(layerManager.getLayerAt(idx));
			idx--;
		}

        // inicializa os elementos do vetor de score...
		for (idx = sizeScore; idx < charScore.length; idx++) charScore[idx] = ' '; 
		
        // inicializa os elementos do vetor de life...
		for (idx = sizeLife; idx < charLife.length; idx++) charLife[idx] = ' '; 
		
		// garantido que os vectores de inimigos e tiros está limpo...
		vshots.removeAllElements();
		venemies.removeAllElements();

		// configurando a visibilidade dos sprites fixos do jogo...
		explosionSprite.setVisible(false);
		bossSprite.setVisible(true);
		jogador.setVisible(true);
		bonusLifeSprite.setVisible(false);
		bonusWeaponSprite.setVisible(false);
		
	 	// Inserindo o sprite com a face do player no gerenciador de camadas...
		layerManager.append(this.playerSprite);

		// Inserindo o player o background no gerenciador de camadas..
		layerManager.append(jogador);
		layer_insert = layerManager.getSize(); 
        layerManager.append(background);
		jogador.setImage(jogadorImage, Jogador.WIDTH, Jogador.HEIGHT);
		jogador.init();
		bossSprite.init(challenge); //inicializa o chefão...
	
		// Inicializando variáveis globais da classe...
    	tick_background = TICK_BACKGROUND_IN_FRAMES;
    	tick_enemy = TICK_ENEMY_IN_FRAMES;
		
		last_enemy_create = 0;
		explosion_frame = 0;
		enemy_lost = 0;
		enemy_killed = 0;
		old_life = old_score = -1;
		enemy_sequential = 0;
		desafio = 1 + challenge;
		game_state = GAME_RUNNING;
		
		explosion	= false;
		centered 	= false;
		boss_active = false;
		life_bonus 	= false;
		weapon_bonus = false;
		show_message_final = false;
		
		System.gc();
	}
	
	/*
	 * Preenche o área visivel do tiledLayer com o conto inferior
	 * esquerdo do mapa lógico do jogo...
	*/
	private final void fillBackgroundMap(){
		int row = 0;
		row_in_map = (HEIGHT_IN_TILES - (ROW_PER_SCREEN + 1));
		if (row_in_map < 0) row_in_map = 0;
		for (int i=row_in_map; i < HEIGHT_IN_TILES; i++){
			for (int col=0; col < WIDTH_IN_TILES; col++)
				background.setCell(col, row, GAME_MAP[i][col]);
			row++;
		}
		row_in_map = HEIGHT_IN_TILES - 1;
	}

	public final synchronized void start(){
		animationThread = new Thread(this);
		animationThread.start();
	}

	public final synchronized void stop(){
		animationThread = null;
	}
	
	public final void run(){
		long timeTaken = 0;
		long startTime = 0;
		Thread currentThread = Thread.currentThread();
		//try{
			// This ends when animationThread is set to null, or when
			// it is subsequently set to a new thread; either way, the
			// current thread should terminate
			while (currentThread == animationThread){
				startTime = System.currentTimeMillis();
				// Don't advance game or draw if canvas is covered by
				// a system screen.
				if (isShown()){
					tick();
					draw();
					flushGraphics();
				}
				for (;;){
					timeTaken = System.currentTimeMillis() - startTime;
					if (timeTaken >= MILLIS_PER_TICK) break;
					synchronized (this){
						currentThread.yield();
					}
				}
				/*
				timeTaken = System.currentTimeMillis() - startTime;
				if (timeTaken < MILLIS_PER_TICK){
					synchronized (this){
						wait(MILLIS_PER_TICK - timeTaken);
					}
				}
				else{
					currentThread.yield();
				}
				*/
			}
		//}
		/*
		catch (InterruptedException ie){
			ie.printStackTrace();
        }
        */
    }

	protected final int getWidthGame(){
		return (wViewWindow);
	}
	
	protected final int getHeightGame(){
		return (hViewWindow);
	}
	
	protected final int getLimitX(int red){
		return (wViewWindow - red);
	}
	
	protected final int getLimitY(int red){
		return (hViewWindow - red);
	}

	/*
	 * O metódo tick implementa toda a lógica do jogo que
	 * deve ser executada a cada quadro.
	 * Neste metódo encontra-se todos os testes de colisão,
	 * os efeitos do jogo, a movimentação dos personagens 
	 * além das rotinas de IA.
	 */
	private final void tick(){
		int idxe = 0; // index for enemies
		int idxs = 0; // index for shots..
		boolean collide = false;
		boolean shifted = false;
		int key = this.getKeyStates();
		 
		// movimenta os inimigos a cada TICK_ENEMY_IN_FRAMES por segundos
		if (tick_enemy == TICK_ENEMY_IN_FRAMES){
			shifted = true;
			tick_enemy = 0;
		}
		tick_enemy++;
		
		// verifica se o chefão não está em processo de explosão...
		if (explosion && boss_active)
			if (explosion_frame >= 0){
				explosionSprite.setFrame((EXPLODE_FRAMES - explosion_frame));
				explosion_frame--;		
			}
			else{
				explosion = false;
				explosionSprite.setVisible(false);
				layerManager.remove(explosionSprite);
			}
		else
		// executa o processo de movimentação do player..
			jogador.tick(key);
		
		if (game_state == GAME_FINISHED || game_state == GAME_OVER) return;

		//movimenta o background
		scrollBackground(); 
	
		// se o chefão estiver ativo dispara o movimento dele....
		if (boss_active) bossSprite.tick();
		
		// verifica se existe alguma vida bonus ativa para controlar...
		if (life_bonus){
			bonusLifeSprite.move(0, 1);
			switch  (life_bonus_rotate){
				case Sprite.TRANS_ROT90: life_bonus_rotate = Sprite.TRANS_ROT180; break;
				case Sprite.TRANS_ROT180: life_bonus_rotate = Sprite.TRANS_ROT270; break;
				case Sprite.TRANS_ROT270: life_bonus_rotate = Sprite.TRANS_NONE; break;
				default: life_bonus_rotate = Sprite.TRANS_ROT90; break;
			}
			bonusLifeSprite.setTransform(Sprite.TRANS_NONE);
			bonusLifeSprite.setTransform(life_bonus_rotate);
			if (bonusLifeSprite.getY() > hViewWindow){
				layerManager.remove(bonusLifeSprite);
				bonusLifeSprite.setVisible(false);
				life_bonus = false;
			}
			else
				if ((!jogador.explosion) && jogador.collidesWith(bonusLifeSprite, false)){
					layerManager.remove(bonusLifeSprite);
					bonusLifeSprite.setVisible(false);
					life_bonus = false;
					jogador.life++;
				}
		}

		// verifica se existe alguma arma bonus ativa para controlar...
		if (weapon_bonus){
			bonusWeaponSprite.move(0, 1);
			switch  (weapon_bonus_rotate){
				case Sprite.TRANS_ROT90: weapon_bonus_rotate = Sprite.TRANS_ROT180; break;
				case Sprite.TRANS_ROT180: weapon_bonus_rotate = Sprite.TRANS_ROT270; break;
				case Sprite.TRANS_ROT270: weapon_bonus_rotate = Sprite.TRANS_NONE; break;
				default: weapon_bonus_rotate = Sprite.TRANS_ROT90; break;
			}
			bonusWeaponSprite.setTransform(Sprite.TRANS_NONE);
			bonusWeaponSprite.setTransform(weapon_bonus_rotate);
			if (bonusWeaponSprite.getY() > hViewWindow){
				layerManager.remove(bonusWeaponSprite);
				bonusWeaponSprite.setVisible(false);
				weapon_bonus = false;
			}
			else
				if ((!jogador.explosion) && jogador.collidesWith(bonusWeaponSprite, false)){
					layerManager.remove(bonusWeaponSprite);
					bonusWeaponSprite.setVisible(false);
					weapon_bonus = false;
					if (jogador.power < WEAPON_LASER)
						jogador.power++;
				}
		}
		
		/* analiza cada um dos inimigos para verifcar se:
		 * colidiram com os limites da tela;
		 * colidiram com o jogador;
		 * ou colidiram contra um tiro disparado.
		 * tb faz a movimentação do inimigo....
		*/
		for (idxe=0; idxe < venemies.size(); idxe++){
			collide = false;
			enemySprite = (Enemy) venemies.elementAt(idxe);
			
			if (enemySprite.live){
				if (shifted)
					enemySprite.tick();
				
				if (enemySprite.getY() > hViewWindow){
					idxe = removeEnemy(idxe);
					if (!boss_active)
						enemy_lost += desafio; 
					collide = true;
					enemy_sequential = 0; 
				}
				else
//					// primeiro verifica se os retangualos colidem
					if ((!jogador.explosion) && jogador.collidesWith(enemySprite, true)){		 
						fireExplosionPlayer();
						idxe = removeEnemy(idxe);
						collide = true;
					}
					else{
						for (idxs=0; idxs<vshots.size(); idxs++){
							weaponSprite = (Shots) vshots.elementAt(idxs);
							if (enemySprite.collidesWith(weaponSprite, false)){
								idxs = removeWeapon(idxs);
								enemySprite.setDead(this.explosionImage, EXPLODE_WIDTH, EXPLODE_HEIGHT);
								collide = true;
								enemy_killed += desafio;
								enemy_sequential++;
								break;
							}
						}
					}
			}
			else
				if (!enemySprite.setExplosion())
					idxe = removeEnemy(idxe);			
		}
		
		/*
		 * Analiza colisação do player com o chefão....
		 */
		if ((boss_active) && (!explosion)) 
			if ((!jogador.explosion) && jogador.collidesWith(bossSprite, true))
				fireExplosionPlayer();
		
		/*
		 * Analiza cada um dos tiros ativos, fazendo:
		 * verificação dos limites da tela
		 * movimentação do tiro...
		*/
		for (idxs=0; idxs < vshots.size(); idxs++){
			weaponSprite = (Shots) vshots.elementAt(idxs);
			if (!weaponSprite.tick(hViewWindow)) 
				idxs = removeWeapon(idxs);
			else
				if (boss_active) 
					if (weaponSprite.collidesWith(bossSprite, false)){
						enemy_killed += desafio;
						bossSprite.life -= weaponSprite.POWER;
						idxs = removeWeapon(idxs);
						enemy_sequential++; 
					}
		}
		
		// verifica se o chefão não foi morto....
		if (bossSprite.life <= 0 && boss_active){
			game_state = GAME_FINISHED;
			fireExplosionBoss();
			return;
		}
		
		// gera novos inimigos de acordo com o estágio do jogo...
		generateEnemies();
		
		/*
		 * Verifca e atualiza o status do jogo.
		 * Verifica as vidas do player chegaram a zero.
		 * Verifica se o player não deixou escaram mais inimigos que o permito.
		 * Se o chefão ainda não estiver ativo verifca se o usuário já 
		 * destruiu o numero minimo de inimigos para enfrentar o chefão...
		 */
		if (jogador.life == 0){
			game_state = GAME_OVER;
			return;
		}
		if (enemy_lost >= (MAX_ENEMY_LOST * desafio)){
			game_state = GAME_OVER;
			return;
		}
		if (!boss_active)
			if (enemy_killed >= (MIN_ENEMY_BOSS * desafio)){
				game_state = GAME_BOSS;
				layerManager.insert(bossSprite, layer_insert);
				boss_active = true;
				agm.fireFlashBackLight();
			}

		/*
		 * Caso não tenha nenhuma arma bonus ativa e o jogador
		 * ainda não esteja com a arma máxima, verifica o jogador
		 * já eleminou o nro de inimigos minimos para sortear outra
		 * arma bonus...
		 */
		if (!weapon_bonus && jogador.power < WEAPON_LASER)	
			if (enemy_sequential >= ENEMY_FOR_WEAPON){
				bonusWeaponSprite.setPosition(random(hViewWindow - bonusWeaponSprite.getWidth()), 0);
				bonusWeaponSprite.setFrame(jogador.power + 1);
				bonusWeaponSprite.setVisible(true);
				layerManager.insert(bonusWeaponSprite, layer_insert);
				weapon_bonus = true;
				enemy_sequential = 0;
			}
			
		/*
		 * Caso não tenha nenhuma vida bonus ativa verifica se o jogador
		 * já eleminou o nro de inimigos minimos para sortear outra
		 * vida bonus...
		 */
		if (!life_bonus)	
			if (enemy_sequential >= ENEMY_FOR_LIFE){
				bonusLifeSprite.setPosition(random(hViewWindow  - bonusLifeSprite.getWidth()), 0);
				bonusLifeSprite.setVisible(true);
				layerManager.insert(bonusLifeSprite, layer_insert);
				life_bonus = true;
				enemy_sequential = 0;
			}
		if (jogador.life > MAX_LIFE_VALUE) jogador.life = MAX_LIFE_VALUE;
		if (enemy_killed > MAX_SCORE_VALUE) enemy_killed = MAX_SCORE_VALUE;
	}
	
	/*
	 * Verifica o numéro de inimigos atualmente ativos e compara
	 * com o número maximo de inimigos de acordo com o grau de
	 * dificuldade selecionado.
	 * Quando tem menos inimigos ativos que o numero de inimigos
	 * maximo o jogo entra num estado de sorteio de inimigos
	 * e cada batido do relógio (tique-taque) um novo inimigos e
	 * lançado, até que o numero de inimigos fica igual ao total.
	 */
	private final void generateEnemies(){
		int posy = -Enemy.HEIGHT;
		int posx = 0;
		int EXTRA = boss_active ? MAX_ENEMY:0; 
		
		if (last_enemy_create <= 0){
			if (venemies.size() < ((MAX_ENEMY * desafio) + EXTRA)){
				if (!boss_active){
					posx = random(wViewWindow);
					if ((posx + Enemy.WIDTH) > wViewWindow) posx -= Enemy.WIDTH;
					enemySprite = new Enemy(enemyImage, desafio);
					enemySprite.setPosition(posx, posy);
			
					venemies.addElement(enemySprite);
					layerManager.insert(enemySprite, layer_insert);			
				}
				else{
					enemySprite = new Enemy(weaponsImage, desafio, (bossSprite.getX() + random(bossSprite.getWidth())), bossSprite.getY() + Boss.SHIFTY, jogador.getX() + Jogador.WIDTH);
					venemies.addElement(enemySprite);
					layerManager.insert(enemySprite, layer_insert);
				}
				last_enemy_create = Enemy.HEIGHT - EXTRA;
			}
		}
		else{
			last_enemy_create -= (Enemy.vy);
		}
	}
	
	// dispara o processo de explosão do player qdo este colide com algum inimigo...
	private final void fireExplosionPlayer(){
		jogador.beginExplosion();
		layerManager.insert(explosionSprite, layer_insert);
		agm.fireVibrate();
		enemy_sequential = 0; 
	}
	
	protected final void finishExplosionPlayer(){
		explosionSprite.setVisible(false);
		layerManager.remove(explosionSprite);
	}
	
	// dispara o processo de explosão do boss qdo este é eliminado...
	private final void fireExplosionBoss(){
		bossSprite.setVisible(false);
		explosionSprite.setVisible(true);
		explosionSprite.setPosition(bossSprite.getX(), bossSprite.getY());
		layerManager.insert(explosionSprite, layer_insert);
		agm.fireVibrate();
		explosion_frame = EXPLODE_FRAMES;
		explosion = true;
	}
	
	/*
	 * Remove o inimigo indicado pelo indice idx do vector
	 * de inimigos e do layerManager.
	 * @return Retorna o indice passado decrementado de um.
	 */
	private final int removeEnemy(int idx){
		layerManager.remove(enemySprite);
		enemySprite = null;
		venemies.removeElementAt(idx);
		return (--idx);
	}

	/*
	 * Remove o tiro indicado pelo indice idx do vector
	 * de tiros e do layerManager.
	 * @return Retorna o indice passado decrementado de um.
	 */
	private final int removeWeapon(int idx){
		layerManager.remove(weaponSprite);
		weaponSprite = null;
		vshots.removeElementAt(idx);
		return (--idx);
	}

	/*
	 * Este método é responsável pela rolagem do background.
	 * A cada duas batidas do relógio (tique-taque) o background
	 * deslisa um pixel pra baixo, até atingir o numero de pixel 
	 * de uma telha, quando isto ocorre todo o mapa visual é
	 * deslocado uma linha de telhas pra cima no mapa lógico. 
	 * Quando atinge-se o topo do mapa lógico a variável de controle
	 * do mapa lógico recebe a última posição do mapa e processo 
	 * recomeça.
	 */
	private final void scrollBackground(){
		if (tick_background != TICK_BACKGROUND_IN_FRAMES){
			tick_background++;
			return;
		}
		tick_background = 0;
		
		backgroundScroll += 1;       // scrolling by 1 was too slow
		if (backgroundScroll > 0){
			int row = 0;
			int col = 0;
			int idx = 0;
			backgroundScroll = 1 - TILE_HEIGHT;
			if (row_in_map == 0) row_in_map = HEIGHT_IN_TILES;
			row_in_map--;
			for (row = 0; row <= ROW_PER_SCREEN; row++){
				if (row >= HEIGHT_IN_TILES) break; 
				idx = row_in_map - row;
				if (idx < 0) idx = HEIGHT_IN_TILES + idx;
				for (col = 0; col < WIDTH_IN_TILES; col++)
					background.setCell(col, ROW_PER_SCREEN - row, GAME_MAP[idx][col]);
			}
		}
		background.setPosition(0, backgroundScroll);
	}

	/*
	 * Este método é responsável pela pintura dos elementos visuais
	 * do jogo na tela do dispositivo. Entretanto, esta pintura não
	 * é realizada diretamente na tela do dispositivo, isto é feito 
	 * em um buffer "off-screen" que depois de todo pintado é descar-
	 * regado de uma só vez na tela do dispositivo pelo método 
	 * flushGraphics();
	 */
	private final void draw(){
	
		/* 
		 * Se a área da tela do dispositivo que estiver disponível 
		 * para pintura for maior que a representação visual do mapa
		 * lógico do jogo em qualquer um dos eixos, então o jogo deve
		 * ser centralizado na área central da tela do dispositivo.
		*/
		if (!centered){
			graphics.setClip(GAME_DX, GAME_DY, wViewWindow, hViewWindow);
			graphics.translate(GAME_DX, GAME_DY);
			centered = true;
		}

		/*
		 * O gerenciador de camada do jogo, chama o seu método de 
		 * pintura que é responsável pelo desenho de todos os elementos
		 * visuais a ele vinculados.
		 */
		if (game_state == GAME_OVER && (!jogador.explosion))
			drawGameOver();
		else
			if (game_state == GAME_FINISHED && (!explosion))
				drawGameWinner();
			else{
				// limpa toda área da tela, pintando com um retangulo branco.
				graphics.setColor(0x00888888);
				graphics.fillRect(0, 0, wViewWindow, hViewWindow);
		
				layerManager.paint(graphics, 0, 0);
				
				//exibe o numero de vidas e o placar..
				graphics.setColor(0xFFFFFF00);
				
				bufLife.delete(sizeLife, bufLife.length());
				bufLife.append((int)jogador.life);			
				bufLife.getChars(0, bufLife.length(), charLife, 0);
				graphics.drawChars(charLife, 0, charLife.length, STR_DX, STR_DY, textTopLeft);
				
				bufScore.delete(sizeScore, bufScore.length());
				bufScore.append((int)enemy_killed);
				bufScore.getChars(0, bufScore.length(), charScore, 0);
				graphics.drawChars(charScore, 0, charScore.length, wViewWindow, STR_DY, textTopRight);
				if (boss_active){
					graphics.drawRect(wViewWindow - 60, (STR_DY + font.getHeight()), 39, 3);
					graphics.setColor(0xFFFF0000);
					graphics.fillRect(wViewWindow - 59, (STR_DY + font.getHeight() + 1), ((this.bossSprite.getLife() * 38)/100), 2);
				}
			}
	}

	private final void drawGameOver(){
		if (show_message_final) return;
		String strg = new String(agm.dictionary.getString(Dictionary.TEXT_GAME_OVER));
		String stry = new String(agm.dictionary.getString(Dictionary.TEXT_GAME_YOU_LOST));
		int posx = (wViewWindow / 2);
		int posy = (hViewWindow / 2);
		int fheight = font.getHeight();
		posy -= fheight;
		
		graphics.setColor(0x00FFFFFF);
		graphics.drawString(strg, posx, posy, Graphics.TOP|Graphics.HCENTER);	
		posy += fheight;
		graphics.drawString(stry, posx, posy, Graphics.TOP|Graphics.HCENTER);
		agm.fireVibrate();
		agm.fireFlashBackLight();
		show_message_final = true;
	}
	
	private final void drawGameWinner(){
		if (show_message_final) return;
		String strg = new String(agm.dictionary.getString(Dictionary.TEXT_GAME_WINNER));
		String stry = new String(agm.dictionary.getString(Dictionary.TEXT_GAME_YOU_WON));
		int posx = (wViewWindow / 2);
		int posy = (hViewWindow / 2);
		int fheight = font.getHeight();
		posy -= fheight;
		
		graphics.setColor(0x00FFFFFF);
		graphics.drawString(strg, posx, posy, Graphics.TOP|Graphics.HCENTER);	
		posy += fheight;
		graphics.drawString(stry, posx, posy, Graphics.TOP|Graphics.HCENTER);	
		agm.fireVibrate();
		agm.fireFlashBackLight();
		show_message_final = true;
	}

	/*
	 * Este médodo realiza o sorteio aleatório de numero que 
	 * podem variar entre 0 e o size.
	 */
	private static final int random(int size){
		return ((random.nextInt() & 0x7FFFFFFF) % size);
	}
	
	private final Player createSoundPlayer(String filename, String format){
		Player p = null;
		try{
			InputStream is = getClass().getResourceAsStream("/amazonguard/" + filename);
			p = Manager.createPlayer(is, format);
			p.prefetch();
        }
        catch (IOException ioe){
			ioe.printStackTrace();
		}
		catch (MediaException me){
			me.printStackTrace();
		}
		return p;
	}

	protected final void startSound(Player p){
		if (!agm.useSound) return ; 
		if (p != null){
			try{
				p.stop();
				p.setMediaTime(0L);
				p.start();
			}
			catch (MediaException me){
				me.printStackTrace();
			}
		}
	}
	
	protected final void createShotPlayer(int posx, int posy, short type){
		weaponSprite = new Shots(weaponsImage, posx, posy, type);
		vshots.addElement(weaponSprite);
		layerManager.insert(weaponSprite, layer_insert);
		startSound(shotSoundPlayer);
	}
}