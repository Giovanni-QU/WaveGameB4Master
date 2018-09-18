package mainGame;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequencer;

// The complex version of a Midi Player. Includes ability to loop continously and checks opens streams and playing status.

public class Midi {
	
	float beatsPerMinute;
	Sequencer sequencer;
	boolean isOpen;
	boolean isPlaying;
	
	public Midi() {
		try {
			// Obtains the default Sequencer connected to a default device.
			sequencer = MidiSystem.getSequencer();
		} 
		catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
		
	}
	/**
	 *  Sets the looping to continuous, inputs the file to play, and plays the MIDI music. 
	 * @param fileName The name of the MIDI file that plays.
	 * @throws IOException
	 * @throws InvalidMidiDataException
	 * @throws MidiUnavailableException
	 */
	public void PlayMidi(String fileName) throws IOException, InvalidMidiDataException, MidiUnavailableException {
		if (isPlaying == false) {
			sequencer.open();
			isOpen = true;
			
			// create a stream from a file
			InputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
			
        	// Sets the current sequence on which the sequencer operates.
			// The stream must point to MIDI file data.
			sequencer.setSequence(is);
			
        	// Starts playback of the MIDI data in the currently loaded sequence.
			sequencer.setLoopStartPoint(0);
			sequencer.setLoopEndPoint(sequencer.getTickLength());
			sequencer.setLoopCount(sequencer.LOOP_CONTINUOUSLY);
			
			beatsPerMinute = sequencer.getTempoInBPM();
			sequencer.start();
			isPlaying = true;
        }
	}
	/**
	 * Stops the Midi class from playing what it was currently playing.
	 */
	public void StopMidi() {
		if (isOpen == true && isPlaying == true) {
			sequencer.stop();
			isOpen = false;
			isPlaying = false;
		}
		else {
		}
	}
	public float getTempo() {
		return beatsPerMinute;
	}
	public void setTempo(float i) {
		sequencer.setTempoInBPM(i);
	}
}

