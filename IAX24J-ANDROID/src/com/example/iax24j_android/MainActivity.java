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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TableLayout;

public class MainActivity extends Activity  implements android.view.View.OnClickListener, PeerListener {
    public static Peer mypeer;
    Button btnOk;
    Button btnAns;
    boolean bound = false;
    ServiceConnection sConn;
    Intent intent;
    private iaxConnection serviceConnection = null;
    final String LOG_TAG = "MAINACTIVITY";
    iaxConnection ic;
    ImageButton ione;
    ImageButton itwo;
    ImageButton ithree;
    ImageButton ifour;
    ImageButton ifive;
    ImageButton isix;
    ImageButton iseven;
    ImageButton ieight;
    ImageButton inine;
    ImageButton izero;
    ImageButton istar;
    ImageButton ipound;
    ImageButton iphone;
    ImageButton iphonedel;
    ImageButton iphonecont;
    boolean callinit = false;
    //
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(com.example.iax24j_android.R.layout.dialpad);
		
		ione = (ImageButton) findViewById(R.id.one);
		itwo = (ImageButton) findViewById(R.id.two);
		ithree = (ImageButton) findViewById(R.id.three);
		ifour = (ImageButton) findViewById(R.id.four);
		ifive = (ImageButton) findViewById(R.id.five);
		isix = (ImageButton) findViewById(R.id.six);
		iseven = (ImageButton) findViewById(R.id.seven);
		ieight = (ImageButton) findViewById(R.id.eight);
		inine = (ImageButton) findViewById(R.id.nine);
		izero = (ImageButton) findViewById(R.id.zero);
		istar = (ImageButton) findViewById(R.id.star);
		ipound = (ImageButton) findViewById(R.id.pound);
		iphone = (ImageButton) findViewById(R.id.phone);
		iphonedel = (ImageButton) findViewById(R.id.phonedel);
		iphonecont = (ImageButton) findViewById(R.id.phonecont);		
		
		ione.setOnClickListener(this);
		itwo.setOnClickListener(this);
		ithree.setOnClickListener(this);
		ifour.setOnClickListener(this);
		ifive.setOnClickListener(this);
		isix.setOnClickListener(this);
		iseven.setOnClickListener(this);
		ieight.setOnClickListener(this);
		inine.setOnClickListener(this);
		izero.setOnClickListener(this);
		istar.setOnClickListener(this);
		ipound.setOnClickListener(this);
		iphone.setOnClickListener(this);
		iphonedel.setOnClickListener(this);
		iphonecont.setOnClickListener(this);
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
	       /*

	       

	        
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
				     
				     btnAns.setOnClickListener(oclBtnAns);		*/     
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
	public void onClick(View v) {
		String num = "";
		//System.out.println(""+R.id.one);
		switch (v.getId()) {
		case R.id.one:
			 num = "1";
			break;
		case R.id.two:
			 num = "2";
			break;
		case R.id.three:
			 num = "3";
			break;
		case R.id.four:
			 num = "4";
			break;
		case R.id.five:
			 num = "5";
			break;
		case R.id.six:
			 num = "6";
			break;
		case R.id.seven:
			 num = "7";
			break;
		case R.id.eight:
			 num = "8";
			break;
		case R.id.nine:
			 num = "9";
			break;
		case R.id.zero:
			 num = "0";
			break;
		case R.id.star:
			 num = "*";
			break;
		case R.id.pound:
			 num = "#";
			break;
		case R.id.phone:
			if (callinit) {
				callinit=false;
				EditText dtmfBox1=(EditText) findViewById(R.id.digits);
				ic.hungup(dtmfBox1.getText().toString());				
			} else {
				callinit=true;
				EditText dtmfBox1=(EditText) findViewById(R.id.digits);
				ic.call(dtmfBox1.getText().toString());
			}
			break;
		case R.id.phonedel:
			EditText dtmfBox2=(EditText) findViewById(R.id.digits);
			dtmfBox2.setText(dtmfBox2.getText().subSequence(0, dtmfBox2.getText().length()-1));
			break;
		case R.id.phonecont:
			  
			break;			
		}
		
		EditText dtmfBox=(EditText) findViewById(R.id.digits);
		dtmfBox.setText(dtmfBox.getText()+num);
		// TODO Auto-generated method stub
		
	}



	
	 
	
}
