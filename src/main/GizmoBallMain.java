package main;


import javax.swing.SwingUtilities;

import model.Model;
import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Absorber;
import model.gizmos.IGizmo;
import view.GUI;

/**
 * The main starting class for the gizmoball application
 * 
 * @author Tom, Cameron, Andrew, Marc, Nathan
 *
 */
public class GizmoBallMain {
	
	public static GUI gui;
	final static Model m = new Model();
	
	/**
	 * Things start here and go else where
	 * 
	 * @param args none
	 */
	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				gui = new GUI('r',m);
				makeGizmos();
			}	
		});	
	}
	
	/**
	 * This adds the default gizmos to the screen
	 */
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
			e.printStackTrace();
		}
		m.registerKeyStroke(32, g);

	}
}
