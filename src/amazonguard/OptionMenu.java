/*
 * Created on 10/06/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package amazonguard;

import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.List;

/**
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class OptionMenu extends List implements CommandListener {
	/**
	 * @param arg0
	 * @param arg1
	 */
	private final AmazonGuardMIDlet agm;
	private Command backCommand;
	
	public OptionMenu(AmazonGuardMIDlet agm) {
		// TODO Auto-generated constructor stub
		super(agm.dictionary.getString(Dictionary.MENU_OPTIONS), List.IMPLICIT);
		this.agm = agm;
		this.setFitPolicy(TEXT_WRAP_OFF);
		
		// adiciona itens padrão do menu...
		append(agm.dictionary.getString(Dictionary.LABEL_SOUND), null);
		append(agm.dictionary.getString(Dictionary.LABEL_VIBRATE), null);
		append(agm.dictionary.getString(Dictionary.LABEL_LIGHT), null);
		append(agm.dictionary.getString(Dictionary.LABEL_LEVEL), null);
		
		backCommand = new Command(agm.dictionary.getString(Dictionary.LABEL_BACK), Command.BACK, 1);
		addCommand(backCommand);  
		setCommandListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public final void commandAction(Command command, Displayable display) {
		// TODO Auto-generated method stub
        if (command == List.SELECT_COMMAND){
        	switch(this.getSelectedIndex()){
        		case 0: agm.optionMenu(Dictionary.LABEL_SOUND); break;
        		case 1: agm.optionMenu(Dictionary.LABEL_VIBRATE); break;
        		case 2: agm.optionMenu(Dictionary.LABEL_LIGHT); break;
        		default: agm.optionMenu(Dictionary.LABEL_LEVEL); break;
        	}
        }
        else
        	if (command == backCommand) agm.backToMenu(agm.MAINMENU);
	}
}
