package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import view.IGUI;
import model.IModel;
import model.gizmos.IGizmo;

/**
 * This action listener handles connecting a key press to a gizmo.
 * It is first triggered when the user selects a gizmo, at which point
 * a reference to the selected gizmo is saved. After this, the next time a key is
 * pressed the runKeyListener calls setKeyCode and passes in the pressed key.
 * ActionPerformed is then called with a null action passed in. At this point
 * the key is connected to the saved gizmo and the user notified that the connection
 * was successful. 
 */
public class ConnectKeyListener implements ActionListener {

	private IGizmo g;
	private IGUI ui;
	private IModel m;
	private int keyCode;
	
	public ConnectKeyListener(IGizmo g, IGUI ui, IModel m) {
		
		this.g = g;
		this.ui = ui;
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		
		if(event != null){
			// notify the ui that this is the action listener for this connection
			ui.setKeyConnecting(this);
		}else{
			m.registerKeyStroke(keyCode, g);
			ui.showKeyConnectedMessage(KeyEvent.getKeyText(keyCode) + " key has been connected to the gizmo");
		}
	}
	
	/**
	 * Set the key to be connected to gizmo g
	 * @param keyCode integer value of a key
	 */
	public void setKey(int keyCode) {
		this.keyCode = keyCode;
	}

	
	
	

}
