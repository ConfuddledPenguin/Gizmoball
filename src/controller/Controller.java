package controller;

import java.awt.event.ActionListener;

import model.IModel;
import sound.ISoundController;
import sound.SoundController;
import view.IGUI;

/**
 * The starting point for the gizmoball control system
 *
 */
public class Controller  {
	
	private ActionListener runListener;
	private ActionListener buildListener;
	private ActionListener settingsListener;
	private RunKeyListener runKeyListener;
	private IModel m;
	private IGUI g;
	
	private SoundController sc;
	
	/**
	 * The constructor
	 * 
	 * @param m The model to effect
	 * @param g The ui to sue
	 */
	public Controller(IModel m, IGUI g) {
		this.m = m;
		this.g = g;
		
		sc = new SoundController(g);
		m.addObserver(sc);

		runKeyListener =  new RunKeyListener(m, g);
		runListener = new RunActionlistner(m, g, runKeyListener, sc);
		runKeyListener.registerRunActionListener(runListener);
		buildListener = new BuildActionlistner(m, g, runKeyListener, sc);
		runKeyListener.registerBuildActionListener(buildListener);
		
		settingsListener = new SettingsListener(m, sc);
	}
	
	/**
	 * Returns the run listener
	 * 
	 * @return this.runListener
	 */
	public ActionListener getRunListener() {
		return runListener;
	}

	/**
	 * Returns the build listener
	 * 
	 * @return this.buildListener
	 */
	public ActionListener getBuildListener() {
		return buildListener;
	}

	/**
	 * Returns the key listener
	 * 
	 * @return this.keyListener
	 */
	public RunKeyListener getRunKeyListener() {
		return runKeyListener;
	}
	
	/**
	 * Returns the settings listener
	 * 
	 * @return this.settings listener
	 */
	public ActionListener getSettingsListener() {
		return settingsListener;
	}
	
	/**
	 * Get the sound controller
	 * 
	 * @return the sound controller
	 */
	public ISoundController getSoundController() {
		return sc;
	}
}
