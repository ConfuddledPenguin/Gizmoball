package sound;

import javax.sound.midi.MidiChannel;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Synthesizer;

import model.gizmos.Gizmo.TriggerType;
import model.gizmos.Gizmo.Type;
import model.gizmos.IGizmo;

public class GizmoSoundPlayer extends Thread{
	
	private IGizmo gizmo;
	
	public GizmoSoundPlayer(IGizmo g) {
		
		gizmo = g;
	}
	
	@Override
	public void run() {
		System.out.println("Sound for " + gizmo.getType());
		
		int channel = 0;

		int volume = 127;
		int duration = 200;
		
		int note = 0;
		
		switch (gizmo.getType()) {
		case Square:
			note = 5;
			break;
		case Circle:
			note = 10;
			break;
		case Absorber:
			note = 0;
		case RightFlipper:
		case LeftFlipper:
			note = 40;
			break;
		case Triangle:
			note = 60;
			break;
		}

		try {
			Synthesizer synth = MidiSystem.getSynthesizer();
			synth.open();
			MidiChannel[] channels = synth.getChannels();
			
			channels[channel].noteOn(note, volume);
			
			Thread.sleep(duration);
			
			channels[channel].noteOff(note);
			
			synth.close();
		}catch(MidiUnavailableException e){
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
