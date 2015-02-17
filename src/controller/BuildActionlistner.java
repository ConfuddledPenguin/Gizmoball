package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import model.Model;
import model.gizmos.Circle;
import model.gizmos.Square;
import model.gizmos.Triangle;

public class BuildActionlistner implements ActionListener {

	private Model model;

	public BuildActionlistner(Model m) {
		model = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());

		switch (e.getActionCommand()) {

		case ("Square"):
			model.addGizmo(new Square(0, 0));
			break;

		case ("Left Triangle"):
			model.addGizmo(new Triangle(0,0,'L'));
			break;

		case ("Circle"):
			model.addGizmo(new Circle(0,0));
			break;

		case ("Right Triangle"):
			model.addGizmo(new Triangle(0,0,'R'));
			break;

		}

	}

}
