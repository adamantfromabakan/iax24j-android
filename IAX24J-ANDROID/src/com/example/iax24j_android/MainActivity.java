package com.example.iax24j_android;

import iax.client.audio.AudioFactory;
import iax.client.audio.impl.NullAudioFactory;
import iax.client.protocol.call.Call;
import iax.client.protocol.peer.Peer;
import iax.client.protocol.peer.PeerListener;
import iax.client.protocol.user.command.NewCall;
import iax.client.protocol.user.command.UserCommandFacade;

import com.example.iax24j_android.iaxConnection;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MainActivity extends Activity  implements PeerListener {
    public static Peer mypeer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
	       iaxConnection ic = new iaxConnection();
	       ic.connect();
	       try {
	           Thread.sleep(10000);
	         } catch (InterruptedException ie) {
	           ie.printStackTrace();
	         }
	       
	       ic.call("999");

	        //mypeer = new Peer(this,"201","q1kdid93","90.189.119.84",14570, true,10000);
			//UserCommandFacade.newCall(mypeer, "999");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void hungup(String calledNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void recvCall(String callingName, String callingNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void registered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void waiting() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unregistered() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void exited() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void answered(String calledNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playWaitTones(String calledNumber) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

}
