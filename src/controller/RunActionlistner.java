package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import main.GizmoBallMain;
import model.Model;

public class RunActionlistner implements ActionListener {

	private Model model;
	private Timer timer;

	public RunActionlistner(Model m) {
		model = m;
		timer = new Timer(50, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());

		if (e.getSource() == timer) {
			// move ball
		} else
			switch (e.getActionCommand()) {
			case ("Build Mode"):
				GizmoBallMain.gui.switchMode();
				System.out.println("Going into Build Mode!!");
			}
	}

}
