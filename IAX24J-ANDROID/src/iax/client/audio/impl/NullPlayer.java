package iax.client.audio.impl;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import iax.client.audio.Player;
import iax.client.audio.PlayerException;


/**
 * GSM audio player.
 */
public class NullPlayer extends Player {
	

	/**
	 * Constructor. Initializes player.
	 * @throws PlayerException
	 */
	public NullPlayer() throws PlayerException {
		super(Player.JITTER_BUFFER);
			
	}
	
	public void play() {

	}

	public void stop() {

	}

	public void write(long timestamp, byte[] audioData, boolean absolute) {

	}

}
