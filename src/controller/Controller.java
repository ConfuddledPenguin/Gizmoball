package controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import model.IModel;
import view.GUI;

public class Controller  {
	
	private ActionListener runListener;
	private ActionListener buildListener;
	private RunKeyListener runKeyListener;
	
	public Controller(IModel m, GUI g) {

		runKeyListener =  new RunKeyListener(m);
		runListener = new RunActionlistner(m, g, runKeyListener);
		buildListener = new BuildActionlistner(m, g, runKeyListener);
	}
	
	public ActionListener getRunListener() {
		return runListener;
	}

	public ActionListener getBuildListener() {
		return buildListener;
	}

	public KeyListener getRunKeyListener() {
		return runKeyListener;
	}
}
