package main;


import javax.swing.SwingUtilities;

import model.Model;
import model.gizmos.Absorber;
import model.gizmos.Circle;
import model.gizmos.IGizmo;
import model.gizmos.Square;
import model.gizmos.LeftFlipper;
import model.gizmos.RightFlipper;
import view.GUI;

public class GizmoBallMain {
	/**
	 * @author Cameron, Andrew, Tom, Marc, Nathan
	 * 
	 */
	public static GUI gui;
	final static Model m = new Model(50, 50);
	
	public static void main(String[] args){

		SwingUtilities.invokeLater(new Runnable(){

			@Override
			public void run() {
				gui = new GUI('r',m);
				makeGizmos();
			}
			
		});	
	}
	
	private static void makeGizmos(){
		m.addBall();

//		m.addGizmo(new Square(10,5));
		IGizmo g = new Absorber(0, 19, 20, 1);
		m.addGizmo(g);
		m.registerKeyStroke(32, g);
//		m.addGizmo(new Circle(15,15));
//		m.addGizmo(new Circle(25,10));
//		m.addGizmo(new Circle(18,9));
//		m.addGizmo(new Circle(14,14));
//		m.addGizmo(new Circle(29,4));	
//		
//		m.addGizmo(new Circle(10,4));
//		m.addGizmo(new Circle(11,5));
//		m.addGizmo(new Circle(12,6));
//		m.addGizmo(new Circle(13,7));
//		m.addGizmo(new Circle(14,8));
//		m.addGizmo(new Circle(15,9));
//		m.addGizmo(new Circle(16,10));
//		m.addGizmo(new Circle(17,11));
//		m.addGizmo(new Circle(18,12));
//		m.addGizmo(new Circle(19,13));
//		m.addGizmo(new Circle(20,14));
//		m.addGizmo(new Circle(21,14));
//		m.addGizmo(new Circle(22,14));
//		m.addGizmo(new Circle(23,13));
//		m.addGizmo(new Circle(26,10));

		//For flipper demo
//		IGizmo flip = new LeftFlipper(5,5);
//		m.addGizmo(flip);
//		IGizmo flipr = new RightFlipper(10,10);
//		m.addGizmo(flipr);
//		
//		m.registerKeyStroke(65, flip); // key 'a'
//		m.registerKeyStroke(83, flipr); // key 's'

	}
}
