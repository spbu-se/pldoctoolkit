package org.spbu.pldoctoolkit.clones;

import java.util.ArrayList;
import java.util.List;

public final class ClonesGroupImpl implements IClonesGroup {
	
	private static final int MAX_LENGTH_OF_TEXT_4_PRINT = 70;
	private final List<ICloneInst> insts = new ArrayList<ICloneInst>();
	

	@Override
	public List<ICloneInst> getInstances() {
		return insts;
	}

	@Override
	public String getName() {
		if (insts.isEmpty())
			return "Empty clone group";
		String cloneText = insts.get(0).getCloneText();
		if (cloneText.length()>MAX_LENGTH_OF_TEXT_4_PRINT)
			cloneText = cloneText.substring(0, MAX_LENGTH_OF_TEXT_4_PRINT)+"...";
		return "Text: \""+cloneText + "\" found "+insts.size() + " times.";
	}
	@Override
	public void addCloneInst(ICloneInst cloneInst) {
		insts.add(cloneInst);
	}

}
