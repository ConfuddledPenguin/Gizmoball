package controller;

import interfaces.IController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import model.gizmos.Circle;
import model.gizmos.Square;



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
	}

}
