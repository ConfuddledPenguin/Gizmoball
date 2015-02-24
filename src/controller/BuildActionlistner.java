package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.GizmoBallMain;
import model.Model;
import view.GUI;
import model.gizmos.Circle;
import model.gizmos.Square;
import model.gizmos.Triangle;


public class BuildActionlistner implements ActionListener {

	private Model model;
	private GUI view;

	public BuildActionlistner(Model m, GUI g) {
		model = m;
		view = g;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());
		
		// not always needed but better than repeating for each case
		int x = view.getClickedCell().x;
		int y = view.getClickedCell().y;

		switch (e.getActionCommand()) {

		case ("Square"):
			model.addGizmo(new Square(x, y));
			break;

		case ("Triangle"):
			model.addGizmo(new Triangle(x,y));
			break;

		case ("Circle"):
			model.addGizmo(new Circle(x,y));
			break;
			
		case ("Clockwise"):
			model.RotateClockwise(view.getClickedCell());
			break;
		
		case ("Anti-Clockwise"):
			model.RotateAntiClockwise(view.getClickedCell());
			break;
		
		case ("Delete"):
			model.deleteGizmo(view.getClickedCell());
			break;
		case ("Run Mode"):
			System.out.println("Going into running mode!!!!");
			GizmoBallMain.gui.switchMode();
			break;
		case ("Move"):
			model.moveGizmo(view.getClickedCell(), view.getMovedPoint());	
			break;
		}

	}

}
