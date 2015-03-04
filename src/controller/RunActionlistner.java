package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;

import main.GizmoBallMain;
import model.Global;
import model.IModel;
import model.exceptions.IncorrectFileFormatException;
import view.FileChooser;
import view.GUI;
import view.IFileChooser;


public class RunActionlistner implements ActionListener {

	private IModel model;
	private Timer timer;
	private GUI gui;

	public RunActionlistner(IModel m, GUI g) {

		model = m;
		gui = g;
		timer = new Timer((int) Global.REFRESHTIME, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == timer) {
			model.moveBall();
		} else
			switch (e.getActionCommand()) {
			case ("Build Mode"):
				GizmoBallMain.gui.switchMode();
				break;
			case "Load":
				IFileChooser fc = new FileChooser();
				File file = fc.getFile();
				
				if (file == null){
					break;
				}
				
				try {
					model.loadBoard(file);
				} catch (IOException | IncorrectFileFormatException e1) {
					// TODO inform the user their file is dreadful
					e1.printStackTrace();
				}
				break;
			case "Save As":
				fc = new FileChooser();
				file = fc.saveFile();
				
				if (file == null){
					break;
				}
				
				try {
					model.saveBoard(file);
				} catch (IOException e1) {
					// TODO inform user we failed
					e1.printStackTrace();
				}
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
				timer.stop();
				gui.changeStartStop("Start");
				model.addBall();
				break;
			case ("Quit"):
				System.exit(0);
				break;
			}
		
	}
}
