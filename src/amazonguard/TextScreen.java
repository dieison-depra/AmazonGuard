package amazonguard;
import javax.microedition.lcdui.Command;
import javax.microedition.lcdui.CommandListener;
import javax.microedition.lcdui.Displayable;
import javax.microedition.lcdui.Form;
import javax.microedition.lcdui.StringItem;

/* *
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class TextScreen extends Form implements CommandListener {
	/**
	 * @param arg0
	 */
	private final AmazonGuardMIDlet agm;
	private final Command backCommand;
	
	public TextScreen(AmazonGuardMIDlet agm, String tid, short msgid) {
		super(tid);
		this.agm = agm;
		// TODO Auto-generated constructor stub
        append(new StringItem(null, agm.dictionary.getString(msgid)));

        backCommand = new Command(agm.dictionary.getString(Dictionary.LABEL_BACK), Command.BACK, 1);
        addCommand(backCommand);
        setCommandListener(this);
	}
	
	/* (non-Javadoc)
	 * @see javax.microedition.lcdui.CommandListener#commandAction(javax.microedition.lcdui.Command, javax.microedition.lcdui.Displayable)
	 */
	public final void commandAction(Command c, Displayable d){
		// TODO Auto-generated method stub
		agm.backToMenu(agm.MAINMENU);
	}
}
