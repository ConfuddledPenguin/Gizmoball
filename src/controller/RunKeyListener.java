package controller;

import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import view.Board;
import view.IBuildBoard;
import view.IGUI;
import model.IModel;

/**
 * Run Key Listener for passing key press/releases to the model during the Run mode.
 *
 */
public class RunKeyListener implements KeyListener {
	
	private ActionListener run;
	private ActionListener build;
	private IModel model;
	private IGUI ui;
	private boolean processKey = true;
	private boolean processBuildMode = true;
	
	/**
	 * All the keys that have ever been pressed...
	 * 
	 * kinda
	 */
	private int[] keyArray = new int[10];
	
	/**
	 * The disco key order
	 */
	private int[] discoKeys = {38,38,37,40,37,38};
	
	/**
	 * The rave key order
	 */
	private int[] raveKeys = {38,39,37,38,40,38};
	

	/**
	 * @param game The Gizmoball model
	 */
	public RunKeyListener(IModel game, IGUI ui) {
		this.model = game;
		this.ui = ui;
		
		
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
	
	private void checkForDisco(){
		
		int start = keyArray.length - discoKeys.length;
		for(int i = 0; i < discoKeys.length; i++){
			if(discoKeys[i] != keyArray[start + i])
				return;
		}
		build.actionPerformed(new ActionEvent(this, 0, "Disco"));
		
	}
	
	/**
	 * Check for rave mode
	 */
	private void checkForRave(){
		
		int start = keyArray.length - raveKeys.length;
		for(int i = 0; i < raveKeys.length; i++){
			if(raveKeys[i] != keyArray[start + i])
				return;
		}
		build.actionPerformed(new ActionEvent(this, 0, "Rave"));
		
	}
	
	/**
	 * Add a key to the keylist
	 * 
	 * @param key the key to add
	 */
	private void addKey(int key){
		
		for (int i = 0; i < keyArray.length-1; i++){
			keyArray[i] = keyArray[i+1];
		}
		
		keyArray[keyArray.length-1] = key;
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyPressed(java.awt.event.KeyEvent)
	 */
	
	@Override
	public void keyPressed(KeyEvent arg0) {
		model.triggerKeyPress(arg0.getKeyCode(), true);
	}

	/* (non-Javadoc)
	 * @see java.awt.event.KeyListener#keyReleased(java.awt.event.KeyEvent)
	 */	
	@Override
	public void keyReleased(KeyEvent arg0) {
		int keyCode = arg0.getKeyCode();
		addKey(keyCode);
		
		IBuildBoard board = ui.getBuildBoard();
		if (board != null) {  // check if we are in build mode
			ConnectKeyListener connectingKey = (ConnectKeyListener)board.getConnectingKey();
			if (connectingKey != null) {  // check if this key press is to be assigned to a gizmo as a trigger
				connectingKey.setKey(keyCode);
				connectingKey.actionPerformed(null);  // updates the model with the new key -> gizmo connection
				board.setConnectingKey(null);  // notify the view we are no longer waiting for a key press for this connection
				return;
			}
		}
		
		//On alt
		if(keyArray[keyArray.length -2] == 18){
			
			if(keyCode == 115)
				System.exit(0);
		}
		
		//On ctrl
		if(keyArray[keyArray.length -2] == 17){
			
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
		
		checkForDisco();
		checkForRave();
		
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