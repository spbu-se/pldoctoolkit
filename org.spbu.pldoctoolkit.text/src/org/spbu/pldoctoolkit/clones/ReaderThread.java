package org.spbu.pldoctoolkit.clones;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.HashSet;

final class ReaderThread implements Runnable {
	private final BufferedReader in;
	private volatile boolean stopped;
	private final String processName;
	private final IStopper stopper;

	ReaderThread(InputStream inputStream, IStopper stopCond, String processName) {
		this(inputStream, stopCond,true, processName);
	}

	ReaderThread(InputStream inputStream, IStopper stopCond, boolean isDaemon, String processName) {
		assert inputStream != null;
		this.processName = processName;
		this.stopper = stopCond;
		BufferedReader inTmp=null;
		try {
			inTmp = new BufferedReader(new InputStreamReader(inputStream,"ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		in = inTmp;
		Thread myThread = new Thread(this, "Process Reader "+ processName);
		myThread.setDaemon(isDaemon);
		myThread.start();
	}

	@Override
	public void run() {
		if (in != null) {
			String line = null;
			while (!stopped) {
				try {
					line = in.readLine();
					if (line != null) {
						System.out.println("PROCESS "+processName+": " + line);
						if (stopper.stop(line))
							this.stop();
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	void stop() {
		stopped = true;
	}
	
	static interface IStopper{

		boolean stop(String line);
		
	}

//	synchronized void addStopListener(StopListenerI stopListener) {
//		listeners.add(stopListener);
//	}
}