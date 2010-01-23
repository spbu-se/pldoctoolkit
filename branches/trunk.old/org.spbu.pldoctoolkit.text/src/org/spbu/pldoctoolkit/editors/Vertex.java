package org.spbu.pldoctoolkit.editors;

import java.util.Iterator;
import java.util.LinkedList;

public class Vertex {

	/*
	 * Vertix = tag in rng schem this class contains all necessary information
	 * about tag such as type, attribute, parent tag, children tags
	 */
	int type;
	Vertex parent;
	String attr;
	LinkedList<Vertex> child;
	Vertex next;

	public Vertex(int t, Vertex p, String a) {
		type = t;
		parent = p;
		attr = a;
		child = new LinkedList<Vertex>();
		next = p;
	}

	public Vertex(int t, Vertex p) {
		this(t, p, "");
	}

	public Vertex next() {
		if (parent == null)
			return null;
		if (next == parent) {
			for (Iterator it = parent.child.iterator(); it.hasNext();) {
				Vertex t = (Vertex) it.next();
				if (t == this) {
					if (it.hasNext()) {
						next = (Vertex) it.next();
					} else {
						next = null;
					}
				}
			}
		}
		return next;
	}

	public void addChild(Vertex v) {
		this.child.add(v);
	}
}
