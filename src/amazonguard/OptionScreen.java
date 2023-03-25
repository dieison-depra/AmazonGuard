/*
 * Created on 10/06/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package amazonguard;

import javax.microedition.lcdui.ChoiceGroup;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;

/**
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
//public class OptionScreen extends Form implements CommandListener, ItemStateListener {
public final class OptionScreen extends Form implements CommandListener {
	private final AmazonGuardMIDlet agm;
	private final Command backCommand;
	private final Command saveCommand;
	private final ChoiceGroup list;
	private final String[] stritens;
	private final short option;
	
	public OptionScreen(AmazonGuardMIDlet agm, short option) {
		// TODO Auto-generated constructor stub
		super(agm.dictionary.getString(option));
		this.agm = agm;
		this.option = option;
		
		// adiciona itens padrão do menu...
		if (option == Dictionary.LABEL_LEVEL){
			stritens = new String[3];
			stritens[0] = agm.dictionary.getString(Dictionary.LABEL_EASY);
			stritens[1] = agm.dictionary.getString(Dictionary.LABEL_MEDIUM);
			stritens[2] = agm.dictionary.getString(Dictionary.LABEL_HARD);
		}
		else{
			stritens = new String[2];
			stritens[0] = agm.dictionary.getString(Dictionary.LABEL_ON);
			stritens[1] = agm.dictionary.getString(Dictionary.LABEL_OFF);
		}
		list = new ChoiceGroup(agm.dictionary.getString(Dictionary.LABEL_CHOICE), ChoiceGroup.EXCLUSIVE, stritens, null);
		append(list);
		list.setSelectedIndex(agm.getRecordPreferred(option), true);

		backCommand = new Command(agm.dictionary.getString(Dictionary.LABEL_BACK), Command.BACK, 1);
		addCommand(backCommand);  
		saveCommand = new Command(agm.dictionary.getString(Dictionary.LABEL_SAVE), Command.OK, 2);
		addCommand(saveCommand);  
		setCommandListener(this);
	}

	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public final void commandAction(Command command, Displayable display) {
		// TODO Auto-generated method stub
        if (command == saveCommand){
        	agm.setRecordPreferred(option, list.getSelectedIndex());
        	agm.backToMenu(agm.OPTIONMENU);
        }
        else
        	if (command == backCommand) agm.backToMenu(agm.OPTIONMENU);
	}	
}
