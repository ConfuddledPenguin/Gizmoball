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

import model.Global;
import model.gizmos.Gizmo;
import model.gizmos.Gizmo.TriggerType;
import model.gizmos.IGizmo;

public class SoundController implements Observer {

	private List<IGizmo> gizmoList = new ArrayList<IGizmo>();
	private List<IGizmo> triggered = new ArrayList<IGizmo>();
	private MidiChannel[] channels;
	
	private ExecutorService threadPool = Executors.newCachedThreadPool();
	
	private boolean raveMode = false;
	private boolean discoMode = false;
	
	public SoundController() {
		Synthesizer synth;
		try {
			synth = MidiSystem.getSynthesizer();
			synth.open();
			channels = synth.getChannels();
			
		} catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	
	private void triggerGizmoSounds() {
		
		for(IGizmo g: gizmoList){
			if(g.isTriggered() && !triggered.contains(g)){
				
				if(g.getTriggerType() == TriggerType.BALL){
					triggered.add(g);
					playGizmoSound(g);
				}
			}
			
			if (!g.isTriggered()){
				triggered.remove(g);
			}
		}

	}
	
	private void playGizmoSound(IGizmo g) {
		
		threadPool.execute(new GizmoSoundPlayer(g, channels));

	}
	
	@Override
	public void update(Observable o, Object arg) {
		
		if (arg instanceof Gizmo) {
			gizmoList.add((Gizmo) arg);
		} else if(arg instanceof List<?>){
			
			gizmoList = new ArrayList<IGizmo>( (List<IGizmo>) arg);
		}
		
		triggerGizmoSounds();
		

	}

}
