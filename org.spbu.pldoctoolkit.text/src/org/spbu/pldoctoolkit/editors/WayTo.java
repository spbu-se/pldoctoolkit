package org.spbu.pldoctoolkit.editors;

import java.util.HashSet;
import java.util.Stack;


public class WayTo {
	/*This is an auxiliary class, which contains  information about the way we reach tag*/
	Stack<Vertex> defines;
	Vertex at;
	HashSet<Vertex> were;
	public WayTo(Vertex w){
		at = w;
		defines = new Stack<Vertex>();
		were = new HashSet<Vertex>();
	}
}
