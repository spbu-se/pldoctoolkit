package org.spbu.pldoctoolkit.registry;

import org.eclipse.core.resources.IFile;

public interface IProjectContent {
	void remove(IFile file);
	void add(IFile file);
	void change(IFile file);
}
