package com.example.iax24j_android;

import java.io.IOException;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.IBinder;
import android.util.Log;
import iax.client.audio.AudioFactory;
import iax.client.audio.impl.NullAudioFactory;
import iax.client.audio.impl.ULAWAudioFactory;
import iax.client.protocol.call.Call;
import iax.client.protocol.peer.Peer;
import iax.client.protocol.peer.PeerListener;
import iax.client.protocol.user.command.NewCall;
import iax.client.protocol.user.command.UserCommandFacade;

 
/**
 *
 * @author john
 */
public class iaxConnection extends Service implements PeerListener,OnPreparedListener {
    public static Peer mypeer;
    public static boolean registered = false;
	public audioandroid aa;
	
	
    public void hungup(String calledNumber) {
    	System.out.println("Hungup from "+calledNumber);
    }

    public void recvCall(String callingName, String callingNumber) {
    	UserCommandFacade.answerCall(mypeer, callingNumber);
        System.out.println("New Call From: " + callingNumber);
    }

    public void registered() {
        System.out.println("Registered");
        registered = true;
    }
        
    public void waiting() {
        System.out.println("Wating");
    }

    public void unregistered() {}

    public void exited() {
        System.out.println("Exited");
    }

    public void answered(String calledNumber) {
    	System.out.println("Answered from "+calledNumber);

    	
    	 /* new Thread(new Runnable() {
    		    public void run() {
    		    	aa = new audioandroid();
    		    	aa.listern(0);
    		    }
    		  }).start();
    		*/
    	
        //mediaPlayer = MediaPlayer.create(this,android.R.raw);
       // mediaPlayer.start();
       /* mediaPlayer = new MediaPlayer();
        try {
			mediaPlayer.setDataSource(DATA_STREAM);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        //Log.d(LOG_TAG, "prepareAsync");
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.prepareAsync();*/
    	/*am = (AudioManager) getSystemService(AUDIO_SERVICE);

        am.setMode(AudioManager.MODE_IN_CALL);
        am.startBluetoothSco();
        am.setBluetoothScoOn(true);
*/
       /* short[] soundData = new short [8000*200];
        for (int iii = 0; iii < 20*8000; iii++) {
            soundData[iii] = 32767;
            iii++;
            soundData[iii] = -32768;
        }
        System.out.println(soundData.length);*/
        /*AudioTrack audioTrack = new AudioTrack(AudioManager.STREAM_SYSTEM,
                8000, AudioFormat.CHANNEL_OUT_MONO,
                AudioFormat.ENCODING_PCM_8BIT, soundData.length
                * Short.SIZE, AudioTrack.MODE_STATIC);
        audioTrack.write(soundData, 0, soundData.length);
        audioTrack.play();*/
        
        /*int minSize =AudioTrack.getMinBufferSize( 44100, AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT );        
        track = new AudioTrack( AudioManager.STREAM_MUSIC, 44100, 
                                          AudioFormat.CHANNEL_CONFIGURATION_MONO, AudioFormat.ENCODING_PCM_16BIT, 
                                          minSize, AudioTrack.MODE_STREAM);
        track.play();*/

        /*final int minPlayBuffer = AudioTrack.getMinBufferSize(
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

AudioManager aMan = (AudioManager) this.context.getSystemService(Context.AUDIO_SERVICE);
aMan.setRouting(AudioManager.MODE_IN_CALL, 
AudioManager.ROUTE_EARPIECE, 
AudioManager.ROUTE_ALL);
aMan.setMode(AudioManager.MODE_IN_CALL);

/*final Runnable tplay = new Runnable() {
public void run() {
playTick();
}
};

this.playThread = new Thread(tplay, "play_thread");
this.playThread.start();*/

//track.play();

    }
	/*private void playTick() {
		final long delta = FRAME_LEN;

        android.os.Process.setThreadPriority(android.os.Process.THREAD_PRIORITY_AUDIO);
		
		while (this.playThread != null) {
			//playbackTime();
			try {
				Thread.sleep(delta);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}*/
	


    public void playWaitTones(String calledNumber) {}      
    
    /** Creates a new instance of iaxConnection */
    public iaxConnection() {
    }
    
    public void connect() {
    	//NullAudioFactory aFactory= new NullAudioFactory();
    	ULAWAudioFactory aFactory= new ULAWAudioFactory();
        mypeer = new Peer(this,"201","q1kdid93","90.189.119.84",14570, true,1000,aFactory);
        System.out.println(mypeer.getState());
 
    }
    public void call(String number) {
        try {
            if (registered) {
            	//System.out.println("Call");
               /* NewCall call = new NewCall(mypeer,number);
                call.execute();
                NullAudioFactory aFactory= new NullAudioFactory();
                Call c = new Call(mypeer,201, aFactory);
                c.startCall(number);*/
                UserCommandFacade.newCall(mypeer, number);
                
                //System.out.println(mypeer.getState());
                
            } else {
                System.out.println("Not Registered: " + mypeer.getState());
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

	@Override
	public boolean isEnabled() {

		return false;
	}

	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {

	 	   iaxConnection ic = new iaxConnection();
	       ic.connect();
	       try {
	           Thread.sleep(1000);
	         } catch (InterruptedException ie) {
	           ie.printStackTrace();
	         }
	       ic.call("999");
		Log.d("iaxConnection", "onBind()");
		return null;
	}
	
	@Override
	public void onCreate() {

	}

}