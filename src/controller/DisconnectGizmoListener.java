package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.gizmos.IGizmo;
import model.IModel;

/**
 * This controller handles dicounting the gizmos from other gizmos
 *
 */
public class DisconnectGizmoListener implements ActionListener{
	private IModel model;
	private IGizmo trigger;
	private IGizmo actor;
	
	/**
	 * The constructor
	 * 
	 * @param m The model to update
	 * @param trigger The gizmo trigging the action
	 * @param actor The gizmo consumming the action
	 */
	public DisconnectGizmoListener(IModel m, IGizmo trigger, IGizmo actor) {
		// TODO Auto-generated constructor stub
		this.model = m;
		this.trigger = trigger;
		this.actor = actor;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		model.disconnectGizmos(trigger, actor);
	}
}
