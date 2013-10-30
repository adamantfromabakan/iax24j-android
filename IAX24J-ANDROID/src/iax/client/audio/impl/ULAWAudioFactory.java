package iax.client.audio.impl;

import iax.client.audio.AudioFactory;
import iax.client.audio.Player;
import iax.client.audio.PlayerException;
import iax.client.audio.Recorder;
import iax.client.audio.RecorderException;



public class ULAWAudioFactory implements AudioFactory{
    /**
     * ULAW codec.
     */
    public static final long ULAW_V = 4;
    /**
     * Voice frame with codec ULAW
     */
    public static final int ULAW_SC = 4;
    
	@Override
	public Player createPlayer() throws PlayerException {
		// TODO Auto-generated method stub
		return new ULAWPlayer();
	}

	@Override
	public Recorder createRecorder() throws RecorderException {
		// TODO Auto-generated method stub
		return new ULAWRecorder();
	}

	@Override
	public long getCodec() {
		// TODO Auto-generated method stub
		return ULAW_V;
	}

	@Override
	public int getCodecSubclass() {
		// TODO Auto-generated method stub
		return ULAW_SC;
	}

}
