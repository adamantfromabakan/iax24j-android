package iax.client.audio.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import iax.client.audio.codecs.ULAW;

import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.util.Log;
import iax.client.audio.AudioListener;
import iax.client.audio.Recorder;
import iax.client.audio.RecorderException;

public class ULAWRecorder extends Recorder{
    MediaPlayer mediaPlayer;
    AudioManager am;
	AudioTrack track;
	Context context;
	AudioRecord record;
	private static final int SAMPLE_RATE = 8000;
	private static final int SAMPLES_PER_FRAME = 160;
	private static final int FRAME_LEN = 20; /* ms */
	private static int minRecBuffer = 0;
	private Thread recThread = null;
	private List<short[]> playQ = Collections.synchronizedList(new LinkedList<short[]>());
	private static final int UNUSED_CACHE_MAX = 10;
	private List<short[]> unusedQ = Collections.synchronizedList(new LinkedList<short[]>());
	private byte[] ulawBuf = new byte[SAMPLES_PER_FRAME];
	
	public ULAWRecorder() throws RecorderException {
		super();
		this.minRecBuffer = AudioRecord.getMinBufferSize(
				SAMPLE_RATE,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT);
		recordSourceDataLine();
		
	}
	
	private void recordSourceDataLine() {
		Log.d("ULAWRecorder", "recordSourceDataLine()");
		try {
			Log.d("ULAWRecorder", "AudioRecord before");
			this.record = new AudioRecord(MediaRecorder.AudioSource.MIC,SAMPLE_RATE,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,	AudioFormat.ENCODING_PCM_16BIT,
					(1600 > minRecBuffer) ? 1600 : minRecBuffer);
			Log.d("ULAWRecorder", "AudioRecord after");
			this.record.startRecording();
		} catch (IllegalArgumentException e) {
			Log.e("ULAWRecorder", "Failed to create AudioRecord: " +
					e.getMessage());
		}
		
		final Runnable trec = new Runnable() {
			public void run() {
				recTick();
			}
		};

		this.recThread = new Thread(trec, "rec_thread");
		this.recThread.start();
		
		
	}
	public void stop(){
		if (this.record != null) {
			this.record.stop();
		}
		
		try {
			if (this.recThread != null) {
				final Thread t = this.recThread;
				/* Setting this to null is the signal to the thread to exit. */
				this.recThread = null;
				t.join();
			}
		} catch (final InterruptedException e) {
			e.printStackTrace();
		}

		//this.as = null;
	
		if (this.record != null) {
			this.record.release();
			this.record = null;
		}
		
		//this.timestamp = 0;
	}

	public void record(AudioListener al) {
	}
	
	private void recTick() {
		short[] slinBuf = new short[SAMPLES_PER_FRAME];
		
        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
		
		while (this.recThread != null) {
			int soFar = 0;
			boolean error = false;
			
			while (soFar < slinBuf.length) {
				final int res = this.record.read(slinBuf, soFar, slinBuf.length - soFar);

				switch (res) {
				case AudioTrack.ERROR_INVALID_OPERATION:
					Log.e("ULAWRecorder", "Invalid read()");
					error = true;
					break;
				case AudioTrack.ERROR_BAD_VALUE:
					Log.e("ULAWRecorder", "Bad arguments to read()");
					error = true;
					break;
				}
				
				if (error) {
					break;
				}
				
				soFar += res;
			}
			
			if (error == false) {
				for (int i = 0; i < slinBuf.length; i++) {
					this.ulawBuf[i] = ULAW.linear2ulaw(slinBuf[i]);
				}
				
				try {
					Log.e("ULAWRecorder", "send()");
					//this.as.send();
				} catch (Exception e) {
					Log.e("ULAWRecorder", "IOError in sending: " + e.getMessage());
				}
			}
		}
	}

}
