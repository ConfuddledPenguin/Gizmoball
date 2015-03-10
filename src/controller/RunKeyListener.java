package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.IModel;

/**
 * Run Key Listener for passing key press/releases to the model during the Run mode.
 *
 */
public class RunKeyListener implements KeyListener {
	
	private IModel model;
	private boolean processKey = true;

	/**
	 * @param game The Gizmoball model
	 */
	public RunKeyListener(IModel game) {
		this.model = game;
		
		/*
		 * Grab the focus manager so we are always listening to
		 * the keyboard, no matter what the ui focus is on
		 */
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new Dispatcher(this));
	}
	
	/**
	 * Process the key press
	 * 
	 * Set to false if you wish for the focus 
	 * to be on the swing component. Otherwise
	 * set to true;
	 * 
	 * @param process true for yes, otherwise false;
	 */
	public void processkey(boolean process){
		processKey = process;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		model.triggerKeyPress(arg0.getKeyCode(), true);
		System.out.println("key pressed " + arg0.getKeyCode());
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	
	@Override
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
	
	/**
	 * The dispatcher is responsible for handling 
	 * the key events and sending them to the focused
	 * component.
	 * 
	 * In this case it basically hijacks the key event
	 * and handles it its self.
	 * 
	 * @author Tom Maxwell
	 *
	 */
	private class Dispatcher implements KeyEventDispatcher{

		//This listener to call.
		private KeyListener listener;
		
		/**
		 * The constructor
		 * 
		 * @param listener the listener to use
		 */
		public Dispatcher(RunKeyListener listener) {
			this.listener = listener;
		}
		
		/**
		 * The dispatch key event
		 * 
		 * This is the overwritten method that deals with
		 * the handling of the event
		 * 
		 * Here we catch it and pass it to the listener
		 * specified
		 * 
		 * see {@link java.awt.keyEventDispatcher#dispatchKeyEvent(KeyEvent e) } for more info
		 * 
		 * @param e The event to dispatch
		 * 
		 * @return true if the KeyboardFocusManager should take 
		 * no further action with regard to the KeyEvent; 
		 * false otherwise
		 */
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			
			if(!processKey)
				return false;
			
			switch (e.getID()) {
				case KeyEvent.KEY_PRESSED:
					listener.keyPressed(e);
					break;
					
				case KeyEvent.KEY_RELEASED:
					listener.keyReleased(e);
					break;
				
				case KeyEvent.KEY_TYPED:
					listener.keyTyped(e);
					break;		
			}
			
			return true;
		}
	}
}