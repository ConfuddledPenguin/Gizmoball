package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

import view.GUI;
import main.GizmoBallMain;
import model.Global;
import model.Model;

public class RunActionlistner implements ActionListener {

	private Model model;
	private Timer timer;
	private GUI gui;

	public RunActionlistner(Model m, GUI g) {
		model = m;
		gui = g;
		timer = new Timer((int) Global.REFRESHTIME, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		System.out.println("Controller: The " + e.getActionCommand()
				+ " button is clicked at " + new java.util.Date(e.getWhen())
				+ " with e.paramString " + e.paramString());

		if (e.getSource() == timer) {
			System.out.println("timer.");
			model.moveBall();
		} else
			switch (e.getActionCommand()) {
			case ("Build Mode"):
				GizmoBallMain.gui.switchMode();
				System.out.println("Going into Build Mode!!");
				break;
			case ("Start"):
				timer.start();
				gui.changeStartStop("Stop");
				break;
			case ("Step"):
				model.moveBall();
				break;
			case ("Stop"):
				timer.stop();
				gui.changeStartStop("Start");
				break;
			case ("Restart"):
				break;
			case ("Quit"):
				System.exit(0);
				break;
			}
		
	}

}
