package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import main.GizmoBallMain;
import model.Global;
import model.IModel;
import model.exceptions.IncorrectFileFormatException;
import view.FileChooser;
import view.GUI;


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
				
				File portfolios = new File((System.getProperty("user.dir"))+"\\res");
				FileSystemView fsv = new RestrictedFileSystemView(new File[] {portfolios});
				
				JFileChooser jfc = new JFileChooser(fsv);
				jfc.setCurrentDirectory(portfolios);
				FileNameExtensionFilter filter = new FileNameExtensionFilter("TEXT FILES", "txt", "text");
				jfc.setFileFilter(filter);
				
				int returnVal = jfc.showOpenDialog(this.gui.frame);
				
				 if (returnVal == JFileChooser.APPROVE_OPTION) {
			            File file = jfc.getSelectedFile();
			            
			            System.out.println("Opening: " + file.getName() + ".");
			            try {
							model.loadBoard(file);
						} catch (IOException | IncorrectFileFormatException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
			
			        } else {
			        	System.out.println("Open command cancelled by user.");
			        }
				break;
			case "Save As":
				FileChooser fc = new FileChooser();
				File file = fc.saveFile();
				
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
