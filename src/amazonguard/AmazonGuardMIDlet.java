// Imports para as instância do midlet...
package amazonguard;
import java.util.Random;

import javax.microedition.lcdui.Display;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Image;
import javax.microedition.midlet.MIDlet;
import javax.microedition.midlet.MIDletStateChangeException;

/**
 * @author Dieison
 * Created on 17/05/2004
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public class AmazonGuardMIDlet extends MIDlet implements Runnable{
	/**
	 * 
	 */
	protected Dictionary dictionary;
	private MainMenu mainMenu;
	private TextScreen textScreen;
	private GameManager gameManager;
	private Display display;
	private OptionMenu optionMenu;
	private OptionScreen optionScreen;
	private RecordManager recordManager;
    //private final Player shotSoundPlayer;

    private final short VIBRATION_MILLIS = 200;
    private final short BLACKLIGHT_MILLIS = 200;
	
    private boolean initDone = false;
    private static final String pacote = "/amazonguard/";
	private static final Random random = new Random();
	protected boolean useSound = false;
	private boolean useFlash = false;
	private boolean useVibration = false;
	protected final short MAINMENU = 0; 
	protected final short OPTIONMENU = 1;
   	
	public AmazonGuardMIDlet() {
		// TODO Auto-generated constructor stub
		if (display == null) 
			display = Display.getDisplay(this);
		if (dictionary == null)	
			dictionary	= new Dictionary(this.getAppProperty("MIDlet-Name"), System.getProperty("microedition.locale"));
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#startApp()
	 */
	protected void startApp() throws MIDletStateChangeException {
		// TODO Auto-generated method stub
		Displayable current = display.getCurrent();
		
		if (current == null){
			display.setCurrent(new SplashScreen(this));
        }
        else{
            display.setCurrent(current);

            // resume game after pauseApp
            gameManager.start();
        }
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#pauseApp()
	 */
	protected void pauseApp() {
		// TODO Auto-generated method stub
		
		Displayable current = display.getCurrent();
		if (current == gameManager){
			gameManager.stop();   // kill its animation thread
		}
		notifyPaused();
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.midlet.MIDlet#destroyApp(boolean)
	 */
	protected void destroyApp(boolean arg0) throws MIDletStateChangeException {
		// TODO Auto-generated method stub

		if (gameManager != null){
			gameManager.stop();   // kill its animation thread
		}
		System.gc();
	}
	
	public void run(){
		//display.callSerially(this);
		init();
	}
	
	/* (Javadoc)
	*	Logo abaixo aparecem alguns métodos genéricos que são 
	* utilizados por divesas classes e por conveniência foram
	* colocados aqui. 
	*/
	protected final Image createImage(String filename){
		Image image = null;
		try{
			image = Image.createImage((pacote + filename));
		}
		catch (java.io.IOException ioe){
			ioe.printStackTrace();
			// just let return value be null
		}
		return image;
	}
	
    // only the MIDlet has access to the display, so put this method here
	protected final void fireFlashBackLight(){
		if (!useFlash) return ; 
		display.flashBacklight(BLACKLIGHT_MILLIS);
	}

    // only the MIDlet has access to the display, so put this method here
	protected final void fireVibrate(){
		if (!useVibration) return ; 
    	display.vibrate(VIBRATION_MILLIS);
	}

	/* (Javadoc)
	*	Este método é um callback para ser usado pela classe
	* splashscreen, sua finalidade é de iniciar um thread
	* para realizar o carregamento das classes dicionario, 
	* mainMenu e gameManager em background, enquanto a tela 
	* de splash é exibida.         
	*/
	void splashScreenPainted(){
		new Thread(this).start();  // start background initialization
	}
	
	/* (Javadoc)
	*	Este método é um callback para ser usado pela classe
	* splashscreen, sua finalidade é de iniciar um thread
	* para realizar o carregamento das classes dicionario, 
	* mainMenu e gameManager em background, enquanto a tela 
	* de splash é exibida.         
	*/
	void splashScreenDone(){
		init();   // if not already done
		display.setCurrent(mainMenu);
	}
	
	/* (Javadoc)
	*	Este método é resposável pelo carregamento das 
	* instâncias das classes dicionario, mainMenu e gameManager em background, enquanto a tela 
	* de splash é exibida.         
	*/
	private synchronized void init(){
		if (!initDone){
			mainMenu 		= new MainMenu(this);
			gameManager 	= new GameManager(this);
			recordManager 	= new RecordManager(dictionary.getString(Dictionary.MENU_MIDLET_NAME));
			getEffects();
			initDone 		= true;
		}
	}
	
	void mainMenuContinue(){
		display.setCurrent(gameManager);
        gameManager.start();
	}

	void mainMenuNewGame(){
		getEffects();
		gameManager.init((short)getRecordPreferred(Dictionary.LABEL_LEVEL));
        mainMenu.setGameActive(true);
        display.setCurrent(gameManager);
        gameManager.start();
	}

	void mainMenuOptions(){
        // create and discard a new textScreen screen each time, to
        // avoid keeping heap memory for it when it's not in use
			if (optionMenu == null)
				optionMenu = new OptionMenu(this);
			display.setCurrent(optionMenu);
    }
	
	void optionMenu(short item){
		if (optionScreen != null){
			optionScreen = null;
			System.gc();
		}
		optionScreen = new OptionScreen(this, item);
		display.setCurrent(optionScreen);
	}
	void setRecordPreferred(short op, int selected){
		short rid = 0; // record identificador....
		
		if (op == Dictionary.LABEL_SOUND)
			rid = RecordManager.ID_SOUND;
		else
			if (op == Dictionary.LABEL_LIGHT)
				rid = RecordManager.ID_LIGHT;
			else
				if (op == Dictionary.LABEL_VIBRATE)
					rid = RecordManager.ID_VIBRATE;
				else
					if (op == Dictionary.LABEL_LEVEL)
						rid = RecordManager.ID_LEVEL;
					else
						rid = RecordManager.ID_SCORE;
		recordManager.setValue(rid, (short)selected);
		getEffects();
	}
	
	int getRecordPreferred(short op){
		short rid = 0; // record identificador....
		
		if (op == Dictionary.LABEL_SOUND)
			rid = RecordManager.ID_SOUND;
		else
			if (op == Dictionary.LABEL_LIGHT)
				rid = RecordManager.ID_LIGHT;
			else
				if (op == Dictionary.LABEL_VIBRATE)
					rid = RecordManager.ID_VIBRATE;
				else
					if (op == Dictionary.LABEL_LEVEL)
						rid = RecordManager.ID_LEVEL;
					else
						rid = RecordManager.ID_SCORE;
		return ((int)recordManager.getValue(rid));
	}
	
	private void getEffects(){
		useSound = getRecordPreferred(Dictionary.LABEL_SOUND) == 0 ? true : false; 
		useFlash = getRecordPreferred(Dictionary.LABEL_LIGHT) == 0 ? true : false; 
		useVibration = getRecordPreferred(Dictionary.LABEL_VIBRATE) == 0 ? true : false; 
	}
	
	void mainMenuTextScreen(short tid, short msgid){
        // create and discard a new textScreen screen each time, to
        // avoid keeping heap memory for it when it's not in use
		if (textScreen != null){
			textScreen = null;
			System.gc();
		}
		textScreen = new TextScreen(this, new String(dictionary.getString(tid)), msgid);
		display.setCurrent(textScreen);
    }
	
	void mainMenuQuit(){
		try{
			destroyApp(true);
		}
		catch (MIDletStateChangeException msce){
			msce.printStackTrace();
			// se não for possível destruir azar!!
		}
		notifyDestroyed();
	}
	
	void backToMenu(short menu){
		if (menu == MAINMENU)
			display.setCurrent(mainMenu);
		else
			display.setCurrent(optionMenu);	
	}
	
	void gameManagerMenu(boolean game_active){
        gameManager.stop();
        mainMenu.setGameActive(game_active);
        backToMenu(MAINMENU);
	}
}
