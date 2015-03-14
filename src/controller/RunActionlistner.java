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
	private RunKeyListener runKey;

	public RunActionlistner(IModel m, GUI g, RunKeyListener runKey) {

		model = m;
		gui = g;
		this.runKey = runKey;
		
		timer = new Timer((int) Global.REFRESHTIME, this);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == timer) {
			
			//get time
			long time = System.nanoTime()/ 1000/ 1000;
			
			//perform update
			model.update();
			
			//compensate for update time
			time = System.nanoTime() / 1000 / 1000 - time;
			int delay = (int) (Global.REFRESHTIME - time);
			
			if(delay < 0){ // drop frames
				
				if(delay < Global.REFRESHTIME){ // if one frame
				
					delay = (int) (Global.REFRESHTIME - delay);
					Global.MOVETIME = Global.MOVETIME * 2;
				}else{
					delay *= -1;
					
					int frames = (int) (delay / Global.REFRESHTIME);
					Global.MOVETIME *= frames;
					delay = (int) Global.REFRESHTIME;					
				}
			}else{ // move for frame
				Global.MOVETIME = Global.REFRESHTIME / 1000;
			}
			
			//set timer
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
				gui.changeStartStop("Start");
				runKey.processkey(false);
				
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
				
				runKey.processkey(true);
				
				break;
			case "Save":
			case "Save As":
				
				timer.stop();
				gui.changeStartStop("Start");
				runKey.processkey(false);
				
				fc = new FileChooser();
				file = null;
				try {
					file = fc.saveFile();
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
				
				if (file == null){
					break;
				}
				
				try {
					model.saveBoard(file);
				} catch (IOException e1) {
					// TODO inform user we failed
					e1.printStackTrace();
				}
				
				runKey.processkey(true);
				timer.start();
				
				break;
			case ("Start"):
				timer.start();
				gui.changeStartStop("Stop");
				break;
			case ("Step"):
				model.update();
				break;
			case ("Stop"):
				timer.stop();
				gui.changeStartStop("Start");
				break;
			case ("Restart"):
				timer.stop();
				gui.changeStartStop("Start");
				model.reset();
				break;
			case ("Quit"):
				System.exit(0);
				break;
			}
		
	}
}
