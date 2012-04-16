package org.spbu.pldoctoolkit.clones;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;

final class ClonesGroupData{
	
	final Collection<CloneInstData> clones = new HashSet<CloneInstData>();
	final int clonesGroupId;
	final int termCount;
	final int countOfClones;	

	public ClonesGroupData(int clonesGroupId, int termCount, int countOfClones) {
		this.clonesGroupId = clonesGroupId;
		this.termCount = termCount;
		this.countOfClones = countOfClones;
	}

	public void addCloneInst(int fileNumber, int startLine, int startColumn,
			int endLine, int endColumn) {
		CloneInstData clone = new CloneInstData(fileNumber, startLine, startColumn, endLine, endColumn);
		clones.add(clone);
	}
	
	public String toString(){
		StringBuffer stBuf = new StringBuffer("ClonesGroup id:"+clonesGroupId+";terms:"+termCount+";clones:"+countOfClones);
		for (CloneInstData clone: clones) {
			stBuf.append("\n"+clone.toString());
		}
		return stBuf.toString();
	}

}
