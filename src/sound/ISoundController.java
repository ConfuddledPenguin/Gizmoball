package sound;

import java.util.Observable;

import sound.SoundController.Mode;

public interface ISoundController {

	/**
	 * Sets the current mode to play back
	 * 
	 * @param mode Get mode
	 */
	public abstract void setMode(Mode mode);

	/**
	 * Plays the music
	 */
	public abstract void play();

	/**
	 * Stop playing the music
	 */
	public abstract void stop();

	/**
	 * Toggles the mute status of the gizmos to the value passed in
	 * 
	 * @param mute the mute status
	 */
	public abstract void muteGizmos(boolean mute);

	/**
	 * Toggles the mute status of the music to the value passed in
	 * 
	 * @param mute the mute status
	 */
	public abstract void muteMusic(boolean mute);

	/**
	 * Return the mute status of the gizmos
	 * 
	 * @return the status
	 */
	public abstract boolean muteGizmoState();

	/**
	 * Return the mute status of the music
	 * 
	 * @return the status
	 */
	public abstract boolean muteMusicState();

	/*
	 * (non-Javadoc)
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public abstract void update(Observable o, Object arg);

}