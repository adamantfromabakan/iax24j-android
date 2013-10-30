package iax.client.audio.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.util.Log;
import iax.client.audio.Player;
import iax.client.audio.PlayerException;
import iax.client.audio.codecs.ULAW;

public class ULAWPlayer extends Player{
    MediaPlayer mediaPlayer;
    AudioManager am;
	AudioTrack track;
	Context context;
	private static final int SAMPLE_RATE = 8000;
	private static final int SAMPLES_PER_FRAME = 160;
	private static final int FRAME_LEN = 20; /* ms */
	private static int minPlayBuffer = 0;
	private Thread playThread = null;
	private List<short[]> playQ = Collections.synchronizedList(new LinkedList<short[]>());
	private static final int UNUSED_CACHE_MAX = 10;
	private List<short[]> unusedQ = Collections.synchronizedList(new LinkedList<short[]>());
	
	public ULAWPlayer() throws PlayerException {
		super(Player.JITTER_BUFFER);
		// TODO Auto-generated constructor stub
		this.minPlayBuffer = AudioTrack.getMinBufferSize(SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,AudioFormat.ENCODING_PCM_16BIT);
		openSourceDataLine();
		
	}

	private void openSourceDataLine() {
		Log.d("IAX2Audio", "openSourceDataLine()");
		// TODO Auto-generated method stub
		Log.d("IAX2Audio", "startPlay()");
		final int minPlayBuffer = AudioTrack.getMinBufferSize(
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);

		try {
			this.track = new AudioTrack(AudioManager.STREAM_VOICE_CALL,
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT,
				(640 > minPlayBuffer) ? 640 : minPlayBuffer,
				AudioTrack.MODE_STREAM);
			} catch (final IllegalArgumentException e) {
				Log.e("IAX2Audio", "Failed to create AudioTrack");
					e.printStackTrace();
						return;
			}

		Log.d("IAX2Audio", "AudioManager before");
		AudioManager aMan = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
		Log.d("IAX2Audio", "AudioManager after");
		aMan.setRouting(AudioManager.MODE_IN_CALL, 
				AudioManager.ROUTE_EARPIECE, 
				AudioManager.ROUTE_ALL);
		aMan.setMode(AudioManager.MODE_IN_CALL);

		final Runnable tplay = new Runnable() {
			public void run() {
				playTick();
			}
		};

		this.playThread = new Thread(tplay, "play_thread");
		this.playThread.start();
		//System.out.println("track.play");
		Log.d("IAX2Audio", "play_thread");
	}

	public void play() {
		
		track.play();
	}

	public void stop() {
		Log.d("IAX2Audio", "stopPlay()");

		if (this.track != null) {
			this.track.stop();
		}

		try {
			if (this.playThread != null) {
				final Thread t = this.playThread;
				/* Setting this to null is the signal to the thread to exit. */
				this.playThread = null;
				t.join();
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		if (this.track != null) {
			this.track.release();
			this.track = null;
		}
	}

	public void write(long timestamp, byte[] audioData, boolean absolute) {
		Log.d("IAX2Audio", "write()");
		short[] qBuf = null;
		
		/* Try to used a cached buffer if we can */
		
		while (qBuf == null && this.unusedQ.isEmpty() == false) {
			qBuf = this.unusedQ.remove(0);
			if (qBuf.length != audioData.length) {
				qBuf = null;
			}
		}
		
		if (qBuf == null) {
			/* Did not find a suitable cached buffer */
			Log.d("IAX2Audio", "unused buffer cache miss");
			qBuf = new short[audioData.length];
		}
		
		for (int i = 0; i < audioData.length; i++) {
			qBuf[i] = ULAW.ulaw2linear(audioData[i]);
		}
		
		this.playQ.add(qBuf);

	}	
	
	private void playTick() {
		final long delta = FRAME_LEN;

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
		
		while (this.playThread != null) {
			playbackTime();
			try {
				Thread.sleep(delta);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void playbackTime() {
		while (this.playQ.isEmpty() == false) {
			short[] buf = this.playQ.remove(0);
			writeBuff(buf);
			if (this.unusedQ.size() < this.UNUSED_CACHE_MAX) {
				/* Cache a max of 10 buffers */
				this.unusedQ.add(buf);
			}
		}
	}
	
	private void writeBuff(short[] buf) {
		if (this.track == null) {
			Log.w("IAX2Audio", "write() without an AudioTrack");
			return;
		}

		int written = 0;
		while (written < buf.length) {
			int res;

			res = this.track.write(buf, written, buf.length - written);
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
