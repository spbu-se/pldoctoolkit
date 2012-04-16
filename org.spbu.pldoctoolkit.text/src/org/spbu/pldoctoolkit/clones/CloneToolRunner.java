package org.spbu.pldoctoolkit.clones;

import java.io.File;
import java.io.IOException;

import org.spbu.pldoctoolkit.clones.ReaderThread.IStopper;

public final class CloneToolRunner {

	private static final String CMD = "clones_run.bat";

	public static void runTool(String cloneToolDir) {
		try {
			Runtime r = Runtime.getRuntime();
			System.out.println("CMD:"+cloneToolDir+CMD);
			final Process p = r.exec(cloneToolDir + CMD);
			IStopper readStopper = new IStopper(){
				@Override
				public boolean stop(String line) {
					boolean needStop = needStop(line);
					if (needStop ){
						try {
							p.getOutputStream().write(' ');
							p.getOutputStream().flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					return needStop;
				}
			};
			new ReaderThread(p.getInputStream(), readStopper ,false, "clone_tool" );
//			ReaderThread rt2 = new ReaderThread(p.getErrorStream(),readStopper,true, name+"_err" );
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private static boolean needStop(String line){
		//TODO Need delete second condition
		return line.startsWith("Total Time Taken =")||line.startsWith("Total Groups 0");
	}

}
