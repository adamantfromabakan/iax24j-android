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
	
    MediaPlayer mediaPlayer;
    AudioManager am;
	AudioTrack track;
	Context context;
	private static final int SAMPLE_RATE = 8000;
	private static final int SAMPLES_PER_FRAME = 160;
	private static final int FRAME_LEN = 20; /* ms */
	/**
	 * Constructor. Initializes player.
	 * @throws PlayerException
	 */
	public NullPlayer() throws PlayerException {
		super(Player.JITTER_BUFFER);
		final int minPlayBuffer = AudioTrack.getMinBufferSize(
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);

			track = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT,
				(640 > minPlayBuffer) ? 640 : minPlayBuffer,
				AudioTrack.MODE_STREAM);

			
	}
	
	public void play() {
		track.play();
	}

	public void stop() {
		track.stop();
	}

	public void write(long timestamp, byte[] audioData, boolean absolute) {
		AudioManager aMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
		aMan.setRouting(AudioManager.MODE_IN_CALL,AudioManager.ROUTE_EARPIECE,AudioManager.ROUTE_ALL);
		aMan.setMode(AudioManager.MODE_IN_CALL);
	}

}
