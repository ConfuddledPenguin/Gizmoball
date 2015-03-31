package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.GUI;
import model.IModel;
import model.gizmos.IGizmo;

public class ConnectKeyListener implements ActionListener {

	private IGizmo g;
	private GUI ui;
	private IModel m;
	private int keyCode;
	
	
	public ConnectKeyListener(IGizmo g, GUI ui, IModel m) {
		
		this.g = g;
		this.ui = ui;
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0 != null){
			ui.setKeyConnecting(this);
		}else{
			m.registerKeyStroke(keyCode, g);
			ui.showKeyConnectedMessage();
		}
		
	}
	
	public void setKey(int keyCode) {
		this.keyCode = keyCode;
	}

	
	
	

}
