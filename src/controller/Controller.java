package controller;

import java.awt.event.ActionListener;

import model.IModel;
import view.GUI;

/**
 * The starting point for the gizmoball control system
 *
 */
public class Controller  {
	
	private ActionListener runListener;
	private ActionListener buildListener;
	private RunKeyListener runKeyListener;
	
	/**
	 * The constructor
	 * 
	 * @param m The model to effect
	 * @param g The ui to sue
	 */
	public Controller(IModel m, GUI g) {

		runKeyListener =  new RunKeyListener(m);
		runListener = new RunActionlistner(m, g, runKeyListener);
		runKeyListener.registerRunActionListener(runListener);
		buildListener = new BuildActionlistner(m, g, runKeyListener);
		runKeyListener.registerBuildActionListener(buildListener);

	}
	
	/**
	 * Returns the run listener
	 * 
	 * @return this.runListener
	 */
	public ActionListener getRunListener() {
		return runListener;
	}

	/**
	 * Returns the build listener
	 * 
	 * @return this.buildListener
	 */
	public ActionListener getBuildListener() {
		return buildListener;
	}

	/**
	 * Returns the key listener
	 * 
	 * @return this.keyListener
	 */
	public RunKeyListener getRunKeyListener() {
		return runKeyListener;
	}
}
