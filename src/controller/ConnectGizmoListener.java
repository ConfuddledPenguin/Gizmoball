package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GUI;
import model.IModel;
import model.gizmos.IGizmo;

public class ConnectGizmoListener implements ActionListener {

	private IGizmo g;
	private GUI ui;
	private IModel m;
	
	public ConnectGizmoListener(IGizmo g, GUI ui, IModel m) {
		
		this.g = g;
		this.ui = ui;
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0 != null){
			ui.setGizmoConnecting(this);
		}else{
			
			m.connectGizmos(g, m.getGizmo(ui.getClickedCell()));
			ui.showGizmoConnectedMessage();
		}
		
	}
	
	
	

}
