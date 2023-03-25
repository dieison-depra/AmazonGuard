package amazonguard;
import java.io.InputStream;

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
public final class Dictionary{
    private static short ix = 0;
    protected final static short MENU_MIDLET_NAME = ix++;
    protected final static short MENU_NEWGAME 	= ix++;
    protected final static short MENU_OPTIONS 	= ix++;
    protected final static short MENU_INSTRUCTIONS = ix++;
    protected final static short MENU_CONTINUE 	= ix++;
    
    protected final static short CMD_EXIT = ix++;
    
    protected final static short LABEL_ABOUT 		= ix++;
    protected final static short LABEL_BACK 		= ix++;
    protected final static short LABEL_EDIT 		= ix++;
    protected final static short LABEL_EXIT 		= ix++;
    protected final static short LABEL_MODIFY 	= ix++;//10
    protected final static short LABEL_OFF 		= ix++;
    protected final static short LABEL_ON 		= ix++;
    protected final static short LABEL_SAVE     	= ix++;
    protected final static short LABEL_VERSION 	= ix++;
    protected final static short LABEL_SOUND 		= ix++;//15
    protected final static short LABEL_VIBRATE   	= ix++;
    protected final static short LABEL_LIGHT     	= ix++;
    protected final static short LABEL_LEVEL	 	= ix++;
    protected final static short LABEL_CHOICE     = ix++;
    protected final static short LABEL_EASY       = ix++;
    protected final static short LABEL_MEDIUM     = ix++;
    protected final static short LABEL_HARD       = ix++;
    protected final static short LABEL_ERROR_LOAD = ix++;
    
    protected final static short TEXT_GAME_PLAYER 		= ix++;
    protected final static short TEXT_GAME_ENIMY 			= ix++;
    protected final static short TEXT_GAME_LIVES 			= ix++;
    protected final static short TEXT_GAME_SCORE		 	= ix++;
    protected final static short TEXT_GAME_OVER        	= ix++;
    protected final static short TEXT_GAME_WINNER       	= ix++;
    protected final static short TEXT_GAME_YOU_WON 		= ix++;
    protected final static short TEXT_GAME_YOU_LOST 		= ix++;
    protected final static short TEXT_INSTRUCTIONS 		= ix++;
    protected final static short TEXT_HISTORY			 	= ix++;
    protected final static short NUM_IDS = ix;
    
    private final static String IDIOMA_DEFAULT = "en";
    private final static String MIDLET_NAME = "AmazonGuard";

    private String[] strings;
	
    //public Dictionary(String locale, String midlet_name){
    public Dictionary(String agmName, String locale){
    	strings = new String[NUM_IDS];
    	String sigla = new String((locale == null)? IDIOMA_DEFAULT:locale.substring(0, 2));
    	try{
			loadText(sigla.toLowerCase());
    	}
    	catch (java.io.IOException ioe){
    		ioe.printStackTrace();
    		for (short i=1; i < NUM_IDS; i++)
    			strings[i] = getStringsDefault(i);
    	}
    	finally{
    		strings[MENU_MIDLET_NAME] = agmName == null ? MIDLET_NAME : agmName;

    		// para garantir que nenhuma entrada do array ficara com valor null ou em branco...
    		for (short i=1; i < NUM_IDS; i++)
    			if (strings[i] == null || strings[i].equals(""))
    				strings[i] = getStringsDefault(i);
    	}
	}

