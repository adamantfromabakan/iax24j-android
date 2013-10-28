package com.example.iax24j_android;

import iax.client.protocol.peer.Peer;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;

public class audioandroid {
    public static Peer mypeer;
    public static boolean registered = false;
    MediaPlayer mediaPlayer;
    AudioManager am;
	private AudioTrack track = null;
    //final String DATA_STREAM = "udp://90.189.119.84:14570";
	private static final int SAMPLE_RATE = 8000;
	private static final int SAMPLES_PER_FRAME = 160;
	private static final int FRAME_LEN = 20; /* ms */
	private Thread playThread = null;
	private static Context context = null;
	/**
	 * @param args
	 */
	public static void listern(int vl) {
		// TODO Auto-generated method stub
		final int minPlayBuffer = AudioTrack.getMinBufferSize(
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);

	AudioTrack track = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT,
				(640 > minPlayBuffer) ? 640 : minPlayBuffer,
				AudioTrack.MODE_STREAM);
		

AudioManager aMan = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
aMan.setRouting(AudioManager.MODE_IN_CALL, 
AudioManager.ROUTE_EARPIECE, 
AudioManager.ROUTE_ALL);
aMan.setMode(AudioManager.MODE_IN_CALL);
track.play();
	}

}
