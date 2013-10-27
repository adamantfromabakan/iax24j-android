package com.example.iax24j_android;

import java.io.IOException;


import iax.client.audio.AudioFactory;
import iax.client.audio.impl.NullAudioFactory;
import iax.client.protocol.call.Call;
import iax.client.protocol.peer.Peer;
import iax.client.protocol.peer.PeerListener;
import iax.client.protocol.user.command.NewCall;
import iax.client.protocol.user.command.UserCommandFacade;

import com.example.iax24j_android.iaxConnection;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity  implements PeerListener,OnPreparedListener {
	//, android.view.View.OnClickListener
	private audioandroid audioInterface = new audioandroid();
    public static Peer mypeer;
    Button btnOk;


    //
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
	       

	        
		btnOk = (Button) findViewById(R.id.button1);
		//mypeer = new Peer(this,"201","q1kdid93","90.189.119.84",14570, true,1000);
		 
		OnClickListener oclBtnOk = new OnClickListener() {
		       @Override
		       public void onClick(View v) {
 	 
		       }
		     };
		     
		     btnOk.setOnClickListener(oclBtnOk);
	       
		


	        
		//	UserCommandFacade.newCall(mypeer, "999");
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

	@Override
	public void onPrepared(MediaPlayer arg0) {
		// TODO Auto-generated method stub
		
	}
	
/*	@Override
	public void onClick(View v) {
		  
		 //int i = (int) Math.round(v.getId()/10000000);
		int i =v.getId();
		System.out.println(i);
		 switch (i) {
	     case 2131230721 :
		       iaxConnection ic = new iaxConnection();
		       ic.connect();
		       try {
		           Thread.sleep(10000);
		         } catch (InterruptedException ie) {
		           ie.printStackTrace();
		         }
		       
		       ic.call("999");
	       break;
		 }
	}*/
	
}
