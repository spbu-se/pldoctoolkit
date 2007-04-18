package org.spbu.pldoctoolkit.registry;

import org.eclipse.core.resources.IFile;

public class RegisteredLocation {
	private IFile file;
	private int lineNumber;
	
	public RegisteredLocation(IFile file, int lineNumber) {
		this.file = file;
		this.lineNumber = lineNumber;
	}

	public IFile getFile() {
		return file;
	}

	public int getLineNumber() {
		return lineNumber;
	}
}
