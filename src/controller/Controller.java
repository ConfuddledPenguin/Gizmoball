package controller;

import interfaces.IController;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Model;


public class Controller implements IController, ActionListener {

	private Model model;
	
	public Controller(Model m) {
		model = m;
	}
	
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());

	}

}
