package controller;

import java.awt.event.ActionListener;

import model.IModel;
import view.GUI;

public class Controller  {
	
	private ActionListener runListener;
	private ActionListener buildListener;
	private RunKeyListener runKeyListener;
	
	public Controller(IModel m, GUI g) {

		runKeyListener =  new RunKeyListener(m);
		runListener = new RunActionlistner(m, g, runKeyListener);
		runKeyListener.registerRunActionListener(runListener);
		buildListener = new BuildActionlistner(m, g, runKeyListener);
		runKeyListener.registerBuildActionListener(buildListener);

	}
	
	public ActionListener getRunListener() {
		return runListener;
	}

	public ActionListener getBuildListener() {
		return buildListener;
	}

	public RunKeyListener getRunKeyListener() {
		return runKeyListener;
	}
}
