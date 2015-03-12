package org.spbu.pldoctoolkit.refactor;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.core.runtime.IProgressMonitor;

// dluciv
public class VariationDetectAndBrowseUtil {
	
	private VariationDetectAndBrowseUtil(){};

	// TODO: make configurable and cross-platform as possible
	protected static String varDecCmd = "d:\\Python3.x86\\pythonw.exe D:\\VCSWF\\bitbucket\\pyclonestats\\element_miner_ui.py";
	
	protected static void runTool(final IProgressMonitor mon, final String cmd) {
		Thread vbr = new Thread() {
			@Override
	         public void run() {
				Runtime r = Runtime.getRuntime();		
				try {
					Process p;
					p = r.exec(cmd);
					int result = p.waitFor();
					System.out.println("Variation browser exited with code: " + result);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (InterruptedException e) {
					e.printStackTrace();
				} finally {
					if(mon != null)
						mon.done();
				}
	         }
		};
		vbr.start();
	}
	
	
	public static void run(IEditorPart editor) {
		System.out.println("Running py variations detector...");
		IEditorInput ei = editor.getEditorInput();
		IFileEditorInput fei = (IFileEditorInput)ei; 
		final String fn = fei.getFile().getLocation().toString();

		/*
		IRunnableWithProgress op = new IRunnableWithProgress() {
			public void run(IProgressMonitor monitor) throws InvocationTargetException, InterruptedException {
				monitor.beginTask("Running variations detector...", 1);
				runTool(monitor, varDecCmd + " -if " + fn);
			}
		};
		try {
			op.run(null);
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		*/
		runTool(null, varDecCmd + " -if " + fn);
	}	
}
