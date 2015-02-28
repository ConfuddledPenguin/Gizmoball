package controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.IModel;

/**
 * Run Key Listener for passing key press/releases to the model during the Run mode.
 *
 */
public class RunKeyListener implements KeyListener {
	private IModel model;

	/**
	 * @param game The Gizmoball model
	 */
	public RunKeyListener(IModel game) {
		this.model = game;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	public void keyPressed(KeyEvent arg0) {
		model.triggerKeyPress(arg0.getKeyCode(), true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	public void keyReleased(KeyEvent arg0) {
		model.triggerKeyPress(arg0.getKeyCode(), false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		// do nothing
	}
}