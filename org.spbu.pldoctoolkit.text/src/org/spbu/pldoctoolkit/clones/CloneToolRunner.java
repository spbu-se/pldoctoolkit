package org.spbu.pldoctoolkit.clones;

import java.io.IOException;

import org.spbu.pldoctoolkit.clones.ReaderThread.IStopper;

public final class CloneToolRunner {

	private static final String CMD = "clones_run.bat";
	private volatile boolean completed;

	public void runTool(String cloneToolDir) {
		try {
			Runtime r = Runtime.getRuntime();
			final Process p = r.exec(cloneToolDir + CMD);
			IStopper readStopper = new IStopper(){
				@Override
				public boolean stop(String line) {
					boolean needStop = needStop(line);
					if (needStop ){
						try {
							p.getOutputStream().write(' ');//clone tool exit
							p.getOutputStream().flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						completed = true;
					}
					return needStop;
				}
			};
			new ReaderThread(p.getInputStream(), readStopper ,false, "clone_tool", false );
//			ReaderThread rt2 = new ReaderThread(p.getErrorStream(),readStopper,true, name+"_err" );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean needStop(String line){
		//TODO Need delete second condition
		return line.startsWith("Total Time Taken =")||line.startsWith("Total Groups 0");
	}

	public boolean completed() {
		return completed;
	}

}
