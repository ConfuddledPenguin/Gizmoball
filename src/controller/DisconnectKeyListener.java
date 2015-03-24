package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.gizmos.IGizmo;
import model.IModel;

public class DisconnectKeyListener implements ActionListener{
	private IModel model;
	private int key;
	private IGizmo actor;
	
	public DisconnectKeyListener(IModel m, int key, IGizmo actor) {
		this.model = m;
		this.key = key;
		this.actor = actor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.unRegisterKeyStroke(key, actor);
	}
}
