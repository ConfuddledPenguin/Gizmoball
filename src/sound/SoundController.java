package sound;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import model.gizmos.Gizmo.TriggerType;
import model.gizmos.IGizmo;
import view.IGUI;

/**
 * Controls the playback of sounds to the user
 *
 */
public class SoundController implements Observer, ISoundController {

	/**
	 * Enum to represent the mode to play back in
	 *
	 */
	public enum Mode{
		
		normalMode,
		raveMode,
		discoMode;
	}
	
	private List<IGizmo> gizmoList = new ArrayList<IGizmo>();
	private List<IGizmo> triggered = new ArrayList<IGizmo>();
	private MidiChannel[] channels;
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private MusicPlayer player;
	
	private IGUI ui;
	
	private boolean muteMusic = false;
	private boolean muteGizmos = false;
	
	/**
	 * The constructor
	 * 
	 * @param ui The ui to display errors on
	 */
	public SoundController(IGUI ui) {
		this.ui = ui;
		
		player = new MusicPlayer(ui);
		
		Synthesizer synth;
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
			
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#setMode(sound.SoundController.Mode)
	 */
	@Override
	public void setMode(Mode mode){
		player.mode(mode);
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#play()
	 */
	@Override
	public void play(){
		
		if(!muteMusic){
			player.play();
		}
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#stop()
	 */
	@Override
	public void stop(){
		player.stop();
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#muteGizmos(boolean)
	 */
	@Override
	public void muteGizmos(boolean mute){
		muteGizmos = mute;
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#muteMusic(boolean)
	 */
	@Override
	public void muteMusic(boolean mute){
		muteMusic = mute;
		stop();
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#muteGizmoState()
	 */
	@Override
	public boolean muteGizmoState(){
		return muteGizmos;
	}
	
	/* (non-Javadoc)
	 * @see sound.ISoundController#muteMusicState()
	 */
	@Override
	public boolean muteMusicState(){
		return muteMusic;
	}
	
	/**
	 * Triggers the gizmo sounds
	 */
	private void triggerGizmoSounds() {
		
		if(muteGizmos)
			return;
		
		for(IGizmo g: gizmoList){
			if(g.isTriggered() && !triggered.contains(g)){
				
				if(g.getTriggerType() == TriggerType.BALL){
					triggered.add(g);
					threadPool.execute(new GizmoSoundPlayer(g, ui, channels));
				}
			}
			
			if (!g.isTriggered()){
				triggered.remove(g);
			}
		}

	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof IGizmo) {
			gizmoList.add((IGizmo) arg);
		} else if(arg instanceof List<?>){
			
			gizmoList = new ArrayList<IGizmo>( (List<IGizmo>) arg);
		}
		
		triggerGizmoSounds();
	}
}
