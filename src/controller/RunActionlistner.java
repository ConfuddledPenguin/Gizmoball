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

	private File currentFile = null;
	
	
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
				
			case "Reload":
				
				if(currentFile != null){
					
					timer.stop();
					gui.changeStartStop("Start");
					
					try{
						model.loadBoard(currentFile);
					}catch (IOException | IncorrectFileFormatException e1){
						e1.printStackTrace();
					}
					
					break;
				}
				
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
					gui.displayErrorMessage(e1.getMessage());
				}
				
				currentFile = file;
				runKey.processkey(true);
				
				break;
			case "Save":
				
				if(currentFile != null){
					
					boolean start = timer.isRunning();
					timer.stop();
					
					try{
						model.saveBoard(currentFile);
					}catch(IOException e1){
						e1.printStackTrace();
					}
					
					if(start){
						timer.start();
					}
					
					break;
					
				}
				
			case "Save As":
				
				boolean start = timer.isRunning();
				timer.stop();
				runKey.processkey(false);
				
				fc = new FileChooser();
				file = null;
				try {
					file = fc.saveFile();
				} catch (IOException e2) {
					gui.displayErrorMessage(e2.getMessage());
				}
				
				if (file == null){
					break;
				}
				
				try {
					model.saveBoard(file);
				} catch (IOException e1) {
					gui.displayErrorMessage(e1.getMessage());
				}
				
				runKey.processkey(true);
				
				if(start){
					timer.start();
				}
				
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
