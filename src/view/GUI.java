package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Point;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import model.Model;
import controller.Controller;

public class GUI {

	private JFrame frame;
	private RunBoard runBoard;
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

		this.runBoard = new RunBoard(600, 600, this.model);
		this.buildBoard = null;
		Container cp = frame.getContentPane();

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());

		cp.add(menuBar, BorderLayout.PAGE_START);
		cp.add(createRunButtons(), BorderLayout.CENTER);
		cp.add(runBoard, BorderLayout.SOUTH);

		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void createAndShowBuildGUI() {

		frame = new JFrame("Gizmoball (Build Mode)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		Container cp = frame.getContentPane();
		
		buildBoard = new BuildBoard(model, controller.getBuildListener());
		runBoard = null;
		cp.add(buildBoard, BorderLayout.SOUTH);

		cp.add(createBuildButtons(), BorderLayout.PAGE_START);
		//cp.add(board, BorderLayout.SOUTH);

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
		menuBar.add(createBuildMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createSettingsMenu());

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
		
		JMenuItem SaveAsButton = new JMenuItem("Save As");
		SaveAsButton.addActionListener(controller.getRunListener());
		menuList.add(SaveAsButton);

		JMenuItem ModeButton;

		if (this.mode == 'b')
			ModeButton = new JMenuItem("Run Mode");
		else
			ModeButton = new JMenuItem("Build Mode");

		ModeButton.addActionListener(controller.getRunListener());
		ModeButton.addActionListener(controller.getBuildListener());
		menuList.add(ModeButton);

		JMenuItem QuitButton = new JMenuItem("Quit");
		QuitButton.addActionListener(controller.getRunListener());
		menuList.add(QuitButton);

		return menuList;
	}
	
	private JMenu createSettingsMenu() {

		JMenu menuList = new JMenu("Settings");

		JMenuItem Friction = new JMenuItem("Friction");
		Friction.addActionListener(controller.getBuildListener());
		menuList.add(Friction);

		JMenuItem Gravity = new JMenuItem("Gravity");
		Gravity.addActionListener(controller.getBuildListener());
		menuList.add(Gravity);

		JMenuItem Connect = new JMenuItem("Connect");
		Connect.addActionListener(controller.getBuildListener());
		menuList.add(Connect);

		JMenuItem Disconnect = new JMenuItem("Disconnect");
		Disconnect.addActionListener(controller.getBuildListener());
		menuList.add(Disconnect);

		return menuList;
	}

	private JMenu createEditMenu() {

		JMenu menuList = new JMenu("Edit");

		JMenuItem Rotate = new JMenuItem("Rotate");
		Rotate.addActionListener(controller.getBuildListener());
		menuList.add(Rotate);

		JMenuItem Move = new JMenuItem("Move");
		Move.addActionListener(controller.getBuildListener());
		menuList.add(Move);

		JMenuItem Delete = new JMenuItem("Delete");
		Delete.addActionListener(controller.getBuildListener());
		menuList.add(Delete);

		JMenuItem ClearBoard = new JMenuItem("Clear Board");
		ClearBoard.addActionListener(controller.getBuildListener());
		menuList.add(ClearBoard);

		return menuList;
	}

	private JMenu createBuildMenu() {
		
		JMenu menuList = new JMenu("Build");

		menuList.add(createGizmoMenu());

		JMenuItem AddBall = new JMenuItem("Add Ball");
		AddBall.addActionListener(controller.getBuildListener());
		menuList.add(AddBall);

		menuList.add(createFlipperMenu());

		JMenuItem AddAbsorber = new JMenuItem("Add Absorber");
		AddAbsorber.addActionListener(controller.getBuildListener());
		menuList.add(AddAbsorber);

		return menuList;
	}
	
	private JMenu createGizmoMenu(){
	
		JMenu AddGizmo = new JMenu("AddGizmo");
		
		JMenuItem AddSqaure = new JMenuItem("Square");
		AddSqaure.addActionListener(controller.getBuildListener());
		AddGizmo.add(AddSqaure);
		
		JMenuItem AddCircle = new JMenuItem("Circle");
		AddCircle.addActionListener(controller.getBuildListener());
		AddGizmo.add(AddCircle);	
		
		JMenu addTriangle = new JMenu("Triangle");
		
		JMenuItem leftTriangle = new JMenuItem("Left Triangle");
		leftTriangle.addActionListener(controller.getBuildListener());
		addTriangle.add(leftTriangle);
		
		JMenuItem rightTriangle = new JMenuItem("Right Triangle");
		rightTriangle.addActionListener(controller.getBuildListener());
		addTriangle.add(rightTriangle);
		
		AddGizmo.add(addTriangle);
		
		return AddGizmo;
	}
	
	private JMenu createFlipperMenu(){
		
		JMenu flipperMenu = new JMenu("Add Flipper");
		
		JMenuItem Left = new JMenuItem("Left Flipper");
		Left.addActionListener(controller.getBuildListener());
		flipperMenu.add(Left);
		
		JMenuItem Right = new JMenuItem("Right Flipper");
		Right.addActionListener(controller.getBuildListener());
		flipperMenu.add(Right);	
		
		return flipperMenu;
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
		return this.buildBoard.getMousePt();
	}
}
