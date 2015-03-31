package sound;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

import sound.SoundController.Mode;
import view.GUI;

/**
 * Plays music
 */
class MusicPlayer{
	
	private AudioInputStream inFile1;
	private AudioInputStream inFile2;
	private Clip clip;
	private Mode currentMode = Mode.normalMode;
	
	private GUI ui;
	
	private boolean playing = false;
	
	private URL file1;
	private URL file2;
	
	/**
	 * The constructor
	 * 
	 * @param ui The ui to show errors on
	 */
	public MusicPlayer(GUI ui) {
		this.ui = ui;
		
		file1 = this.getClass().getResource("soundfiles/RaveTunnnnnnne.wav");
		file2 = this.getClass().getResource("soundfiles/file2.wav");

		try {
			inFile1 = AudioSystem.getAudioInputStream(file1);
			inFile2 = AudioSystem.getAudioInputStream(file2);
			clip = AudioSystem.getClip();
		} catch (UnsupportedAudioFileException e) {
			ui.displayErrorMessage("Unsupported file tupe, try a .wav file");
		} catch (IOException e) {
			ui.displayErrorMessage("Error reading music file");
		} catch (LineUnavailableException e) {
			ui.displayErrorMessage("LineUnavailable");
		}
	}
	
	/**
	 * Play the current file
	 */
	public void play(){
		
		System.out.println("Asked to play");
		
		playing = true;
		
		if(playing){
			clip.stop();
			clip.close();
		}
		
		if(currentMode == Mode.normalMode)
			return;
		
		AudioInputStream in = null;
		if(currentMode == Mode.raveMode)
			in = inFile1;
		else if (currentMode == Mode.discoMode)
			in = inFile2;
		
		System.out.println(in);
		
		try {
			clip.open(in);
		} catch (LineUnavailableException e) {
			ui.displayErrorMessage("Line unavailable");
		} catch (IOException e) {
			ui.displayErrorMessage("Error reading in music file");
		} catch (IllegalStateException e) {
			ui.displayErrorMessage("Need to stop music");
		}
		
		clip.loop(Clip.LOOP_CONTINUOUSLY);
		clip.setFramePosition(0);
		clip.start();
		
		System.out.println(clip);
	}
	
	/**
	 * Stop playing the current file
	 */
	public void stop(){
		
		if(currentMode == Mode.normalMode)
			return;
		
		
		try {
			if(currentMode == Mode.raveMode){
			inFile1.close();
			inFile1 = AudioSystem.getAudioInputStream(file1);
		}else if(currentMode == Mode.discoMode){
			inFile2.close();
			inFile2 = AudioSystem.getAudioInputStream(file2);
		}
		} catch (IOException | UnsupportedAudioFileException e) {
			ui.displayErrorMessage("Things went badly");
		}
			
		
		clip.stop();
		clip.close();
		
		playing = false;
	}
	
	/**
	 * Swap the current mode to the given mode
	 * 
	 * @param mode The mode to swap to
	 */
	public void mode(Mode mode){
		
		currentMode = mode;
		
		if(playing){
			play();
		}
	}
}
