package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.gizmos.IGizmo;
import model.IModel;

public class DisconnectGizmoListener implements ActionListener{
	private IModel model;
	private IGizmo trigger;
	private IGizmo actor;
	
	public DisconnectGizmoListener(IModel m, IGizmo trigger, IGizmo actor) {
		// TODO Auto-generated constructor stub
		this.model = m;
		this.trigger = trigger;
		this.actor = actor;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		model.disconnectGizmos(trigger, actor);
	}
}
