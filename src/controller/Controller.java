package controller;

import interfaces.IController;
import java.awt.event.ActionListener;
import model.Model;
<<<<<<< HEAD
=======
import model.gizmos.Circle;
import model.gizmos.Square;
>>>>>>> origin/master



public class Controller implements IController  {
	
	private Model model;
	private ActionListener runListener;
	private ActionListener buildListener;
	
	public Controller(Model m) {
		model = m;
		runListener = new RunActionlistner(m);
		buildListener = new BuildActionlistner(m);
	}
	
<<<<<<< HEAD
	public ActionListener getRunListener() {
		return runListener;
	}

	public ActionListener getBuildListener() {
		return buildListener;
=======
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());
		switch(e.getActionCommand()){
		
		case("Square"):
			model.addGizmo(new Square(0,0));
			break;
		
		case("Left Triangle"):
			break;
		
		case("Circle"):
			model.addGizmo(new Circle(0,0));
			break;
		
		case("Right Triangle"):
			break;
		
		}
>>>>>>> origin/master
	}

}
