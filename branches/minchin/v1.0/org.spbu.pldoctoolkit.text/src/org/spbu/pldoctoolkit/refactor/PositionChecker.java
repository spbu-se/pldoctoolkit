package org.spbu.pldoctoolkit.refactor;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class PositionChecker implements IChecker {
	private int line;
	private int offset;
	
	public PositionChecker(int line, int offset)
	{
		this.line = line;
		this.line = line;
	}

	public boolean check(Node node) {
		//((Element)node).
		return false;
	}
}
