package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.gizmos.IGizmo;
import model.IModel;

/**
 * This controller listens to requests to diconnect the key from a
 * gizmo
 *
 */
public class DisconnectKeyListener implements ActionListener{
	private IModel model;
	private int key;
	private IGizmo actor;
	
	/**
	 * The constructor
	 * 
	 * @param m  the model to update
	 * @param key The key trigger
	 * @param actor The gizmo that acts
	 */
	public DisconnectKeyListener(IModel m, int key, IGizmo actor) {
		this.model = m;
		this.key = key;
		this.actor = actor;
	}

	
	/**
	 * Perform the disconnect
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		model.unRegisterKeyStroke(key, actor);
	}
}
