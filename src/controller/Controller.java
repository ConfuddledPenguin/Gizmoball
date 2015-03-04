package controller;

import java.awt.event.ActionListener;
import java.awt.event.KeyListener;

import model.IModel;
import view.GUI;

public class Controller  {
	
	private IModel model;
	private ActionListener runListener;
	private ActionListener buildListener;
	private KeyListener runKeyListener;
	
	public Controller(IModel m, GUI g) {
		model = m;
		runListener = new RunActionlistner(m, g);
		buildListener = new BuildActionlistner(m, g);
		runKeyListener =  new RunKeyListener(m);
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