    private final String getStringsDefault(short id){
        // USA English strings
    	if (id == MENU_MIDLET_NAME) return MIDLET_NAME;
    	if (id == MENU_NEWGAME) 	return ("New game");
    	if (id == MENU_OPTIONS) 	return ("Options");
    	if (id == MENU_INSTRUCTIONS) return ("Instructions");
    	if (id == MENU_CONTINUE) 	return ("Continue");
    	
    	if (id == CMD_EXIT) return ("EXIT");
    	
    	if (id == LABEL_ABOUT) 		return ("About");
    	if (id == LABEL_BACK) 		return ("Back");
    	if (id == LABEL_EXIT) 		return ("Exit");
    	if (id == LABEL_MODIFY) 	return ("Modify");
    	if (id == LABEL_OFF) 		return ("Off");
    	if (id == LABEL_ON) 		return ("On");
    	if (id == LABEL_SAVE) 		return ("Save");
    	if (id == LABEL_SOUND) 		return ("Sound");
    	if (id == LABEL_VERSION) 	return ("Version");
    	if (id == LABEL_VIBRATE) 	return ("Vibrate");
    	if (id == LABEL_LIGHT) 		return ("BlackLight");
    	if (id == LABEL_LEVEL) 		return ("Challenge");
    	if (id == LABEL_CHOICE) 	return ("It selects one:");
    	if (id == LABEL_EASY) 		return ("Easy");
    	if (id == LABEL_MEDIUM) 	return ("Medium");
    	if (id == LABEL_HARD) 		return ("Hard");
    	if (id == LABEL_ERROR_LOAD) return ("Resource not found!");
    	
        // Game screen text strings
    	if (id == TEXT_GAME_LIVES) 		return ("Lives");
    	if (id == TEXT_GAME_SCORE) 		return ("Score:");
    	if (id == TEXT_GAME_OVER) 		return ("Game Over!");
    	if (id == TEXT_GAME_WINNER) 	return ("Finished Game!");
    	if (id == TEXT_GAME_YOU_WON) 	return ("You won!");
    	if (id == TEXT_GAME_YOU_LOST)	return ("You win!");
    	
    	if (id == TEXT_INSTRUCTIONS) 
    		return (
            // Describe game's objective and how to play:
            "The objective of the game is for you to get " +
            " points before the blocks do. " +
            "Destroying blocks earns you points. " +
            "The blocks get points for flying off the screen, " +
            "and extra points for hitting or destroying the base.\n\n" +

            // Describe block's point values:
            "Not all blocks are equally easy to destroy. " +
            "The blocks increase in difficulty (and points value) " +
            "as follows: empty dark, single white diagonal line, " +
            "double white diagonal lines, empty white, " +
            "single black diagonal line, " +
            "and double black diagonal lines.\n\n" +
            "Your score and remaining lives are shown in the upper left " +
            "hand corner of the screen. The blocks' score is shown in the " +
            "lower right hand corner of the screen. The blocks can win by " +
            "you do, or by destroying your base until you have no lives " +
            "left.\n\n" +

            // Describe keypad usage:
            "The default keys to use for the " +
            "up, down, left, right and fire game actions are: " +
            " respectively. This can vary in different phone models.\n\n" +

            // Describe softkey usage:
            "In the game screen, pressing an appropriate softkey causes " +
            "the game to pause. Use '" + strings[MENU_CONTINUE] +
            "' to return to the game in progress. " +
            "Game play will resume when you press a non-softkey.\n\n"
		);

		// Describe gauge
    	if (id == TEXT_HISTORY) 
    		return (
            "A gauge may sometimes be shown in the upper right " +
            "corner of the screen. " +
            "It is used to indicate when the base's laser cannon is " +
            "overheating. The cannon may overheat if you continuously " +
            "hold down the game's 'fire' button for too long. " +
            "When this occurs, the cannon can not be fired again, " +
            "until it has cooled down (i.e. until the gauge empties)."
            );
		return ("Message not found!");
    }

    public final String getString(int id){
    	if ((id >= 0) && (id < strings.length)){
    		return strings[id].trim();
    	}
    	else{
    		throw new IllegalArgumentException("id=" + id +
                " is out of bounds. max=" + strings.length);
    	}
    }

    private final void loadText(String idioma) throws java.io.IOException {
    	InputStream is = null;
    	int idx = 0 ;
    	int ch = 0;
    	byte[] buf = new byte[4];
    	StringBuffer strbuf = new StringBuffer();
    	String file = new String("/amazonguard/data/" + idioma + ".lng");
    	
    	try {
    		is = getClass().getResourceAsStream(file);
    		if (is != null){
				while ( (ch = is.read()) != -1){
					if (ch == '{'){
						if (idx < NUM_IDS && idx > 0){
							strings[idx] = strbuf.toString().trim();
							strbuf.delete(0, strbuf.length() - 1);
							idx = 0;
   						}
						is.read(buf);
						idx = Integer.parseInt(new String(buf));
						is.skip(1);
   					} 
   					else
   						if (ch == '\\'){
   							strbuf.append('\n');
   							is.skip(1);
   						}
   						else	
   							strbuf.append((char)ch);
   				}
   				is.close();
   				if (idx > 0) strings[idx] = strbuf.toString();
   			}
   		}
    	catch (java.io.IOException ex){
			ex.printStackTrace();
    		throw new java.io.IOException();
    	}
	}
}