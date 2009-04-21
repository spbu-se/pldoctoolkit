package org.spbu.pldoctoolkit.editors;
import java.util.HashMap;
import java.util.HashSet;

public class AttrTree {
	HashSet<String> attributes;
	HashSet<HashSet<AttrTree>> choices;
	HashSet<String> canReach;
	HashMap<HashSet<AttrTree>, HashSet<String>> reachFromCh;
	
	public AttrTree(){
		attributes = new HashSet<String>();
		choices = new HashSet<HashSet<AttrTree>>();
		canReach = new HashSet<String>();
		reachFromCh = new HashMap<HashSet<AttrTree>, HashSet<String>>();
	}

}
