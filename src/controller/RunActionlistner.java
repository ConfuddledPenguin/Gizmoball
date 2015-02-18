package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;

import view.FileChooser;
import view.IFileChooser;
import main.GizmoBallMain;
import model.IModel;
import model.exceptions.IncorrectFileFormatException;

public class RunActionlistner implements ActionListener {

	private IModel model;
	private Timer timer;

	public RunActionlistner(IModel m) {
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
			}
	}
}
