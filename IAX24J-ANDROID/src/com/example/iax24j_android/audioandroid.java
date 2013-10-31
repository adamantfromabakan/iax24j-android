package com.example.iax24j_android;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import iax.client.protocol.peer.Peer;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;

public class audioandroid {
    MediaPlayer mediaPlayer;
    AudioManager am;
	static AudioTrack track;
	private static Context context;
	private static final int SAMPLE_RATE = 8000;
	private static final int SAMPLES_PER_FRAME = 160;
	private static final int FRAME_LEN = 20; /* ms */
	private static int minPlayBuffer = 0;
	private static Thread playThread = null;
	private static List<short[]> playQ = Collections.synchronizedList(new LinkedList<short[]>());
	private static final int UNUSED_CACHE_MAX = 10;
	private static List<short[]> unusedQ = Collections.synchronizedList(new LinkedList<short[]>());
	/**
	 * @param args
	 */
	public static void listern(int vl) {
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
	
  	  AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
 	  audioManager.setRouting(AudioManager.MODE_IN_CALL, AudioManager.ROUTE_EARPIECE,AudioManager.ROUTE_ALL);
 	  audioManager.setMode(AudioManager.MODE_IN_CALL);
 	  
		final Runnable tplay = new Runnable() {
			public void run() {
				playTick();
			}
		};

		playThread = new Thread(tplay, "play_thread");
		playThread.start();
		//System.out.println("track.play");
		Log.d("IAX2Audio", "play_thread");
		track.play();
	}
	 private static void playTick() {
			final long delta = FRAME_LEN;

	        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
			
			while (playThread != null) {
				playbackTime();
				try {
					Thread.sleep(delta);
				} catch (final InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		private static void playbackTime() {
			while (playQ.isEmpty() == false) {
				short[] buf = playQ.remove(0);
				writeBuff(buf);
				if (unusedQ.size() < UNUSED_CACHE_MAX) {
					/* Cache a max of 10 buffers */
					unusedQ.add(buf);
				}
			}
		}
		
		private static void writeBuff(short[] buf) {
			if (track == null) {
				Log.w("IAX2Audio", "write() without an AudioTrack");
				return;
			}

			int written = 0;
			while (written < buf.length) {
				int res;

				res = track.write(buf, written, buf.length - written);
				switch (res) {
				case AudioTrack.ERROR_INVALID_OPERATION:
					Log.e("IAX2Audio", "Invalid write()");
					return;
				case AudioTrack.ERROR_BAD_VALUE:
					Log.e("IAX2Audio", "Bad arguments to write()");
					return;
				}

				written += res;
			}
		}

}
