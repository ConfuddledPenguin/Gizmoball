package controller;

import java.awt.event.ActionListener;

import model.IModel;
import view.GUI;

public class Controller  {
	
	private IModel model;
	private ActionListener runListener;
	private ActionListener buildListener;
	
	public Controller(IModel m, GUI g) {
		model = m;
		runListener = new RunActionlistner(m, g);
		buildListener = new BuildActionlistner(m, g);
	}
	
	public ActionListener getRunListener() {
		return runListener;
	}

	public ActionListener getBuildListener() {
		return buildListener;
	}

}
