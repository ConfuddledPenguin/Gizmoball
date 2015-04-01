package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import sound.ISoundController;
import model.IModel;

/**
 * Listens for changes in the settings
 *
 */
public class SettingsListener implements ActionListener {

	//private IModel model;
	private ISoundController sc;
	
	public SettingsListener(IModel model, ISoundController sc) {
		
		//this.model = model;
		this.sc = sc;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		
		switch (e.getActionCommand()) {
		case "Mute Gizmos":
			sc.muteGizmos(true);
			break;
		case "Mute Music":
			sc.muteMusic(true);
			break;
		}
	}
}
