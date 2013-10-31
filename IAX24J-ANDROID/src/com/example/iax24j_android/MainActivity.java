package com.example.iax24j_android;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import iax.client.audio.AudioFactory;
import iax.client.audio.impl.NullAudioFactory;
import iax.client.protocol.call.Call;
import iax.client.protocol.peer.Peer;
import iax.client.protocol.peer.PeerListener;
import iax.client.protocol.user.command.NewCall;
import iax.client.protocol.user.command.UserCommandFacade;

import com.example.iax24j_android.iaxConnection;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TableLayout;

public class MainActivity extends Activity  implements PeerListener {
    public static Peer mypeer;
    Button btnOk;
    Button btnAns;
    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    private iaxConnection serviceConnection = null;
    final String LOG_TAG = "MAINACTIVITY";
    iaxConnection ic;


    //
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
  
		/*intent = new Intent(this,iaxConnection.class);
	    sConn = new ServiceConnection() {
			      public void onServiceConnected(ComponentName name, IBinder binder) {
			        Log.d(LOG_TAG, "MainActivity onServiceConnected");
			        bound = true;
			      }

			      public void onServiceDisconnected(ComponentName name) {
			        Log.d(LOG_TAG, "MainActivity onServiceDisconnected");
			        bound = false;
			      }
			    };
			    //startService(intent);
			    bindService(intent, sConn, BIND_AUTO_CREATE);*/
			    
		
 	       ic = new iaxConnection();
	       ic.connect();
	       try {
	           Thread.sleep(1000);
	         } catch (InterruptedException ie) {
	           ie.printStackTrace();
	         }


	       

	        
		btnOk = (Button) findViewById(R.id.button1);
		 
		OnClickListener oclBtnOk = new OnClickListener() {
		       @Override
		       public void onClick(View v) {
		   		
			       ic.call("999");
			       //ic.hungup("999");
			       //ic.waiting();

		       }
		     };
		     
		     btnOk.setOnClickListener(oclBtnOk);
	  
		btnAns = (Button) findViewById(R.id.button2);
				 
		OnClickListener oclBtnAns = new OnClickListener() {
				       @Override
				       public void onClick(View v) {
				   		
				    	   //ic.recvCall("202", "202");
				    	   ic.call("202");
				       }
				     };
				     
				     btnAns.setOnClickListener(oclBtnAns);		     
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
