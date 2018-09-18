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

// The simplistic version of a Midi Player. Does not loop and is intended for recurring sounds, such as hitsounds.

public class SimpleMidi {
	Sequencer sequencer;
	public SimpleMidi() {
		try {
			sequencer = MidiSystem.getSequencer();
		} 
		catch (MidiUnavailableException e) {
			e.printStackTrace();
		}
	}
	public void PlayMidi(String fileName) throws IOException, InvalidMidiDataException, MidiUnavailableException {
		sequencer.open();
        InputStream is = new BufferedInputStream(new FileInputStream(new File(fileName)));
        sequencer.setSequence(is);
        sequencer.start();
	}
}
