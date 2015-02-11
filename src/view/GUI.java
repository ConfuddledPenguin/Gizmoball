package view;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionListener;

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
	private Board board;
	private ActionListener controller;
	private char mode;
	private Model model;

	public GUI(char mode, Model m) {

		this.model = m;
		this.mode = mode;
		this.controller = new Controller(this.model);

		if (mode == 'r')
			createAndShowRunGUI();
		else if (mode == 'b')
			createAndShowBuildGUI();
	}

	private void createAndShowRunGUI() {

		frame = new JFrame("Gizmoball");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new Board(500, 500, this.model);
		Container cp = frame.getContentPane();

		JMenuBar menuBar = new JMenuBar();
		menuBar.add(createFileMenu());

		cp.add(menuBar, BorderLayout.PAGE_START);
		cp.add(createRunButtons(), BorderLayout.CENTER);
		cp.add(board, BorderLayout.SOUTH);

		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private void createAndShowBuildGUI() {

		frame = new JFrame("Gizmoball (Build Mode)");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		board = new Board(500, 500, this.model);
		Container cp = frame.getContentPane();

		cp.add(createBuildButtons(), BorderLayout.PAGE_START);
		cp.add(board, BorderLayout.SOUTH);

		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}

	private JPanel createRunButtons() {

		JPanel buttons = new JPanel();
		Font gf = new Font("Calbri", Font.BOLD, 12);
		buttons.setLayout(new GridLayout(1, 4));

		JButton button1 = new JButton("Start");
		button1.setFont(gf);
		button1.setMaximumSize(new Dimension(100, 100));
		button1.addActionListener(controller);
		buttons.add(button1);

		JButton button2 = new JButton("Step");
		button2.setFont(gf);
		button2.setMaximumSize(new Dimension(100, 100));
		button2.addActionListener(controller);
		buttons.add(button2);

		JButton button3 = new JButton("Restart");
		button3.setFont(gf);
		button3.setMaximumSize(new Dimension(100, 100));
		button3.addActionListener(controller);
		buttons.add(button3);

		JButton button4 = new JButton("Quit");
		button4.setFont(gf);
		button4.setMaximumSize(new Dimension(100, 100));
		button4.addActionListener(controller);
		buttons.add(button4);

		return buttons;
	}

	private JMenuBar createBuildButtons() {

		JMenuBar menuBar = new JMenuBar();

		menuBar.add(createFileMenu());
		menuBar.add(createBuildMenu());
		menuBar.add(createEditMenu());
		menuBar.add(createSettingsMenu());

		return menuBar;

	}

	private JMenu createSettingsMenu() {

		JMenu menuList = new JMenu("Settings");

		JMenuItem Friction = new JMenuItem("Friction");
		Friction.addActionListener(controller);
		menuList.add(Friction);

		JMenuItem Gravity = new JMenuItem("Gravity");
		Gravity.addActionListener(controller);
		menuList.add(Gravity);

		JMenuItem Connect = new JMenuItem("Connect");
		Connect.addActionListener(controller);
		menuList.add(Connect);

		JMenuItem Disconnect = new JMenuItem("Disconnect");
		Disconnect.addActionListener(controller);
		menuList.add(Disconnect);

		return menuList;
	}

	private JMenu createEditMenu() {

		JMenu menuList = new JMenu("Edit");

		JMenuItem Rotate = new JMenuItem("Rotate");
		Rotate.addActionListener(controller);
		menuList.add(Rotate);

		JMenuItem Move = new JMenuItem("Move");
		Move.addActionListener(controller);
		menuList.add(Move);

		JMenuItem Delete = new JMenuItem("Delete");
		Delete.addActionListener(controller);
		menuList.add(Delete);

		JMenuItem ClearBoard = new JMenuItem("Clear Board");
		ClearBoard.addActionListener(controller);
		menuList.add(ClearBoard);

		return menuList;
	}

	private JMenu createBuildMenu() {

		JMenu menuList = new JMenu("Build");

		JMenuItem AddGizmo = new JMenuItem("Add Gizmo");
		AddGizmo.addActionListener(controller);
		menuList.add(AddGizmo);

		JMenuItem AddBall = new JMenuItem("Add Ball");
		AddBall.addActionListener(controller);
		menuList.add(AddBall);

		JMenuItem AddFlipper = new JMenuItem("Add Flipper");
		AddFlipper.addActionListener(controller);
		menuList.add(AddFlipper);

		JMenuItem AddAbsorber = new JMenuItem("AddAbsorber");
		AddAbsorber.addActionListener(controller);
		menuList.add(AddAbsorber);

		return menuList;
	}

	private JMenu createFileMenu() {

		JMenu menuList = new JMenu("Menu");

		JMenuItem LoadButton = new JMenuItem("Load");
		LoadButton.addActionListener(controller);
		menuList.add(LoadButton);

		JMenuItem ReloadButton = new JMenuItem("Reload");
		ReloadButton.addActionListener(controller);
		menuList.add(ReloadButton);

		JMenuItem ModeButton;

		if (this.mode == 'b')
			ModeButton = new JMenuItem("Run Mode");
		else
			ModeButton = new JMenuItem("Build Mode");

		ModeButton.addActionListener(controller);
		menuList.add(ModeButton);

		JMenuItem QuitButton = new JMenuItem("Quit");
		QuitButton.addActionListener(controller);
		menuList.add(QuitButton);

		return menuList;
	}

	public String switchMode() {
		if (this.mode == 'r') {
			this.mode = 'b';
			return "Switch to Build Mode.";
		} else {
			this.mode = 'r';
			return "Switch to Build Mode.";
		}
	}
}