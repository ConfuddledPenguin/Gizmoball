package sound;

import javax.sound.midi.MidiChannel;
import model.gizmos.IGizmo;

public class GizmoSoundPlayer extends Thread{
	
	private IGizmo gizmo;
	private MidiChannel[] channels;
	
	public GizmoSoundPlayer(IGizmo g, MidiChannel[] chan) {
		channels = chan;
		gizmo = g;
	}
	
	@Override
	public void run() {
		int channel = 0; //type of instrument, 0 is piano, 9 is some random thing
		int volume = 100;
		int duration = 1000;
		int note = 0;
		
		switch (gizmo.getType()) {
		case Square:
			note = 29;
			break;
		case Circle:
			note = 53;
			break;
		case Absorber:
			note = 5;
		case RightFlipper:
		case LeftFlipper:
			note = 89;
			break;
		case Triangle:
			note = 101;
			break;
		}

		try {
			channels[channel].noteOn(note, volume); //turn on sound
			Thread.sleep(duration); //keep sound on
			channels[channel].noteOff(note); //turn off sound	
		}catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

}
