package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import view.IGUI;
import model.IModel;
import model.gizmos.IGizmo;

/**
 * This action listener handles connecting a gizmo to another gizmo.
 * It is initialised when the user right clicks on a gizmo. A reference
 * to this gizmo is saved. If the user clicks "connect gizmo to gizmo"
 * the ui is notified that the next click should be used to select
 * the gizmo to connect to. The mouseClickedListener checks with the ui
 * on every mouse click to see if the click is intended to complete
 * a gizmo -> gizmo connection. The mouseClickedListener then calls
 * actionPerformed on this listener. We get the clicked cell from the
 * view, use that to get the gizmo in that cell from the model. We then
 * tell the model to connect the two gizmos.
 */
public class ConnectGizmoListener implements ActionListener {

	private IGizmo g;
	private IGUI ui;
	private IModel m;
	
	
	public ConnectGizmoListener(IGizmo g, IGUI ui, IModel m) {
		
		this.g = g;
		this.ui = ui;
		this.m = m;
	}
	
	@Override
	public void actionPerformed(ActionEvent arg0) {
		
		if(arg0 != null){
			// notify the ui that the next click is to be used to select the gizmo to connect to
			ui.setGizmoConnecting(this);
		}else{
			// the user has selected both gizmos, so complete the connection
			m.connectGizmos(g, m.getGizmo(ui.getClickedCell()));
			ui.showGizmoConnectedMessage();
		}
		
	}
	
	
	

}
