package controller;

import java.awt.event.ActionListener;
import model.Model;
import view.GUI;

public class Controller  {
	
	private Model model;
	private ActionListener runListener;
	private ActionListener buildListener;
	
	public Controller(Model m, GUI g) {
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
