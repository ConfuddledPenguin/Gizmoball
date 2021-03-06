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
import sound.ISoundController;
import sound.SoundController.Mode;
import view.FileChooser;
import view.IGUI;
import view.IFileChooser;

/**
 * This controller listens for events coming from the run
 * portion of gizmoball
 */
public class RunActionlistner implements ActionListener {

	private IModel model;
	private Timer timer;
	private IGUI gui;
	private ISoundController sc;
	private RunKeyListener runKey;

	private File currentFile = null;
	
	/**
	 * The constructor
	 * 
	 * @param m  the model
	 * @param g The gui
	 * @param runKey The run keylistener
	 */
	public RunActionlistner(IModel m, IGUI g, RunKeyListener runKey, ISoundController sc) {

		model = m;
		gui = g;
		this.runKey = runKey;
		this.sc = sc;
		
		timer = new Timer((int) Global.REFRESHTIME, this);
	}
	
	/*
	 * (non-Javadoc)
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		
		if (e.getSource() == timer) {
			
			//get time
			long time = System.nanoTime()/ 1000/ 1000;
			
			//perform update
			model.update();
			gui.getRunBoard().nextColour();
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
				sc.stop();
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
				sc.stop();
				gui.changeStartStop("Start");
				runKey.processkey(false);
				
				IFileChooser fc = new FileChooser();
				File file = fc.getFile();
				
				if (file == null){
					runKey.processkey(true);
					sc.play();
					break;
				}
				
				try {
					model.loadBoard(file);
				} catch (IOException | IncorrectFileFormatException e1) {
					gui.displayErrorMessage(e1.getMessage());
				}
				
				currentFile = file;
				runKey.processkey(true);
				sc.setMode(Mode.normalMode);
				Global.raveMode = false;
				Global.discoMode = false;
				
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
					runKey.processkey(true);
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
				sc.play();
				break;
			case ("Step"):
				model.update();
				break;
			case ("Stop"):
				timer.stop();
				gui.changeStartStop("Start");
				sc.stop();
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
