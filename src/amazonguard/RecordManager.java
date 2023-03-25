/*
 * Created on 23/05/2004
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package amazonguard;
import javax.microedition.rms.RecordStore;
import javax.microedition.rms.RecordStoreException;
/**
 * @author Dieison
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public final class RecordManager {
	/**
	 * 
	 */
	private static short idx = 1;
    protected final static short ID_VIBRATE = idx++;
    protected final static short ID_LIGHT = idx++;
    protected final static short ID_SOUND = idx++;
    protected final static short ID_LEVEL = idx++;
    protected final static short ID_SCORE = idx;
    protected final static byte[] bytes = { 0, 1, 2};
    
    private final static short EASY = 0;
    private final static short MEDIUM = 1;
    private final static short HARD = 2;
    
    private final static int TRUE = 0; 		// bytes[TRUE] = 'T'
    private final static int FALSE = 1; 	// bytes[FALSE] = 'F'
    private RecordStore rs = null;
    private String recordname;

    // método construtor, recebe o nome do registro a ser armazenado...
	public RecordManager(String rn) {
		// TODO Auto-generated constructor stub
		recordname = rn;
	}
	
	protected final void setRecordName(String rn){
		setClose();
		recordname = rn;
	}
	
	// método para abrir registro armazenado e criar a sequencia padrão...
    private final void setOpen() throws RecordStoreException{
    	if (rs == null){
    		rs = RecordStore.openRecordStore(recordname, true);
    	}
    	int nr = rs.getNumRecords();
    	
    	if (nr >= 0 && nr != idx){
    		setClose();
    		RecordStore.deleteRecordStore(recordname);
    		rs = RecordStore.openRecordStore(recordname, true);
    	}

    	if (nr == 0){
    		// save a default value for ID_VIBRATION in the record store
    		rs.addRecord(bytes, FALSE, 1);
    		
    		// save a default value for ID_BLACKLIGHT in the record store
    		rs.addRecord(bytes, FALSE, 1);

    		// save a default value for ID_SOUND in the record store
    		rs.addRecord(bytes, FALSE, 1);
    		
    		// save a default value for ID_SCORE in the record store
    		rs.addRecord(bytes, FALSE, 1);
    		
    		// save a default value for ID_LEVEL in the record store
    		rs.addRecord(bytes, MEDIUM, 1);
    	}
    }

    // Método para fechar um registro aberto...
    private final void setClose(){
    	try{
    		if (rs != null){
    			rs.closeRecordStore();
    			rs = null;
    		}
    	}
    	catch (RecordStoreException rse){}
    }
    
    /* Método para alterar o valor de um registro já existente.
     * @param id identifica o registro a ser alterado.
     * @param buffer a ser gravado a partir de sua posicação 
     * inicial gravando todo seu comprimento.
     * @see vetValue
    */
    protected final boolean setValue(short rid, short index){
    	try{
    		if (rs == null) setOpen();
    		if (rid < ID_VIBRATE || rid > ID_SCORE) return false;
    		rs.setRecord(rid, bytes, index, 1);
    	   	setClose();
    	   	return true;
    	}
    	catch (RecordStoreException rse){
    		rse.printStackTrace();
    		return false;
    	}
    }

    /* Método para recuperar o valor de um registro armazenado 
     * de acordo com um identificador especifico.
     * @param id identifica o registro a ser recuperado.
     */
    protected final byte getValue(short id){
    	byte buffer;
    	try{
    		if (rs == null) setOpen();
    		if (id < ID_VIBRATE || id > ID_SCORE) return bytes[TRUE];
    		buffer = rs.getRecord(id)[0];
    	   	setClose();
    		return buffer;
    	}
    	catch(RecordStoreException rse){
    		rse.printStackTrace();
    		return bytes[TRUE];
    	}
   	}
}