package org.spbu.pldoctoolkit.clones;

import java.util.List;

public interface IClonesGroup {

	String getName();
	List<ICloneInst> getInstances();
	void addCloneInst(ICloneInst cloneInst);

}
