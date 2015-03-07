package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.Timer;

import com.sun.jmx.snmp.Timestamp;

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
			long time = System.nanoTime()/ 1000/ 1000;
			model.moveBall();
			time = System.nanoTime() / 1000 / 1000 - time;
			
			int delay = (int) (Global.REFRESHTIME - time);
			
			if(delay < 0){ // drop a frame
				delay = (int) (Global.REFRESHTIME - delay);
				Global.MOVETIME = Global.MOVETIME * 2;
			}else{ // move for frame
				Global.MOVETIME = Global.REFRESHTIME / 1000;
			}
			
			timer.setDelay(delay);
		} else
			switch (e.getActionCommand()) {
			case ("Build Mode"):
				GizmoBallMain.gui.switchMode();
				timer.stop();
				gui.changeStartStop("Start");
				break;
			case "Load":
				
				timer.stop();
				
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
				model.addBall(19.5,18.5,0,-50);
				break;
			case ("Quit"):
				System.exit(0);
				break;
			}
		
	}
}
