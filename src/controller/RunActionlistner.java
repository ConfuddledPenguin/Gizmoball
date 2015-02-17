package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import main.GizmoBallMain;
import model.Model;

public class RunActionlistner implements ActionListener {

	private Model model;

	public RunActionlistner(Model m) {
		model = m;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());

		switch (e.getActionCommand()) {
		case ("Build Mode"):
			GizmoBallMain.gui.switchMode();
			System.out.println("Going into Build Mode!!");
		}
	}

}
