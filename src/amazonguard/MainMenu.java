package amazonguard;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/*
 * Created on 21/05/2004
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
public final class MainMenu extends List implements CommandListener {
	/**
	 * @param arg0
	 * @param arg1
	 */
	private final AmazonGuardMIDlet agm;
	private boolean gameactive = false;
	private Command exitCommand;
	
	public MainMenu(AmazonGuardMIDlet agm) {
		// TODO Auto-generated constructor stub
		super(agm.dictionary.getString(Dictionary.MENU_MIDLET_NAME), List.IMPLICIT);
		this.agm = agm;
		this.setFitPolicy(TEXT_WRAP_OFF);
		
		// adiciona itens padrão do menu...
		append(agm.dictionary.getString(Dictionary.MENU_NEWGAME), null);
		append(agm.dictionary.getString(Dictionary.MENU_OPTIONS), null);
		append(agm.dictionary.getString(Dictionary.MENU_INSTRUCTIONS), null);
		
		exitCommand = new Command(agm.dictionary.getString(Dictionary.CMD_EXIT), Command.EXIT, 1);
		addCommand(exitCommand);  
		setCommandListener(this);
	}
	
	protected final void setGameActive(boolean active){
		if (active && !gameactive){
            gameactive = true;
            insert(0, agm.dictionary.getString(Dictionary.MENU_CONTINUE), null);
        }
        else if (!active && gameactive){
            gameactive = false;
            delete(0);
        }
    }

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public final void commandAction(Command command, Displayable d) {
		// TODO Auto-generated method stub
        if (command == List.SELECT_COMMAND){
			String selected = getString(getSelectedIndex());

            if (selected.equals(agm.dictionary.getString(Dictionary.MENU_CONTINUE))){
				agm.mainMenuContinue();
			}
			else if (selected.equals(agm.dictionary.getString(Dictionary.MENU_NEWGAME))){
				agm.mainMenuNewGame();
			}
			else if (selected.equals(agm.dictionary.getString(Dictionary.MENU_OPTIONS))){
				agm.mainMenuOptions();
			}
			else if (selected.equals(agm.dictionary.getString(Dictionary.MENU_INSTRUCTIONS))){
				agm.mainMenuTextScreen(Dictionary.MENU_INSTRUCTIONS, Dictionary.TEXT_INSTRUCTIONS);
			}
        }
        else
        	if (command == exitCommand) agm.mainMenuQuit();
	}
}
