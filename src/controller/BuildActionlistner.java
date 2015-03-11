package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

import main.GizmoBallMain;
import model.IModel;
import view.GUI;
import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.*;
import model.Global;


public class BuildActionlistner implements ActionListener {

	private IModel model;
	private GUI view;
	private RunKeyListener run;

	public BuildActionlistner(IModel m, GUI g, RunKeyListener r) {
		model = m;
		view = g;
		run = r;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());
		
		switch (e.getActionCommand()) {

		case ("Square"):
			model.addGizmo(new Square(view.getClickedCell().x,view.getClickedCell().y));
			break;

		case ("Triangle"):
			model.addGizmo(new Triangle(view.getClickedCell().x,view.getClickedCell().y));
			break;

		case ("Circle"):
			model.addGizmo(new Circle(view.getClickedCell().x,view.getClickedCell().y));
			break;
			
		case ("Left Triangle"):
			model.addGizmo(new Triangle(view.getClickedCell().x, view.getClickedCell().y));
			break;
			
		case ("Right Triangle"):
			model.addGizmo(new Triangle(view.getClickedCell().x, view.getClickedCell().y));
			model.RotateClockwise(view.getClickedCell());  // rotate 180 degrees total
			model.RotateClockwise(view.getClickedCell());  // to produce right triangle
			break;
			
		case ("Left Flipper"):
			model.addGizmo(new LeftFlipper(view.getClickedCell().x, view.getClickedCell().y));
			break;
			
		case ("Right Flipper"):
			model.addGizmo(new RightFlipper(view.getClickedCell().x, view.getClickedCell().y));
			break;
			
		case ("Add Ball"):
			// adding 0.5L to x and y makes the centre of the ball == centre of cell
			model.addBall(view.getClickedCell().x + 0.5, view.getClickedCell().y + 0.5, 0, 0);
			break;
			
		case ("Clockwise"):
			model.RotateClockwise(view.getClickedCell());
			break;
		
		case ("Anti-Clockwise"):
			model.RotateAntiClockwise(view.getClickedCell());
			break;
		
		case ("Delete"):
			model.deleteGizmo(view.getClickedCell());
			model.deleteBall(view.getClickedCell());
			break;
		case ("Run Mode"):
			System.out.println("Going into running mode!!!!");
			GizmoBallMain.gui.switchMode();
			break;
		case ("Friction"):
			System.out.println("FRICTION");
			run.processkey(false);
		
			float[] friction = view.getUserFriction();
			model.setFriction(friction[0], friction[1]);
			run.processkey(true);
			break;
		case ("Move"):
			
			if(!view.getClickedCell().equals(view.getMovedPoint())){
				
				try {
					model.moveGizmo(view.getClickedCell(), view.getMovedPoint());
				} catch (InvalidGridPosException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (GridPosAlreadyTakenException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}	
			}
			break;
		}

	}

}
