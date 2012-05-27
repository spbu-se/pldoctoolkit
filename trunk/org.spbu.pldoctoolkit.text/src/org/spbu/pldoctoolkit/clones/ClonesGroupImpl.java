package org.spbu.pldoctoolkit.clones;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public final class ClonesGroupImpl implements IClonesGroup {
	
	private static final int MAX_LENGTH_OF_TEXT_4_PRINT = 70;
	private final List<ICloneInst> insts = new ArrayList<ICloneInst>();
	private boolean instsIsSorted = true;
	private int clonesGroupId;
	private int termCount;
	

	public ClonesGroupImpl(int clonesGroupId, int termCount) {
		this.clonesGroupId = clonesGroupId;
		this.termCount = termCount;
	}

	public ClonesGroupImpl() {
		this.clonesGroupId = -1;
		this.termCount = -1;
	}

	@Override
	public List<ICloneInst> getInstances() {
		if (!instsIsSorted){
			Collections.sort(insts, new Comparator<ICloneInst>(){
				@Override
				public int compare(ICloneInst o1, ICloneInst o2) {
					return o1.getAbsoluteStartPosition()-o2.getAbsoluteStartPosition();
				}});
			instsIsSorted = true;
		}
		return insts;
	}

	@Override
	public String getName() {
		if (insts.isEmpty())
			return "Empty clone group";
		String cloneText = insts.get(0).getCloneText();
		if (cloneText.length() > MAX_LENGTH_OF_TEXT_4_PRINT)
			cloneText = cloneText.substring(0, MAX_LENGTH_OF_TEXT_4_PRINT)
					+ "...";
		if (termCount == -1) {
			termCount = 0;
			for (StringTokenizer tok = new StringTokenizer(insts.get(0)
					.getCloneText(), ClonesGroupsFilter.DELIMITERS_OF_DOCBOOK); tok
					.hasMoreTokens(); tok.nextToken(), termCount++)
				;
		}
		return clonesGroupId+") Text: \""+cloneText + "\" found "+insts.size() + " times and contains "+termCount+" term(s).";
	}
	@Override
	public void addCloneInst(ICloneInst cloneInst) {
		insts.add(cloneInst);
		instsIsSorted = false;
	}

	@Override
	public void setGroupId(int id) {
		clonesGroupId = id;
	}

	@Override
	public int getId() {
		return clonesGroupId;
	}

}
