package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import model.IModel;

/**
 * Run Key Listener for passing key press/releases to the model during the Run mode.
 *
 */
public class RunKeyListener implements KeyListener {
	
	private ActionListener run;
	private ActionListener build;
	private IModel model;
	private boolean processKey = true;
	private boolean processBuildMode = true;
	
	private int lastKey = -1;
	private int secondLastKey = -1;
	

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
	 * Register the run listener 
	 * 
	 * @param run The run listener
	 */
	public void registerRunActionListener(ActionListener run){
		
		this.run = run;		
	}
	
	/**
	 * Register the build listener 
	 * 
	 * @param build The build listener
	 */
	public void registerBuildActionListener(ActionListener build){
		
		this.build = build;		
	}
	
	/**
	 * Process the build mode key strokes
	 * 
	 * @param process true for process else false
	 */
	public void buildMode(boolean process){
		this.processBuildMode = process;
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
		
		if(arg0.getKeyChar() != lastKey){
			secondLastKey = lastKey;
			lastKey = arg0.getKeyCode();
		}
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */
	
	@Override
	public void keyReleased(KeyEvent arg0) {
		
		int keyCode = arg0.getKeyCode();
		
		//On alt
		if(secondLastKey == 18){
			
			if(keyCode == 115)
				System.exit(0);
		}
		
		//On ctrl
		if(secondLastKey == 17){
			
			if(keyCode == 83){ // s
				run.actionPerformed(new ActionEvent(this, 0, "Save"));				
			}
			
			if(keyCode == 76) // l
				run.actionPerformed(new ActionEvent(this, 0, "Load"));
			
			if(keyCode == 82) // r
				run.actionPerformed(new ActionEvent(this, 0, "Load"));
			
			if(keyCode == 87 && processBuildMode) // w
				build.actionPerformed(new ActionEvent(this, 0, "Clear Board"));
		}
		
		model.triggerKeyPress(arg0.getKeyCode(), false);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyTyped(java.awt.event.KeyEvent)
	 */
	@Override
	public void keyTyped(KeyEvent e) {
		
		
		
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