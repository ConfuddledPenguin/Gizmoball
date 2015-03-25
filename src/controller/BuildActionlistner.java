package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.GizmoBallMain;
import model.Global;
import model.IModel;
import model.exceptions.GridPosAlreadyTakenException;
import model.exceptions.InvalidGridPosException;
import model.gizmos.Circle;
import model.gizmos.Gizmo.Type;
import model.gizmos.IGizmo;
import model.gizmos.LeftFlipper;
import model.gizmos.RightFlipper;
import model.gizmos.Square;
import model.gizmos.Triangle;
import view.GUI;

/**
 * This controller listens to events coming from the build
 * portion of gizmoball
 *
 */
public class BuildActionlistner implements ActionListener {

	private IModel model;
	private GUI view;
	private RunKeyListener run;

	/**
	 * The constructor
	 * 
	 * @param m The model to effect
	 * @param g The ui to use
	 * @param r The run key listener to update
	 */
	public BuildActionlistner(IModel m, GUI g, RunKeyListener r) {
		model = m;
		view = g;
		run = r;
	}

	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());
		
		switch (e.getActionCommand()) {

		case ("Square"):
			try {
				model.addGizmo(new Square(view.getClickedCell().x,view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			break;

		case ("Triangle"):
			try {
				model.addGizmo(new Triangle(view.getClickedCell().x,view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			break;

		case ("Circle"):
			try {
				model.addGizmo(new Circle(view.getClickedCell().x,view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			break;
			
		case ("Left Triangle"):
			try {
				model.addGizmo(new Triangle(view.getClickedCell().x, view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			break;
			
		case ("Right Triangle"):
			try {
				model.addGizmo(new Triangle(view.getClickedCell().x, view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			model.RotateClockwise(view.getClickedCell());  // rotate 180 degrees total
			model.RotateClockwise(view.getClickedCell());  // to produce right triangle
			break;
			
		case ("Left Flipper"):
			try {
				model.addGizmo(new LeftFlipper(view.getClickedCell().x, view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			break;
			
		case ("Right Flipper"):
			try {
				model.addGizmo(new RightFlipper(view.getClickedCell().x, view.getClickedCell().y));
			} catch (InvalidGridPosException | GridPosAlreadyTakenException e3) {
				view.displayErrorMessage(e3.getMessage());
			}
			break;
			
		case ("Absorber"):
			view.setAbsorberStart(view.getClickedCell());
			break;
			
		case ("Add Ball"):
			// adding 0.5L to x and y makes the centre of the ball == centre of cell
			try {
				model.addBall(view.getClickedCell().x + 0.5, view.getClickedCell().y + 0.5, 0, 50);
			} catch (InvalidGridPosException e2) {
				view.displayErrorMessage(e2.getMessage());
			}
			break;
			
		case ("Set Velocity"):
			
			run.processkey(false);
			model.getBall(view.getClickedCell()).setVelo(view.getBallVelocity());
			run.processkey(true);
			
			break;
			
		case ("Set Exit Velocity"):
			
			run.processkey(false);
			IGizmo g = model.getGizmo(view.getClickedCell());
			if(g.getType() == Type.Absorber){
				g.setBallExitVelocity(view.getBallVelocity());
			}
			run.processkey(true);
			
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
			GizmoBallMain.gui.switchMode();
			break;
		case ("Friction"):
			run.processkey(false);
		
			float[] friction = view.getUserFriction();
			if (friction != null) {
				model.setFriction(friction[0], friction[1]);
			}
			run.processkey(true);
			break;
		case ("Gravity"):
			run.processkey(false);
			Double gravity = view.getUserGravity();
			if (gravity != null) {
				model.setGravity((double)gravity);
			}
			run.processkey(true);			
			break;
			
		case "Clear Board":
			model.clear();
			break;
			
		case ("Move"):
			
			if(!view.getClickedCell().equals(view.getMovedPoint())){
				
				try {
					model.moveGizmo(view.getClickedCell(), view.getMovedPoint());
				}catch(GridPosAlreadyTakenException | InvalidGridPosException e2){
					//TODO be smarter about this
					view.displayErrorMessage(e2.getMessage());
				}
			}
			break;
		case ("Normal"):
			Global.raveMode = false;
			Global.discoMode = false;
			break;
		case ("Disco"):
			Global.raveMode = false;
			Global.discoMode = true;
			break;
		case ("Rave"):
			Global.raveMode = true;
			Global.discoMode = false;
			break;
		}
	}
}
