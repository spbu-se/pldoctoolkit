package org.spbu.pldoctoolkit.registry;

import org.eclipse.core.resources.IFile;

public class RegisteredLocation {
	public static final String CORE = "Core";
	public static final String PRODUCT = "Product";
	
	public static final String INF_ELEMENT = "InfElement";
	public static final String INF_PRODUCT = "InfProduct";
	public static final String DICTIONARY = "Dictionary";
	public static final String FINAL_INF_PRODUCT = "FinalInfProduct";
	
	private final String context;
	private final String name;
	private final String id;
	private final IFile file;
	private final int lineNumber;
	
	public RegisteredLocation(String context, String name, String id, IFile file, int lineNumber) {
		this.context = context;
		this.name = name;
		this.id = id;
		this.file = file;
		this.lineNumber = lineNumber;
	}
	
	public String getContext() {
		return context;
	}

	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public IFile getFile() {
		return file;
	}

	public int getLineNumber() {
		return lineNumber;
	}
	
	public String toString() {
		return context + "/" + name + "/" + id + " @ " + file + ":" + lineNumber;
	}
}
