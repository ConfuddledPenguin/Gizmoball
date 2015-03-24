package main;


import javax.swing.SwingUtilities;

import model.Model;
import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Absorber;
import model.gizmos.IGizmo;
import view.GUI;

public class GizmoBallMain {
	/**
	 * @author Cameron, Andrew, Tom, Marc, Nathan
	 * 
	 */
	public static GUI gui;
	final static Model m = new Model();
	
	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				gui = new GUI('r',m);
				makeGizmos();
			}	
			
		});	
	}
	
	private static void makeGizmos() {
		
		try {
			m.addBall(19.5,18.5,0,-50);
		} catch (InvalidGridPosException e) {
			e.printStackTrace();
		}
		IGizmo g = new Absorber(0, 19, 20, 1);
		try {
			m.addGizmo(g);
		} catch (InvalidGridPosException | GridPosAlreadyTakenException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		m.registerKeyStroke(32, g);

	}
}
