package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import model.Global;
import model.Model;
import physics.Vect;
import controller.Controller;

public class GUI {

	public JFrame frame;
	public RunBoard runBoard;
	private Controller controller;
	private char mode;
	private Model model;
	private BuildBoard buildBoard;
	private JButton startStopButton;

	public GUI(char mode, Model m) {

		this.model = m;
		this.mode = mode;
		this.controller = new Controller(this.model, this);
		
		if (mode == 'r')
			createAndShowRunGUI();
		else if (mode == 'b')
			createAndShowBuildGUI();
	}
	
	private void createAndShowRunGUI() {

		frame = new JFrame("Gizmoball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		

		//int width = (Global.L * Global.BOARDHEIGHT);
		
		this.runBoard = new RunBoard((Global.L * Global.BOARDWIDTH), (Global.L * Global.BOARDHEIGHT), this.model);
		this.buildBoard = null;
		Container cp = frame.getContentPane();
		
		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());
		menuBar.add(createSettingsMenu());

		cp.add(menuBar, BorderLayout.PAGE_START);
		cp.add(createRunButtons(), BorderLayout.CENTER);
		cp.add(runBoard, BorderLayout.SOUTH);

		cp.addKeyListener(controller.getRunKeyListener());
		cp.setFocusable(true);
		cp.setFocusTraversalKeysEnabled(false);
		
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void createAndShowBuildGUI() {

		frame = new JFrame("Gizmoball (Build Mode)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container cp = frame.getContentPane();
		
		buildBoard = new BuildBoard(model, controller.getBuildListener(), this);
		runBoard = null;
		cp.add(buildBoard, BorderLayout.SOUTH);

		cp.add(createBuildButtons(), BorderLayout.PAGE_START);

		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createRunButtons() {

		JPanel buttons = new JPanel();
		Font gf = new Font("Calbri", Font.BOLD, 12);
		buttons.setLayout(new GridLayout(1, 4));

		startStopButton = new JButton("Start");
		startStopButton.setFont(gf);
		startStopButton.setMaximumSize(new Dimension(100, 100));
		startStopButton.addActionListener(controller.getRunListener());
		buttons.add(startStopButton);

		JButton button2 = new JButton("Step");
		button2.setFont(gf);
		button2.setMaximumSize(new Dimension(100, 100));
		button2.addActionListener(controller.getRunListener());
		buttons.add(button2);

		JButton button3 = new JButton("Restart");
		button3.setFont(gf);
		button3.setMaximumSize(new Dimension(100, 100));
		button3.addActionListener(controller.getRunListener());
		buttons.add(button3);

		JButton button4 = new JButton("Quit");
		button4.setFont(gf);
		button4.setMaximumSize(new Dimension(100, 100));
		button4.addActionListener(controller.getRunListener());
		buttons.add(button4);
		
		return buttons;
	}
	
	public void changeStartStop(String toWhat){
		startStopButton.setText(toWhat);
	}

	private JMenuBar createBuildButtons() {

		JMenuBar menuBar = new JMenuBar();

		menuBar.add(createFileMenu());
		menuBar.add(createLevelSettingsMenu());

		return menuBar;

	}

	private JMenu createFileMenu() {

		JMenu menuList = new JMenu("Menu");

		JMenuItem LoadButton = new JMenuItem("Load");
		LoadButton.addActionListener(controller.getRunListener());
		menuList.add(LoadButton);

		JMenuItem ReloadButton = new JMenuItem("Reload");
		ReloadButton.addActionListener(controller.getRunListener());
		menuList.add(ReloadButton);
		
		JMenuItem SaveButton = new JMenuItem("Save");
		SaveButton.addActionListener(controller.getRunListener());
		menuList.add(SaveButton);
		
		JMenuItem SaveAsButton = new JMenuItem("Save As");
		SaveAsButton.addActionListener(controller.getRunListener());
		menuList.add(SaveAsButton);

		JMenuItem ModeButton;

		if (this.mode == 'b'){
			ModeButton = new JMenuItem("Run Mode");
			ModeButton.addActionListener(controller.getBuildListener());
			controller.getRunKeyListener().buildMode(true);
		}else{
			ModeButton = new JMenuItem("Build Mode");
			ModeButton.addActionListener(controller.getRunListener());	
			controller.getRunKeyListener().buildMode(false);
		}

		menuList.add(ModeButton);

		JMenuItem QuitButton = new JMenuItem("Quit");
		QuitButton.addActionListener(controller.getRunListener());
		menuList.add(QuitButton);

		return menuList;
	}
	
	private JMenu createLevelSettingsMenu() {

		JMenu menuList = new JMenu("Level Settings");

		JMenuItem Friction = new JMenuItem("Friction");
		Friction.addActionListener(controller.getBuildListener());
		menuList.add(Friction);

		JMenuItem Gravity = new JMenuItem("Gravity");
		Gravity.addActionListener(controller.getBuildListener());
		menuList.add(Gravity);
		
		JMenu modes = new JMenu("Modes");
		
		JMenuItem normal = new JMenuItem("Normal");
		normal.addActionListener(controller.getBuildListener());
		modes.add(normal);
		
		JMenuItem disco = new JMenuItem("Disco");
		disco.addActionListener(controller.getBuildListener());
		modes.add(disco);
		
		JMenuItem rave = new JMenuItem("Rave");
		rave.addActionListener(controller.getBuildListener());
		modes.add(rave);
		
		menuList.add(modes);
		
		
		JMenuItem ClearBoard = new JMenuItem("Clear Board");
		ClearBoard.addActionListener(controller.getBuildListener());
		menuList.add(ClearBoard);

		return menuList;
	}
	
	private JMenu createSettingsMenu() {

		JMenu menuList = new JMenu("Settings");

		JMenuItem muteGizmos = new JMenuItem("Mute Gizmos");
		muteGizmos.addActionListener(controller.getSettingsListener());
		menuList.add(muteGizmos);
		
		JMenuItem muteMusic = new JMenuItem("Mute Music");
		muteMusic.addActionListener(controller.getSettingsListener());
		menuList.add(muteMusic);

		return menuList;
	}
	
	public Vect getBallVelocity(){
		
		float x = 0;
		float y = 0;
		
		JTextField xfield = new JTextField();
		JTextField yfield = new JTextField();
		Object[] input = {"X velocity", xfield, "Y velocity", yfield};
		boolean valid = false;
		
		// loop until valid values are entered by the user
		while (!valid) {
			int n = JOptionPane.showConfirmDialog(this.frame, input, "Enter the Balls velocity", JOptionPane.OK_CANCEL_OPTION);
			if (n == JOptionPane.OK_OPTION) {
				try {
					x = Float.parseFloat(xfield.getText());
					y = Float.parseFloat(yfield.getText());
					valid = true;
				}
				catch (NumberFormatException e) {
					String error = "Error: Please enter numeric values for x and y";
					JOptionPane.showMessageDialog(this.frame, error, "Error",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			// user clicked cancel
			if (n == JOptionPane.CANCEL_OPTION) {
				return null;
			}	
			
		}
		
		return new Vect(x, y*-1);  
		
	}
	
	/**
	 * Prompt the user to enter numeric values for friction (mu and mu2)
	 * @return array containing values for mu and mu2. Null on cancel
	 */
	public float[] getUserFriction() {
		float[] friction = new float[2];
		JTextField mu = new JTextField();
		JTextField mu2 = new JTextField();
		Object[] input = {"Friction MU", mu, "Friction MU2", mu2};
		boolean valid = false;
		
		// loop until valid values are entered by the user
		while (!valid) {
			int n = JOptionPane.showConfirmDialog(this.frame, input, "Enter Friction", JOptionPane.OK_CANCEL_OPTION);
			if (n == JOptionPane.OK_OPTION) {
				try {
					friction[0] = Float.parseFloat(mu.getText());
					friction[1] = Float.parseFloat(mu2.getText());
					valid = true;
				}
				catch (NumberFormatException e) {
					String error = "Error: Please enter numeric values for MU and MU2";
					JOptionPane.showMessageDialog(this.frame, error, "Error",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			// user clicked cancel
			if (n == JOptionPane.CANCEL_OPTION) {
				return null;
			}	
			
		}
		return friction;
	}
	
	/**
	 * Shows a message informing the user of how to 
	 * connect gizmos together.
	 * 
	 */
	public void showConnectMessage(){
		
		int n = JOptionPane.showConfirmDialog(this.frame, "Next select he gizmo you wish to trigger", null, JOptionPane.OK_CANCEL_OPTION);
	
		if(n == JOptionPane.CANCEL_OPTION){
			buildBoard.cancelGizmoConnect();
		}
	}
	
	/**
	 * Alert user to successful connection
	 */
	public void showGizmoConnectedMessage(){
		JOptionPane.showConfirmDialog(this.frame, "Gizmos connected", null, JOptionPane.CLOSED_OPTION);
	}
	
	/**
	 * Prompt the user to enter a numeric value for gravity
	 * @return users value for gravity. Null on cancel
	 */
	public Double getUserGravity() {
		JTextField g = new JTextField();
		Object[] input = {"Gravity", g};
		boolean valid = false;
		
		// loop until a valid value is entered by the user
		while (!valid) {
			int n = JOptionPane.showConfirmDialog(this.frame, input, "Enter Gravity", JOptionPane.OK_CANCEL_OPTION);
			if (n == JOptionPane.OK_OPTION) {
				try {
					Double.parseDouble(g.getText());
					valid = true;
				}
				catch (NumberFormatException e) {
					String error = "Error: Please enter a numeric value for gravity";
					JOptionPane.showMessageDialog(this.frame, error, "Error",JOptionPane.INFORMATION_MESSAGE);
				}
			}
			
			// user clicked cancel
			if (n == JOptionPane.CANCEL_OPTION) {
				return null;
			}	
		}
		return Double.parseDouble(g.getText());
	}
	
	public void displayErrorMessage(String msg){
		JOptionPane.showConfirmDialog(this.frame, msg, null, JOptionPane.CLOSED_OPTION, JOptionPane.ERROR_MESSAGE);
	}


	public String switchMode() {
		if (this.mode == 'r') {
			this.mode = 'b';
			frame.dispose();
			createAndShowBuildGUI();
			return "Switch to Build Mode.";
		} else {
			this.mode = 'r';
			frame.dispose();
			createAndShowRunGUI();
			return "Switch to Build Mode.";
		}
		
		
	}
	
	public Point getClickedCell() {
		Point cell = buildBoard.getclickedCell();
		if (cell == null)
			return new Point(0,0);
		return cell;
	}
	
	public Point getMovedPoint(){
		return this.buildBoard.getMoveTarget();
	}

	public void setAbsorberStart(Point p) {
		this.buildBoard.setAbsorberStart(p);
	}
	
	public void setGizmoConnecting(ActionListener listener){
		this.buildBoard.connectingGizmos(listener);
		showConnectMessage();
	}
}
