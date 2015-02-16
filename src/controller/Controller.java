package controller;

import interfaces.IController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import model.gizmos.Circle;
import model.gizmos.Square;
import model.gizmos.Triangle;


public class Controller implements IController, ActionListener {

	private Model model;
	
	public Controller(Model m) {
		model = m;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());
		switch(e.getActionCommand()){
		
		case("Sqaure"):
			model.addGizmo(new Square());
			break;
		
		case("Left Triangle"):
			model.addGizmo(new Triangle());
			break;
		
		case("Circle"):
			model.addGizmo(new Circle());
			break;
		
		case("Right Triangle"):
			model.addGizmo(new Triangle());
			break;
		
		}
	}

}
